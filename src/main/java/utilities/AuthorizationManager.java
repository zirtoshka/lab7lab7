package utilities;

import IO.ConsoleManager;
import IO.ScannerManager;
import data.User;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class AuthorizationManager {
    private Scanner scanner;
    public AuthorizationManager(Scanner scanner){
        this.scanner=scanner;
    }
    public User run() {
        try {
            if(askSign()){
            ConsoleManager.printInfoCyan("Enter your login: ");
            String login = scanner.nextLine().trim();
            ConsoleManager.printInfoCyan("Enter your password: ");
            String rawPassowrd = scanner.nextLine().trim();
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            byte[] messageDigest = md.digest(rawPassowrd.getBytes());
            return new User(login,"ddddd", true);}
            //todo add sluchay
        }catch (NoSuchAlgorithmException e){
            ConsoleManager.printError("Authorization declined( beee");
        }
        return null;
    }
    public boolean askSign(){
        while (true){
            ConsoleManager.printInfoCyan("If you want to sign up, enter 'up'");
            ConsoleManager.printInfoCyan("If you want to sign in, enter 'in'");
            String answer=scanner.nextLine().toLowerCase().trim();
            if(answer.equals("up")){
                return true;
            }else if(answer.equals("in")){
                return false;
            }
            ConsoleManager.printError("What do you want??? I don't understand((");
        }
    }
}
