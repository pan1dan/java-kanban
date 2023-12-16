import java.util.ArrayList;

public class InMemoryHistoryManager implements HistoryManager{
    private ArrayList<Task> taskHistory = new ArrayList<>();

    public void add(Task task){
        if (taskHistory.size() == 10) {
            taskHistory.remove(0);
            taskHistory.add(task);
            return;
        }
        taskHistory.add(task);
    }

    @Override
    public ArrayList<Task> getHistory() {
        return taskHistory;
    }
}
