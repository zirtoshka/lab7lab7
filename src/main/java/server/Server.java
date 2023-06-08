package server;


import IO.ConsoleManager;
import commands.Command;
import exceptions.PropertiesException;
import utilities.*;
import utilities.Module;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

import static utilities.PropHelper.*;

public class Server {
    private int port;
    private Socket socket;
    private ServerSocket server;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;
    private int portForAns;
    private InetAddress hostForAns;
    private InputStream stream;
    private final int DEFAULT_PORT = 2023;
    private Command command;

    public Server() throws PropertiesException {
        this.port = DEFAULT_PORT;
        boolean connect = false;
        while (!connect) {
            try {
                server = new ServerSocket(port);
//                datagramSocket=new DatagramSocket(port);
                connect = true;
                ConsoleManager.printInfoPurple("The server is up and accessible by port " + port);
            } catch (Exception e) {
                port = (int) (Math.random() * 20000 + 10000);
            }
        }
        stream = System.in;
        getProperties();
        DataBaseHandler dataBaseHandler = new DataBaseHandler(PropHelper.getHost(), PropHelper.getPort(), PropHelper.getUser(), PropHelper.getPassword(), PropHelper.getBaseName());
        DataBaseUserManager dataBaseUserManager = new DataBaseUserManager(dataBaseHandler);
        DataBaseCollectionManager dataBaseCollectionManager = new DataBaseCollectionManager(dataBaseHandler, dataBaseUserManager);
        CollectionManager collectionManager = new CollectionManager(dataBaseCollectionManager);
        Module.setCollectionManager(collectionManager);
    }

    public void runServer() {
//        try {
            ForkJoinPool pool = ForkJoinPool.commonPool();
            pool.invoke(new ForkJoinTask<Object>() {
                @Override
                public Object getRawResult() {
                    return null;
                }

                @Override
                protected void setRawResult(Object value) {

                }

                @Override
                protected boolean exec() {

                    Object o = null;
                    try {
                        connect();
                        while (o == null) {
                            o = getObject();
                            command = (Command) o;
                        }B b =getB();
                        b.setHostForAns2(hostForAns);
                        b.setPortForAns2(portForAns);
                        new Thread(b).start();
                        return true;
                    }catch (ClassNotFoundException | IOException e){
                        e.printStackTrace();
                        return true;
                    }
                }
            });

    }

    private B getB() {
        return new B();
    }

    class B implements Runnable {
        private int portForAns2;
        private InetAddress hostForAns2;

        public void setPortForAns2(int portForAns2) {
            this.portForAns2 = portForAns2;
        }

        public void setHostForAns2(InetAddress hostForAns2) {
            this.hostForAns2 = hostForAns2;
        }

        @Override
        public void run() {
            try {
//                connect();
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
                ExecutorService service = Executors.newCachedThreadPool();
                C c = getC();
                c.setHostForAns3(hostForAns2);
                c.setPortForAns3(portForAns2);
                service.execute(c);
                service.shutdown();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public C getC() {
            return new C();
        }

        class C implements Runnable {

            private int portForAns3;
            private InetAddress hostForAns3;

            public void setPortForAns3(int portForAns3) {
                this.portForAns3 = portForAns3;
            }

            public void setHostForAns3(InetAddress hostForAns3) {
                this.hostForAns3 = hostForAns3;
            }

            @Override
            public void run() {
                try {
                    sendObject(Module.messageFlush(), hostForAns3, portForAns3);
                } catch (IOException ignore) {
                    //ignore
                }
            }
        }
    }

//    public void runServer() {
//        try {
//            connect();
//            Command command = null;
//            while (command == null) {
//                try {
//                    command = (Command) getObject();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//            boolean result = Module.runningCmd(command);
//            if (result) {
//                Module.addMessage("Execution is successful");
//
//            } else {
//                Module.addMessage("The command could not be executed ((");
//            }
//            sendObject(Module.messageFlush());
//        } catch (IOException  e) {
//            e.printStackTrace();
//        }
//        try {
//            if (stream.available() > 0) {
//                BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
//
//            }
//        } catch (IOException  e) {
//            e.printStackTrace();
//        }
//    }

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

    private void sendObject(Serializable o, InetAddress host, int port) throws IOException {
        outputStream = new ObjectOutputStream(socket.getOutputStream());
        outputStream.writeObject(o);
        outputStream.flush();
    }
}
