package utilities;


import IO.ScannerManager;
import client.Client;
import commands.*;
import data.StudyGroup;
import data.User;
import exceptions.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

import static config.ConfigData.*;
import static data.StudyGroup.wrongId;
import static utilities.GeneratorRandomData.generateRandomGroup;

public class CommandManager {
    private final String runCmd = "Running the command ";

    public static final List<Command> commands = new LinkedList<>();

    private final List<String> script = new LinkedList<>();
    boolean runScript = false;
    Scanner scriptScanner = null;
    private final int NAME_CMD = 0;
    private final int ARG_CMD = 1;
    private final HelpCommand helpCmd;
    private final InfoCommand infoCmd;
    private final ShowCommand showCmd;
    private final AddCommand addCmd;
    private final UpdateByIdCommand updateByIdCmd;
    private final RemoveByIdCommand removeByIdCmd;
    private final ClearCommand clearCmd;
    private final SaveCommand saveCmd;
    private final ExecuteScriptCommand executeScriptCmd;
    private final ExitCommand exitCmd;
    private final HeadCommand headCmd;
    private final AddIfMaxCommand addIfMaxCmd;
    private final HistoryCommand historyCmd;
    private final FilterContainsNameCommand filterContainsNameCmd;
    private final PrintUniqueGroupAdminCommand printUniqueAdminCmd;
    private final PrintFieldDescendingSemesterCommand printFieldDescendingSemesterCmd;
    private final HistoryWriter historyWriter;
    private final CheckIdCommand checkIdCmd;


    private final Client client;

    public CommandManager(Client client, User user) {
        this.client = client;

        this.infoCmd = new InfoCommand();
        this.showCmd = new ShowCommand();
        this.addCmd = new AddCommand();
        this.updateByIdCmd = new UpdateByIdCommand();
        this.removeByIdCmd = new RemoveByIdCommand();
        this.clearCmd = new ClearCommand();
        this.saveCmd = new SaveCommand();
        this.executeScriptCmd = new ExecuteScriptCommand();
        this.exitCmd = new ExitCommand();
        this.addIfMaxCmd = new AddIfMaxCommand();
        this.historyWriter = new HistoryWriter();
        this.historyCmd = new HistoryCommand(historyWriter, NUMBER_OF_CMD);
        this.headCmd = new HeadCommand();
        this.filterContainsNameCmd = new FilterContainsNameCommand();
        this.printFieldDescendingSemesterCmd = new PrintFieldDescendingSemesterCommand();
        this.printUniqueAdminCmd = new PrintUniqueGroupAdminCommand();
        this.checkIdCmd=new CheckIdCommand();


        this.helpCmd = new HelpCommand(infoCmd, showCmd, addCmd, updateByIdCmd, removeByIdCmd, clearCmd, saveCmd,
                executeScriptCmd, exitCmd, headCmd, addIfMaxCmd, historyCmd, filterContainsNameCmd, printUniqueAdminCmd, printFieldDescendingSemesterCmd);
        commands.add(helpCmd);
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



    public void managerWork(String s) throws IncorrectScriptException, IncorrectValuesForGroupException, ExitingException{
        String[] data = cmdParser(s);
        switch (data[0]) {
            case HELP: {
                System.out.println(runCmd + helpCmd.getName() + " ...");
                System.out.println(client.run(helpCmd));
                historyWriter.addInHistory(HELP);
                break;
            }
            case INFO: {
                System.out.println(runCmd + infoCmd.getName() + " ...");
                System.out.println(client.run(infoCmd));
                historyWriter.addInHistory(INFO);
                break;
            }
            case ADD: {
                StudyGroup clientGroup;
                if (ScannerManager.askQuestion("Do you want to generate data for a new study group?", runScript,scriptScanner)){
                    clientGroup=generateRandomGroup();
                }else{
                 clientGroup= ScannerManager.askGroup(addCmd.getCollectionManager(),  runScript, scriptScanner);}
                System.out.println(runCmd + addCmd.getName() + " ...");
                addCmd.setArgGroup(clientGroup);
                System.out.println(client.run(addCmd));
                historyWriter.addInHistory(ADD);
                break;
            }
            case SHOW: {
                System.out.println(runCmd + showCmd.getName() + " ...");
                System.out.println(client.run(showCmd));
                historyWriter.addInHistory(SHOW);
                break;
            }
            case ADD_IF_MAX: {
                StudyGroup clientGroup = ScannerManager.askGroup(addIfMaxCmd.getCollectionManager(), runScript, scriptScanner);
                System.out.println(runCmd + addIfMaxCmd.getName() + " ...");
                addIfMaxCmd.setArgGroup(clientGroup);
                System.out.println(client.run(addIfMaxCmd));
                historyWriter.addInHistory(ADD_IF_MAX);
                break;
            }
            case CLEAR: {
                System.out.println(runCmd + clearCmd.getName() + " ...");
                System.out.println(client.run(clearCmd));
                historyWriter.addInHistory(CLEAR);
                break;
            }
            case HEAD: {
                System.out.println(runCmd + headCmd.getName() + " ...");
                System.out.println(client.run(headCmd));
                historyWriter.addInHistory(HEAD);
                break;
            }
            case REMOVE_BY_ID: {
                LinkedList<String> toId = new LinkedList<String>();
                int lengthData = data.length;
                boolean successGetId = false;
                Integer id = wrongId;
                while (!successGetId) {
                    try {
                        if (lengthData == 1) {
                            lengthData = 0;
                            throw new ArgsException();
                        }
                        if (lengthData > 1) {
                            toId.addLast(data[1]);
                            lengthData = 0;
                        }

                        id = Integer.parseInt(toId.getLast());
                        if (!(id > 0)) {
                            throw new NumberFormatException();
                        }
                        successGetId = true;
                    } catch (NumberFormatException e) {
                        System.out.println("It can't be id\nEnter id:");
                        toId.addLast(ScannerManager.askArgForCmd());
                    } catch (ArgsException e) {
                        System.out.println("what id is? why it is empty?\nEnter id:");
                        toId.addLast(ScannerManager.askArgForCmd());
                    }
                }
                System.out.println(runCmd + removeByIdCmd.getName() + " " + id + " ...");
                removeByIdCmd.setArgId(id);
                System.out.println(client.run(removeByIdCmd));
                historyWriter.addInHistory(REMOVE_BY_ID);
                break;
            }
            case EXIT: {
                System.out.println(runCmd + exitCmd.getName() + " ...");
                exitCmd.setSaveCommand(saveCmd);
                System.out.println(client.run(exitCmd));
                historyWriter.addInHistory(EXIT);
                throw new ExitingException();
            }
            case FILTER_CONTAINS_NAME: {
                LinkedList<String> toName = new LinkedList<String>();
                int lengthData = data.length;
                boolean successGetName = false;
                while (!successGetName) {
                    try {
                        if (lengthData == 1) {
                            lengthData = 0;
                            throw new ArgsException();
                        }
                        if (lengthData > 1) {
                            toName.addLast(data[1]);
                            lengthData = 0;
                        }
                        successGetName = true;
                    } catch (ArgsException e) {
                        System.out.println("What do I need to find??? why it is empty?\nEnter name:");
                        toName.addLast(ScannerManager.askArgForCmd());
                    }
                }
                System.out.println(runCmd + filterContainsNameCmd.getName() + " " + toName.getLast() + " ...");
                filterContainsNameCmd.setName(toName.getLast());
                System.out.println(client.run(filterContainsNameCmd));
                historyWriter.addInHistory(FILTER_CONTAINS_NAME);
                break;
            }
            case UPDATE_BY_ID: {
                LinkedList<String> toId = new LinkedList<String>();
                int lengthData = data.length;
                boolean successGetId = false;
                Integer id = wrongId;
                while (!successGetId) {
                    try {
                        if (lengthData == 1) {
                            lengthData = 0;
                            throw new ArgsException();
                        }
                        if (lengthData > 1) {
                            toId.addLast(data[1]);
                            lengthData = 0;
                        }

                        id = Integer.parseInt(toId.getLast());
                        if (!(id > 0)) {
                            throw new NumberFormatException();
                        }
                        successGetId = true;
                    } catch (NumberFormatException e) {
                        System.out.println("It can't be id\nEnter id:");
                        toId.addLast(ScannerManager.askArgForCmd());
                    } catch (ArgsException e) {
                        System.out.println("what id is? why it is empty?\nEnter id:");
                        toId.addLast(ScannerManager.askArgForCmd());
                    }
                }
                updateByIdCmd.setId(id);
                System.out.println(runCmd + updateByIdCmd.getName() + " " + id + " ...");
                checkIdCmd.setId(id);
                String res = client.run(checkIdCmd);
                if (res.contains("The command could not be executed ((")){
                    System.out.println(res);
                    break;
                }
                StudyGroup clientGroup = ScannerManager.askQuestionForUpdate(runScript, scriptScanner);
                updateByIdCmd.setArgGroup(clientGroup);
                System.out.println(client.run(updateByIdCmd));
                historyWriter.addInHistory(UPDATE_BY_ID);
                break;
            }
            case PRINT_FIELD_DESCENDING_SEMESTER: {
                System.out.println(runCmd + printFieldDescendingSemesterCmd.getName() + " ...");
                System.out.println(client.run(printFieldDescendingSemesterCmd));
                historyWriter.addInHistory(PRINT_FIELD_DESCENDING_SEMESTER);
                break;
            }
            case PRINT_UNIQUE_GROUP_ADMIN: {
                System.out.println(runCmd + printUniqueAdminCmd.getName() + " ...");
                System.out.println(client.run(printUniqueAdminCmd));
                historyWriter.addInHistory(PRINT_UNIQUE_GROUP_ADMIN);
                break;
            }
            case HISTORY: {
                System.out.println(runCmd + historyCmd.getName() + " ...");
                System.out.println(client.run(historyCmd));
                historyWriter.addInHistory(HISTORY);
                break;
            }case EXECUTE_SCRIPT: {
                LinkedList<String> toNameFile = new LinkedList<String>();
                int lengthData = data.length;
                boolean successGetFileName = false;
                while (!successGetFileName) {
                    try {
                        if (lengthData == 1) {
                            lengthData = 0;
                            throw new ArgsException();
                        }
                        if (lengthData > 1) {
                            toNameFile.addLast(data[1]);
                            lengthData = 0;
                        }
                        successGetFileName = true;
                    } catch (ArgsException e) {
                        System.out.println("What do I need to execute??? why it is empty?\nEnter fileName:");
                        toNameFile.addLast(ScannerManager.askArgForCmd());
                    }
                }
                System.out.println(runCmd + executeScriptCmd.getName() +" "+toNameFile.getLast()+ " ...");
                System.out.println(client.run(executeScriptCmd));
                historyWriter.addInHistory(EXECUTE_SCRIPT);
                scriptMode(toNameFile.getLast());
                break;}
            default:
                System.out.println("I don't know this command");
                break;
        }

    }



    public String[] cmdParser(String s) {
        try {
            Scanner scanner = new Scanner(s);
            if (!(s.indexOf(" ") == -1)) {
                scanner.useDelimiter("\\s");
                String command = scanner.next();
                String data = "";
                if (scanner.hasNext()) {
                    data = scanner.next();
                }
                return new String[]{command, data};
            } else {
                String commandwodata = scanner.next();
                return new String[]{commandwodata};
            }
        } catch (NoSuchElementException e) {
            return new String[]{"  "};
        }
    }

    public void scriptMode(String arg)  throws ExitingException{
        String path;
        String[] userCmd = {"", ""};
        script.add(arg);
        try {
            path = System.getenv("PWD") + "/" + arg;
            File file = new File(path);
            if (file.exists() && !file.canRead()) throw new NoAccessToFileException();
            Scanner scriptScanner = new Scanner(file);
            setScannerScript(scriptScanner);
            if (!scriptScanner.hasNext()) throw new NoSuchElementException();
            runScript = true;
            do {
                userCmd = (scriptScanner.nextLine().trim() + " ").split(" ", 2);
                userCmd[ARG_CMD] = userCmd[ARG_CMD].trim();
                while (scriptScanner.hasNextLine() && userCmd[NAME_CMD].isEmpty()) {
                    userCmd = (scriptScanner.nextLine().trim() + " ").split(" ", 2);
                    userCmd[ARG_CMD] = userCmd[ARG_CMD].trim();
                }
                System.out.println(inputCommand + String.join(" ", userCmd));
                if (userCmd[NAME_CMD].equals(EXECUTE_SCRIPT)) {
                    for (String scri : script) {
                        if (userCmd[ARG_CMD].equals(scri)) throw new ScriptRecurentException();
                    }
                }


                managerWork(userCmd[NAME_CMD]+" "+userCmd[ARG_CMD]);
                if (userCmd[NAME_CMD].equals(EXIT)){
                    System.exit(1);
                }

            } while (scriptScanner.hasNextLine());

        } catch (NoAccessToFileException e) {
            System.out.println("No rules");
        } catch (NoSuchElementException e) {
            System.out.println("I can't do anything with empty file");
        } catch (FileNotFoundException e) {
            System.out.println("No such file with script");
        } catch (ScriptRecurentException e) {
            System.out.println("Recurrent is cool, but I don't know how to use it");
        } catch (IncorrectScriptException e) {
            System.out.println("Script is incorrect");
        } catch (IncorrectValuesForGroupException e) {
            System.out.println("Script consists of incorrect data for group");
        } finally {
            script.remove(script.size() - 1);
        }
        runScript = false;
        setScannerScript(null);
    }
    private void setScannerScript(Scanner scanner){
        this.scriptScanner =scanner;
    }


}
