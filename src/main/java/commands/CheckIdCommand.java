package commands;

import data.User;
import utilities.CollectionManager;

import utilities.Module;

public class CheckIdCommand extends Command {
    private CollectionManager collectionManager;
    private Integer id;
    private User user;

    public CheckIdCommand(User user) {
        super("check_id", "comd for update");
        this.user=user;

    }

    public void setCollectionManager(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public boolean execute() {

         if(collectionManager.findById(id).getOwner().getUsername().equals(user.getUsername())) {
             if(collectionManager.findById(id)==null){
                 Module.addMessage("No group with this id(");
                 return false;}
             return true;
        }
        Module.addMessage("You can't update this study group because it's not yours(");
        return false;
    }
}
