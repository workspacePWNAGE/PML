package pml;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PML {
    private static final List<BaseMod> loadedMods = new ArrayList<BaseMod>();

    public static void init(File gameDir) {
        PMLLoadingScreen.render("Initializing PML Security Sandbox...", 10);
        System.setSecurityManager(new PMLSecurityManager());

        File modsFolder = new File(gameDir, "mods");
        if (!modsFolder.exists()) {
            modsFolder.mkdirs();
        }

        PMLLoadingScreen.render("Scanning /mods directory...", 20);

        File[] modFiles = modsFolder.listFiles((dir, name) -> 
            name.toLowerCase().endsWith(".jar") || name.toLowerCase().endsWith(".zip")
        );

        if (modFiles == null || modFiles.length == 0) {
            PMLLoadingScreen.render("No mods found. Starting game...", 100);
            sleep(300);
            return;
        }

        ClassLoader classLoader = PML.class.getClassLoader();

        for (int i = 0; i < modFiles.length; i++) {
            File file = modFiles[i];
            int currentPercent = 20 + (int)(((i + 1) / (float)modFiles.length) * 75);

            PMLLoadingScreen.render("Loading mod (" + (i + 1) + "/" + modFiles.length + "): " + file.getName(), currentPercent);

            try {
                // loader init
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        PMLLoadingScreen.render("PML Initialized Successfully!", 100);
        sleep(300); // pause so 100% completion is visible
    }

    private static void sleep(long ms) {
        try { Thread.sleep(ms); } catch (InterruptedException ignored) {}
    }
}