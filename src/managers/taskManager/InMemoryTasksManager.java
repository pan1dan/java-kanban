package managers.taskManager;

import exceptions.TaskInputDateException;
import managers.historyManager.HistoryManager;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;
import tasks.TaskStatuses;
import managers.Managers;

import java.time.LocalDateTime;
import java.util.*;

public class InMemoryTasksManager implements TaskManager {
    protected Map<Integer, Task> tasksMap = new HashMap<>();
    protected Map<Integer, Epic> epicsMap = new HashMap<>();
    protected Map<Integer, Subtask> subtasksMap = new HashMap<>();
    protected Set<LocalDateTime>  tasksTimeList = new HashSet<>();
    protected int counterID = 0;
    protected HistoryManager historyManager;

    public InMemoryTasksManager() {
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
                if (entry.getValue().getStartTime() != null) {
                    tasksTimeList.remove(entry.getValue().getStartTime());
                }
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
        if (newTask.getStartTime() != null) {
            try {
                if (tasksTimeList.contains(newTask.getStartTime())) {
                    throw new TaskInputDateException("Выбранная вами дата уже занята. Введите другую");
                }

            } catch (TaskInputDateException e) {
                System.out.println(e.getMessage());
                return null;
            }
            tasksTimeList.add(newTask.getStartTime());
        }

        newTask.setID(++counterID);
        tasksMap.put(newTask.getID(), newTask);
        return newTask;
    }

    @Override
    public Task updateTask(Task newTask) {
        if (newTask.getStartTime() != null) {
            tasksTimeList.add(newTask.getStartTime());
        }
        newTask.setID(++counterID);
        tasksMap.put(newTask.getID(), newTask);
        return newTask;
    }

    @Override
    public void deleteTaskByID(int id) {
        if (tasksMap.containsKey(id)) {
            historyManager.remove(id);
            if (tasksMap.get(id).getStartTime() != null) {
                tasksTimeList.remove(tasksMap.get(id).getStartTime());
            }
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
                if (entry.getValue().getStartTime() != null) {
                    tasksTimeList.remove(entry.getValue().getStartTime());
                }
            }
            subtasksMap.clear();
            if (!epicsMap.isEmpty()) {
                for (Epic epic : epicsMap.values()) {
                    if (!epic.getSubtasksIds().isEmpty()) {
                        epic.setSubtasksIds(new ArrayList<Integer>());
                        changeEpicStatus(epic);
                        if (epic.getStartTime() != null) {
                            changeEpicDuration(epic);
                        }
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
        if (!epicsMap.containsKey(newSubtask.getIdOfEpic())) {
            return null;
        }
        try {
            if (tasksTimeList.contains(newSubtask.getStartTime())) {
                throw new TaskInputDateException("Выбранная вами дата уже занята. Введите другую");
            }
        } catch (TaskInputDateException e) {
            System.out.println(e.getMessage());
            return null;
        }
        newSubtask.setID(++counterID);
        subtasksMap.put(newSubtask.getID(), newSubtask);
        ArrayList<Integer> newEpicSubtasksIds = epicsMap.get(newSubtask.getIdOfEpic()).getSubtasksIds();
        newEpicSubtasksIds.add(newSubtask.getID());
        epicsMap.get(newSubtask.getIdOfEpic()).setSubtasksIds(newEpicSubtasksIds);
        changeEpicStatus(epicsMap.get(newSubtask.getIdOfEpic()));
        if (newSubtask.getStartTime() != null) {
            changeEpicStartTimeAndEndTime(epicsMap.get(newSubtask.getIdOfEpic()));
            changeEpicDuration(epicsMap.get(newSubtask.getIdOfEpic()));
            tasksTimeList.add(newSubtask.getStartTime());
        }
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
        if (newSubtask.getStartTime() != null) {
            changeEpicStartTimeAndEndTime(epicsMap.get(newSubtask.getIdOfEpic()));
            changeEpicDuration(epicsMap.get(newSubtask.getIdOfEpic()));
            tasksTimeList.add(newSubtask.getStartTime());
        }
        return newSubtask;
    }

    @Override
    public void deleteSubtaskByID(int id) {
        if (subtasksMap.containsKey(id)) {
            if (subtasksMap.get(id).getStartTime() != null) {
                tasksTimeList.remove(subtasksMap.get(id).getStartTime());
            }
            historyManager.remove(id);
            ArrayList<Integer> newSubtasksIds = epicsMap.get(subtasksMap.get(id).getIdOfEpic()).getSubtasksIds();
            newSubtasksIds.remove(newSubtasksIds.indexOf(id));
            epicsMap.get(subtasksMap.get(id).getIdOfEpic()).setSubtasksIds(newSubtasksIds);
            changeEpicStatus(epicsMap.get(subtasksMap.get(id).getIdOfEpic()));
            Epic temp = epicsMap.get(subtasksMap.get(id).getIdOfEpic());
            subtasksMap.remove(id);
            if (temp.getStartTime() != null) {
                changeEpicStartTimeAndEndTime(temp);
                changeEpicDuration(temp);
            }
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
                for (int id : entry.getValue().getSubtasksIds()) {
                    historyManager.remove(id);
                }
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
        changeEpicStartTimeAndEndTime(newEpic);
        changeEpicDuration(newEpic);
        return newEpic;
    }

    @Override
    public Epic updateEpic(Epic newEpic) {
        newEpic.setID(++counterID);
        epicsMap.put(newEpic.getID(), newEpic);
        changeEpicStartTimeAndEndTime(newEpic);
        changeEpicDuration(newEpic);
        return newEpic;
    }

    @Override
    public void deleteEpicByID(int id) {
        if (epicsMap.containsKey(id)) {
            for(int idOfSubtasks : epicsMap.get(id).getSubtasksIds()) {
                historyManager.remove(idOfSubtasks);
                subtasksMap.remove(idOfSubtasks);
            }
            epicsMap.remove(id);
            historyManager.remove(id);
        }
    }

    @Override
    public ArrayList<Subtask> getAllEpicSubtasks (int epicID) {
        ArrayList<Subtask> allEpicSubtasks = new ArrayList<>();
        if (!epicsMap.containsKey(epicID)) {
            return null;
        }
        for (int subtaskID : epicsMap.get(epicID).getSubtasksIds()) {
            allEpicSubtasks.add(subtasksMap.get(subtaskID));
        }
        return allEpicSubtasks;
    }

    public ArrayList<Task> getPrioritizedTasks() {
        TreeSet<Task> priorityList = new TreeSet<>((task1, task2) -> {
            if (task1.getStartTime().isAfter(task2.getStartTime())) {
                return 1;
            } else if (task1.getStartTime().isEqual(task2.getStartTime())) {
                return 0;
            } else {
                return -1;
            }
        });

        for(Map.Entry<Integer, Task> entry : tasksMap.entrySet()) {
            priorityList.add(entry.getValue());
        }
        for(Map.Entry<Integer, Epic> entry : epicsMap.entrySet()) {
            priorityList.add(entry.getValue());
        }
        for(Map.Entry<Integer, Subtask> entry : subtasksMap.entrySet()) {
            priorityList.add(entry.getValue());
        }
        return new ArrayList<>(priorityList);
    }

    protected void changeEpicStatus(Epic epic) {
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

    protected void changeEpicDuration(Epic epic) {
        if (epic.getSubtasksIds().isEmpty()) {
            epic.setDuration(0);

        } else {
            int epicDuration = 0;
            for(int id : epic.getSubtasksIds()) {
                epicDuration += subtasksMap.get(id).getDuration();
            }
            epic.setDuration(epicDuration);
        }
    }

    protected void changeEpicStartTimeAndEndTime(Epic epic) {
        if (epic.getSubtasksIds().isEmpty()) {
            return;
        }
        epic.setStartTime(subtasksMap.get(epic.getSubtasksIds().get(0)).getStartTime());
        epic.setEndTime(subtasksMap.get(epic.getSubtasksIds().get(0)).getEndTime());
        for(Integer id : epic.getSubtasksIds()) {
            if (epic.getStartTime().isAfter(subtasksMap.get(id).getStartTime())) {
                epic.setStartTime(subtasksMap.get(id).getStartTime());
            }
            if (epic.getEndTime().isBefore((subtasksMap.get(id).getEndTime()))) {
                epic.setEndTime(subtasksMap.get(id).getEndTime());
            }
        }
    }
}
