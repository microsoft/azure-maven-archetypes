# Maven Archetypes for Azure Functions
[![Maven Central](https://img.shields.io/maven-central/v/com.microsoft.azure/azure-functions-archetype.svg)](http://search.maven.org/#search%7Cga%7C1%7Cg%3A%22com.microsoft.azure%22%20AND%20a%3A%22azure-functions-archetype%22)

This is the Maven Archetype for Azure Functions.

## Documentation
Please follow the following documents for how to use this archetype
- [Quickstart](https://docs.microsoft.com/en-us/azure/azure-functions/functions-create-first-azure-function-azure-cli?tabs=bash%2Cbrowser&pivots=programming-language-java): Create a function in Azure that responds to HTTP requests
- [Tutorials](https://docs.microsoft.com/en-us/azure/azure-functions/functions-create-function-linux-custom-image?tabs=bash%2Cportal&pivots=programming-language-java): Create a function on Linux using a custom container

## Usage

Run below command to create projects for Azure Java Functions in interactive mode.

```bash
mvn archetype:generate -DarchetypeGroupId=com.microsoft.azure -DarchetypeArtifactId=azure-functions-archetype
```

To generate the project in batch mode, please adjust the following parameters to your needs.

- Adjust `-DgroupId=com.ms` to define the base Maven group ID
- Adjust `-DartifactId=azure-function-demo` to define the base Maven artifact ID
- Adjust `-DappName=MyFunctionApp` to define the function app name
- Adjust `-DappRegion=westus` to specify the region to deploy
- Adjust `-DappServicePlanName=java-functions-app-service-plan` to specify the app service plan which will host your function app
- Adjust `-DresourceGroup="java-functions-group` to specify the resource group of your function app
- Adjust the `-DjavaVersion=8` to specify the function host java version as well as the project compile level

```bash
mvn -B archetype:generate \
    -DarchetypeGroupId=com.microsoft.azure \
    -DarchetypeArtifactId=azure-functions-archetype \
    -DgroupId=com.ms \
    -DartifactId=azure-function-demo \
    -DappName=MyFunctionApp \
    -DappRegion=westus \
    -DappServicePlanName=java-functions-app-service-plan \
    -DresourceGroup=java-functions-group \
    -DjavaVersion=8
```
> To create a function project with docker support, please add `-Ddocker` to above command

> To create a function project with specific trigger, please use `-Dtrigger` to specify the trigger type

## Available Parameters

Parameter Name | Default Value | Description
---|---|---
`groupId`| | Base Maven group ID
`artifactId`| | Base Maven artifact Id
`package` | `${groupId}` | Base package name for java source codes
`appName` | `${artifactId}-${timestamp}` | The name of your Azure Functions, which will be used to package, run and deploy your project.
`appRegion` | `westus` | The region of your Azure Functions, which will be used when creating the new Azure Functions.
`appServicePlanName` | `java-functions-app-service-plan` | The the app service plan of your Azure Functions, which will be used when creating the new Azure Functions.
`resourceGroup` | `java-functions-group` | The the resource group of your Azure Functions, which will be used when creating the new Azure Functions.
`javaVersion` | `17` | The the function host java version as well as the project compile level, supported values are `8`, `11`, `17` or `21` (Linux only).
`docker` | `false` | The whether to enable docker support in your function project.
`trigger` | `HttpTrigger` | Specify the trigger type of Azure Function, supported values are `HttpTrigger`, `BlobTrigger`, `QueueTrigger`, `TimerTrigger`, `EventGridTrigger`, `EventHubTrigger`, `CosmosDBTrigger`, `ServiceBusQueueTrigger`, `ServiceBusTopicTrigger`, `RabbitMQTrigger`, `KafkaTrigger`, `DurableFunctions`, `SqlOutputBinding`, `SqlInputBinding`, `SqlTrigger`, `DaprPublishOutputBinding`, `DaprServiceInvocationTrigger`,
, `DaprTopicTrigger`, `AssistantSkillTrigger`, `AssistantCreate`, `AssistantQuery`, `AssistantPost`, `EmbeddingsInput`, `EmbeddingsStoreOutput`, `SemanticSearch`, `TextCompletion`

## System Requirements
Azure Functions Core Tools | Azure CLI | Java SE | Maven
---------|---------|---------|---------
[2.7.2796+](https://github.com/Azure/azure-functions-core-tools/releases/tag/2.7.2796)/[3.0.2798+](https://github.com/Azure/azure-functions-core-tools/releases/tag/3.0.2798) | 2.4+ | 8,11,17 | 3.3.3+

## More Information
For more information about how to build/test the function project, please refer to the documents of [Maven Plugin for Azure Functions](https://github.com/Microsoft/azure-maven-plugins/blob/master/azure-functions-maven-plugin/README.md).
