# Maven Archetypes for Azure Functions
[![Maven Central](https://img.shields.io/maven-central/v/com.microsoft.azure/azure-functions-kotlin-archetype.svg)](http://search.maven.org/#search%7Cga%7C1%7Cg%3A%22com.microsoft.azure%22%20AND%20a%3A%22azure-functions-kotlin-archetype%22)

This is a Maven Archetype for Azure Functions written in Kotlin.

## Required Parameters

Like any other Maven Archetype, you are required to provide values for parameters `groupId` and `artifactId`. The `version` and `package` will have default values if not set.

### Other parameters and defaults

Parameter Name | Default Value | Description
---|---|---
`appName` | ${artifactId}-${timestamp} | Specifies the name of your Azure Functions, which will be used to package, run and deploy your project.
`appRegion` | westus | Specifies the region of your Azure Functions, which will be used when creating the new Azure Functions.
`resourceGroup` | java-functions-group | Specifies the resource group of your Azure Functions, which will be used when creating the new Azure Functions.
`version` | 1.0-SNAPSHOT | Sets the version of the new project
`package` | ${groupId} | Sets the package name for the classes

## Usage

### Interactive Mode

Run below command to create projects for Azure Java Functions in interactive mode.

```sh
mvn archetype:generate \
  -DarchetypeGroupId=com.microsoft.azure \
  -DarchetypeArtifactId=azure-functions-kotlin-archetype
```

### Batch Mode

```sh
mvn archetype:generate -B \
  -DarchetypeGroupId=com.microsoft.azure \
  -DarchetypeArtifactId=azure-functions-kotlin-archetype \
  -DarchetypeVersion=1.21-SNAPSHOT \
  -DgroupId=com.company \
  -DartifactId=project
```

### More Information
For more information about how to build/test the function project, please refer to the documents of [Maven Plugin for Azure Functions](https://github.com/Microsoft/azure-maven-plugins/blob/master/azure-functions-maven-plugin/README.md).
