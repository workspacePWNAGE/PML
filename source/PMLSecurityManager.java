package pml;

import java.security.Permission;

public class PMLSecurityManager extends SecurityManager {
    @Override
    public void checkPermission(Permission perm) {
        String action = perm.getActions();
        String name = perm.getName();

        // block out-of-instance writes
        if (perm instanceof java.io.FilePermission) {
            if (action != null && (action.contains("write") || action.contains("delete"))) {
                String userDir = System.getProperty("user.dir");
                String tmpDir = System.getProperty("java.io.tmpdir");
                
                if (name != null && !name.startsWith(userDir) && !name.startsWith(tmpDir)) {
                    throw new SecurityException("[PML] Blocked unauthorized file write access to: " + name);
                }
            }
        }

        // block vm closing & running process
        if (perm instanceof RuntimePermission) {
            if (name.equals("exitVM") || name.startsWith("setIO")) {
                throw new SecurityException("[PML] Blocked restricted runtime permission: " + name);
            }
            if (name.startsWith("execute")) {
                throw new SecurityException("[PML] Executing external processes is forbidden by PML.");
            }
        }
    }
}