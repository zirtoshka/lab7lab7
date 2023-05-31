package commands;

import IO.ConsoleManager;
import data.User;
import exceptions.DataBaseAuthorizationException;
import utilities.CollectionManager;
import utilities.Module;



public class Connect extends Command {
    private User user;
    private CollectionManager collectionManager;
    public Connect(String name, String description, User user) {
        super(name, description);
        this.user=user;
    }

    public void setCollectionManager(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public boolean execute()  {
        try {
            Module.addMessage(collectionManager.auth(user));
            return true;
        }catch (DataBaseAuthorizationException e){
            Module.addMessage(e.getMessage());
            return false;
        }
    }
}
