package no.kristiania.http;

import java.io.IOException;
import java.net.ServerSocket;
import java.nio.charset.StandardCharsets;

public class HttpServer {
    private final ServerSocket serverSocket;

    HttpServer(int port) throws IOException {
        serverSocket = new ServerSocket(port);

    }
    private void handelClients() throws IOException {
        var clientSocket = serverSocket.accept();

        var request =new HttpMessage(clientSocket.getInputStream());
        System.out.println(request.getStartLine());
        System.out.println(request.headers);


        var body = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "    <title>Start</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "\n" +
                "<h1>Start new</h1>\n" +
                "<script>alert('test')</script>\n"+
                "\n" +
                "</body>\n" +
                "</html>";
        var contentLength = body.getBytes().length;
        clientSocket.getOutputStream().write(("HTTP/1.1 200 OK\r\n" +
                "Content-Type: text/html; charset=UTF-8\r\n" +
                "Content-Length: " + contentLength + "\r\n"+
                "\r\n"
                + body).getBytes(StandardCharsets.UTF_8));

    }
    public static void main(String[] args) throws IOException {


    }
}
