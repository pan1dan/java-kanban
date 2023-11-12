public class Task {
    String taskName;
    String statusOfTask;
    int ID = 0;

    Task (String taskName) {
        this.taskName = taskName;
        this.statusOfTask = "NEW";
    }

    @Override
    public int hashCode() {
        int hash = 31;

        hash = hash * ((taskName == null) ? 0 : taskName.hashCode());
        ID = hash;

        return hash;
    }
}
