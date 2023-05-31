package commands;


import utilities.CollectionManager;
import utilities.Module;

public class ClearCommand extends Command {
    private CollectionManager collectionManager;

    public ClearCommand() {
        super("clear", "clear collection");

    }

    public void setCollectionManager(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public boolean execute() {
        collectionManager.clearCollection();
        Module.addMessage("Collection is cleared");
        return true;
    }
}
