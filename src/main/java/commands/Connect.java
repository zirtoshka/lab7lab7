package commands;

import IO.ConsoleManager;
import data.User;

import java.io.IOException;

public class Connect extends Command {
    private User user;
    public Connect(String name, String description, User user) {
        super(name, description);
        this.user=user;
    }

    @Override
    public boolean execute() throws IOException {
        ConsoleManager.printInfoGreen("A new client has connected to the server");
        return true;
    }
}
