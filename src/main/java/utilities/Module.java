package utilities;

import commands.*;

import java.io.IOException;
import java.util.Scanner;

import static config.ConfigData.*;

public class Module {

    private static CollectionManager collectionManager;
    private static String outputMessage = "";
    private static CommandManager commandManager;

    public static boolean runningCmd(Command command) throws IOException {
        String currentCmd = command.getName();
        Scanner scanner = new Scanner(currentCmd);
        scanner.useDelimiter("\\s");
        currentCmd = scanner.next();
        switch (currentCmd) {
            case HELP: {
                HelpCommand helpCommand = (HelpCommand) command;
                return helpCommand.execute();
            }
            case ADD: {
                AddCommand addCommand = (AddCommand) command;
                addCommand.setCollectionManager(collectionManager);
                return addCommand.execute();
            }
            case ADD_IF_MAX: {
                AddIfMaxCommand addIfMaxCommand = (AddIfMaxCommand) command;
                addIfMaxCommand.setCollectionManager(collectionManager);
                return addIfMaxCommand.execute();
            }
            case CLEAR: {
                ClearCommand clearCommand = (ClearCommand) command;
                clearCommand.setCollectionManager(collectionManager);
                return clearCommand.execute();
            }
            case EXECUTE_SCRIPT: {
                ExecuteScriptCommand executeScriptCommand = (ExecuteScriptCommand) command;
                executeScriptCommand.setCollectionManager(collectionManager);
                return executeScriptCommand.execute();
            }
            case EXIT: {
                ExitCommand exitCommand = (ExitCommand) command;
                exitCommand.getSaveCommand().setCollectionManager(collectionManager);
                exitCommand.getSaveCommand().execute();
                System.out.println(messageFlush());
                return exitCommand.execute();
            }
            case FILTER_CONTAINS_NAME: {
                FilterContainsNameCommand filterContainsNameCommand = (FilterContainsNameCommand) command;
                filterContainsNameCommand.setCollectionManager(collectionManager);
                return filterContainsNameCommand.execute();
            }
            case HEAD: {
                HeadCommand headCommand = (HeadCommand) command;
                headCommand.setCollectionManager(collectionManager);
                return headCommand.execute();
            }
            case HISTORY: {
                HistoryCommand historyCommand = (HistoryCommand) command;
                return historyCommand.execute();
            }
            case INFO: {
                InfoCommand infoCommand = (InfoCommand) command;
                infoCommand.setCollectionManager(collectionManager);
                return infoCommand.execute();
            }
            case PRINT_FIELD_DESCENDING_SEMESTER: {
                PrintFieldDescendingSemesterCommand printFieldDescendingSemesterCommand = (PrintFieldDescendingSemesterCommand) command;
                printFieldDescendingSemesterCommand.setCollectionManager(collectionManager);
                return printFieldDescendingSemesterCommand.execute();
            }
            case PRINT_UNIQUE_GROUP_ADMIN: {
                PrintUniqueGroupAdminCommand printUniqueGroupAdminCommand = (PrintUniqueGroupAdminCommand) command;
                printUniqueGroupAdminCommand.setCollectionManager(collectionManager);
                return printUniqueGroupAdminCommand.execute();
            }
            case SHOW: {
                ShowCommand showCommand = (ShowCommand) command;
                showCommand.setCollectionManager(collectionManager);
                return showCommand.execute();
            }
            case UPDATE_BY_ID: {
                UpdateByIdCommand updateByIdCommand = (UpdateByIdCommand) command;
                updateByIdCommand.setCollectionManager(collectionManager);
                return updateByIdCommand.execute();
            }
            case CHECK_ID: {
                CheckIdCommand checkIdCommand=(CheckIdCommand) command;
                checkIdCommand.setCollectionManager(collectionManager);
                return checkIdCommand.execute();
            }
            case CONNECT: {
                Connect connect = (Connect) command;
                return connect.execute();
            }
            case REMOVE_BY_ID: {
                RemoveByIdCommand removeByIdCommand = (RemoveByIdCommand) command;
                removeByIdCommand.setCollectionManager(collectionManager);
                return removeByIdCommand.execute();
            }

        }
        return false;
    }

    public static String messageFlush() {
        String output = Module.outputMessage;
        Module.outputMessage = "";
        return output;
    }

    public static void addMessage(String msg) {
        outputMessage += msg + "\n";
    }

    public static void setCollectionManager(CollectionManager collectionManager) {
        Module.collectionManager = collectionManager;
    }


    public static void setCommandManager(CommandManager commandManager) {
        Module.commandManager = commandManager;
        System.out.println("Command manager is set" + commandManager);
    }

    public static CommandManager getCommandManager() {
        return commandManager;
    }

    public static CollectionManager getCollectionManager() {
        return collectionManager;
    }
}


