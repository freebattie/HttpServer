package no.kristiania.http;

import java.io.IOException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class HttpRequestResult  {
    private  HttpMessage response;

    private final String host;
    private final int port;
    private final String requestTarget;


    public HttpRequestResult(String host, int port, String requestTarget) throws IOException {
        System.out.println("host: "+ host);
        System.out.println("port: " +port);
        this.host = host;
        this.port = port;
        this.requestTarget = requestTarget;

    }

    public String getHeader(String HeaderName) {
        return  response.getHeader(HeaderName);
    }

    public int getContentLength() {
        return response.contentLength;
    }

    public String getBody() {

        return response.body;
    }


    public HttpMessage executeRequest() throws IOException {
        var socket = new Socket(host, port);
        String req = "GET " + requestTarget + " HTTP/1.1\r\n" +
                "Host: " + host + "\r\n" +
                "\r\n";
        socket.getOutputStream().write(
                (req.getBytes(StandardCharsets.UTF_8))
        );
        response = new HttpMessage(socket.getInputStream());
        return response;

    }
}
