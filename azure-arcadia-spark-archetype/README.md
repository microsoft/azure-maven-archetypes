# Maven Archetypes for Azure Arcadia Spark
[![Maven Central](!!!)](!!!)

This is the Maven Archetype for Azure Arcadia Spark.

## Required Parameters

Like any other Maven Archetype, you are required to provide values for parameters `groupId`, `artifactId`, `version` and `package`.

## Usage

### Interactive Mode
If you are a developer of this archetype, plese run below command to create projects for Azure Arcadia Spark in interactive mode.

```
mvn install
mvn archetype:generate -DarchetypeGroupId=com.microsoft.azure -DarchetypeArtifactId=azure-arcadia-spark-archetype -DarchetypeVersion=1.0-SNAPSHOT -DarchetypeCatalog=local
```

If the archetype is already published to maven central repository, user can run below command to create projects for Azure Arcadia Spark in interactive mode.

```cmd
mvn archetype:generate -DarchetypeGroupId=com.microsoft.azure -DarchetypeArtifactId=azure-arcadia-spark-archetype -DarchetypeVersion=1.0-SNAPSHOT
```

### Batch Mode
If you are a developer of this archetype, plese run below command to create projects for Azure Arcadia Spark in batch mode.

```
mvn install
mvn archetype:generate -B -DarchetypeGroupId=com.microsoft.azure -DarchetypeArtifactId=azure-arcadia-spark-archetype -DarchetypeVersion=1.0-SNAPSHOT  -DgroupId=com.microsoft.spark -DartifactId=SampleProject -Dversion=1.0-SNAPSHOT -Dpackage=SampleSparkProject -DarchetypeCatalog=local
```

Refer to the example at [here](https://maven.apache.org/archetype/maven-archetype-plugin/examples/generate-batch.html) for more details.

### More Information
For more information about how to build/test the function project, please refer to the documents of [Maven Plugin for Azure Arcadia Spark](!!!).
