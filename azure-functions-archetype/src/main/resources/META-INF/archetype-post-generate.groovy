import java.nio.file.Files
import java.nio.file.StandardCopyOption;

def isDocker = request.getProperties().get("docker")
def dockerFile = "Dockerfile"
def pomFile = "pom.xml"
def dockerPomFile = "pom-docker.xml"
def rootDir = new File(request.getOutputDirectory() + "/" + request.getArtifactId())
if (Boolean.valueOf(isDocker)) {
    // For docker, replace origin pom.xml with pom-docker.xml
    Files.move(new File(rootDir, dockerPomFile).toPath(), new File(rootDir, pomFile).toPath(), StandardCopyOption.REPLACE_EXISTING)
} else {
    // Otherwise, remove Dockerfile and pom-docker.xml
    Files.deleteIfExists(new File(rootDir, dockerFile).toPath())
    Files.deleteIfExists(new File(rootDir, dockerPomFile).toPath())
}