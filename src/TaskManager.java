import java.util.ArrayList;
import java.util.HashMap;

public class TaskManager {
    HashMap<Integer, Task> tasksList = new HashMap<>();
    HashMap<Integer, Epic> epicsList = new HashMap<>();

    ArrayList<Task> getAllTasks() {
        ArrayList<Task> allTasks = new ArrayList<>();

        for (Task task : tasksList.values()) {
            allTasks.add(task);
        }

        return allTasks;
    }

    String deleteAllTasks() {
        if (!tasksList.isEmpty()) {
            tasksList.clear();
            return "Все задачи удалены";
        } else {
            return "Список задач пуст";
        }
    }

    Task getTaskByID(int id) {
        return tasksList.get(id);
    }

    Task addNewTask(Task newTask) {
        tasksList.put(newTask.getID(), newTask);

        return newTask;
    }

    Task updateTask(Task oldTask, Task newTask) {

        tasksList.remove(oldTask.getID());
        addNewTask(newTask);


        return newTask;
    }

    String deleteTaskByID(int id) {
        if (tasksList.containsKey(id)) {
            tasksList.remove(id);

            return "Объект удалён";
        } else {
            return "Данный объект не найден";
        }
    }


    ////!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

    ArrayList<Subtask> getAllSubtasks() { //!!!
        ArrayList<Subtask> allSubtasks = new ArrayList<>();

        for (Epic epic : epicsList.values()) {
            for (Subtask subtask : epic.getSubtasksList().values()) {
                allSubtasks.add(subtask);
            }
        }


        return allSubtasks;
    }

    String deleteAllSubtasks() {
        if (!epicsList.isEmpty()) {
            for (Epic epic : epicsList.values()) {
                if (!epic.getSubtasksList().isEmpty()) {
                    HashMap<Integer, Subtask> newSubtasksList = new HashMap<>();

                    epic.setSubtasksList(newSubtasksList);
                }
            }

            return "Все подзадачи удалены";
        } else {
            return "Список эпиков пуст";
        }

        
    }

    Subtask getSubtasksByID(int id) {
        for (Epic epic : epicsList.values()) {
            for (Subtask subtask : epic.getSubtasksList().values()) {
                if (subtask.getID() == id) {
                    return subtask;
                }
            }
        }

        return null;
    }

    Subtask addNewSubtask(Epic epic,Subtask newsSubtask) {
        HashMap<Integer, Subtask> newSubtasksList = epic.getSubtasksList();

        newSubtasksList.put(newsSubtask.getID(), newsSubtask);
        epic.setSubtasksList(newSubtasksList);

        return newsSubtask;
    }

    Subtask updateSubtask(Epic epic, Subtask oldSubtask, Subtask newSubtask) {
        HashMap<Integer, Subtask> newSubtasksList = epic.getSubtasksList();

        newSubtasksList.remove(oldSubtask.getID());
        newSubtasksList.put(newSubtask.getID(), newSubtask);
        epic.setSubtasksList(newSubtasksList);

        changeEpicStatus(newSubtask, epic);

        return newSubtask;
    }

    String deleteSubtaskByID(int id) {
        for (Epic epic : epicsList.values()) {
            for (Subtask subtask : epic.getSubtasksList().values()) {
                if (subtask.getID() == id) {
                    HashMap<Integer, Subtask> newSubtasksList = epic.getSubtasksList();

                    newSubtasksList.remove(subtask.getID());
                    epic.setSubtasksList(newSubtasksList);

                    return "Объект удалён";
                }
            }
        }

        return "Данный объект не найден";
    }

    ///!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

    ArrayList<Epic> getAllEpics() {
        ArrayList<Epic> allEpics = new ArrayList<>();

        for (Epic epic : epicsList.values()) {
            allEpics.add(epic);
        }

        return allEpics;
    }

    String deleteAllEpics() {
        if (!epicsList.isEmpty()) {
            epicsList.clear();
            return "Все эпики удалены";
        } else {
            return "Список эпиков пуст";
        }
    }

    Task getEpicByID(int id) {
        return epicsList.get(id);
    }

    Task addNewEpic(Epic newEpic) {
        epicsList.put(newEpic.getID(), newEpic);

        return newEpic;
    }

    Task updateEpic(Epic oldEpic, Epic newEpic) {
        newEpic.setSubtasksList(oldEpic.getSubtasksList());
        epicsList.remove(oldEpic.getID());
        addNewEpic(newEpic);

        return newEpic;
    }

    String deleteEpicByID(int id) {
        if (epicsList.containsKey(id)) {
            epicsList.remove(id);

            return "Объект удалён";
        } else {
            return "Данный объект не найден";
        }
    }

    ArrayList<Subtask> getAllEpicSubtasks (Epic epic) {
        ArrayList<Subtask> allEpicSubtasks = new ArrayList<>();

        for (Subtask subtask : epic.getSubtasksList().values()) {
            allEpicSubtasks.add(subtask);
        }

        return allEpicSubtasks;
    }

    //static
    void changeEpicStatus(Subtask newSubtask, Epic epic) {
        if (!epic.getStatus().equals(newSubtask.getStatus())) {
            if (epic.getStatus().equals("NEW") && newSubtask.getStatus().equals("IN_PROGRESS")) {
                epic.setStatus("IN_PROGRESS");

            } else if (epic.getStatus().equals("IN_PROGRESS") && newSubtask.getStatus().equals("DONE") ||
                        epic.getStatus().equals("NEW") && newSubtask.getStatus().equals("DONE")) {
                for(Subtask subtask : epic.getSubtasksList().values()) {
                    if (subtask.getStatus().equals("IN_PROGRESS")) {
                        epic.setStatus("IN_PROGRESS");

                        return;
                    }
                }

                epic.setStatus("DONE");
            }
        }
    }
}


/*
    new new +
    new prog +
    prog new +
    prog prog +
    prog done +

    new done




 */
