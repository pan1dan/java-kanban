package Tasks;

import java.util.ArrayList;

public class Epic extends Task {
    private final TypeOfTask type = TypeOfTask.EPIC;
    private ArrayList<Integer> subtasksIds = new ArrayList<>();

    public ArrayList<Integer> getSubtasksIds() {
        return subtasksIds;
    }

    public void setSubtasksIds(ArrayList<Integer> subtasksIds) {
        this.subtasksIds = subtasksIds;
    }

    public Epic(String name, String additionalInformation) {
        super(name, additionalInformation);
    }

    @Override
    public TypeOfTask getType() {
        return type;
    }

    @Override
    public String taskToString() {
        StringBuilder line = new StringBuilder("");
        line.append(this.getID());
        line.append(",");
        line.append(this.getType());
        line.append(",");
        line.append(this.getName());
        line.append(",");
        line.append(this.getStatus());
        line.append(",");
        line.append(this.getAdditionalInformation());
        line.append(",");

        return line.toString();
    }
}
