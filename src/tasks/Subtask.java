package tasks;

import java.time.LocalDateTime;

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

    public Subtask(String name, String additionalInformation, int idOfEpic, LocalDateTime startTime,
                   int duration) {
        super(name, additionalInformation, startTime, duration);
        this.idOfEpic = idOfEpic;
    }

    public Subtask(String name, String additionalInformation, int idOfEpic, TaskStatuses status) {
        super(name, additionalInformation, status);
        this.idOfEpic = idOfEpic;
    }

    public Subtask(String name, String additionalInformation, int idOfEpic, TaskStatuses status, int id) {
        super(name, additionalInformation, status, id);
        this.idOfEpic = idOfEpic;
    }

    public Subtask(String name, String additionalInformation, int idOfEpic, TaskStatuses status, LocalDateTime startTime,
                 int duration) {
        super(name, additionalInformation, status, startTime, duration);
        this.idOfEpic = idOfEpic;
    }

    public Subtask(String name, String additionalInformation, int idOfEpic, TaskStatuses status, int id,
                   LocalDateTime startTime, int duration) {
        super(name, additionalInformation, status, id, startTime, duration);
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
        line.append(this.getStartTime());
        line.append(",");
        line.append(this.getDuration());
        line.append(",");
        line.append(this.getIdOfEpic());

        return line.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Subtask subtask = (Subtask) o;
        return type == subtask.type && name.equals(subtask.name) && status == subtask.status
                && id == subtask.id && additionalInformation.equals(subtask.additionalInformation)
                && idOfEpic == subtask.idOfEpic;
    }
}
