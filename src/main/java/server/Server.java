package server;


import IO.ConsoleManager;
import commands.Command;
import exceptions.PropertiesException;
import utilities.*;
import utilities.Module;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import static utilities.PropHelper.*;

public class Server {
    private int port;
    private Socket socket;
    private ServerSocket server;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;
    private InputStream stream;
    private final int DEFAULT_PORT=2023;

    public Server() throws PropertiesException {
        this.port = DEFAULT_PORT;
        boolean connect = false;
        while (!connect) {
            try {
                server = new ServerSocket(port);
//                datagramSocket=new DatagramSocket(port);
                connect = true;
                ConsoleManager.printInfoPurple( "The server is up and accessible by port " + port );
            } catch (Exception e) {
                port = (int) (Math.random() * 20000 + 10000);
            }
        }
        stream = System.in;
        getProperties();
        DataBaseHandler dataBaseHandler = new DataBaseHandler(PropHelper.getHost(), PropHelper.getPort(), PropHelper.getUser(), PropHelper.getPassword(), PropHelper.getBaseName());
        DataBaseUserManager dataBaseUserManager = new DataBaseUserManager(dataBaseHandler);
        DataBaseCollectionManager dataBaseCollectionManager = new DataBaseCollectionManager(dataBaseHandler,dataBaseUserManager);
        CollectionManager collectionManager = new CollectionManager(dataBaseCollectionManager);
        Module.setCollectionManager(collectionManager);
    }

    public void runServer() {
        try {
            connect();
            Command command = null;
            while (command == null) {
                try {
                    command = (Command) getObject();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            boolean result = Module.runningCmd(command);
            if (result) {
                Module.addMessage("Execution is successful");

            } else {
                Module.addMessage("The command could not be executed ((");
            }
            sendObject(Module.messageFlush());
        } catch (IOException  e) {
            e.printStackTrace();
        }
        try {
            if (stream.available() > 0) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

            }
        } catch (IOException  e) {
            e.printStackTrace();
        }
    }

    private void connect() throws IOException {
        socket = server.accept();
    }

    private Object getObject() throws IOException, ClassNotFoundException {
        inputStream = new ObjectInputStream(socket.getInputStream());
        return inputStream.readObject();
    }

    public void close() throws IOException {
        server.close();
    }

    private void sendObject(Object o) throws IOException {
        outputStream = new ObjectOutputStream(socket.getOutputStream());
        outputStream.writeObject(o);
        outputStream.flush();
    }
}
