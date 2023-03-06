# Maven Archetypes for Azure

This repo contains Maven Archetypes for Azure. The following table lists Maven Archetypes for azure services.

> **Note**
> The Azure SDK maven archetype can be found at [the Azure SDK GitHub repository](https://github.com/Azure/azure-sdk-for-java/tree/main/sdk/tools/azure-sdk-archetype).

Archetype Artifact Id | Archetype Group Id | Maven Central Version
---|---|---
`azure-functions-archetype`|`com.microsoft.azure`| [![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.microsoft.azure/azure-functions-archetype/badge.svg?style=plastic)](https://maven-badges.herokuapp.com/maven-central/com.microsoft.azure/azure-functions-archetype)
`azure-functions-kotlin-archetype`|`com.microsoft.azure`| [![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.microsoft.azure/azure-functions-kotlin-archetype/badge.svg?style=plastic)](https://maven-badges.herokuapp.com/maven-central/com.microsoft.azure/azure-functions-kotlin-archetype)
`azure-iot-edge-archetype`|`com.microsoft.azure`| [![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.microsoft.azure/azure-iot-edge-archetype/badge.svg?style=plastic)](https://maven-badges.herokuapp.com/maven-central/com.microsoft.azure/azure-iot-edge-archetype)
`apache-spark-archetype`|`com.microsoft.azure`| [![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.microsoft.azure/apache-spark-archetype/badge.svg?style=plastic)](https://maven-badges.herokuapp.com/maven-central/com.microsoft.azure/apache-spark-archetype)

### Related repository: azure-maven-plugins

You may be also interested in the [Azure Maven Plugins](https://github.com/microsoft/azure-maven-plugins). These archetypes are very closely related to the plugins, and both kinds of artifacts work together to give you Java your way on Azure.

Of particular interest to users of these archytypes is the `azure-webapp-maven-plugin` and its `config` goal. This goal acts like an archetype. It instruments the current project's POM so that the `target` artifact can subsequently be published to Azure App Service using the `deploy` goal.

### Reporting Issues and Feedback
If you encounter any bugs with the Maven Archetypes, please file an issue in the [Issues](https://github.com/microsoft/azure-maven-archetypes/issues) section of our GitHub repo.

#### Contributing

This project welcomes contributions and suggestions.  Most contributions require you to agree to a
Contributor License Agreement (CLA) declaring that you have the right to, and actually do, grant us
the rights to use your contribution. For details, visit https://cla.microsoft.com.

When you submit a pull request, a CLA-bot will automatically determine whether you need to provide
a CLA and decorate the PR appropriately (e.g., label, comment). Simply follow the instructions
provided by the bot. You will only need to do this once across all repos using our CLA.

This project has adopted the [Microsoft Open Source Code of Conduct](https://opensource.microsoft.com/codeofconduct/).
For more information see the [Code of Conduct FAQ](https://opensource.microsoft.com/codeofconduct/faq/) or
contact [opencode@microsoft.com](mailto:opencode@microsoft.com) with any additional questions or comments.
