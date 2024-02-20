package managers.historyManager;

import tasks.Task;

import java.util.ArrayList;

public interface HistoryManager {
    void add(Task task);

    public void remove(int id);

    ArrayList<Task> getHistory();
}
