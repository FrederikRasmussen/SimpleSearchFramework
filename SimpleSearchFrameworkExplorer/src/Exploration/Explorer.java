package Exploration;

import java.util.UUID;

public interface Explorer {
    UUID id();
    void id(UUID id);
    String name();
    void name(String name);

    void explore(ExplorationContext context);
    // Do nothing on stop by default.
    // ExplorationContext should already be set to stop.
    default void stop() {}

    static UUID newId()
    {
        return UUID.randomUUID();
    }
}
