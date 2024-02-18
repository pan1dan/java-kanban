import java.io.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

public class FileBackedTasksManager extends InMemoryTasksManager implements TaskManager{
    File file;

    FileBackedTasksManager(File file) {
        super();
        this.file = file;
    }

    private void save() throws ManagerSaveException {
        try ( Writer fileWriter = new FileWriter(file.getPath())) {
            fileWriter.write("id,type,name,status,description,epic\n");

            for(Map.Entry<Integer, Task> entry : tasksMap.entrySet()) {
                fileWriter.write(entry.getValue().taskToString() + "\n");
            }
            for(Map.Entry<Integer, Epic> entry : epicsMap.entrySet()) {
                fileWriter.write(entry.getValue().taskToString() + "\n");
            }
            for(Map.Entry<Integer, Subtask> entry : subtasksMap.entrySet()) {
                fileWriter.write(entry.getValue().taskToString() + "\n");
            }

            if (historyManager.getHistory() != null) {
                fileWriter.write("\n" + historyToString(historyManager));
            }
        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка при сохранение данных в файл: " + e.getMessage());
        }
    }

    public Task taskFromString(String value) {
        String[] line = value.split(",");
        if (line[1].equals(TypeOfTask.TASK.toString())) {
            Task task = new Task(line[2], line[4], TaskStatuses.valueOf(line[3]));
            task.setID(Integer.parseInt(line[0]));
            tasksMap.put(task.getID(), task);
            return task;
        } else if (line[1].equals(TypeOfTask.EPIC.toString())) {
            Epic epic = new Epic(line[2], line[4]);
            epic.setID(Integer.parseInt(line[0]));
            epic.setStatus(TaskStatuses.valueOf(line[3]));
            epicsMap.put(epic.getID(), epic);
            return epic;
        } else {
            Subtask subtask = new Subtask(line[2], line[4], Integer.parseInt(line[5]), TaskStatuses.valueOf(line[3]));
            subtask.setID(Integer.parseInt(line[0]));
            ArrayList<Integer> newsSubtasksIds = epicsMap.get(subtask.getIdOfEpic()).getSubtasksIds();
            newsSubtasksIds.add(subtask.getID());
            epicsMap.get(subtask.getIdOfEpic()).setSubtasksIds(newsSubtasksIds);
            subtasksMap.put(subtask.getID(), subtask);
            return subtask;
        }
    }

    static String historyToString(HistoryManager manager) {
        StringBuilder lineOfHistory = new StringBuilder("");
        for (Task task : manager.getHistory()) {
            lineOfHistory.append(task.getID());
            lineOfHistory.append(",");
        }
        return lineOfHistory.toString();
    }

    static ArrayList<Integer> historyFromString(String value) {
        String[] split = value.split(",");
        ArrayList<Integer> historyList = new ArrayList<>();
        for(int i = 0; i < split.length; i++) {
            historyList.add(Integer.parseInt(split[i]));
        }
        return historyList;
    }

    static FileBackedTasksManager loadFromFile(File file) throws FileNotFoundException, IOException {
        try {
            Reader fileReader = new FileReader(file.getPath());
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager(file);

            String line = bufferedReader.readLine();
            while(bufferedReader.ready()) {
                line = bufferedReader.readLine();
                if (line.isEmpty()) {
                    continue;
                }
                if (isNumber(line.split(",")[1])) {
                    historyFromString(line);
                    return fileBackedTasksManager;
                }
                Managers.getDefaultHistory().add(fileBackedTasksManager.taskFromString(line));
            }

            return fileBackedTasksManager;
        } catch (FileNotFoundException e) {
            System.out.println("Файл не найден");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    private static boolean isNumber(String str) throws NumberFormatException {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public class ManagerSaveException extends RuntimeException {
        public ManagerSaveException(final String message) {
            super(message);
        }
    }

    @Override
    public void deleteAllTasks() {
        super.deleteAllTasks();
        save();
    }

    @Override
    public Task getTaskByID(int id) {
        Task returnableTask = super.getTaskByID(id);
        save();
        return returnableTask;
    }

    @Override
    public void deleteTaskByID(int id) {
        super.deleteTaskByID(id);
        save();
    }

    @Override
    public void deleteAllSubtasks() {
        super.deleteAllSubtasks();
        save();
    }

    @Override
    public Subtask getSubtasksByID(int id) {
        Subtask returnableSubtask = super.getSubtasksByID(id);
        save();
        return returnableSubtask;
    }

    @Override
    public void deleteSubtaskByID(int id) {
        super.deleteSubtaskByID(id);
        save();
    }

    @Override
    public void deleteAllEpics() {
        super.deleteAllEpics();
        save();
    }

    @Override
    public Epic getEpicByID(int id) {
        Epic returnableEpic = super.getEpicByID(id);
        save();
        return returnableEpic;
    }

    @Override
    public void deleteEpicByID(int id) {
        super.deleteEpicByID(id);
        save();
    }

    @Override
    public Task addNewTask(Task newTask) {
        Task returnableTask = super.addNewTask(newTask);
        save();
        return returnableTask;
    }

    @Override
    public Task updateTask(Task newTask) {
        Task returnableTask = super.updateTask(newTask);
        save();
        return returnableTask;
    }

    @Override
    public Subtask addNewSubtask(Subtask newSubtask) {
        Subtask returnableSubtask = super.addNewSubtask(newSubtask);
        save();
        return returnableSubtask;
    }

    @Override
    public Subtask updateSubtask(Subtask newSubtask) {
        Subtask returnableSubtask = super.updateSubtask(newSubtask);
        save();
        return  returnableSubtask;
    }

    @Override
    public Epic addNewEpic(Epic newEpic) {
        Epic returnableEpic = super.addNewEpic(newEpic);
        save();
        return returnableEpic;
    }

    @Override
    public Epic updateEpic(Epic newEpic) {
        Epic returnableEpic = super.updateEpic(newEpic);
        save();
        return returnableEpic;
    }

    public static void main(String[] args) throws IOException {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Хотите восстановить версию программы до закрытия?");
        System.out.println("1. Да");
        System.out.println("2. Нет");
        int answer = scanner.nextInt();

        if (answer == 2) {
            FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager(
                    new File(System.getProperty("user.dir") + "\\history.txt"));
            Task task1 = new Task("Read book every day", "30 pages");
            fileBackedTasksManager.addNewTask(task1);
            Task task2 = new Task("jump every day", "30 iterations");
            fileBackedTasksManager.addNewTask(task2);
            Task task3 = new Task("Eat every day", "3 iterations");
            fileBackedTasksManager.addNewTask(task3);

            Epic epic1 = new Epic("Съездить в Москву", "обязательно до лета");
            fileBackedTasksManager.addNewEpic(epic1);
            Subtask subtask11 = new Subtask("купить билеты", "дешёвые билеты", epic1.getID());
            fileBackedTasksManager.addNewSubtask(subtask11);
            Subtask subtask12 = new Subtask("купить одежду", "крутую одежду", epic1.getID());
            fileBackedTasksManager.addNewSubtask(subtask12);

            Epic epic2 = new Epic("посмотреть кино", "обязательно до конца месяца");
            fileBackedTasksManager.addNewEpic(epic2);
            Subtask subtask21 = new Subtask("найти кино", "в хорошем качестве", epic2.getID());
            fileBackedTasksManager.addNewSubtask(subtask21);


            fileBackedTasksManager.getTaskByID(task1.getID());
            fileBackedTasksManager.getTaskByID(task2.getID());
            fileBackedTasksManager.getTaskByID(task3.getID());

            fileBackedTasksManager.getEpicByID(epic1.getID());
            fileBackedTasksManager.getSubtasksByID(subtask11.getID());
            fileBackedTasksManager.getSubtasksByID(subtask12.getID());

            fileBackedTasksManager.getEpicByID(epic2.getID());
            fileBackedTasksManager.getSubtasksByID(subtask21.getID());
            Managers.getDefaultHistory().getHistory();
        } else if (answer == 1) {
            FileBackedTasksManager fileBackedTasksManager = loadFromFile(
                    new File(System.getProperty("user.dir") + "\\history.txt"));
            Managers.getDefaultHistory().getHistory();
        }



    }
}
