package tests;
import com.google.gson.Gson;

import org.junit.jupiter.api.BeforeEach;
import server.servers.HttpTaskServer;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class HttpTaskServerTest {

    @BeforeEach
    public void init() throws IOException {
        HttpTaskServer taskServer = new HttpTaskServer();
    }

    @Test
    public void tess() throws IOException, InterruptedException {
        Gson gson = new Gson();
        HttpClient httpClient = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8078/tasks/task/");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .DELETE()
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());

    }

}