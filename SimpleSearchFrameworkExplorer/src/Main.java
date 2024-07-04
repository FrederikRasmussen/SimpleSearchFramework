import Exploration.ExplorationContext;
import Exploration.FileExploration.FileExplorer;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        var explorer = new FileExplorer();

        var context = new ExplorationContext();
        explorer.explore(context);
    }
}