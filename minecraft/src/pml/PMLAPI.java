package pml;

import net.minecraft.client.Minecraft;
import net.minecraft.src.*;

public class PMLAPI {

    public static void printChat(String message) {
        if (Minecraft.instance != null && Minecraft.instance.ingameGUI != null) {
            Minecraft.instance.ingameGUI.addChatMessage("\u00a7b[PML] \u00a7f" + message);
        }
    }

    public static World getCurrentWorld() {
        return Minecraft.instance.theWorld;
    }

    public static EntityPlayerSP getPlayer() {
        return Minecraft.instance.thePlayer;
    }

    public static void registerBlock(Block block, String name) {
        Item.itemsList[block.blockID] = new ItemBlock(block.blockID - 256).setItemName(name);
    }

    public static void addRecipe(ItemStack output, Object... inputs) {
        CraftingManager.getInstance().addRecipe(output, inputs);
    }

    public static void addSmelting(int inputItemID, ItemStack output) {
        FurnaceRecipes.smelting().addSmelting(inputItemID, output);
    }
}