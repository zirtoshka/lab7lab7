package client;


import IO.ConsoleManager;
import IO.ScannerManager;
import exceptions.ExitingException;
import exceptions.IncorrectScriptException;
import exceptions.IncorrectValuesForGroupException;
import utilities.CommandManager;

import java.io.IOException;
import java.util.Scanner;


public class Console {

    public static void run(CommandManager commandManager) throws IncorrectValuesForGroupException, IncorrectScriptException, IOException {
        ConsoleManager.printInfoGreen("App is working, to get more information enter \"help\"" );
        String input;
        while (true) {
            try {
                input = ScannerManager.askCommand();
                commandManager.managerWork(input);
            } catch (ExitingException e) {
                break;
            }
        }

    }
}
