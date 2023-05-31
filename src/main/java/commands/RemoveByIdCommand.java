package commands;


import data.StudyGroup;
import exceptions.NullCollectionException;
import exceptions.StudyGroupNullException;
import utilities.CollectionManager;
import utilities.Module;

public class RemoveByIdCommand extends Command {
    private CollectionManager collectionManager;
    private Integer argId;

    public RemoveByIdCommand() {
        super("remove_by_id <id>", "remove element by id");

    }

    public void setArgId(Integer argId) {
        this.argId = argId;
    }

    public void setCollectionManager(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public boolean execute() {
        try {
            if (collectionManager.collectionSize() == 0) throw new NullCollectionException();
            StudyGroup studyGroup = collectionManager.getById(argId);
            if (studyGroup == null) throw new StudyGroupNullException();
            collectionManager.removeById(studyGroup);
            Module.addMessage("Study group was removed");
            return true;
        } catch (NullCollectionException e) {
            Module.addMessage("Collection is empty");
        } catch (StudyGroupNullException e) {
            Module.addMessage("No Study Group with that ID");
        }
        return false;
    }
}
