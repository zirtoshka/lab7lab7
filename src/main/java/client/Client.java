package client;


import IO.ConsoleManager;
import commands.Connect;
import data.User;
import exceptions.Disconnect;

import static config.ConfigData.CAPACITY_BUFFER;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SocketChannel;

public class Client {

    private String host;
    private int port;
    private SocketChannel client;
    private Serializer serializer;
    private Deserializer deserializer;
    private ByteBuffer buffer;
    private DatagramChannel datagramChannel;
    private User user;

    public Client(String h, int p, User user) throws Disconnect, IOException{
        this.host = h;
        this.port = p;
        this.user=user;
        serializer = new Serializer();
        deserializer = new Deserializer();
        datagramChannel=DatagramChannel.open();
        datagramChannel.configureBlocking(false);
        buffer = ByteBuffer.allocate(CAPACITY_BUFFER);
        System.out.println(3);
        findServer();
        System.out.println(4);

    }

    public String run(Object o1) {
        String out = "";
        try {
            connect();
            sendObject(o1);
            out = (String) getObject();
            close();
        } catch (IOException  e) {
            System.out.println(e);
            return "ohh(( No connection with the server";
        }
        return out;
    }

    private void connect() throws IOException {
        client = SocketChannel.open(new InetSocketAddress(host, port));
        client.configureBlocking(false);
    }

    private void sendObject(Object object) throws IOException {
        client.write(serializer.serialize(object));
    }

    private Object getObject()  {
        while (true) {
            try {

                client.read(buffer);
                Object o = deserializer.deserialize(buffer);
                buffer = ByteBuffer.allocate(CAPACITY_BUFFER);
                return o;
        }catch (IOException | ClassNotFoundException e){
            }
    }}

    private void close() throws IOException {
        client.close();
    }

    private void findServer() throws Disconnect {
        ConsoleManager.printInfoPurple( "Connecting to the server...");
        System.out.println(1);
        String result = run(new Connect(user));
        System.out.println(result);
//        System.out.println(result.equals("Registration and authorization succeeded\nExecution is successful\n"));
        if (!(result.equals("Registration and authorization succeeded\nExecution is successful\n")||result.equals("Authorization succeeded\nExecution is successful\n"))) {
            ConsoleManager.printInfoPurple(result);
            throw new Disconnect("No connection");
        }
        ConsoleManager.printInfoPurple(result);
    }
//    public boolean waitReceive(DatagramChannel channel, ByteBuffer byteBuffer){
//        return waitReceive(channel, byteBuffer, 5);
//    }

//    private boolean waitReceive(DatagramChannel channel, ByteBuffer byteBuffer, int i) {
//    }
    //todo


}