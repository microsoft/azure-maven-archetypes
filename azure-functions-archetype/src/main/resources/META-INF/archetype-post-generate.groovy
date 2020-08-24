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
