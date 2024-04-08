package tests;

import managers.Managers;
import managers.taskManager.FileBackedTasksManager;
import org.junit.jupiter.api.Test;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Map;

import static java.time.Month.*;
import static managers.taskManager.FileBackedTasksManager.loadFromFile;
import static org.junit.jupiter.api.Assertions.*;

public class FileBackedTasksManagerTest {
    @Test
    void isWorkingFileBackedTasksManagerStandard() {
        FileBackedTasksManager fileBackedTasksManager1 = new FileBackedTasksManager(
                new File(System.getProperty("user.dir") + "\\history.txt"));
        Task task1 = new Task("Read book every day", "30 pages",
                LocalDateTime.of(2024, MARCH,28,13, 0), 60);
        fileBackedTasksManager1.addNewTask(task1);
        Task task2 = new Task("jump every day", "30 iterations",
                LocalDateTime.of(2024, APRIL,28,13, 0), 60);
        fileBackedTasksManager1.addNewTask(task2);
        Task task3 = new Task("Eat every day", "3 iterations",
                LocalDateTime.of(2024, MAY,28,13, 0), 60);
        fileBackedTasksManager1.addNewTask(task3);

        Epic epic1 = new Epic("Съездить в Москву", "обязательно до лета");
        fileBackedTasksManager1.addNewEpic(epic1);
        Subtask subtask11 = new Subtask("купить билеты", "дешёвые билеты", epic1.getID(),
                LocalDateTime.of(2023, AUGUST,28,13, 0), 60);
        fileBackedTasksManager1.addNewSubtask(subtask11);
        Subtask subtask12 = new Subtask("купить одежду", "крутую одежду", epic1.getID(),
                LocalDateTime.of(2023, SEPTEMBER,28,13, 0), 60);
        fileBackedTasksManager1.addNewSubtask(subtask12);

        Epic epic2 = new Epic("посмотреть кино", "обязательно до конца месяца");
        fileBackedTasksManager1.addNewEpic(epic2);
        Subtask subtask21 = new Subtask("найти кино", "в хорошем качестве", epic2.getID(),
                LocalDateTime.of(2023, DECEMBER,28,13, 0), 60);
        fileBackedTasksManager1.addNewSubtask(subtask21);

        fileBackedTasksManager1.getTaskByID(task1.getID());
        fileBackedTasksManager1.getTaskByID(task2.getID());
        fileBackedTasksManager1.getTaskByID(task3.getID());
        fileBackedTasksManager1.getTaskByID(task2.getID());

        fileBackedTasksManager1.getEpicByID(epic1.getID());
        fileBackedTasksManager1.getSubtasksByID(subtask11.getID());
        fileBackedTasksManager1.getSubtasksByID(subtask12.getID());
        fileBackedTasksManager1.getTaskByID(task1.getID());

        fileBackedTasksManager1.getEpicByID(epic2.getID());
        fileBackedTasksManager1.getSubtasksByID(subtask21.getID());
        fileBackedTasksManager1.getAllEpicSubtasks(epic2.getID());
        fileBackedTasksManager1.deleteTaskByID(task2.getID());
        fileBackedTasksManager1.deleteAllSubtasks();
        ArrayList<Task> oldHistory = Managers.getDefaultHistory().getHistory();

        FileBackedTasksManager fileBackedTasksManager2 = loadFromFile(fileBackedTasksManager1.file);
        assertEquals(fileBackedTasksManager1.getAllTasks(), fileBackedTasksManager2.getAllTasks(), "Исходные " +
                "задачи не были возвращены");
        assertEquals(fileBackedTasksManager1.getAllEpics(), fileBackedTasksManager2.getAllEpics(), "Исходные " +
                "эпики не были возвращены");
        assertEquals(fileBackedTasksManager1.getAllSubtasks(), fileBackedTasksManager2.getAllSubtasks(),
                "Исходные подзадачи не были возвращены");
        ArrayList<Task> newHistory = Managers.getDefaultHistory().getHistory();
        assertEquals(oldHistory, newHistory, "Исходная история не была возвращена");

        fileBackedTasksManager1.deleteAllTasks();
        fileBackedTasksManager1.deleteAllSubtasks();
        fileBackedTasksManager1.deleteAllEpics();
        fileBackedTasksManager2.deleteAllTasks();
        fileBackedTasksManager2.deleteAllSubtasks();
        fileBackedTasksManager2.deleteAllEpics();
    }

    @Test
    void isWorkingFileBackedTasksManagerWithoutTasks() {
        FileBackedTasksManager fileBackedTasksManager1 = new FileBackedTasksManager(
                new File(System.getProperty("user.dir") + "\\history.txt"));
        ArrayList<Task> oldHistory = Managers.getDefaultHistory().getHistory();

        FileBackedTasksManager fileBackedTasksManager2 = loadFromFile(fileBackedTasksManager1.file);
        assertEquals(fileBackedTasksManager1.getAllTasks(), fileBackedTasksManager2.getAllTasks(), "Исходные " +
                "задачи не были возвращены");
        assertEquals(fileBackedTasksManager1.getAllEpics(), fileBackedTasksManager2.getAllEpics(), "Исходные " +
                "эпики не были возвращены");
        assertEquals(fileBackedTasksManager1.getAllSubtasks(), fileBackedTasksManager2.getAllSubtasks(),
                "Исходные подзадачи не были возвращены");
        ArrayList<Task> newHistory = Managers.getDefaultHistory().getHistory();
        assertEquals(oldHistory, newHistory, "Исходная история не была возвращена");
    }

    @Test
    void isWorkingFileBackedTasksManagerWithEpicsButWithoutSubtasks() {
        FileBackedTasksManager fileBackedTasksManager1 = new FileBackedTasksManager(
                new File(System.getProperty("user.dir") + "\\history.txt"));
        Task task1 = new Task("Read book every day", "30 pages",
                LocalDateTime.of(2024, MAY,28,13, 0), 60);
        fileBackedTasksManager1.addNewTask(task1);
        Task task2 = new Task("jump every day", "30 iterations",
                LocalDateTime.of(2024, APRIL,28,13, 0), 1440);
        fileBackedTasksManager1.addNewTask(task2);
        Task task3 = new Task("Eat every day", "3 iterations",
                LocalDateTime.of(2024, AUGUST,28,13, 0), 21440);
        fileBackedTasksManager1.addNewTask(task3);

        Epic epic1 = new Epic("Съездить в Москву", "обязательно до лета");
        fileBackedTasksManager1.addNewEpic(epic1);

        Epic epic2 = new Epic("посмотреть кино", "обязательно до конца месяца");
        fileBackedTasksManager1.addNewEpic(epic2);

        fileBackedTasksManager1.getTaskByID(task1.getID());
        fileBackedTasksManager1.getTaskByID(task2.getID());
        fileBackedTasksManager1.getTaskByID(task3.getID());
        fileBackedTasksManager1.deleteTaskByID(task2.getID());
        fileBackedTasksManager1.getTaskByID(task2.getID());

        fileBackedTasksManager1.getEpicByID(epic1.getID());
        fileBackedTasksManager1.getTaskByID(task1.getID());
        fileBackedTasksManager1.deleteAllSubtasks();

        fileBackedTasksManager1.getEpicByID(epic2.getID());
        fileBackedTasksManager1.getAllEpicSubtasks(epic2.getID());
        ArrayList<Task> oldHistory = Managers.getDefaultHistory().getHistory();

        FileBackedTasksManager fileBackedTasksManager2 = loadFromFile(fileBackedTasksManager1.file);
        assertEquals(fileBackedTasksManager1.getAllTasks(), fileBackedTasksManager2.getAllTasks(), "Исходные " +
                "задачи не были возвращены");
        assertEquals(fileBackedTasksManager1.getAllEpics(), fileBackedTasksManager2.getAllEpics(), "Исходные " +
                "эпики не были возвращены");
        assertEquals(fileBackedTasksManager1.getAllSubtasks(), fileBackedTasksManager2.getAllSubtasks(),
                "Исходные подзадачи не были возвращены");
        ArrayList<Task> newHistory = Managers.getDefaultHistory().getHistory();
        assertEquals(oldHistory, newHistory, "Исходная история не была возвращена");
    }

    @Test
    void isWorkingFileBackedTasksManagerWithoutHistory() {
        FileBackedTasksManager fileBackedTasksManager1 = new FileBackedTasksManager(
                new File(System.getProperty("user.dir") + "\\history.txt"));
        Task task1 = new Task("Read book every day", "30 pages");
        fileBackedTasksManager1.addNewTask(task1);
        Task task2 = new Task("jump every day", "30 iterations");
        fileBackedTasksManager1.addNewTask(task2);
        Task task3 = new Task("Eat every day", "3 iterations");
        fileBackedTasksManager1.addNewTask(task3);

        Epic epic1 = new Epic("Съездить в Москву", "обязательно до лета");
        fileBackedTasksManager1.addNewEpic(epic1);
        Subtask subtask11 = new Subtask("купить билеты", "дешёвые билеты", epic1.getID());
        fileBackedTasksManager1.addNewSubtask(subtask11);
        Subtask subtask12 = new Subtask("купить одежду", "крутую одежду", epic1.getID());
        fileBackedTasksManager1.addNewSubtask(subtask12);

        Epic epic2 = new Epic("посмотреть кино", "обязательно до конца месяца");
        fileBackedTasksManager1.addNewEpic(epic2);
        Subtask subtask21 = new Subtask("найти кино", "в хорошем качестве", epic2.getID());
        fileBackedTasksManager1.addNewSubtask(subtask21);

        fileBackedTasksManager1.getTaskByID(task1.getID());
        fileBackedTasksManager1.getTaskByID(task2.getID());
        fileBackedTasksManager1.getTaskByID(task3.getID());
        fileBackedTasksManager1.getTaskByID(task2.getID());

        fileBackedTasksManager1.getEpicByID(epic1.getID());
        fileBackedTasksManager1.getSubtasksByID(subtask11.getID());
        fileBackedTasksManager1.getSubtasksByID(subtask12.getID());
        fileBackedTasksManager1.getTaskByID(task1.getID());

        fileBackedTasksManager1.getEpicByID(epic2.getID());
        fileBackedTasksManager1.getSubtasksByID(subtask21.getID());
        fileBackedTasksManager1.getAllEpicSubtasks(epic2.getID());
        fileBackedTasksManager1.deleteTaskByID(task2.getID());
        fileBackedTasksManager1.deleteAllSubtasks();
        fileBackedTasksManager1.deleteAllTasks();
        fileBackedTasksManager1.deleteAllEpics();
        ArrayList<Task> oldHistory = Managers.getDefaultHistory().getHistory();

        FileBackedTasksManager fileBackedTasksManager2 = loadFromFile(fileBackedTasksManager1.file);
        assertEquals(fileBackedTasksManager1.getAllTasks(), fileBackedTasksManager2.getAllTasks(), "Исходные " +
                "задачи не были возвращены");
        assertEquals(fileBackedTasksManager1.getAllEpics(), fileBackedTasksManager2.getAllEpics(), "Исходные " +
                "эпики не были возвращены");
        assertEquals(fileBackedTasksManager1.getAllSubtasks(), fileBackedTasksManager2.getAllSubtasks(),
                "Исходные подзадачи не были возвращены");
        ArrayList<Task> newHistory = Managers.getDefaultHistory().getHistory();
        assertEquals(oldHistory, newHistory, "Исходная история не была возвращена");

        fileBackedTasksManager1.deleteAllTasks();
        fileBackedTasksManager1.deleteAllSubtasks();
        fileBackedTasksManager1.deleteAllEpics();
        fileBackedTasksManager2.deleteAllTasks();
        fileBackedTasksManager2.deleteAllSubtasks();
        fileBackedTasksManager2.deleteAllEpics();
    }

    @Test
    void isWorkingFileBackedTasksManagerWithSaveInFile() {
        FileBackedTasksManager fileBackedTasksManager1 = new FileBackedTasksManager(
                new File(System.getProperty("user.dir") + "\\history.txt"));
        Task task1 = new Task("Read book every day", "30 pages",
                LocalDateTime.of(2024, MARCH,28,13, 0), 60);
        fileBackedTasksManager1.addNewTask(task1);
        Task task2 = new Task("jump every day", "30 iterations",
                LocalDateTime.of(2024, APRIL,28,13, 0), 60);
        fileBackedTasksManager1.addNewTask(task2);
        Task task3 = new Task("Eat every day", "3 iterations",
                LocalDateTime.of(2024, MAY,28,13, 0), 60);
        fileBackedTasksManager1.addNewTask(task3);

        Epic epic1 = new Epic("Съездить в Москву", "обязательно до лета");
        fileBackedTasksManager1.addNewEpic(epic1);
        Subtask subtask11 = new Subtask("купить билеты", "дешёвые билеты", epic1.getID(),
                LocalDateTime.of(2023, AUGUST,28,13, 0), 60);
        fileBackedTasksManager1.addNewSubtask(subtask11);
        Subtask subtask12 = new Subtask("купить одежду", "крутую одежду", epic1.getID(),
                LocalDateTime.of(2023, SEPTEMBER,28,13, 0), 60);
        fileBackedTasksManager1.addNewSubtask(subtask12);

        Epic epic2 = new Epic("посмотреть кино", "обязательно до конца месяца");
        fileBackedTasksManager1.addNewEpic(epic2);
        Subtask subtask21 = new Subtask("найти кино", "в хорошем качестве", epic2.getID(),
                LocalDateTime.of(2023, DECEMBER,28,13, 0), 60);
        fileBackedTasksManager1.addNewSubtask(subtask21);

        fileBackedTasksManager1.getTaskByID(task1.getID());
        fileBackedTasksManager1.getTaskByID(task2.getID());
        fileBackedTasksManager1.getTaskByID(task3.getID());
        fileBackedTasksManager1.getTaskByID(task2.getID());

        fileBackedTasksManager1.getEpicByID(epic1.getID());
        fileBackedTasksManager1.getSubtasksByID(subtask11.getID());
        fileBackedTasksManager1.getSubtasksByID(subtask12.getID());
        fileBackedTasksManager1.getTaskByID(task1.getID());

        fileBackedTasksManager1.getEpicByID(epic2.getID());
        fileBackedTasksManager1.getSubtasksByID(subtask21.getID());
        fileBackedTasksManager1.getAllEpicSubtasks(epic2.getID());
    }
}
