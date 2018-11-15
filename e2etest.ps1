# Define the functions
Function RemoveFileIfExist($fileName) {
    if (Test-Path $fileName) {
        Remove-Item -Force $fileName
    }
}

Function RemoveFolderIfExist($folderName) {
    if (Test-Path $folderName) {
        Remove-Item -Recurse -Force $folderName
    }
}

Function DownloadFileFromUrl($url, $destination) {
    $wc = New-Object System.Net.WebClient
    $wc.DownloadFile($Env:FUNCTIONCLI_URL, $functionCLIZipPath)
}

#Scripts
$base = pwd
$functionCLIPath = "$base\Azure.Functions.Cli"
$functionCLIZipPath = "$base\Azure.Functions.Cli.zip"

# Download Functions Core Tools
RemoveFileIfExist $functionCLIZipPath
RemoveFolderIfExist $functionCLIPath
DownloadFileFromUrl $Env:FUNCTIONCLI_URL $functionCLIZipPath
Expand-Archive $functionCLIZipPath -DestinationPath $functionCLIPath
$Env:Path = $Env:Path + ";$functionCLIPath"

# Install maven plguin archetype
mvn clean install

# Generate function project through archetype
$testProjectBaseFolder = ".\testprojects"
# Get the version of archetype and set archetypeVersion in following steps
$atchetypeVersion = ([xml](gc ".\azure-functions-archetype\pom.xml")).project.version 
RemoveFolderIfExist $testProjectBaseFolder
mkdir $testProjectBaseFolder
cd $testProjectBaseFolder
mvn archetype:generate -DarchetypeCatalog="local" -DarchetypeGroupId="com.microsoft.azure" -DarchetypeArtifactId="azure-functions-archetype" -DarchetypeVersion="$atchetypeVersion" -DgroupId="com.microsoft" -DartifactId="e2etestproject" -Dversion="1.0-SNAPSHOT" -Dpackage="com.microsoft" -DappRegion="westus" -DresourceGroup="e2etest-java-functions-group" -DappName="e2etest-java-functions" -B
mvn -f ".\e2etestproject\pom.xml" clean package
cd ..

# Run function host
$Env:FUNCTIONS_WORKER_RUNTIME = "java"
$Env:AZURE_FUNCTIONS_ENVIRONMENT = "development"
$Env:AzureWebJobsScriptRoot = "$base\$testProjectBaseFolder\e2etestproject\target\azure-functions\e2etest-java-functions"
$proc = start-process -filepath "$functionCLIPath\func.exe" -WorkingDirectory "$Env:AzureWebJobsScriptRoot" -ArgumentList "host start" -RedirectStandardOutput "output.txt" -RedirectStandardError "error.txt" -PassThru
Start-Sleep -s 30
return $proc
