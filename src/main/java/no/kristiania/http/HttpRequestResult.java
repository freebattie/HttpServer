package no.kristiania.http;

import java.io.IOException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class HttpRequestResult {
    private final HttpMessage response;
    private final int status;
    private final String reasonPhrase;


    public HttpRequestResult(String host, int port, String requestTarget) throws IOException {
        var socket = new Socket(host, port);
        String req = "GET " + requestTarget + " HTTP/1.1\r\n" +
                "Connection: close\r\n" +
                "Host: " + host + "\r\n" +
                "\r\n";
        socket.getOutputStream().write(
                (req.getBytes(StandardCharsets.UTF_8))
        );
        response = new HttpMessage(socket.getInputStream());
        String[] responseStatusLine = response.getStartLine().split(" ",3);
        status = Integer.parseInt(responseStatusLine[1]);
        reasonPhrase = responseStatusLine[2];

    }

    public String getHeader(String HeaderName) {
        return  response.getHeader(HeaderName);
    }
    public int getStatus(){
        return status;
    }
    public int getContentLength() {
        return response.contentLength;
    }

    public String getBody() {

        return response.body;
    }
    public static void main(String[] args) throws IOException {
        var client = new HttpRequestResult("127.0.0.1", 9000, "/test");
        System.out.println(client.status);
        System.out.println(client.getBody());
    }
}
