import java.nio.file.Files
import java.nio.file.StandardCopyOption;
import java.nio.file.Paths;

def rootDir = Paths.get(request.getOutputDirectory(), artifactId).toFile()

def isDocker = request.getProperties().get("docker")
def isContainer = request.getProperties().get("container")
if (!Boolean.valueOf(isDocker) && !Boolean.valueOf(isContainer)) {
    def artifactId = request.getProperties().get("artifactId")
    def dockerFile = "Dockerfile"
    Files.deleteIfExists(new File(rootDir, dockerFile).toPath()) // Delete Dockerfile for none-docker project
}

def trigger = request.getProperties().get("trigger");
if ("HttpTrigger".equalsIgnoreCase(trigger) || "Http".equalsIgnoreCase(trigger)) {
    return
}
// Remove origin source dir which contains unused test cases
def sourceFolder = new File(rootDir, "src")
sourceFolder.deleteDir()
// todo: remove the parameter with default values, may need to update maven plugin
def templateMap = [
        "BlobTrigger"                   : "-Dfunctions.template=BlobTrigger -Dconnection=\"<connection>\" -Dpath=mycontainer",
        "QueueTrigger"                  : "-Dfunctions.template=QueueTrigger -Dconnection=\"<connection>\" -DqueueName=myqueue",
        "TimerTrigger"                  : "-Dfunctions.template=TimerTrigger -Dschedule=\"0 * * * * *\"",
        "EventGridTrigger"              : "-Dfunctions.template=EventGridTrigger",
        "EventHubTrigger"               : "-Dfunctions.template=EventHubTrigger -Dconnection=\"<connection>\" -DeventHubName=myeventhub -DconsumerGroup=\$Default",
        "CosmosDBTrigger"               : "-Dfunctions.template=\"CosmosDBTrigger (Bundle V4)\" -DconnectionStringSetting=\"<connection_string_setting>\" -DdatabaseName=\"<databaseName>\" -DcollectionName=\"<collectionName>\" -DleaseCollectionName=\"<leaseCollectionName>\"",
        "ServiceBusQueueTrigger"        : "-Dfunctions.template=ServiceBusQueueTrigger -Dconnection=\"<connection>\" -DqueueName=mysbqueue",
        "ServiceBusTopicTrigger"        : "-Dfunctions.template=ServiceBusTopicTrigger -Dconnection=\"<connection>\" -DtopicName=mysbtopic -DsubscriptionName=mysubscription",
        "RabbitMQTrigger"               : "-Dfunctions.template=RabbitMQTrigger -DconnectionStringSetting=\"<connection>\" -DqueueName=myqueue",
        "KafkaTrigger"                  : "-Dfunctions.template=KafkaTrigger -Dname=kafkaTrigger -Dtopic=topic -DbrokerList=BrokerList -DconsumerGroup=\$Default -DauthenticationMode=PLAIN -Dprotocol=SASLSSL",
        "DurableFunctions"              : "-Dfunctions.template=DurableFunctionsOrchestrator",
        "SqlOutputBinding"              : "-Dfunctions.template=SqlOutputBinding -Dtable=[dbo].[table1] -DSqlConnectionString=\"<connection>\"",
        "SqlInputBinding"               : "-Dfunctions.template=SqlInputBinding  -Dobject=[dbo].[table1] -DSqlConnectionString=\"<connection>\"",
        "DaprPublishOutputBinding"      : "-Dfunctions.template=DaprPublishOutputBinding",
        "DaprServiceInvocationTrigger"  : "-Dfunctions.template=DaprServiceInvocationTrigger",
        "DaprTopicTrigger"              : "-Dfunctions.template=DaprTopicTrigger",
        "SqlTrigger"                    : "-Dfunctions.template=SqlTrigger -Dtable=[dbo].[table1] -DSqlConnectionString=\"<connection>\""
];
def triggerParameter = templateMap.keySet().stream()
        .filter({ key -> key.equalsIgnoreCase(trigger) || (key.lastIndexOf("Trigger") > 0 && key.substring(0, key.lastIndexOf("Trigger")).equalsIgnoreCase(trigger)) }).findFirst()
        .map(templateMap.&get)
        .orElseThrow({ ->  new RuntimeException(String.format("Invalid trigger type `%s`, supported values are %s and HttpTrigger", trigger, String.join(", ", templateMap.keySet()))) })
println("Generating trigger from template, which may take some moments. Please replace the values with placeholder in annotation with real value if necessary")
def pomFile = new File(rootDir, "pom.xml")
def isWindows = System.properties['os.name'].toLowerCase().contains('windows')
def starter = isWindows ? "cmd.exe" : "/bin/sh"
def switcher = isWindows ? "/c" : "-c"
def command = "mvn azure-functions:add -f \"${pomFile.getAbsolutePath()}\" -Dfunctions.package=\"${request.getProperties().get("groupId")}\" -Dfunctions.name=\"Function\" ${triggerParameter} -B"
if (!isWindows) {
    command = command.replace("\$", "\\\$")
}
def output = new StringBuilder()
def proc = [starter, switcher, command].execute();
proc.consumeProcessOutput(output, output)
proc.waitFor()
if (proc.exitValue() != 0 || output == null || !output.contains("BUILD SUCCESS")) {
    println("${output}")
    throw new RuntimeException("Failed to generate target trigger, please run `mvn azure-functions:add` manually in project root")
}
