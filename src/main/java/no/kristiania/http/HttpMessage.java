package no.kristiania.http;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.TreeMap;

public class HttpMessage {
    private final String startLine;
    public final Map<String,String> headers;
    String body;
    int contentLength;
    public HttpMessage(InputStream inputStream) throws IOException {
        startLine = readLine(inputStream);
        headers = readHeaders(inputStream);

        if (getHeader("Content-Length") != null) {
            contentLength = Integer.parseInt(getHeader("Content-Length"));
            body = readBody(inputStream);
        }
    }

    private String readBody(InputStream inputStream) throws IOException {
        var body = new byte[contentLength];
        for (int i = 0; i < body.length; i++) {
            body[i] = (byte) inputStream.read();
        }
        return new String(body, StandardCharsets.UTF_8);
    }

    protected String getHeader(String headerName) {
        return headers.get(headerName);
    }

    private Map<String, String> readHeaders(InputStream inputStream) throws IOException {
        Map<String, String> headers = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        String headerLine;
        while(!(headerLine = readLine(inputStream)).isEmpty()) {
            String[] parts = headerLine.split(":\\s*");
            headers.put(parts[0], parts[1]);
        }
        return headers;
    }

    private String readLine(InputStream inputStream) throws IOException {
        StringBuilder line = new StringBuilder();
        int c;
        while ((c = inputStream.read()) != '\r') {
            line.append((char)c);
        }
        c = inputStream.read(); // read the next \n
        return line.toString();
    }

    public String getStartLine() {
        return startLine;
    }
}
