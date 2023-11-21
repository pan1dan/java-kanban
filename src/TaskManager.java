import java.util.ArrayList;
import java.util.HashMap;

public class TaskManager {
    HashMap<Integer, Task> tasksMap = new HashMap<>();
    HashMap<Integer, Epic> epicsMap = new HashMap<>();
    HashMap<Integer, Subtask> subtasksMap = new HashMap<>();
    private int counterID = 0;

    public ArrayList<Task> getAllTasks() {
        return new ArrayList<>(tasksMap.values());
    }

    public void deleteAllTasks() {
        if (!tasksMap.isEmpty()) {
            tasksMap.clear();
        }
    }

    public Task getTaskByID(int id) {
        if (tasksMap.containsKey(id)) {
            return tasksMap.get(id);
        }
        return null;
    }

    public Task addNewTask(Task newTask) {
        newTask.setID(++counterID);
        tasksMap.put(newTask.getID(), newTask);
        return newTask;
    }

    public Task updateTask(Task newTask) {
        newTask.setID(++counterID);
        tasksMap.put(newTask.getID(), newTask);
        return newTask;
    }

    public void deleteTaskByID(int id) {
        if (tasksMap.containsKey(id)) {
            tasksMap.remove(id);
        }
    }

    public ArrayList<Subtask> getAllSubtasks() {
        return new ArrayList<>(subtasksMap.values());
    }

    public void deleteAllSubtasks() {
        subtasksMap.clear();
        if (!epicsMap.isEmpty()) {
            for (Epic epic : epicsMap.values()) {
                if (!epic.getSubtasksIds().isEmpty()) {
                    epic.setSubtasksIds(new ArrayList<Integer>());
                    changeEpicStatus(epic);
                }
            }
        }
    }

    public Subtask getSubtasksByID(int id) {
        if (subtasksMap.containsKey(id)) {
            return subtasksMap.get(id);
        }
        return null;
    }

    public Subtask addNewSubtask(Subtask newSubtask) {
        newSubtask.setID(++counterID);
        subtasksMap.put(newSubtask.getID(), newSubtask);
        ArrayList<Integer> newEpicSubtasksIds = epicsMap.get(newSubtask.getIdOfEpic()).getSubtasksIds();
        newEpicSubtasksIds.add(newSubtask.getID());
        epicsMap.get(newSubtask.getIdOfEpic()).setSubtasksIds(newEpicSubtasksIds);
        changeEpicStatus(epicsMap.get(newSubtask.getIdOfEpic()));
        return newSubtask;
    }

    public Subtask updateSubtask(Subtask newSubtask) {
        newSubtask.setID(++counterID);
        subtasksMap.put(newSubtask.getID(), newSubtask);
        ArrayList<Integer> newEpicSubtasksIds = epicsMap.get(newSubtask.getIdOfEpic()).getSubtasksIds();
        newEpicSubtasksIds.add(newSubtask.getID());
        epicsMap.get(newSubtask.getIdOfEpic()).setSubtasksIds(newEpicSubtasksIds);
        changeEpicStatus(epicsMap.get(newSubtask.getIdOfEpic()));
        return newSubtask;
    }

    public void deleteSubtaskByID(int id) {
        subtasksMap.remove(id);
        ArrayList<Integer> newSubtasksIds = epicsMap.get(subtasksMap.get(id).getIdOfEpic()).getSubtasksIds();
        newSubtasksIds.remove(id);
        epicsMap.get(subtasksMap.get(id).getIdOfEpic()).setSubtasksIds(newSubtasksIds);
        changeEpicStatus(epicsMap.get(subtasksMap.get(id).getIdOfEpic()));
    }

    public ArrayList<Epic> getAllEpics() {
        return new ArrayList<>(epicsMap.values());
    }

    public void deleteAllEpics() {
        if (!epicsMap.isEmpty()) {
            epicsMap.clear();
            subtasksMap.clear();
        }
    }

    public Epic getEpicByID(int id) {
        if (epicsMap.containsKey(id)) {
            return epicsMap.get(id);
        }
        return null;
    }

    public Epic addNewEpic(Epic newEpic) {
        newEpic.setID(++counterID);
        epicsMap.put(newEpic.getID(), newEpic);
        return newEpic;
    }

    public Epic updateEpic(Epic newEpic) {
        newEpic.setID(++counterID);
        epicsMap.put(newEpic.getID(), newEpic);
        return newEpic;
    }

    public void deleteEpicByID(int id) {
        if (epicsMap.containsKey(id)) {
            for(int idOfSubtasks : epicsMap.get(id).getSubtasksIds()) {
                subtasksMap.remove(idOfSubtasks);
            }
            epicsMap.remove(id);
        }
    }

    public ArrayList<Subtask> getAllEpicSubtasks (int epicID) {
        ArrayList<Subtask> allEpicSubtasks = new ArrayList<>();
        for (int subtaskID : epicsMap.get(epicID).getSubtasksIds()) {
            allEpicSubtasks.add(subtasksMap.get(subtaskID));
        }
        return allEpicSubtasks;
    }

    private void changeEpicStatus(Epic epic) {
        if (epic.getSubtasksIds().isEmpty()) {
            epic.setStatus("NEW");
            return;
        }
        int counterOfDone = 0;
        int counterOfNew = 0;
        for(int idOfSubtask : epic.getSubtasksIds()) {
            if (subtasksMap.get(idOfSubtask).getStatus().equals("IN_PROGRESS")) {
                epic.setStatus("IN_PROGRESS");
                return;
            }
            if (subtasksMap.get(idOfSubtask).getStatus().equals("DONE")) {
                counterOfDone++;
            } else {
                counterOfNew++;
            }
        }
        if (counterOfDone == epic.getSubtasksIds().size()) {
            epic.setStatus("DONE");
        } else if (counterOfNew == epic.getSubtasksIds().size()) {
            epic.setStatus("NEW");
        }
        else if (counterOfDone > 0 && counterOfNew > 0) {
            epic.setStatus("IN_PROGRESS");
        }
    }
}
