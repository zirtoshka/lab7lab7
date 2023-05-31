package commands;


import utilities.CollectionManager;

public class SaveCommand extends Command {
    private CollectionManager collectionManager;

    public SaveCommand() {
        super("save", "save to file, available only for server");
    }

    public void setCollectionManager(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public boolean execute() {
        collectionManager.saveCollection();
        return true;
    }
}
