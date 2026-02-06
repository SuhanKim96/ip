package kiki;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class DeadlineTest {
    @Test
    public void testDeadlineString() throws KikiException {
        Deadline d = new Deadline("return book", "2025-12-25");
        assertEquals("[D][ ] return book (by: Dec 25 2025)", d.toString());
    }
}
