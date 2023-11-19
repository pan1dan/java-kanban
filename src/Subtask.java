public class Subtask extends Task {
    private int idOfEpic;

    public int getIdOfEpic() {
        return idOfEpic;
    }

    public void setIdOfEpic(int idOfEpic) {
        this.idOfEpic = idOfEpic;
    }

    Subtask(String name, String additionalInformation, int idOfEpic) {
        super(name, additionalInformation);

        this.idOfEpic = idOfEpic;
    }

    Subtask(String name, String additionalInformation, int idOfEpic, String status) {
        super(name, additionalInformation, status);

        this.idOfEpic = idOfEpic;
    }
}
