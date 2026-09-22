# Pwn's Mod Loader
_(Or PML for short)_
___A VERY simple mod loader for Minecraft Beta 1.7.3!___

# Information
__PML Requires basic knowledge of the Minecraft Beta 1.7.3 engine!__
__You can learn about it yourself by decompiling via [RetroMCP](https://github.com/MCPHackers/RetroMCP-Java)__

# Creating a mod
## Quick Jump
[Main Class](#creating-the-main-class)
[PML API]()

## Creating the main class
To create a mod for the PML loader, your main class must extend BaseMod.  
This abstract class requires you to implement three core methods to define your mod's data and startup logic:
- `getName()` ___(Gets the name of the mod)___
- `getVersion()` ___(Gets the version of the mod)___
- `load()` ___(Startup functions & values)___
  
_Example_  
```java
import pml.BaseMod;

public class MyCustomMod extends BaseMod {
    
    @Override
    public String getName() {
        // sets the name of the mod
        return "Mod Name";
    }

    @Override
    public String getVersion() {
        // sets the version string of the mod
        return "1.0.0";
    }

    @Override
    public void load() {
        // the startup values & functions
    }
}
```

## Using the PML API
The PMLAPI class allows for easy access to different areas of the game!  
You typically use these from your `load()` function, but you _should_ be able to use them elsewhere.  
  
_Methods_  
- `printChat(String message)`: Sends a chat message to the player locally, it's automatically prefixed with "[PML]", but it will become a toggleable later on
- `getCurrentWorld()`: Gets the player's current `World` instance
- `getPlayer()`: Returns the `EntityPlayerSP` instance (the current/local player)
- `registerBlock(Block block, String name)`: Creates a new block and adds it to the engine's block list
- `addRecipe(ItemStack output, Object... inputs)`: Registers/creates a new crafting recipe
- `addSmelting(int inputItemID, ItemStack output)`: Registers/creates a new smelting recipe  

## Listening to Game Events
PML includes a event bus (basically a game loop reader/editor) that allows you to update things in your mod per-tick.  
This only works based on the client.  

To listen for game ticks, a class needs to add the IPMLListener interface and be registered to the event bus:
```java 
import pml.PMLEventBus;

public class TickHandler implements PMLEventBus.IPMLListener {
    
    public TickHandler() {
        // setup this instance to receive events
        PMLEventBus.registerListener(this);
    }

    @Override
    public void onTick() {
        // updates whatever each tick
    }
}
```