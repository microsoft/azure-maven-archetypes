# Maven Archetypes for Azure Functions
[![Maven Central](https://img.shields.io/maven-central/v/com.microsoft.azure/azure-functions-kotlin-archetype.svg)](http://search.maven.org/#search%7Cga%7C1%7Cg%3A%22com.microsoft.azure%22%20AND%20a%3A%22azure-functions-kotlin-archetype%22)

This is a Maven Archetype for Azure Functions written in Kotlin.

## Required Parameters

Like any other Maven Archetype, you are required to provide values for parameters `groupId`, `artifactId`, `version` and `package`.
On top of that, two extra parameters are required for Azure Functions Archetype as listed in below table. They both have default values.
You can use their default values or enter your own.

Parameter Name | Default Value | Description
---|---|---
`appName` | ${artifactId}-${timestamp} | Specifies the name of your Azure Functions, which will be used to package, run and deploy your project.
`appRegion` | westus | Specifies the region of your Azure Functions, which will be used when creating the new Azure Functions.
`resourceGroup` | java-functions-group | Specifies the resource group of your Azure Functions, which will be used when creating the new Azure Functions..

## Usage

### Interactive Mode
Run below command to create projects for Azure Java Functions in interactive mode.

```cmd
mvn archetype:generate -DarchetypeGroupId=com.microsoft.azure -DarchetypeArtifactId=azure-functions-kotlin-archetype
```

### Batch Mode
Refer to the example at [here](https://maven.apache.org/archetype/maven-archetype-plugin/examples/generate-batch.html) to generate project in batch mode.

### More Information
For more information about how to build/test the function project, please refer to the documents of [Maven Plugin for Azure Functions](https://github.com/Microsoft/azure-maven-plugins/blob/master/azure-functions-maven-plugin/README.md).
