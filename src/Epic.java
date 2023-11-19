import java.util.ArrayList;

public class Epic extends Task {
    private ArrayList<Integer> subtasksIds = new ArrayList<>();

    public ArrayList<Integer> getSubtasksIds() {
        return subtasksIds;
    }

    public void setSubtasksIds(ArrayList<Integer> subtasksIds) {
        this.subtasksIds = subtasksIds;
    }

    Epic(String name, String additionalInformation) {
        super(name, additionalInformation);
    }
}
