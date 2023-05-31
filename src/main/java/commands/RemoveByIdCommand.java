package commands;


import data.StudyGroup;
import data.User;
import exceptions.NullCollectionException;
import exceptions.StudyGroupNullException;
import utilities.CollectionManager;
import utilities.Module;

public class RemoveByIdCommand extends Command {
    private CollectionManager collectionManager;
    private Integer argId;
    private User user;

    public RemoveByIdCommand(User user) {
        super("remove_by_id <id>", "remove element by id");
        this.user=user;

    }

    public void setArgId(Integer argId) {
        this.argId = argId;
    }

    public void setCollectionManager(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public boolean execute() {
        Module.addMessage(collectionManager.removeById(argId, user));
        return true;
    }
}
