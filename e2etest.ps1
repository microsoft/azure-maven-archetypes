$base = pwd;
# Download Functions Core Tools
Remove-Item -Force "$base\Azure.Functions.Cli.zip" -ErrorAction Ignore
Remove-Item -Recurse -Force "$base\Azure.Functions.Cli" -ErrorAction Ignore
Write-Host "Downloading Functions Host...."
$url = "https://functionsclibuilds.blob.core.windows.net/builds/2/latest/Azure.Functions.Cli.win-x64.zip"
$output = "$base\Azure.Functions.Cli.zip"
$wc = New-Object System.Net.WebClient
$wc.DownloadFile($url, $output)
Expand-Archive "$base\Azure.Functions.Cli.zip" -DestinationPath "$base\Azure.Functions.Cli"
$Env:Path = $Env:Path+";$base\Azure.Functions.Cli"
# Install maven plguin archetype
$archetypePom = Get-Content ".\azure-functions-archetype\pom.xml" -Raw
$archetypePom -match "<version>(.*)</version>"
$atchetypeVersion = $matches[1]
mvn clean install
# Generate function project through archetype
Remove-Item -Recurse -Force ".\e2etestproject" -ErrorAction Ignore
mkdir e2etestproject
cd e2etestproject
mvn archetype:generate -DarchetypeCatalog="local" -DarchetypeGroupId="com.microsoft.azure" -DarchetypeArtifactId="azure-functions-archetype" -DarchetypeVersion="$atchetypeVersion" -DgroupId="com.microsoft" -DartifactId="e2etestproject" -Dversion="1.0-SNAPSHOT" -Dpackage="com.microsoft" -DappRegion="westus" -DresourceGroup="e2etest-java-functions-group" -DappName="e2etest-java-functions" -B
mvn -f ".\e2etestproject\pom.xml" clean package
cd ..
# Run function host
$Env:FUNCTIONS_WORKER_RUNTIME = "java"
$Env:AZURE_FUNCTIONS_ENVIRONMENT = "development"
$Env:AzureWebJobsScriptRoot = "$base\e2etestproject\e2etestproject\target\azure-functions\e2etest-java-functions"
$proc = start-process -filepath "$base\Azure.Functions.Cli\func.exe" -WorkingDirectory "$Env:AzureWebJobsScriptRoot" -ArgumentList "host start" -PassThru
Start-Sleep -s 30
return $proc