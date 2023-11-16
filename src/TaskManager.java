import java.util.ArrayList;
import java.util.HashMap;

public class TaskManager {
    HashMap<Integer, Task> tasksList = new HashMap<>();
    HashMap<Integer, Epic> epicsList = new HashMap<>();
    HashMap<Integer, Subtask> subtasksList = new HashMap<>();

    public ArrayList<Task> getAllTasks() {
        ArrayList<Task> allTasks = new ArrayList<>();
        for (Task task : tasksList.values()) {
            allTasks.add(task);
        }
        return allTasks;
    }

    public void deleteAllTasks() {
        if (!tasksList.isEmpty()) {
            tasksList.clear();
        }
    }

    public Task getTaskByID(int id) {
        return tasksList.get(id);
    }

    public Task addNewTask(Task newTask) {
        tasksList.put(newTask.getID(), newTask);
        return newTask;
    }

    public Task updateTask(Task oldTask, Task newTask) {
        tasksList.remove(oldTask.getID());
        addNewTask(newTask);
        return newTask;
    }

    public void deleteTaskByID(int id) {
        if (tasksList.containsKey(id)) {
            tasksList.remove(id);
        }
    }

    public ArrayList<Subtask> getAllSubtasks() {
        ArrayList<Subtask> allSubtasks = new ArrayList<>();
        for (Subtask subtask : subtasksList.values()) {
            allSubtasks.add(subtask);
        }
        return allSubtasks;
    }

    public void deleteAllSubtasks() {
        if (!epicsList.isEmpty()) {
            for (Epic epic : epicsList.values()) {
                if (!epic.getSubtaskIds().isEmpty()) {
                    epic.setSubtaskIds(new ArrayList<Integer>());
                    subtasksList.clear();
                    changeEpicStatus(epic);
                }
            }
        }
    }

    public Subtask getSubtasksByID(int id) {
        for (Epic epic : epicsList.values()) {
            if (epic.getSubtaskIds().contains(id)) {
                return subtasksList.get(id);
            }
        }
        return null;
    }

    public Subtask addNewSubtask(Epic epic,Subtask newsSubtask) {
        ArrayList<Integer> newEpicSubtaskIds = epic.getSubtaskIds();
        newEpicSubtaskIds.add(newsSubtask.getID());
        epic.setSubtaskIds(newEpicSubtaskIds);
        subtasksList.put(newsSubtask.getID(), newsSubtask);
        return newsSubtask;
    }

    public Subtask updateSubtask(Epic epic, Subtask oldSubtask, Subtask newSubtask) {
        if (epic.getSubtaskIds().contains(oldSubtask.getID())) {
            ArrayList<Integer> newSubtaskIds = epic.getSubtaskIds();
            newSubtaskIds.remove((Integer) oldSubtask.getID());
            newSubtaskIds.add(newSubtask.getID());
            epic.setSubtaskIds(newSubtaskIds);
            subtasksList.remove(oldSubtask.getID());
            subtasksList.put(newSubtask.getID(), newSubtask);
            changeEpicStatus(newSubtask, epic);
            return newSubtask;
        }
        return null;
    }

    public void deleteSubtaskByID(int id) {
        subtasksList.remove(id);
        for (Epic epic : epicsList.values()) {
            if (epic.getSubtaskIds().contains(id)) {
                ArrayList<Integer> newSubtaskIds = epic.getSubtaskIds();
                newSubtaskIds.remove(id);
                epic.setSubtaskIds(newSubtaskIds);
            }
        }
    }

    public ArrayList<Epic> getAllEpics() {
        ArrayList<Epic> allEpics = new ArrayList<>();
        for (Epic epic : epicsList.values()) {
            allEpics.add(epic);
        }
        return allEpics;
    }

    public void deleteAllEpics() {
        if (!epicsList.isEmpty()) {
            epicsList.clear();
        }
    }

    public Task getEpicByID(int id) {
        return epicsList.get(id);
    }

    public Task addNewEpic(Epic newEpic) {
        epicsList.put(newEpic.getID(), newEpic);
        return newEpic;
    }

    public Epic updateEpic(Epic newEpic) {
        addNewEpic(newEpic);
        return newEpic;
    }

    public void deleteEpicByID(int id) {
        if (epicsList.containsKey(id)) {
            epicsList.remove(id);
        }
    }
    
    public ArrayList<Subtask> getAllEpicSubtasks (int epicID) {
        ArrayList<Subtask> allEpicSubtasks = new ArrayList<>();
        for (int subtaskID : epicsList.get(epicID).getSubtaskIds()) {
            allEpicSubtasks.add(subtasksList.get(subtaskID));
        }
        return allEpicSubtasks;
    }

    private void changeEpicStatus(Subtask newSubtask, Epic epic) {
        if (!epic.getStatus().equals(newSubtask.getStatus())) {
            if (epic.getStatus().equals("NEW") && newSubtask.getStatus().equals("IN_PROGRESS")) {
                epic.setStatus("IN_PROGRESS");
            } else if (epic.getStatus().equals("IN_PROGRESS") && newSubtask.getStatus().equals("DONE") ||
                        epic.getStatus().equals("NEW") && newSubtask.getStatus().equals("DONE")) {
                for(int subtaskID : epicsList.get(epic.getID()).getSubtaskIds()) {
                    if (subtasksList.get(subtaskID).getStatus().equals("IN_PROGRESS")) {
                        epic.setStatus("IN_PROGRESS");
                        return;
                    }
                }
                epic.setStatus("DONE");
            }
        }
    }

    private void changeEpicStatus(Epic epic) {
        if (epic.getSubtaskIds().isEmpty()) {
            epic.setStatus("NEW");
        }
    }
}
