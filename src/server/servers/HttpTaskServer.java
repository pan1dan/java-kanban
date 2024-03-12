package server.servers;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import managers.Managers;
import managers.taskManager.FileBackedTasksManager;
import tasks.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.function.Predicate;

public class HttpTaskServer {
    //Задачи передаются в теле запроса в формате json
    // Поменять коды
    // Разобраться с update
    private static final int PORT = 8080;
    private static final String PATH = "/tasks";

    HttpServer httpServer;
    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;
    private FileBackedTasksManager managerFileBacked = Managers.getDefaultFileBackedTasksManager();
    //HttpServer httpServer;
    //Gson gson;

    public HttpTaskServer() throws IOException{
        httpServer = HttpServer.create(new InetSocketAddress(PORT), 0);

        //gson = new Gson();
        //httpServer = HttpServer.create(new InetSocketAddress(PORT), 0);

    }
    public static void main(String[] args) throws IOException {
        HttpServer httpServer = HttpServer.create(new InetSocketAddress(PORT), 0);
        httpServer.createContext(PATH, new TasksHandler());
        httpServer.start(); // запускаем сервер

        System.out.println("HTTP-сервер запущен на " + PORT + " порту!");

        //---------------------------------------------------------------------------------------------------------//
        //FileBackedTasksManager fileBackedTasksManager1 = new FileBackedTasksManager(
          //      new File(System.getProperty("user.dir") + "\\history.txt"));
        //---------------------------------------------------------------------------------------------------------//
    }

    static class TasksHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            System.out.println("Началась обработка /tasks запроса от клиента.");
            String method = exchange.getRequestMethod();
            String path = exchange.getRequestURI().getPath();
            String query = exchange.getRequestURI().getQuery();
            String response = "";

            switch (method) {
                case "GET":
                    handleGet(exchange, path, query);
                    break;
                case "POST":
                    handlePost(exchange, path);
                    break;
                case "DELETE":
                    handleDelete(exchange, path, query);
                    break;
                default:
                    writeResponse(exchange, "Данный метод отсутствует", 405);
            }






//            try (OutputStream os = httpExchange.getResponseBody()) {
//                os.write(response.getBytes());
//            }
            //---------------------------------------------------------------------------------------------------------//



        }

        private void handleGet(HttpExchange exchange, String path, String query) throws IOException {
            Gson gson = new Gson();
            int statusCode;
            String response;

            if (query == null) {
                switch (path){
                    case "/tasks/history":
                        response = gson.toJson(Managers.getDefaultHistory());
                        statusCode = 200;
                        break;
                    case "/tasks/task/":
                        response = gson.toJson(managerFileBacked.getAllTasks());
                        statusCode = 200;
                        break;
                    case "/tasks/epic/":
                        response = gson.toJson(managerFileBacked.getAllEpics());
                        statusCode = 200;
                        break;
                    case "/tasks/subtask/":
                        response = gson.toJson(managerFileBacked.getAllSubtasks());
                        statusCode = 200;
                        break;
                    case "/tasks":
                        ArrayList<Task> allTasks = new ArrayList<>();
                        allTasks.addAll(managerFileBacked.getAllTasks());
                        allTasks.addAll(managerFileBacked.getAllEpics());
                        allTasks.addAll(managerFileBacked.getAllSubtasks());
                        response = gson.toJson(allTasks);
                        statusCode = 200;
                        break;
                    default:
                        response = "Not Found";
                        statusCode = 404;
                }
            } else {
                int id = Integer.parseInt(query.split("=")[1]);
                path = path.substring(0, path.length() - (query.length() + 1));
                switch (path) {
                    case "/tasks/task/":
                        if (managerFileBacked.getTaskByID(id) != null) {
                            response = gson.toJson(managerFileBacked.getTaskByID(id));
                            statusCode = 200;
                        } else {
                            response = null;
                            statusCode = 400;
                        }
                        break;
                    case "/tasks/epic/":
                        if (managerFileBacked.getEpicByID(id) != null) {
                            response = gson.toJson(managerFileBacked.getEpicByID(id));
                            statusCode = 200;
                        } else {
                            response = null;
                            statusCode = 400;
                        }
                        break;
                    case "/tasks/subtask/":
                        if (managerFileBacked.getSubtasksByID(id) != null) {
                            response = gson.toJson(managerFileBacked.getSubtasksByID(id));
                            statusCode = 200;
                        } else {
                            response = null;
                            statusCode = 400;
                        }
                        break;
                    case "/tasks/epicSubtasks/":
                        if (managerFileBacked.getAllEpicSubtasks(id) != null) {
                            response = gson.toJson(managerFileBacked.getAllEpicSubtasks(id));
                            statusCode = 200;
                        } else {
                            response = null;
                            statusCode = 400;
                        }
                        break;
                    default:
                        response = "Not Found";
                        statusCode = 404;
                }
            }


            //---------------------------------------------------------------------------------------------------------//
            writeResponse(exchange, response, statusCode);
            //---------------------------------------------------------------------------------------------------------//
        }

        private void handlePost(HttpExchange exchange, String path) throws IOException {
            Gson gson = new Gson();
//            Task addNewTask(Task newTask);
//            Task updateTask(Task newTask);
//            Subtask addNewSubtask(Subtask newSubtask);
//            Subtask updateSubtask(Subtask newSubtask);
//            Epic addNewEpic(Epic newEpic);
//            Epic updateEpic(Epic newEpic);
            switch (path) {
                case "/tasks/task/":
                    try {
                        InputStream inputStream = exchange.getRequestBody();
                        String body = new String(inputStream.readAllBytes(), DEFAULT_CHARSET);
                        Task task = gson.fromJson(body, Task.class);
                        managerFileBacked.addNewTask(task);
                        writeResponse(exchange, "Задача добавлена", 201);
                        return;
                    } catch (JsonSyntaxException e) {
                        writeResponse(exchange, "Получен некорректный JSON", 400);
                        return;
                    }
                case "/tasks/epic/":
                    try {
                        InputStream inputStream = exchange.getRequestBody();
                        String body = new String(inputStream.readAllBytes(), DEFAULT_CHARSET);
                        Epic epic = gson.fromJson(body, Epic.class);
                        managerFileBacked.addNewEpic(epic);
                        writeResponse(exchange, "Эпик добавлен", 201);
                        return;
                    } catch (JsonSyntaxException e) {
                        writeResponse(exchange, "Получен некорректный JSON", 400);
                        return;
                    }
                case "/tasks/subtask/":
                    try {
                        InputStream inputStream = exchange.getRequestBody();
                        String body = new String(inputStream.readAllBytes(), DEFAULT_CHARSET);
                        Subtask subtask = gson.fromJson(body, Subtask.class);
                        managerFileBacked.addNewSubtask(subtask);
                        writeResponse(exchange, "Подзадача добавлена", 201);
                        return;
                    } catch (JsonSyntaxException e) {
                        writeResponse(exchange, "Получен некорректный JSON", 400);
                        return;
                    }
            }
        }

        private void handleDelete(HttpExchange exchange, String path, String query) throws IOException {
            int statusCode = 0;
            String response = "";

            if (query == null) {
                switch (path){
                    case "/tasks/task/":
                        managerFileBacked.deleteAllTasks();
                        response = "Успешно!";
                        statusCode = 200;
                        break;
                    case "/tasks/epic/":
                        managerFileBacked.deleteAllEpics();
                        response = "Успешно!";
                        statusCode = 200;
                        break;
                    case "/tasks/subtask/":
                        managerFileBacked.deleteAllSubtasks();
                        response = "Успешно!";
                        statusCode = 200;
                        break;
                    case "/tasks":
                        managerFileBacked.deleteAllTasks();
                        managerFileBacked.deleteAllEpics();
                        managerFileBacked.deleteAllSubtasks();
                        response = "Успешно!";
                        statusCode = 200;
                        break;
                }
            } else {
                int id = Integer.parseInt(query.split("=")[1]);
                path = path.substring(0, path.length() - (query.length() + 1));
                switch (path) {
                    case "/tasks/task/":
                        managerFileBacked.deleteTaskByID(id);
                        response = "Успешно!";
                        statusCode = 200;
                        break;
                    case "/tasks/epic/":
                        managerFileBacked.deleteEpicByID(id);
                        response = "Успешно!";
                        statusCode = 200;
                        break;
                    case "/tasks/subtask/":
                        managerFileBacked.deleteSubtaskByID(id);
                        response = "Успешно!";
                        statusCode = 200;
                        break;
                    default:
                        response = "Not Found";
                        statusCode = 404;
                }
            }

            writeResponse(exchange, response, statusCode);
        }


        private void writeResponse(HttpExchange exchange, String responseString, int responseCode) throws IOException {
            exchange.getResponseHeaders().set("Content-Type", "application/json");
            if(responseString.isBlank()) {
                exchange.sendResponseHeaders(responseCode, 0);
            } else {
                byte[] bytes = responseString.getBytes(DEFAULT_CHARSET);
                exchange.sendResponseHeaders(responseCode, bytes.length);
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(bytes);
                }
            }
            //---------------------------------------------------------------------------------------------------------//
            exchange.close();
            //---------------------------------------------------------------------------------------------------------//
        }
    }
}
