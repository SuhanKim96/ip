package kiki;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TodoTest {
    @Test
    public void testTodoString() {
        assertEquals("[T][ ] read book", new Todo("read book").toString());
    }
}