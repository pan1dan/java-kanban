package managers;

import managers.historyManager.*;
import managers.taskManager.*;;

import java.io.File;
import java.io.IOException;
import java.net.URI;

public class Managers {
    private static InMemoryHistoryManager historyManager = new InMemoryHistoryManager();
    private static InMemoryTasksManager taskManager = new InMemoryTasksManager();
    private static FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager(
            new File(System.getProperty("user.dir") + "\\history.txt"));
    //private static HttpTaskManager httpTaskManager;

    public static TaskManager getDefault() {
        return taskManager;
    }

    public static HistoryManager getDefaultHistory() {
        return historyManager;
    }

    public static FileBackedTasksManager getDefaultFileBackedTasksManager() {
        return fileBackedTasksManager;
    }

//    public static HttpTaskManager getDefaultHttpTaskManager() throws IOException, InterruptedException {
//        httpTaskManager = new HttpTaskManager(URI.create("http://localhost:8070"));
//        return httpTaskManager;
//    }
}
