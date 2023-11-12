import java.util.ArrayList;
import java.util.HashMap;

public class EpicAndSubtaskData {
    HashMap<Integer, Epic> epicsList = new HashMap<>();

    Epic addNewEpic(String epicName, String additionalInformation) {
        Epic newEpic = new Epic(epicName, additionalInformation);

        epicsList.put(newEpic.hashCode(), newEpic);

        return newEpic;
    }

    ArrayList<String> getAllEpics() {
        ArrayList<String> allEpics = new ArrayList<>();

        for (Epic epic : epicsList.values()) {
            allEpics.add(epic.epicName);
        }

        return allEpics;
    }

    String deleteAllEpics() {
        if (!epicsList.isEmpty()) {
            epicsList.clear();
            return "Список очещиен";
        }

        return "Эпики для удаления отсутствуют";
    }

    String getEpicByID(int epicID) {
        return epicsList.get(epicID).epicName;
    }

    Epic changeEpicName(String oldEpicName, String newEpicName) {
        for (int key : epicsList.keySet()) {
            if (epicsList.get(key).epicName.equals(oldEpicName)) {
                epicsList.get(key).epicName = newEpicName;

                return epicsList.get(key);
            }
        }

        return null;
    }

    Epic changeEpicAdditionalInformation(String nameOfEpic, String newEpicInformation) {
        for (int key : epicsList.keySet()) {
            if (epicsList.get(key).epicName.equals(nameOfEpic)) {
                epicsList.get(key).additionalInformation = newEpicInformation;

                return epicsList.get(key);
            }
        }

        return null;
    }

    String deleteEpicByID(int IDOfEpic) {
        epicsList.remove(IDOfEpic);

        return "Эпик успешно удален";
    }

    ArrayList<String> getAllEpicSubtasks (String nameOfEpic) {
        ArrayList<String> allEpicSubtask = new ArrayList<>();

        for (Epic epic : epicsList.values()) {
            if (epic.epicName.equals(nameOfEpic)) {
                for (Subtask subtask : epic.listOfSubtasks.values()) {
                    allEpicSubtask.add(subtask.subtaskName);
                }
            }
        }

        return allEpicSubtask;
    }

    ArrayList<String> getAllSubtask() {
        ArrayList<String> allSubtask = new ArrayList<>();

        for (Epic epic : epicsList.values()) {
            for (Subtask subtask : epic.listOfSubtasks.values()) {
                allSubtask.add(subtask.subtaskName);
            }
        }

        return allSubtask;
    }

    String deleteAllSubtask() {
        ArrayList<String> allSubtask = new ArrayList<>();

        for (Epic epic : epicsList.values()) {

            if (!epic.listOfSubtasks.isEmpty()) {
                epic.listOfSubtasks.clear();
            }
        }

        return "Список очещиен";
    }

    String getSubtaskByID(int subtaskID) {
        for (Epic epic : epicsList.values()) {
            for (Subtask subtask : epic.listOfSubtasks.values()) {
                if (subtask.ID == subtaskID) {
                    return subtask.subtaskName;
                }
            }
        }

        return null;
    }

    Subtask addNewSubtask(String nameOfEpic, String nameOfSubtask) {
        Subtask subtask = new Subtask(nameOfSubtask);

        for (Epic epic : epicsList.values()) {
            if (epic.epicName.equals(nameOfEpic)) {
                epic.listOfSubtasks.put(nameOfEpic, subtask);
            }
        }

        return subtask;
    }

    Subtask changeSubtaskName(String oldSubtaskName, String epicName, String newSubtaskName) {
        for (Epic epic : epicsList.values()) {
            if (epic.epicName.equals(epicName)) {
                for (Subtask subtask : epic.listOfSubtasks.values()) {
                    if (subtask.subtaskName.equals(oldSubtaskName)) {
                        subtask.subtaskName = newSubtaskName;

                        return subtask;
                    }
                }
            }
        }

        return null;
    }

    Subtask changeSubtaskStatus(String nameOfSubtask, String epicName, String newSubtaskStatus) {
        for (Epic epic : epicsList.values()) {
            if (epic.epicName.equals(epicName)) {
                for (Subtask subtask : epic.listOfSubtasks.values()) {
                    if (subtask.subtaskName.equals(nameOfSubtask)) {
                        subtask.subtaskStatus = newSubtaskStatus;
                        epic.changeEpicStatus();

                        return subtask;
                    }
                }
            }
        }

        return null;
    }

    String deleteSubtaskByID(int IDOfSubtask) {
        for (Epic epic : epicsList.values()) {
            for (Subtask subtask : epic.listOfSubtasks.values()) {
                if (subtask.ID == IDOfSubtask) {
                    epic.listOfSubtasks.remove(epic.epicName);
git
                    return "Подзадача успешно удалена";
                }
            }
        }

        return null;
    }
}
