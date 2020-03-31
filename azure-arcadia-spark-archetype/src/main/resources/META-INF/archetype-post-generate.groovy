import java.nio.file.Files

def needSampleCode = request.getProperties().get("needSampleCode")
if (!Boolean.valueOf(needSampleCode)) {
    def rootPath = "${request.getOutputDirectory()}/${request.getArtifactId()}"
    def packageRelativePath = request.getGroupId().replace('.', '/')
    def scalaCodesDir = new File("$rootPath/src/main/scala/$packageRelativePath")
    def javaCodesDir = new File("$rootPath/src/main/java/$packageRelativePath")
    def dataDir = new File("$rootPath/data/__default__/")
    cleanupFolder(scalaCodesDir)
    cleanupFolder(javaCodesDir)
    cleanupFolder(dataDir)
    new File("${dataDir.path}/user/current").mkdirs()
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