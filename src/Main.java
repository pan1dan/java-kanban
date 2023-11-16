public class Main {
    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();

        Task task1 = new Task("Read book every day", "30 pages");
        taskManager.addNewTask(task1);
        System.out.println(taskManager.getAllTasks());
        taskManager.updateTask(task1, new Task("Read book every day", "30 pages", "IN_PROGRESS"));
        System.out.println(taskManager.getAllTasks());

        Task task2 = new Task("jump every day", "30 iterations");
        taskManager.addNewTask(task2);
        System.out.println(taskManager.getAllTasks());
        taskManager.updateTask(task2, new Task("jump every day", "30 iterations", "DONE"));
        System.out.println(taskManager.getAllTasks());
        taskManager.deleteAllTasks();
        System.out.println(taskManager.getAllTasks());

        Epic epic1 = new Epic("Съездить в Москву", "обязательно до лета");
        taskManager.addNewEpic(epic1);
        Subtask subtask11 = new Subtask("купить билеты", "дешёвые билеты", epic1.getName());
        Subtask subtask12 = new Subtask("купить одежду", "крутую одежду", epic1.getName());
        taskManager.addNewSubtask(epic1, subtask11);
        taskManager.addNewSubtask(epic1, subtask12);
        System.out.println(taskManager.getAllEpics());
        System.out.println(taskManager.getAllSubtasks());
        taskManager.updateSubtask(epic1, subtask11, new Subtask("купить билеты",
                "дешёвые билеты", epic1.getName(), "IN_PROGRESS"));
        taskManager.updateSubtask(epic1, subtask12, new Subtask("купить одежду",
                "крутую одежду", epic1.getName(), "DONE"));
        System.out.println(taskManager.getAllEpics());
        System.out.println(taskManager.getAllSubtasks());

        Epic epic2 = new Epic("посмотреть кино", "обязательно до конца месяца");
        taskManager.addNewEpic(epic2);
        Subtask subtask21 = new Subtask("найти кино", "в хорошем качестве", epic2.getName());
        taskManager.addNewSubtask(epic2, subtask21);
        System.out.println(taskManager.getAllEpics());
        System.out.println(taskManager.getAllSubtasks());
        taskManager.updateSubtask(epic2, subtask21, new Subtask("найти кино",
                "в хорошем качестве", epic2.getName(), "DONE"));
        System.out.println(taskManager.getAllEpics());
        System.out.println(taskManager.getAllSubtasks());
        taskManager.deleteAllSubtasks();
        System.out.println(taskManager.getAllSubtasks());
        System.out.println(taskManager.getAllEpics());
        taskManager.deleteAllEpics();
        System.out.println(taskManager.getAllEpics());
    }
}