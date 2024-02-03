import java.util.ArrayList;

public class InMemoryHistoryManager implements HistoryManager {
    private ArrayList<Task> taskHistory = new ArrayList<>();

    @Override
    public void add(Task task) {
        if (taskHistory.size() == 10) {
            taskHistory.remove(0);
        }
        taskHistory.add(task);
    }

    @Override
    public ArrayList<Task> getHistory() {
        return taskHistory;
    }

    @Override
    public void remove(int id) {
        for (int i = 0; i < taskHistory.size(); i++) {
            if (taskHistory.get(i).getID() == id) {
                taskHistory.remove(i);
                i -= 1;
            }
        }
    }
}
