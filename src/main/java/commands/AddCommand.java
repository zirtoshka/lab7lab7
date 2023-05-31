package commands;


import data.StudyGroup;
import utilities.CollectionManager;
import utilities.Module;

public class AddCommand extends Command {
    private CollectionManager collectionManager;
    private StudyGroup argGroup;

    public AddCommand() {
        super("add", "add a new element to the collection");
    }

    public void setCollectionManager(CollectionManager manager) {
        this.collectionManager = manager;
    }

    public void setArgGroup(StudyGroup argGroup) {
        this.argGroup = argGroup;
    }

    public CollectionManager getCollectionManager() {
        return collectionManager;
    }

    @Override
    public boolean execute() {
        Module.addMessage(collectionManager.addToCollection(argGroup));
        return true;
    }
}
