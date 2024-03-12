package servers;

import tasks.Task;

import java.time.LocalDateTime;

import static java.time.Month.MAY;
import static org.junit.jupiter.api.Assertions.*;

class HttpTaskServerTest {
    Task task1 = new Task("Read book every day", "30 pages",
            LocalDateTime.of(2024, MAY,28,13, 0), 60);
    
}