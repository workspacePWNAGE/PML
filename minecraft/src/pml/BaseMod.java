package pml;

public abstract class BaseMod {
    // initialize the mod
    public abstract void load();

    // gets the name of the mod
    public abstract String getName();

    // gets the version of the mod
    public abstract String getVersion();
}