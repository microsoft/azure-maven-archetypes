import java.nio.file.Files
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.nio.file.Paths;

def isDocker = request.getProperties().get("docker")
def artifactId = request.getProperties().get("artifactId")
def dockerFile = "Dockerfile"
def pomFile = "pom.xml"
def dockerPomFile = "pom-docker.xml"
def rootDir = Paths.get(request.getOutputDirectory(), artifactId).toFile()
if (Boolean.valueOf(isDocker)) {
    // For docker, replace origin pom.xml with pom-docker.xml
    Files.move(new File(rootDir, dockerPomFile).toPath(), new File(rootDir, pomFile).toPath(), StandardCopyOption.REPLACE_EXISTING)
} else {
    // Otherwise, remove Dockerfile and pom-docker.xml
    Files.deleteIfExists(new File(rootDir, dockerFile).toPath())
    Files.deleteIfExists(new File(rootDir, dockerPomFile).toPath())
}

def appName = request.getProperties().get("appName")
def javaVersion = request.getProperties().get("javaVersion")
def pom = Paths.get(request.getOutputDirectory(), artifactId, pomFile).toFile()
def pomText = pom.text
// Update java compile version & java runtime version
// Supported values are 8/11, otherwise will use java 8 by default
pomText = pomText.replaceFirst("<java.version>.*</java.version>", String.format("<java.version>%s</java.version>", "11".equals(javaVersion) ? "11" : "1.8"))
pomText = pomText.replaceFirst("<javaVersion>.*</javaVersion>", String.format("<javaVersion>%s</javaVersion>", "11".equals(javaVersion) ? "11" : "8"))
// If user didn't modify appName, replace the expression with real value
// Set the values here as users will get prompt if we use expressions in <defaultValue> of <requiredProperty> in archetype metadata
if (appName == null || appName.equals("\$(artifactId)-\$(timestamp)")) {
    def finalAppName = String.format("%s-%s", artifactId, LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS")))
    // Replace the string directly as use XmlNodePrinter will break origin file style and comments
    pomText = pomText.replace("<functionAppName>\$(artifactId)-\$(timestamp)</functionAppName>", String.format("<functionAppName>%s</functionAppName>", finalAppName))
}
pom.text = pomText

def trigger = request.getProperties().get("trigger");
// todo: remove the parameter with default values, may need to update maven plugin
def templateMap = [
        "BlobTrigger"           : "-Dfunctions.template=BlobTrigger -Dconnection=\"<connection>\" -Dpath=mycontainer",
        "QueueTrigger"          : "-Dfunctions.template=QueueTrigger -Dconnection=\"<connection>\"",
        "TimerTrigger"          : "-Dfunctions.template=TimerTrigger -Dschedule=\"0 * * * * *\"",
        "EventGridTrigger"      : "-Dfunctions.template=EventGridTrigger",
        "EventHubTrigger"       : "-Dfunctions.template=EventHubTrigger -Dconnection=\"<connection>\" -DeventHubName=myeventhub -DconsumerGroup=\$Default ",
        "CosmosDBTrigger"       : "-Dfunctions.template=CosmosDBTrigger -DconnectionStringSetting=\"<connection_string_setting>\" -DdatabaseName=\"<databaseName>\" -DcollectionName=\"<collectionName>\" -DleaseCollectionName=\"<leaseCollectionName>\"",
        "ServiceBusQueueTrigger": "-Dfunctions.template=ServiceBusQueueTrigger -Dconnection=\"<connection>\" -DqueueName=mysbqueue",
        "ServiceBusTopicTrigger": "-Dfunctions.template=ServiceBusTopicTrigger -Dconnection=\"<connection>\" -DtopicName=mysbtopic -DsubscriptionName=mysubscription",
];
if (!"HttpTrigger".equalsIgnoreCase(trigger)) {
    println("Generating trigger from template, please replace the values with placeholder in annotation with real value if necessary")
    def triggerParameter = templateMap.get(trigger)
    if (triggerParameter == null) {
        println(String.format("Invalid trigger type, supported values are %s and HttpTrigger, using HttpTrigger by default", String.join(",", templateMap.keySet())));
        return
    }
    def sourceFolder = new File(rootDir, "src")
    sourceFolder.deleteDir(); // Remove origin source dir which contains unused test cases
    def isWindows = System.properties['os.name'].toLowerCase().contains('windows')
    def starter = isWindows ? "cmd.exe" : "/bin/sh"
    def switcher = isWindows ? "/c" : "-c"
    def command = "mvn azure-functions:add -f ${pom.getAbsolutePath()} -Dfunctions.package=${request.getProperties().get("groupId")} -Dfunctions.name=Function ${triggerParameter} -B"
    def proc = [starter, switcher, command].execute();
    proc.waitForOrKill(60 * 1000); // wait for 60s
    // Todo: check the execution result and show correspond prompt
}
