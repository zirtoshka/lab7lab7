package commands;


import exceptions.ExitingException;
import utilities.CollectionManager;
import utilities.Module;

public class ExitCommand extends Command {
    private SaveCommand saveCommand;

    public ExitCommand() {
        super("exit", "finish program without saving",false);
    }


    public SaveCommand getSaveCommand() {
        return saveCommand;
    }

    @Override
    public boolean execute(){
        Module.addMessage("The program is ending");
//        throw new ExitingException();
        return true;
    }


}
