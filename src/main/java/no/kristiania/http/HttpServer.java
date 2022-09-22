package no.kristiania.http;


import java.io.IOException;
import java.net.ServerSocket;

import java.nio.charset.StandardCharsets;

public class HttpServer   {
    private final ServerSocket serverSocket;


    HttpServer(int port) throws IOException {
        serverSocket = new ServerSocket(port);

    }
    public void handelClients() throws IOException {
        var clientSocket = serverSocket.accept();




        var body ="t";
        var contentLength = body.getBytes().length;
        clientSocket.getOutputStream().write(("HTTP/1.1 200 OK\r\n" +
                "Content-Type: text/plain\r\n" +
                "Content-Length: " + contentLength + "\r\n"+
                "\r\n"
                + body).getBytes(StandardCharsets.UTF_8));

    }




    public int getActualPort() {
        return serverSocket.getLocalPort();

    }

    public void start() {
        new Thread(()->{
            // waits and blocks the current thread until a client tries to connect
        while (true){
            try {
                handelClients();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }


            // What the server writes to socket.getOutputStream,
            // the client can read from socket.getInputStream

        }).start();
    }
}
