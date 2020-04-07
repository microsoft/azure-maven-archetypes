# Maven Archetypes for Azure Spark
[![Maven Central](!!!)](!!!)

This is the Maven Archetype for Azure Spark.

## Required Parameters

Like any other Maven Archetype, you are required to provide values for parameters `groupId`, `artifactId`, `version` and `package`.
On top of that, extra parameters required for Apache Spark archetype are listed below.

Parameter Name | Default Value | Description
---|---|---
`sparkVersion` | 2.4.0 | Apache Spark version
`scalaVersion` | 2.11.12 | Scala language version
`needSampleCode` | true | Whether Java and Scala sample codes are required or not

## Usage

### Interactive Mode

Run the command below to create an Apache Spark project in interactive mode.

```cmd
mvn archetype:generate -DarchetypeGroupId=com.microsoft.azure -DarchetypeArtifactId=azure-spark-archetype -DarchetypeVersion=1.0-SNAPSHOT
```

### Batch Mode
Refer to the example [here](https://maven.apache.org/archetype/maven-archetype-plugin/examples/generate-batch.html) to generate project in batch mode.