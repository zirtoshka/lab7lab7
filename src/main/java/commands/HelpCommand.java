package commands;


import utilities.Module;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;



public class HelpCommand extends Command {
    private final List<Command> commands = new LinkedList<>();


    public HelpCommand(InfoCommand infoCmd, ShowCommand showCmd, AddCommand addCmd, UpdateByIdCommand updateByIdCmd, RemoveByIdCommand removeByIdCmd, ClearCommand clearCmd,
                       SaveCommand saveCmd, ExecuteScriptCommand executeScriptCmd, ExitCommand exitCmd, HeadCommand headCmd, AddIfMaxCommand addIfMaxCmd, HistoryCommand historyCmd,
                       FilterContainsNameCommand filterContainsNameCmd, PrintUniqueGroupAdminCommand printUniqueAdminCmd, PrintFieldDescendingSemesterCommand printFieldDescendingSemesterCmd) {
        super("help", "display help on available commands");
        commands.add(this);
        commands.add(infoCmd);
        commands.add(showCmd);
        commands.add(addCmd);
        commands.add(updateByIdCmd);
        commands.add(removeByIdCmd);
        commands.add(clearCmd);
        commands.add(saveCmd);
        commands.add(executeScriptCmd);
        commands.add(exitCmd);
        commands.add(addIfMaxCmd);
        commands.add(historyCmd);
        commands.add(filterContainsNameCmd);
        commands.add(printFieldDescendingSemesterCmd);
        commands.add(printUniqueAdminCmd);

    }


    @Override
    public boolean execute() {
        String res = "";
        for (Command cmd : commands) {
            res += (cmd.getName() + " Desc: " + cmd.getDescription() + "\n");
        }
        Module.addMessage(res);
        return true;
    }
}
