package managers.taskManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import managers.historyManager.HistoryManager;
import server.clients.KVTaskClient;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;

public class HttpTaskManager extends FileBackedTasksManager {
    KVTaskClient kvTaskClient;
    Gson gson = new Gson();
    public HttpTaskManager(URI uri) throws IOException, InterruptedException {
        super(null);
        kvTaskClient = new KVTaskClient(uri);
    }

    @Override
    protected void save() {
        kvTaskClient.put("tasksMap", gson.toJson(tasksMap));
        kvTaskClient.put("epicsMap", gson.toJson(epicsMap));
        kvTaskClient.put("subtasksMap", gson.toJson(subtasksMap));
        kvTaskClient.put("tasksTimeList", gson.toJson(tasksTimeList));
        kvTaskClient.put("historyManager", gson.toJson(historyManager.getHistory()));

    }

    public void load() {
        String jsonTasks = kvTaskClient.load("tasksMap");
        if (jsonTasks != null) {
            tasksMap = gson.fromJson(jsonTasks, new TypeToken<Map<Integer, Task>>(){}.getType());
        }

        String jsonEpics = kvTaskClient.load("epicsMap");
        if (jsonEpics != null) {
            epicsMap = gson.fromJson(jsonEpics, new TypeToken<Map<Integer, Epic>>(){}.getType());
        }

        String jsonSubtasks = kvTaskClient.load("subtasksMap");
        if (jsonSubtasks != null) {
            subtasksMap = gson.fromJson(jsonSubtasks, new TypeToken<Map<Integer, Subtask>>(){}.getType());
        }

        String jsonTasksTimeList = kvTaskClient.load("tasksTimeList");
        if (jsonTasksTimeList != null) {
            tasksTimeList = gson.fromJson(jsonTasksTimeList, new TypeToken<Set<LocalDateTime>>(){}.getType());
        }

        String jsonHistory = kvTaskClient.load("history");
        if (jsonSubtasks != null) {
            historyManager = gson.fromJson(jsonHistory, HistoryManager.class);
        }
    }
}
