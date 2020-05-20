import java.nio.file.Files

def needSampleCode = request.getProperties().get("needSampleCode")
def rootPath = "${request.getOutputDirectory()}/${request.getArtifactId()}"
// Create an empty folder to unblock Spark local run feature in azure-toolikit-for-intellij plugin
new File("$rootPath/data/__default__/user/current").mkdirs()

if (!Boolean.valueOf(needSampleCode)) {
    def packageRelativePath = request.getPackage().replace('.', '/')
    def scalaCodesDir = new File("$rootPath/src/main/scala/${packageRelativePath}")
    def javaCodesDir = new File("$rootPath/src/main/java/${packageRelativePath}")
    cleanupFolder(scalaCodesDir)
    cleanupFolder(javaCodesDir)
}

// Clean up all the contents in the "dir" not including "dir" itself
void cleanupFolder(File dir) {
    if (!dir.isDirectory())
        return

    for (file in dir.listFiles())
        cleanupContent(file)
}

void cleanupContent(File dir) {
    if (dir.isFile()) {
        Files.deleteIfExists(dir.toPath())
        return
    }

    for (file in dir.listFiles()) {
        cleanupContent(file)
    }

    Files.deleteIfExists(dir.toPath())
}