import java.nio.file.Files
import java.nio.file.StandardCopyOption;
import java.nio.file.Paths;

def isDocker = request.getProperties().get("docker")
def artifactId = request.getProperties().get("artifactId")
def dockerFile = "Dockerfile"
def rootDir = Paths.get(request.getOutputDirectory(), artifactId).toFile()
if (!Boolean.valueOf(isDocker)) {
    Files.deleteIfExists(new File(rootDir, dockerFile).toPath()) // Delete Dockerfile for none-docker project
}

def trigger = request.getProperties().get("trigger");
if ("HttpTrigger".equalsIgnoreCase(trigger)) {
    return
}
// Remove origin source dir which contains unused test cases
def sourceFolder = new File(rootDir, "src")
sourceFolder.deleteDir()
// todo: remove the parameter with default values, may need to update maven plugin
def templateMap = [
        "BlobTrigger"           : "-Dfunctions.template=BlobTrigger -Dconnection=\"<connection>\" -Dpath=mycontainer",
        "QueueTrigger"          : "-Dfunctions.template=QueueTrigger -Dconnection=\"<connection>\" -DqueueName=myqueue",
        "TimerTrigger"          : "-Dfunctions.template=TimerTrigger -Dschedule=\"0 * * * * *\"",
        "EventGridTrigger"      : "-Dfunctions.template=EventGridTrigger",
        "EventHubTrigger"       : "-Dfunctions.template=EventHubTrigger -Dconnection=\"<connection>\" -DeventHubName=myeventhub -DconsumerGroup=\$Default",
        "CosmosDBTrigger"       : "-Dfunctions.template=CosmosDBTrigger -DconnectionStringSetting=\"<connection_string_setting>\" -DdatabaseName=\"<databaseName>\" -DcollectionName=\"<collectionName>\" -DleaseCollectionName=\"<leaseCollectionName>\"",
        "ServiceBusQueueTrigger": "-Dfunctions.template=ServiceBusQueueTrigger -Dconnection=\"<connection>\" -DqueueName=mysbqueue",
        "ServiceBusTopicTrigger": "-Dfunctions.template=ServiceBusTopicTrigger -Dconnection=\"<connection>\" -DtopicName=mysbtopic -DsubscriptionName=mysubscription",
];
println("Generating trigger from template, please replace the values with placeholder in annotation with real value if necessary")
def triggerParameter = templateMap.get(trigger)
if (triggerParameter == null) {
    throw new RuntimeException(String.format("Invalid trigger type, supported values are %s and HttpTrigger", String.join(",", templateMap.keySet())))
}
def isWindows = System.properties['os.name'].toLowerCase().contains('windows')
def starter = isWindows ? "cmd.exe" : "/bin/sh"
def switcher = isWindows ? "/c" : "-c"
def command = "mvn azure-functions:add -f ${pom.getAbsolutePath()} -Dfunctions.package=${request.getProperties().get("groupId")} -Dfunctions.name=Function ${triggerParameter} -B"
if (!isWindows) {
    command = command.replace("\$", "\\\$")
}
def output = new StringBuilder()
def proc = [starter, switcher, command].execute();
proc.consumeProcessOutput(output, output)
proc.waitForOrKill(60 * 1000); // wait for 60s
if (proc.exitValue() != 0 || output == null || !output.contains("BUILD SUCCESS")) {
    println("${output}")
    throw new RuntimeException("Failed to generate target trigger, please run `mvn azure-functions:add` manually in project root")
}
