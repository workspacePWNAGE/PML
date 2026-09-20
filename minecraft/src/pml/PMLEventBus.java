package pml;

import java.util.ArrayList;
import java.util.List;

public class PMLEventBus {
    private static final List<IPMLListener> listeners = new ArrayList<IPMLListener>();

    public static void registerListener(IPMLListener listener) {
        listeners.add(listener);
    }

    // called inside Minecraft.runTick() via injection
    public static void onClientTick() {
        for (IPMLListener listener : listeners) {
            listener.onTick();
        }
    }

    public interface IPMLListener {
        void onTick();
    }
}