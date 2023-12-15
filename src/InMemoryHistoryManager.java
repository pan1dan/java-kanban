import java.util.ArrayList;

public class InMemoryHistoryManager implements HistoryManager{
    private ArrayList<Task> taskHistory = new ArrayList<>();

    public void add(Task task){
        if (taskHistory.size() < 11) {
            taskHistory.add(taskHistory.size(), task);
            return;
        }

        for (int i = 1; i < taskHistory.size() - 1; i++) {
            taskHistory.add(i - 1, taskHistory.get(i));
        }
        taskHistory.add(taskHistory.size() - 1, task);
    }

    @Override
    public ArrayList<Task> getHistory() {
        return taskHistory;
    }
}
