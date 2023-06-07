package commands;

import utilities.Module;

import java.io.IOException;
import java.util.Map;


public class LogOutCommand extends Command{
    private boolean isLogOut;

    public LogOutCommand(){
        super("log_out", "You log out after this cmd", true);
        this.isLogOut=false;

    }
    @Override
    public boolean execute() throws IOException {
        isLogOut=true;
        Module.addMessage("You've log out");
        return true;
    }

    public void setLogOut(boolean logOut) {
        isLogOut = logOut;
    }
    public boolean getIsLogOut(){
        return isLogOut;
    }
}
