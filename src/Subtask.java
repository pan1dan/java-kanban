public class Subtask extends Task {
    String nameSubtaskEpic;

    public String getNameSubtaskEpic() {
        return nameSubtaskEpic;
    }

    public void setNameSubtaskEpic(String nameSubtaskEpic) {
        this.nameSubtaskEpic = nameSubtaskEpic;
    }

    Subtask(String name, String additionalInformation, String nameSubtaskEpic) {
        super(name, additionalInformation);

        this.nameSubtaskEpic = nameSubtaskEpic;
    }

    Subtask(String name, String additionalInformation, String nameSubtaskEpic, String status) {
        super(name, additionalInformation, status);

        this.nameSubtaskEpic = nameSubtaskEpic;
    }

}
