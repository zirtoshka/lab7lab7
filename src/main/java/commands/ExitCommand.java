package commands;


import utilities.Module;

public class ExitCommand extends Command {
    private SaveCommand saveCommand;

    public ExitCommand() {
        super("exit", "finish program without saving");
    }

    public void setSaveCommand(SaveCommand saveCommand) {
        this.saveCommand = saveCommand;
    }

    public SaveCommand getSaveCommand() {
        return saveCommand;
    }

    @Override
    public boolean execute() {
        Module.addMessage("The program is ending");
        return true;
    }
}
