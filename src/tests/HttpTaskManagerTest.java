package tests;

import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import server.clients.KVTaskClient;
import server.servers.KVServer;

import java.io.IOException;
import java.net.URI;

public class HttpTaskManagerTest {
    @Test
    public void standardTest() throws IOException {
        KVServer kvServer = new KVServer();
        kvServer.start();
        Gson gson = new Gson();
        try {
            KVTaskClient kvTaskClient = new KVTaskClient(URI.create("http://localhost:8070"));
            System.out.println(kvTaskClient.apiToken);
            String value = "saveThis";
            System.out.println(gson.toJson(value));
            kvTaskClient.put("1a23", gson.toJson(value));
            kvTaskClient.put("1a23", gson.toJson(value));
            kvTaskClient.put("1a23", gson.toJson(value));
            String answer = kvTaskClient.load("1a23");
            System.out.println(answer);
            answer = gson.fromJson(kvTaskClient.load("1a23"), String.class);
            System.out.println(answer);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        kvServer.stop();
    }
}
