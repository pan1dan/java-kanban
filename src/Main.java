import managers.Managers;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;
import tasks.TaskStatuses;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Вариант проверки:");
        System.out.println("1 (старый)");
        System.out.println("2 (новый)");
        System.out.println("3 (тест для проверки deleteAllEpics)");
        System.out.println("4 !!!!");
        int answer = scanner.nextInt();
        if (answer == 1) {
            Task task1 = new Task("Read book every day", "30 pages");
            Managers.getDefault().addNewTask(task1);
            Managers.getDefault().getTaskByID(task1.getID());
            Managers.getDefaultHistory().getHistory();
            System.out.println(Managers.getDefault().getAllTasks());
            Managers.getDefault().updateTask(new Task("Read book every day", "30 pages", TaskStatuses.IN_PROGRESS));
            Managers.getDefault().getTaskByID(task1.getID());
            Managers.getDefaultHistory().getHistory();
            System.out.println(Managers.getDefault().getAllTasks());

            Task task2 = new Task("jump every day", "30 iterations");
            Managers.getDefault().addNewTask(task2);
            Managers.getDefault().getTaskByID(task2.getID());
            Managers.getDefaultHistory().getHistory();
            System.out.println(Managers.getDefault().getAllTasks());
            Managers.getDefault().updateTask(new Task("jump every day", "30 iterations", TaskStatuses.DONE));
            System.out.println(Managers.getDefault().getAllTasks());
            Managers.getDefaultHistory().getHistory();
            Managers.getDefault().deleteAllTasks();
            System.out.println(Managers.getDefault().getAllTasks());
            Managers.getDefault().deleteAllTasks();
            Managers.getDefaultHistory().getHistory();

            Epic epic1 = new Epic("Съездить в Москву", "обязательно до лета");
            Managers.getDefault().addNewEpic(epic1);
            Managers.getDefault().getEpicByID(epic1.getID());
            Managers.getDefaultHistory().getHistory();
            Subtask subtask11 = new Subtask("купить билеты", "дешёвые билеты", epic1.getID());
            Subtask subtask12 = new Subtask("купить одежду", "крутую одежду", epic1.getID());
            Managers.getDefault().addNewSubtask(subtask11);
            Managers.getDefault().getSubtasksByID(subtask11.getID());
            Managers.getDefaultHistory().getHistory();
            Managers.getDefault().addNewSubtask(subtask12);
            Managers.getDefault().getSubtasksByID(subtask12.getID());
            Managers.getDefaultHistory().getHistory();
            System.out.println(Managers.getDefault().getAllEpics());
            System.out.println(Managers.getDefault().getAllSubtasks());
            Managers.getDefault().updateSubtask(new Subtask("купить билеты",
                    "дешёвые билеты", epic1.getID(), TaskStatuses.IN_PROGRESS));
            Managers.getDefault().updateSubtask(new Subtask("купить одежду",
                    "крутую одежду", epic1.getID(), TaskStatuses.DONE));
            System.out.println(Managers.getDefault().getAllEpics());
            System.out.println(Managers.getDefault().getAllSubtasks());

            Epic epic2 = new Epic("посмотреть кино", "обязательно до конца месяца");
            Managers.getDefault().addNewEpic(epic2);
            Managers.getDefault().getEpicByID(epic2.getID());
            Managers.getDefaultHistory().getHistory();
            Subtask subtask21 = new Subtask("найти кино", "в хорошем качестве", epic2.getID());
            Managers.getDefault().addNewSubtask(subtask21);
            Managers.getDefault().getSubtasksByID(subtask21.getID());
            Managers.getDefaultHistory().getHistory();
            System.out.println(Managers.getDefault().getAllEpics());
            System.out.println(Managers.getDefault().getAllSubtasks());
            Managers.getDefault().updateSubtask(new Subtask("найти кино",
                    "в хорошем качестве", epic2.getID(), TaskStatuses.DONE));
            System.out.println(Managers.getDefault().getAllEpics());
            System.out.println(Managers.getDefault().getAllSubtasks());
            Managers.getDefault().deleteAllSubtasks();
            System.out.println(Managers.getDefault().getAllSubtasks());
            System.out.println(Managers.getDefault().getAllEpics());
            Managers.getDefault().deleteAllEpics();
            System.out.println(Managers.getDefault().getAllEpics());
            Managers.getDefaultHistory().getHistory();
        } else if (answer == 2) {
            Epic epic1 = new Epic("Съездить в Москву", "обязательно до лета");
            Managers.getDefault().addNewEpic(epic1);
            Subtask subtask11 = new Subtask("купить билеты", "дешёвые билеты", epic1.getID());
            Managers.getDefault().addNewSubtask(subtask11);
            Subtask subtask12 = new Subtask("купить одежду", "крутую одежду", epic1.getID());
            Managers.getDefault().addNewSubtask(subtask12);
            Subtask subtask13 = new Subtask("подготовиться", "морально", epic1.getID());
            Managers.getDefault().addNewSubtask(subtask13);
            Epic epic2 = new Epic("посмотреть кино", "обязательно до конца месяца");
            Managers.getDefault().addNewEpic(epic2);

            Managers.getDefaultHistory().getHistory();
            Managers.getDefault().getSubtasksByID(subtask11.getID());
            Managers.getDefaultHistory().getHistory();
            Managers.getDefault().getSubtasksByID(subtask12.getID());
            Managers.getDefaultHistory().getHistory();
            Managers.getDefault().getEpicByID(epic1.getID());
            Managers.getDefaultHistory().getHistory();
            Managers.getDefault().getSubtasksByID(subtask13.getID());
            Managers.getDefaultHistory().getHistory();
            Managers.getDefault().getSubtasksByID(subtask11.getID());
            Managers.getDefaultHistory().getHistory();
            Managers.getDefault().getSubtasksByID(subtask13.getID());
            Managers.getDefaultHistory().getHistory();
            Managers.getDefault().getEpicByID(epic2.getID());
            Managers.getDefaultHistory().getHistory();
            Managers.getDefault().getEpicByID(epic1.getID());
            Managers.getDefaultHistory().getHistory();
            Managers.getDefault().getSubtasksByID(subtask13.getID());
            Managers.getDefaultHistory().getHistory();
            Managers.getDefault().getEpicByID(epic2.getID());
            Managers.getDefaultHistory().getHistory();
            Managers.getDefault().getSubtasksByID(subtask12.getID());
            Managers.getDefaultHistory().getHistory();
            Managers.getDefault().deleteSubtaskByID(subtask11.getID());
            Managers.getDefaultHistory().getHistory();
            Managers.getDefault().deleteEpicByID(epic1.getID());
            Managers.getDefaultHistory().getHistory();
        } else if (answer == 3) {
            Epic epic1 = new Epic("Съездить в Москву", "обязательно до лета");
            Managers.getDefault().addNewEpic(epic1);
            Subtask subtask11 = new Subtask("купить билеты", "дешёвые билеты", epic1.getID());
            Managers.getDefault().addNewSubtask(subtask11);
            Subtask subtask12 = new Subtask("купить одежду", "крутую одежду", epic1.getID());
            Managers.getDefault().addNewSubtask(subtask12);
            Subtask subtask13 = new Subtask("подготовиться", "морально", epic1.getID());
            Managers.getDefault().addNewSubtask(subtask13);
            Epic epic2 = new Epic("посмотреть кино", "обязательно до конца месяца");
            Managers.getDefault().addNewEpic(epic2);
            Managers.getDefault().getSubtasksByID(subtask11.getID());
            Managers.getDefault().getSubtasksByID(subtask12.getID());
            Managers.getDefault().getSubtasksByID(subtask13.getID());
            Managers.getDefault().getEpicByID(epic1.getID());
            Managers.getDefault().getEpicByID(epic2.getID());
            Managers.getDefaultHistory().getHistory();
            Managers.getDefault().deleteAllEpics();
            Managers.getDefaultHistory().getHistory();
        } else if (answer == 4) {
            Managers.getDefault().getTaskByID(123);
        }

    }
}