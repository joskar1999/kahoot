package com.lecimy.fx.net;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class Client {

    private String ip;
    private int port;
    private String nick;
    private Socket socket;
    private PrintWriter printer;
    private DataOutputStream outputStream;

    private Client() {

    }

    private void enableNetworking() {
        try {
            this.socket = new Socket(this.ip, this.port);
            this.printer = new PrintWriter(socket.getOutputStream(), true);
            this.outputStream = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            System.out.println("Unable to create socket: " + e.getMessage());
        }
    }

    public void setConnectionParams(String ip, int port, String nick) {
        this.ip = ip;
        this.port = port;
        this.nick = nick;
        enableNetworking();
    }

    public void sendMessage(String message) {
        printer.print(message);
        printer.flush();
    }

    public void sendLength(int length) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(4);
        byte[] bytes = buffer.order(ByteOrder.LITTLE_ENDIAN).putInt(length).array();
        outputStream.write(bytes);
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public Socket getSocket() {
        return socket;
    }

    private static class ClientSingleton {
        private static final Client INSTANCE = new Client();
    }

    public static Client getInstance() {
        return ClientSingleton.INSTANCE;
    }
}
