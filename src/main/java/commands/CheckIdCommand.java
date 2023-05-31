package commands;

import utilities.CollectionManager;

import utilities.Module;

public class CheckIdCommand extends Command {
    private CollectionManager collectionManager;
    private Integer id;

    public CheckIdCommand() {
        super("check_id", "comd for update");

    }

    public void setCollectionManager(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public boolean execute() {
        if(collectionManager.getById(id)==null){
            Module.addMessage("No group with this id(");
            return false;
        }

        return true;
    }
}
