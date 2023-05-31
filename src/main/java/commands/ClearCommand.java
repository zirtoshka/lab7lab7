package commands;


import data.User;
import utilities.CollectionManager;
import utilities.Module;

public class ClearCommand extends Command {
    private CollectionManager collectionManager;
    private User user;

    public ClearCommand(User user) {
        super("clear", "clear collection");
        this.user=user;

    }

    public void setCollectionManager(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public boolean execute() {
        collectionManager.clearCollection(user);
        Module.addMessage("Collection is cleared");
        return true;
    }
}
