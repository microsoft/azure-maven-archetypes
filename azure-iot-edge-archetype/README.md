# Maven Archetypes for Azure Functions
[![Maven Central](https://img.shields.io/maven-central/v/com.microsoft.azure/azure-functions-archetype.svg)](http://search.maven.org/#search%7Cga%7C1%7Cg%3A%22com.microsoft.azure%22%20AND%20a%3A%22azure-functions-archetype%22)

This is the Maven Archetype for Azure Functions.

## Required Parameters

Like any other Maven Archetype, you are required to provide values for parameters `groupId`, `artifactId`, `version` and `package`.
On top of that, two extra parameters are required for Azure Functions Archetype as listed in below table. They both have default values.
You can use their default values or enter your own.

Parameter Name | Default Value | Description
---|---|---
`appName` | ${artifactId}-${timestamp} | Specifies the name of your Azure Function App, which will be used to package, run and deploy your project.
`appRegion` | westus | Specifies the region of your Azure Function App, which will be used to create new Function App in Azure.

## Usage

### Interactive Mode
Run below command to create projects for Azure Java Functions in interactive mode.

```cmd
mvn archetype:generate -DarchetypeGroupId=com.microsoft.azure -DarchetypeArtifactId=azure-functions-archetype
```

### Batch Mode
Refer to the example at [here](https://maven.apache.org/archetype/maven-archetype-plugin/examples/generate-batch.html) to generate project in batch mode.
