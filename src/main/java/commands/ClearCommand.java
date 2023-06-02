package commands;


import data.User;
import utilities.CollectionManager;
import utilities.Module;

public class ClearCommand extends Command {
    private CollectionManager collectionManager;
    private User user;

    public ClearCommand() {
        super("clear", "clear collection",true);


    }

    public void setCollectionManager(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean execute() {
        collectionManager.clearCollection(user);
        Module.addMessage("Collection is cleared");
        return true;
    }
}
