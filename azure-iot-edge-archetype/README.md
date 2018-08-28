# Maven Archetypes for Azure IoT Edge
[![Maven Central](https://img.shields.io/maven-central/v/com.microsoft.azure/azure-iot-edge-archetype.svg)](http://search.maven.org/#search%7Cga%7C1%7Cg%3A%22com.microsoft.azure%22%20AND%20a%3A%22azure-iot-edge-archetype%22)

This is the Maven Archetype for Azure IoT Edge.

## Required Parameters

Like any other Maven Archetype, you are required to provide values for parameters `groupId`, `artifactId`, `version` and `package`.
On top of that, one extra parameters is required for Azure IoT Edge Archetype as listed in below table. It has default value.
You can use its default value or enter your own.

Parameter Name | Default Value | Description
---|---|---
`repository` | localhost:5000/${artifactId} | Specifies the Docker repository to host your Azure IoT Edge module

## Usage

### Interactive Mode
Run below command to create projects for Azure IoT Edge module in interactive mode.

```cmd
mvn archetype:generate -DarchetypeGroupId=com.microsoft.azure -DarchetypeArtifactId=azure-iot-edge-archetype -DarchetypeVersion=1.0.0
```

### Batch Mode
Refer to the example at [here](https://maven.apache.org/archetype/maven-archetype-plugin/examples/generate-batch.html) to generate project in batch mode.
