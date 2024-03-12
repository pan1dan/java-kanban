import com.google.gson.Gson;
import managers.Managers;
import server.clients.KVTaskClient;
import server.servers.HttpTaskServer;
import server.servers.KVServer;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;
import tasks.TaskStatuses;

import java.io.IOException;
import java.net.URI;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
//        KVServer kvServer = new KVServer();
//        kvServer.start();
//        Gson gson = new Gson();
//        try {
//            KVTaskClient kvTaskClient = new KVTaskClient(URI.create("http://localhost:8070"));
//            System.out.println(kvTaskClient.apiToken);
//            String value = "saveThis";
//            System.out.println(value);
//            kvTaskClient.put("123", value);
//            String answer = kvTaskClient.load("123");
//            System.out.println(answer);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }

        HttpTaskServer httpTaskServer = new HttpTaskServer();

    }
}