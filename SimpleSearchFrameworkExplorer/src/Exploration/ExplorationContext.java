package Exploration;

public class ExplorationContext {
    private boolean stopped = false;
    public boolean stopped() {
        return stopped;
    }

    public void stop() {

    }

    public void add(Document document) {
        System.out.println("document.asJson() = " + document.asJson());
    }

    public boolean shouldSkip(Document document) {
        var version = document.version();
        if (version == null)
            return false;
        throw new UnsupportedOperationException("Not yet implemented.");
    }
}
