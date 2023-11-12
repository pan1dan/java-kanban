import java.util.HashMap;

public class Epic {
    String epicName;
    String additionalInformation;
    String epicStatus;
    int ID;

    HashMap<String, Subtask> listOfSubtasks;

    Epic(String epicName, String additionalInformation) {
        this.epicName = epicName;
        this.epicStatus = "NEW";
        this.additionalInformation = additionalInformation;

        listOfSubtasks = new HashMap<>();
    }

    @Override
    public int hashCode() {
        int hash = 31;

        hash = hash * ((epicName == null) ? 0 : epicName.hashCode());
        ID = hash;

        return hash;
    }

    void changeEpicStatus() {
        int counterOfDone = 0;

        for (Subtask subtask : listOfSubtasks.values()) {
            if (subtask.subtaskStatus.equals("DONE")) {
                counterOfDone++;
            }

            if (counterOfDone == listOfSubtasks.size()) {
                epicStatus = "DONE";
                return;
            }
        }

        for (Subtask subtask : listOfSubtasks.values()) {
            if (subtask.subtaskStatus.equals("IN_PROGRESS")) {
                epicStatus = "IN_PROGRESS";
            }
        }
    }
}