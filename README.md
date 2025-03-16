# Gradle Multiloader mod template (fabric/neoforge)

### An easy-to-use template for creating fabric and neoforge mods using gradle for Minecraft 1.20.1 and 1.21+

## Usage
0. **Create your repo** <br>
   Click the "Use this template" button in the top right to create a repository, then clone & import your new repository into IntelliJ idea (or your preferred IDE)
1. **Add mod info** <br>
   Check the gradle.properties file and set your mod id, package, name and version
2. **IDE integration** <br>
   First, import your repo into IDEA and import the gradle project if it isn't importing already (this might take a while). Once done, run the `genSources` task in the fabric category and then use the "download sources" button in your IDE.
   This ensures you have access to the Minecraft source code in all modules.
4. **Rename package** <br>
   In the `common`, `fabric` and `neoforge` modules, refactor the package name from `net.yourpackage.yourmod` to your actual package name. Also adjust the java file names and the `modID` field
5. **Done** <br>
   You can now enjoy modding in a multiloader setup!
   Working run configurations are automatically generated - just select the relevant one in the top right of your IDE window and run the game right from your IDE.

## More information
<details>
   <summary><h4>Project structure</h4></summary>
   
   A multiloader project consists of a root gradle project and three subprojects: `common`, `fabric` and `neoforge`. <br>
   The root project should not contain any code. It's build.gradle.kts file is used for some  common configuration for all the subprojects. <br>
   The `common` subproject contains all the **common mod code**, which will be included in all built jars. It has access to all of Minecraft,
   and the ability to add Access wideners and mixins, but no access to any modloader's API.
   It's build.gradle.kts is the place to put most of your required dependencies. <br>
   The `fabric` and `neoforge` subprojects contain initialisation and **loader-specific code**, as well as loader-specific resources like `fabric.mod.json` and `neoforge.mods.toml` <br>
   
</details>
<details>
   <summary><h4>Older/newer versions (1.20.1, 1.21.4, ...)</h4></summary>

   In order to change the target Minecraft version, just set the `minecraft_version` property in the `gradle.properties` file and adapt the other properties
   (`mc_versions_fabric`, `mc_versions_neo`, `parchment_version`, `neoforge_version`, `fabric_loader_version` and `fabric_api_version`) appropriately.

   This, however, only works for versions later than 1.20.1, because the neoforge ModDevGradle plugin only works for these newer versions.
   Therefore, this template provides a `1.20.1` branch that you can use instead if you want to use Minecraft versions from 1.17 to 1.20.1.

</details>
<details>
   <summary><h4>Access wideners/Access transformers</h4></summary>

   To use access wideners, create a `.accesswidener` file somewhere in your common resources directory and define the path in the `common/build.gradle.kts` file (~ line 30).
   These access wideners will be loaded in `common` and `fabric`, but they won't work on neoforge. That means you will need to create an `accesstransfomer.cfg` file inside
   your `neoforge/resources/META-INF` and add the same entries to that. The access transformer file will be loaded automatically if it has the default location & file name.

   [More information on access wideners](https://wiki.fabricmc.net/tutorial:accesswideners) / [More information on access transformers](https://docs.neoforged.net/docs/advanced/accesstransformers/#the-access-transformer-specification)

</details>
<details>
   <summary><h4>Mixins</h4></summary>

   To use mixins in your project, just create a mixin configuration file (`mymod.mixins.json`) in your common resources and add the path of it to your
   `fabric.mod.json` and `neoforge.mods.toml` files. No additional configuration required.

   More about mixins: [Mixin introduction](https://wiki.fabricmc.net/tutorial:mixin_introduction) / [Mixin examples](https://wiki.fabricmc.net/tutorial:mixin_examples)

</details>
<details>
   <summary><h4>Platform-specific conversions</h4></summary>

   This template can automatically convert some platform-specific
   parts of datapacks such as fluid units and data loading conditions
   from a defined common format to the platform-specific formats.

   See [platform_conversions.md](./platform_conversions.md) for more information.

</details>
