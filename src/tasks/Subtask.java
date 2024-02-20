package tasks;

public class Subtask extends Task {
    private final TypeOfTask type = TypeOfTask.SUBTASK;
    private int idOfEpic;

    public int getIdOfEpic() {
        return idOfEpic;
    }

    public void setIdOfEpic(int idOfEpic) {
        this.idOfEpic = idOfEpic;
    }

    public Subtask(String name, String additionalInformation, int idOfEpic) {
        super(name, additionalInformation);
        this.idOfEpic = idOfEpic;
    }

    public Subtask(String name, String additionalInformation, int idOfEpic, TaskStatuses status) {
        super(name, additionalInformation, status);
        this.idOfEpic = idOfEpic;
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
        line.append(this.getIdOfEpic());

        return line.toString();
    }
}
