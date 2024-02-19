package Managers;

import Managers.HistoryManager.*;
import Managers.TaskManager.*;

public class Managers {
    private static InMemoryHistoryManager historyManager = new InMemoryHistoryManager();
    private static InMemoryTasksManager taskManager = new InMemoryTasksManager();

    public static TaskManager getDefault() {
        return taskManager;
    }

    public static HistoryManager getDefaultHistory() {
        return historyManager;
    }
}
