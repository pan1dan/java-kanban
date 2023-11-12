public class Subtask{
    String subtaskName;
    String subtaskStatus;
    int ID;

    Subtask(String subtaskName) {
        this.subtaskName = subtaskName;
        this.subtaskStatus = "NEW";

        this.hashCode();
    }

    @Override
    public int hashCode() {
        int hash = 31;

        hash = hash * ((subtaskName == null) ? 0 : subtaskName.hashCode());
        ID = hash;

        return hash;
    }
}
