![TooManyColors](./assets/logo.png?raw=true)

## About
TooManyColors is a Spigot 1.16 Plugin for storing 
hex color codes as placeholders in chat.

Download https://www.spigotmc.org/resources/toomanycolors-1-16.80651/

## Config
- **defaultShortcutLimit** - The amount of shortcuts a player is allowed to create with base permissions. Set to -1 to disable.
- **blacklistedColors** - Players will not be able to create shortcuts for these colors.
## Permissions
- **tmc.command.shortcut.set** - Grants permission to use the set subcommand.
- **tmc.command.shortcut.del** - Grants permission to use the del subcommand.
- **tmc.command.shortcut.set.\[1-Infinity\*]** - Sets the amount of shortcuts the player can create.
- **tmc.command.shortcut.set.unlimited** - Bypass the defaultShortcutLimit.
## API
You can now use TooManyColors in your own plugins through JitPack!

Add the following dependency to your pom.xml:
```maven
</repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
<dependencies>
    <dependency>
        <groupId>com.github.jaywalkn</groupId>
        <artifactId>TooManyColors</artifactId>
        <version>1.0.1</version>
    </dependency>
</dependencies>
```

Then in your onEnable function add:
```java
TMC tmc = (TMC) Bukkit.getPluginManager().getPlugin("TooManyColors");
```

You can then use the function:
```java
tmc.fetchPlaceholders("player uuid as string") // Returns HashMap<String placeholder, String hexCode>
```
