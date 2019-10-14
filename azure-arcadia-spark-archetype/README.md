# Maven Archetypes for Azure Arcadia Spark
[![Maven Central](!!!)](!!!)

This is the Maven Archetype for Azure Arcadia Spark.

## Required Parameters

Like any other Maven Archetype, you are required to provide values for parameters `groupId`, `artifactId`, `version` and `package`.

## Usage

### Interactive Mode
Run below command to create projects for Azure Arcadia Spark in interactive mode. For this repo developer, you need to run `mvn install` to install the  archetype to local repository. When you run the below command, you need to add another argument `-DarchetypeCatalog=local` to read archetype from local repo.

```cmd
mvn archetype:generate -DarchetypeGroupId=com.microsoft.azure -DarchetypeArtifactId=azure-arcadia-spark-archetype -DarchetypeVersion=1.0-SNAPSHOT
```

### Batch Mode
Refer to the example at [here](https://maven.apache.org/archetype/maven-archetype-plugin/examples/generate-batch.html) to generate project in batch mode.

### More Information
For more information about how to build/test the function project, please refer to the documents of [Maven Plugin for Azure Arcadia Spark](!!!).
