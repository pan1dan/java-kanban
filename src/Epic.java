import java.util.HashMap;

public class Epic extends Task {
    private HashMap<Integer, Subtask> subtasksList = new HashMap<>();

    public HashMap<Integer, Subtask> getSubtasksList() {
        return subtasksList;
    }

    public void setSubtasksList(HashMap<Integer, Subtask> subtasksList) {
        this.subtasksList = subtasksList;
    }

    Epic(String name, String additionalInformation) {
        super(name, additionalInformation);
    }
}
