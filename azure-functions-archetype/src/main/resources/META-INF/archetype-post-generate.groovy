import java.nio.file.Files

def isDocker = request.getProperties().get("docker")
// Remove Dockerfile if use didn't set -Ddocker
if (isDocker == null || !isDocker.equals("true")) {
    def dockerFile = "Dockerfile";
    def rootDir = new File(request.getOutputDirectory() + "/" + request.getArtifactId())
    Files.deleteIfExists(new File(rootDir, dockerFile).toPath())
}
