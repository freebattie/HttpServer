package no.kristiania.http;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;


class HttpServerTest {

    @Test
    void shouldReadResponseCode() throws IOException {
        HttpServer server = new HttpServer(0);
        server.start();
        int port = server.getActualPort();
        var client = new HttpRequestResult("localhost", port, "/hello");
        HttpMessage response = client.executeRequest();

        assertEquals(200, response.getResponseCode());
    }
    @Test
    void shouldParseRequestParameters() throws IOException {
        HttpServer server = new HttpServer(0);
        server.start();
        int port = server.getActualPort();
        var client = new HttpRequestResult("localhost", port, "/not-such-file");
        var response = client.executeRequest();
        assertEquals(404, response.getResponseCode());
    }
}