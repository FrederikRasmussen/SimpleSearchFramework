package Exploration;

import java.util.Date;

public class ExplorationStopped extends RuntimeException {
    Date time = new Date();

    @Override
    public String toString() {
        return "ExplorationStopped{" +
                "time = " + time +
                '}';
    }
}
