public class Main {
    public static void main(String[] args) {
        Managers managers = new Managers();

        Task task1 = new Task("Read book every day", "30 pages");
        managers.taskManager.addNewTask(task1);
        managers.taskManager.getTaskByID(task1.getID());
        Managers.getDefaultHistory().getHistory();
        System.out.println(managers.taskManager.getAllTasks());
        managers.taskManager.updateTask(new Task("Read book every day", "30 pages", TaskStatuses.IN_PROGRESS));
        managers.taskManager.getTaskByID(task1.getID());
        Managers.getDefaultHistory().getHistory();
        System.out.println(managers.taskManager.getAllTasks());

        Task task2 = new Task("jump every day", "30 iterations");
        managers.taskManager.addNewTask(task2);
        managers.taskManager.getTaskByID(task2.getID());
        Managers.getDefaultHistory().getHistory();
        System.out.println(managers.taskManager.getAllTasks());
        managers.taskManager.updateTask(new Task("jump every day", "30 iterations", TaskStatuses.DONE));
        System.out.println(managers.taskManager.getAllTasks());
        managers.taskManager.deleteAllTasks();
        System.out.println(managers.taskManager.getAllTasks());

        Epic epic1 = new Epic("Съездить в Москву", "обязательно до лета");
        managers.taskManager.addNewEpic(epic1);
        managers.taskManager.getEpicByID(epic1.getID());
        Managers.getDefaultHistory().getHistory();
        Subtask subtask11 = new Subtask("купить билеты", "дешёвые билеты", epic1.getID());
        Subtask subtask12 = new Subtask("купить одежду", "крутую одежду", epic1.getID());
        managers.taskManager.addNewSubtask(subtask11);
        managers.taskManager.getSubtasksByID(subtask11.getID());
        Managers.getDefaultHistory().getHistory();
        managers.taskManager.addNewSubtask(subtask12);
        managers.taskManager.getSubtasksByID(subtask12.getID());
        Managers.getDefaultHistory().getHistory();
        System.out.println(managers.taskManager.getAllEpics());
        System.out.println(managers.taskManager.getAllSubtasks());
        managers.taskManager.updateSubtask(new Subtask("купить билеты",
                "дешёвые билеты", epic1.getID(), TaskStatuses.IN_PROGRESS));
        managers.taskManager.updateSubtask(new Subtask("купить одежду",
                "крутую одежду", epic1.getID(), TaskStatuses.DONE));
        System.out.println(managers.taskManager.getAllEpics());
        System.out.println(managers.taskManager.getAllSubtasks());

        Epic epic2 = new Epic("посмотреть кино", "обязательно до конца месяца");
        managers.taskManager.addNewEpic(epic2);
        managers.taskManager.getEpicByID(epic2.getID());
        Managers.getDefaultHistory().getHistory();
        Subtask subtask21 = new Subtask("найти кино", "в хорошем качестве", epic2.getID());
        managers.taskManager.addNewSubtask(subtask21);
        managers.taskManager.getSubtasksByID(subtask21.getID());
        Managers.getDefaultHistory().getHistory();
        System.out.println(managers.taskManager.getAllEpics());
        System.out.println(managers.taskManager.getAllSubtasks());
        managers.taskManager.updateSubtask(new Subtask("найти кино",
                "в хорошем качестве", epic2.getID(), TaskStatuses.DONE));
        System.out.println(managers.taskManager.getAllEpics());
        System.out.println(managers.taskManager.getAllSubtasks());
        managers.taskManager.deleteAllSubtasks();
        System.out.println(managers.taskManager.getAllSubtasks());
        System.out.println(managers.taskManager.getAllEpics());
        managers.taskManager.deleteAllEpics();
        System.out.println(managers.taskManager.getAllEpics());
    }
}