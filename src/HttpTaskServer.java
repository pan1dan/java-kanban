import com.google.gson.*;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import managers.Managers;
import managers.taskManager.InMemoryTasksManager.*;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;
import utils.Utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.http.HttpRequest;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Optional;

import static java.time.Month.APRIL;
import static java.time.Month.MARCH;

public class HttpTaskServer {
    private static final int PORT = 8080;

    public static void main(String[] args) throws IOException {
        HttpServer httpServer = HttpServer.create(new InetSocketAddress(PORT), 0);
        httpServer.createContext("/tasks", new TasksHandler());
        httpServer.createContext("/subtasks", new SubtasksHandler());
        httpServer.createContext("/epics", new EpicsHandler());
        httpServer.createContext("/history", new HistoryHandler());
        httpServer.createContext("/prioritized", new PrioritizedHandler());
        httpServer.start();
        System.out.println("HTTP-сервер запущен на " + PORT + " порту!");
    }
}

class TasksHandler implements HttpHandler { //Сделан
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        switch (method){
            case "GET": //Сделан
                getTaskOrTasks(exchange);
                break;
            case "POST": //Сделан
                addOrUpdateTask(exchange);
                break;
            case "DELETE": //сделано
                deleteTaskOrTasks(exchange);
                break;
            default:
                writeResponse(exchange, "Not found", 404);
        }
    }

    private void getTaskOrTasks(HttpExchange exchange) throws IOException {
        String[] splitPath = exchange.getRequestURI().getPath().split("/");
//        Gson gson = new Gson();
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new Utils.LocalDateTimeAdapter())
                .create();
        if (splitPath.length == 2) {
            writeResponse(exchange, gson.toJson(Managers.getDefault().getAllTasks()), 200);
            return;
        } else if (splitPath.length == 3) {
            Optional<Integer> taskIdOptional = getTaskId(exchange);
            if (taskIdOptional.isPresent()) {
                if (Managers.getDefault().getTaskByID(taskIdOptional.get()) != null) {
                    writeResponse(exchange, gson.toJson(Managers.getDefault().getTaskByID(taskIdOptional.get())), 200);
                    return;
                }
            }
        }
        writeResponse(exchange, "Not found", 404);
    }

    private void addOrUpdateTask(HttpExchange exchange) throws IOException {
        String[] splitPath = exchange.getRequestURI().getPath().split("/");
//        Gson gson = new Gson();
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new Utils.LocalDateTimeAdapter())
                .create();

        InputStream inputStream = exchange.getRequestBody();
        String body = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        JsonElement jsonElement = JsonParser.parseString(body);
        if(!jsonElement.isJsonObject()) {
            writeResponse(exchange, "Not Acceptable", 406);
            return;
        }
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        Task taskFromJson = gson.fromJson(jsonObject, Task.class);

        if (splitPath.length == 2) {
            Task newTask = Managers.getDefault().addNewTask(taskFromJson);
            if (newTask == null) {
                writeResponse(exchange, "Not Acceptable", 406);
                return;
            } else {
                writeResponse(exchange, gson.toJson(newTask), 200);
                return;
            }
        } else if (splitPath.length == 3) {
            Optional<Integer> taskIdOptional = getTaskId(exchange);
            if (taskIdOptional.isPresent()) {
                if (taskIdOptional.get() != taskFromJson.getID()) {
                    writeResponse(exchange, "Not Acceptable", 406);
                    return;
                }
                if (Managers.getDefault().getTaskByID(taskIdOptional.get()) != null) {
                    Task updateTask = Managers.getDefault().updateTask(taskFromJson);
                    if (updateTask != null) {
                        writeResponse(exchange, gson.toJson(updateTask), 200);
                    } else {
                        writeResponse(exchange, "Not Acceptable", 406);
                        return;
                    }
                }
            }
        }
        writeResponse(exchange, "Not found", 404);
    }

    private void deleteTaskOrTasks(HttpExchange exchange) throws IOException {
        String[] splitPath = exchange.getRequestURI().getPath().split("/");
        if (splitPath.length == 2) {
            Managers.getDefault().deleteAllTasks();
            try (OutputStream os = exchange.getResponseBody()){
                exchange.sendResponseHeaders(201, 0);
            }
            return;
        } else if (splitPath.length == 3) {
            Optional<Integer> taskIdOptional = getTaskId(exchange);
            if (taskIdOptional.isPresent()) {
                if (Managers.getDefault().getTaskByID(taskIdOptional.get()) != null) {
                    Managers.getDefault().deleteTaskByID(taskIdOptional.get());
                    try (OutputStream os = exchange.getResponseBody()){
                        exchange.sendResponseHeaders(201, 0);
                    }
                    return;
                }
            }
        }
        writeResponse(exchange, "Not found", 404);
    }

    private Optional<Integer> getTaskId(HttpExchange exchange) {
        String[] splitPath = exchange.getRequestURI().getPath().split("/");
        try {
            return Optional.of(Integer.parseInt(splitPath[2]));
        } catch (NumberFormatException exception) {
            return Optional.empty();
        }
    }

    private void writeResponse(HttpExchange exchange, String responseString, int responseCode) throws  IOException{
        try (OutputStream os = exchange.getResponseBody()){
            exchange.sendResponseHeaders(responseCode, 0);
            os.write(responseString.getBytes(StandardCharsets.UTF_8));
        }
    }
}

class SubtasksHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        switch (method){
            case "GET": // Complete
                getSubtaskOrSubtasks(exchange);
                break;
            case "POST": // Complete
                addOrUpdateSubtask(exchange);
                break;
            case "DELETE": // Complete
                deleteSubtaskOrSubtasks(exchange);
                break;
            default:
                writeResponse(exchange, "Not found", 404);
        }
    }

    private void getSubtaskOrSubtasks(HttpExchange exchange) throws IOException {
        String[] splitPath = exchange.getRequestURI().getPath().split("/");
//        Gson gson = new Gson();
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new Utils.LocalDateTimeAdapter())
                .create();
        if (splitPath.length == 2) {
            writeResponse(exchange, gson.toJson(Managers.getDefault().getAllSubtasks()), 200);
            return;
        } else if (splitPath.length == 3) {
            Optional<Integer> subtaskIdOptional = getSubtaskId(exchange);
            if (subtaskIdOptional.isPresent()) {
                if (Managers.getDefault().getSubtasksByID(subtaskIdOptional.get()) != null) {
                    writeResponse(exchange, gson.toJson(Managers.getDefault().getSubtasksByID(subtaskIdOptional.get())), 200);
                    return;
                }
            }
        }
        writeResponse(exchange, "Not found", 404);
    }

    private void addOrUpdateSubtask(HttpExchange exchange) throws IOException {
        String[] splitPath = exchange.getRequestURI().getPath().split("/");
//        Gson gson = new Gson();
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new Utils.LocalDateTimeAdapter())
                .create();

        InputStream inputStream = exchange.getRequestBody();
        String body = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        JsonElement jsonElement = JsonParser.parseString(body);
        if(!jsonElement.isJsonObject()) {
            writeResponse(exchange, "Not Acceptable", 406);
            return;
        }
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        Subtask subtaskFromJson = gson.fromJson(jsonObject, Subtask.class);

        if (splitPath.length == 2) {
            Subtask newSubtask = Managers.getDefault().addNewSubtask(subtaskFromJson);
            if (newSubtask == null) {
                writeResponse(exchange, "Not Acceptable", 406);
                return;
            } else {
                writeResponse(exchange, gson.toJson(newSubtask), 200);
                return;
            }
        } else if (splitPath.length == 3) {
            Optional<Integer> subtaskIdOptional = getSubtaskId(exchange);
            if (subtaskIdOptional.isPresent()) {
                if (subtaskIdOptional.get() != subtaskFromJson.getID()) {
                    writeResponse(exchange, "Not Acceptable", 406);
                    return;
                }
                if (Managers.getDefault().getSubtasksByID(subtaskIdOptional.get()) != null) {
                    Subtask updateSubtask = Managers.getDefault().updateSubtask(subtaskFromJson);
                    if (updateSubtask != null) {
                        writeResponse(exchange, gson.toJson(updateSubtask), 200);
                    } else {
                        writeResponse(exchange, "Not Acceptable", 406);
                        return;
                    }
                }
            }
        }
        writeResponse(exchange, "Not found", 404);
    }

    private void deleteSubtaskOrSubtasks(HttpExchange exchange) throws IOException {
        String[] splitPath = exchange.getRequestURI().getPath().split("/");
        if (splitPath.length == 2) {
            Managers.getDefault().deleteAllSubtasks();
            try (OutputStream os = exchange.getResponseBody()){
                exchange.sendResponseHeaders(201, 0);
            }
            return;
        } else if (splitPath.length == 3) {
            Optional<Integer> subtaskIdOptional = getSubtaskId(exchange);
            if (subtaskIdOptional.isPresent()) {
                if (Managers.getDefault().getSubtasksByID(subtaskIdOptional.get()) != null) {
                    Managers.getDefault().deleteSubtaskByID(subtaskIdOptional.get());
                    try (OutputStream os = exchange.getResponseBody()){
                        exchange.sendResponseHeaders(201, 0);
                    }
                    return;
                }
            }
        }
        writeResponse(exchange, "Not found", 404);
    }

    private Optional<Integer> getSubtaskId(HttpExchange exchange) {
        String[] splitPath = exchange.getRequestURI().getPath().split("/");
        try {
            return Optional.of(Integer.parseInt(splitPath[2]));
        } catch (NumberFormatException exception) {
            return Optional.empty();
        }
    }

    private void writeResponse(HttpExchange exchange, String responseString, int responseCode) throws  IOException{
        try (OutputStream os = exchange.getResponseBody()){
            exchange.sendResponseHeaders(responseCode, 0);
            os.write(responseString.getBytes(StandardCharsets.UTF_8));
        }
    }
}

class EpicsHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        switch (method){
            case "GET":
                getEpicOrEpicsOrEpicSubtasks(exchange);
                break;
            case "POST": //Complete
                addOrUpdateEpic(exchange);
                break;
            case "DELETE": //Complete
                deleteEpicOrEpics(exchange);
                break;
            default:
                writeResponse(exchange, "Not found", 404);
        }
    }

    public void getEpicOrEpicsOrEpicSubtasks(HttpExchange exchange) throws IOException {
        String[] splitPath = exchange.getRequestURI().getPath().split("/");
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new Utils.LocalDateTimeAdapter())
                .create();
        //Gson gson = new Gson();
        if (splitPath.length == 2) {
            writeResponse(exchange, gson.toJson(Managers.getDefault().getAllEpics()), 200);
            return;
        } else if (splitPath.length == 3) {
            Optional<Integer> epicIdOptional = getEpicId(exchange);
            if (epicIdOptional.isPresent()) {
                if (Managers.getDefault().getEpicByID(epicIdOptional.get()) != null) {
                    writeResponse(exchange, gson.toJson(Managers.getDefault().getEpicByID(epicIdOptional.get())), 200);
                    return;
                }
            }
        } else if (splitPath.length == 4) {
            if (!splitPath[3].equals("subtasks")) {
                writeResponse(exchange, "Not found", 406);
            }
            Optional<Integer> epicIdOptional = getEpicId(exchange);
            if (epicIdOptional.isPresent()) {
                if (Managers.getDefault().getEpicByID(epicIdOptional.get()) != null) {
                    writeResponse(exchange, gson.toJson(Managers.getDefault().getAllEpicSubtasks(epicIdOptional.get())), 200);
                    return;
                }
            }

        }
        writeResponse(exchange, "Not found", 404);
    }

    public void addOrUpdateEpic(HttpExchange exchange) throws IOException{
        String[] splitPath = exchange.getRequestURI().getPath().split("/");
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new Utils.LocalDateTimeAdapter())
                .create();
        //Gson gson = new Gson();
        InputStream inputStream = exchange.getRequestBody();
        String body = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        JsonElement jsonElement = JsonParser.parseString(body);
        if(!jsonElement.isJsonObject()) {
            writeResponse(exchange, "Not Acceptable", 406);
            return;
        }
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        Epic epicFromJson = gson.fromJson(jsonObject, Epic.class);

        if (splitPath.length == 2) {
            Epic newEpic = Managers.getDefault().addNewEpic(epicFromJson);
            if (newEpic == null) {
                writeResponse(exchange, "Not Acceptable", 406);
                return;
            } else {
                writeResponse(exchange, gson.toJson(newEpic), 200);
                return;
            }
        } else if (splitPath.length == 3) {
            Optional<Integer> epicIdOptional = getEpicId(exchange);
            if (epicIdOptional.isPresent()) {
                if (epicIdOptional.get() != epicFromJson.getID()) {
                    writeResponse(exchange, "Not Acceptable", 406);
                    return;
                }
                if (Managers.getDefault().getEpicByID(epicIdOptional.get()) != null) {
                    Epic updateEpic = Managers.getDefault().updateEpic(epicFromJson);
                    if (updateEpic != null) {
                        writeResponse(exchange, gson.toJson(updateEpic), 200);
                        return;
                    } else {
                        writeResponse(exchange, "Not Acceptable", 406);
                        return;
                    }
                }
            }
        }
        writeResponse(exchange, "Not found", 404);
    }

    private Optional<Integer> getEpicId(HttpExchange exchange) {
        String[] splitPath = exchange.getRequestURI().getPath().split("/");
        try {
            return Optional.of(Integer.parseInt(splitPath[2]));
        } catch (NumberFormatException exception) {
            return Optional.empty();
        }
    }

    public void deleteEpicOrEpics(HttpExchange exchange) throws IOException{
        String[] splitPath = exchange.getRequestURI().getPath().split("/");
        if (splitPath.length == 2) {
            Managers.getDefault().deleteAllEpics();
            try (OutputStream os = exchange.getResponseBody()){
                exchange.sendResponseHeaders(201, 0);
            }
            return;
        } else if (splitPath.length == 3) {
            Optional<Integer> epicIdOptional = getEpicId(exchange);
            if (epicIdOptional.isPresent()) {
                if (Managers.getDefault().getEpicByID(epicIdOptional.get()) != null) {
                    Managers.getDefault().deleteEpicByID(epicIdOptional.get());
                    try (OutputStream os = exchange.getResponseBody()){
                        exchange.sendResponseHeaders(201, 0);
                    }
                    return;
                }
            }
        }
        writeResponse(exchange, "Not found", 404);
    }

    private void writeResponse(HttpExchange exchange, String responseString, int responseCode) throws  IOException{
        try (OutputStream os = exchange.getResponseBody()){
            exchange.sendResponseHeaders(responseCode, 0);
            os.write(responseString.getBytes(StandardCharsets.UTF_8));
        }
    }

}

class HistoryHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        if (method.equals("GET")) {
            Gson gson = new Gson();
            writeResponse(exchange, gson.toJson(Managers.getDefaultHistory().getHistory()), 200);
        } else {
            writeResponse(exchange, "Not found", 404);
        }
    }

    private void writeResponse(HttpExchange exchange, String responseString, int responseCode) throws  IOException{
        try (OutputStream os = exchange.getResponseBody()){
            exchange.sendResponseHeaders(responseCode, 0);
            os.write(responseString.getBytes(StandardCharsets.UTF_8));
        }
    }
}

class PrioritizedHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        if (method.equals("GET")) {
            Gson gson = new Gson();
            writeResponse(exchange, gson.toJson(Managers.getDefault().getPrioritizedTasks()), 200);
        } else {
            writeResponse(exchange, "Not found", 404);
        }
    }

    private void writeResponse(HttpExchange exchange, String responseString, int responseCode) throws  IOException{
        try (OutputStream os = exchange.getResponseBody()){
            exchange.sendResponseHeaders(responseCode, 0);
            os.write(responseString.getBytes(StandardCharsets.UTF_8));
        }
    }
}
