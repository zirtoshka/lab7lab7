package commands;


import utilities.CollectionManager;

import java.io.IOException;

public class ExecuteScriptCommand extends Command {
    public ExecuteScriptCommand() {
        super("execute_script <file_name>", "use script from file");
    }

    private CollectionManager collectionManager;

    public void setCollectionManager(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public boolean execute() throws IOException {
        return true;
    }
}
