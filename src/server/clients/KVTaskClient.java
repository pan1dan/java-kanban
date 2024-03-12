package server.clients;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static java.nio.charset.StandardCharsets.UTF_8;

public class KVTaskClient {
    private HttpClient client = HttpClient.newHttpClient();
    URI uri;
    public String apiToken;
    public KVTaskClient(URI uri) throws IOException, InterruptedException {
        this.uri = uri;
        HttpRequest requestRegister = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(uri + "/register"))
                .build();
        this.apiToken = client.send(requestRegister, HttpResponse.BodyHandlers.ofString()).body();
    }

    public void put(String key, String value){
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(value))
                .uri(URI.create(uri + "/save/" + key + "?API_TOKEN=" + apiToken))
                .build();

        try {
            HttpResponse<String> httpResponse = client.send(httpRequest, HttpResponse.BodyHandlers.ofString()); ////???
            System.out.println(httpResponse.statusCode());
        } catch (IOException | InterruptedException e) {
            e.getStackTrace();
        }

    }

    public String load(String key) {
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(uri + "/load/" + key + "?API_TOKEN=" + apiToken))
                .build();
        String body = null;
        try {
            HttpResponse<String> httpResponse = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            System.out.println(httpResponse.statusCode());
            body = httpResponse.body();
        } catch (IOException | InterruptedException e) {
            e.getStackTrace();
        }

        return body;
    }

    
}
