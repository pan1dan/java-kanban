import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class InMemoryTaskManager implements TaskManager{
    HashMap<Integer, Task> tasksMap = new HashMap<>();
    HashMap<Integer, Epic> epicsMap = new HashMap<>();
    HashMap<Integer, Subtask> subtasksMap = new HashMap<>();
    private int counterID = 0;
    private HistoryManager historyManager;

    public InMemoryTaskManager() {
        this.historyManager = Managers.getDefaultHistory();
    }

    @Override
    public ArrayList<Task> getAllTasks() {
        return new ArrayList<>(tasksMap.values());
    }

    @Override
    public void deleteAllTasks() {
        if (!tasksMap.isEmpty()) {
            for (Map.Entry<Integer, Task> entry : tasksMap.entrySet()) {
                historyManager.remove(entry.getKey());
            }
            tasksMap.clear();
        }
    }

    @Override
    public Task getTaskByID(int id) {
        if (tasksMap.containsKey(id)) {
            historyManager.add(tasksMap.get(id));
            return tasksMap.get(id);
        }
        return null;
    }

    @Override
    public Task addNewTask(Task newTask) {
        newTask.setID(++counterID);
        tasksMap.put(newTask.getID(), newTask);
        return newTask;
    }

    @Override
    public Task updateTask(Task newTask) {
        newTask.setID(++counterID);
        tasksMap.put(newTask.getID(), newTask);
        return newTask;
    }

    @Override
    public void deleteTaskByID(int id) {
        if (tasksMap.containsKey(id)) {
            historyManager.remove(id);
            tasksMap.remove(id);
        }
    }

    @Override
    public ArrayList<Subtask> getAllSubtasks() {
        return new ArrayList<>(subtasksMap.values());
    }

    @Override
    public void deleteAllSubtasks() {
        if (!subtasksMap.isEmpty()) {
            for (Map.Entry<Integer, Subtask> entry : subtasksMap.entrySet()) {
                historyManager.remove(entry.getKey());
            }
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

    }

    @Override
    public Subtask getSubtasksByID(int id) {
        if (subtasksMap.containsKey(id)) {
            historyManager.add(subtasksMap.get(id));
            return subtasksMap.get(id);
        }
        return null;
    }

    @Override
    public Subtask addNewSubtask(Subtask newSubtask) {
        newSubtask.setID(++counterID);
        subtasksMap.put(newSubtask.getID(), newSubtask);
        ArrayList<Integer> newEpicSubtasksIds = epicsMap.get(newSubtask.getIdOfEpic()).getSubtasksIds();
        newEpicSubtasksIds.add(newSubtask.getID());
        epicsMap.get(newSubtask.getIdOfEpic()).setSubtasksIds(newEpicSubtasksIds);
        changeEpicStatus(epicsMap.get(newSubtask.getIdOfEpic()));
        return newSubtask;
    }

    @Override
    public Subtask updateSubtask(Subtask newSubtask) {
        newSubtask.setID(++counterID);
        subtasksMap.put(newSubtask.getID(), newSubtask);
        ArrayList<Integer> newEpicSubtasksIds = epicsMap.get(newSubtask.getIdOfEpic()).getSubtasksIds();
        newEpicSubtasksIds.add(newSubtask.getID());
        epicsMap.get(newSubtask.getIdOfEpic()).setSubtasksIds(newEpicSubtasksIds);
        changeEpicStatus(epicsMap.get(newSubtask.getIdOfEpic()));
        return newSubtask;
    }

    @Override
    public void deleteSubtaskByID(int id) {
        if (subtasksMap.containsKey(id)) {
            historyManager.remove(id);
            subtasksMap.remove(id);
            ArrayList<Integer> newSubtasksIds = epicsMap.get(subtasksMap.get(id).getIdOfEpic()).getSubtasksIds();
            newSubtasksIds.remove(id);
            epicsMap.get(subtasksMap.get(id).getIdOfEpic()).setSubtasksIds(newSubtasksIds);
            changeEpicStatus(epicsMap.get(subtasksMap.get(id).getIdOfEpic()));
        }
    }

    @Override
    public ArrayList<Epic> getAllEpics() {
        return new ArrayList<>(epicsMap.values());
    }

    @Override
    public void deleteAllEpics() {
        if (!epicsMap.isEmpty()) {
            for (Map.Entry<Integer, Epic> entry : epicsMap.entrySet()) {
                historyManager.remove(entry.getKey());
            }
            epicsMap.clear();
            subtasksMap.clear();
        }
    }

    @Override
    public Epic getEpicByID(int id) {
        if (epicsMap.containsKey(id)) {
            historyManager.add(epicsMap.get(id));
            return epicsMap.get(id);
        }
        return null;
    }

    @Override
    public Epic addNewEpic(Epic newEpic) {
        newEpic.setID(++counterID);
        epicsMap.put(newEpic.getID(), newEpic);
        return newEpic;
    }

    @Override
    public Epic updateEpic(Epic newEpic) {
        newEpic.setID(++counterID);
        epicsMap.put(newEpic.getID(), newEpic);
        return newEpic;
    }

    @Override
    public void deleteEpicByID(int id) {
        if (epicsMap.containsKey(id)) {
            historyManager.remove(id);
            for(int idOfSubtasks : epicsMap.get(id).getSubtasksIds()) {
                subtasksMap.remove(idOfSubtasks);
            }
            epicsMap.remove(id);
        }
    }

    @Override
    public ArrayList<Subtask> getAllEpicSubtasks (int epicID) {
        ArrayList<Subtask> allEpicSubtasks = new ArrayList<>();
        for (int subtaskID : epicsMap.get(epicID).getSubtasksIds()) {
            allEpicSubtasks.add(subtasksMap.get(subtaskID));
        }
        return allEpicSubtasks;
    }

    private void changeEpicStatus(Epic epic) {
        if (epic.getSubtasksIds().isEmpty()) {
            epic.setStatus(TaskStatuses.NEW);
            return;
        }
        int counterOfDone = 0;
        int counterOfNew = 0;
        for(int idOfSubtask : epic.getSubtasksIds()) {
            if (subtasksMap.get(idOfSubtask).getStatus().equals(TaskStatuses.IN_PROGRESS)) {
                epic.setStatus(TaskStatuses.IN_PROGRESS);
                return;
            }
            if (subtasksMap.get(idOfSubtask).getStatus().equals(TaskStatuses.DONE)) {
                counterOfDone++;
            } else {
                counterOfNew++;
            }
        }
        if (counterOfDone == epic.getSubtasksIds().size()) {
            epic.setStatus(TaskStatuses.DONE);
        } else if (counterOfNew == epic.getSubtasksIds().size()) {
            epic.setStatus(TaskStatuses.NEW);
        }
        else if (counterOfDone > 0 && counterOfNew > 0) {
            epic.setStatus(TaskStatuses.IN_PROGRESS);
        }
    }
}
