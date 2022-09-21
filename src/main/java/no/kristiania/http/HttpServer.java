package no.kristiania.http;


import java.io.IOException;
import java.net.ServerSocket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class HttpServer implements Runnable  {
    private final ServerSocket serverSocket;
    Path root = Path.of("");
    private boolean exit;

    HttpServer(int port) throws IOException {
        serverSocket = new ServerSocket(port);

    }
    public void handelClients() throws IOException {
        var clientSocket = serverSocket.accept();

        var request =new HttpMessage(clientSocket.getInputStream());
        String requestTarget = request.getStartLine().split(" ")[1];

        System.out.println(request.getStartLine());
        System.out.println(request.headers);


        var body = """
                <!DOCTYPE html>
                <html>
                <head>
                    <title>Start</title>
                </head>
                <body>

                <h1>Start new ÆØÅ</h1>
                <script>alert('test')</script>

                </body>
                </html>""";
        var contentLength = body.getBytes().length;
        clientSocket.getOutputStream().write(("HTTP/1.1 200 OK\r\n" +
                "Content-Type: text/html; charset=UTF-8\r\n" +
                "Content-Length: " + contentLength + "\r\n"+
                "\r\n"
                + body).getBytes(StandardCharsets.UTF_8));

    }
    public void stop()
    {
        exit = true;
    }
    public static void main(String[] args) throws IOException {

        new Thread(new HttpServer(9000)).run();


    }

    @Override
    public void run() {

        while (!exit){
            try {
                handelClients();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }
}
