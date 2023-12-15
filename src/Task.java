public class Task {
    protected String name;
    protected TaskStatuses status;
    protected int id = 0;
    protected String additionalInformation;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TaskStatuses getStatus() {
        return status;
    }

    public void setStatus(TaskStatuses status) {
        this.status = status;
    }

    public int getID() {
        return id;
    }

    public void setID(int id) {
        this.id = id;
    }

    public String getAdditionalInformation() {
        return additionalInformation;
    }

    public void setAdditionalInformation(String additionalInformation) {
        this.additionalInformation = additionalInformation;
    }

    Task (String name, String additionalInformation) {
        this.name = name;
        this.status = TaskStatuses.NEW;
        this.additionalInformation = additionalInformation;
    }

    Task (String name, String additionalInformation, TaskStatuses status) {
        this.name = name;
        this.additionalInformation = additionalInformation;
        this.status = status;
    }
}