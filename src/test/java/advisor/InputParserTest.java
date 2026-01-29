package advisor;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InputParserTest {

    @Test
    public void test1() {
        assertEquals(-1, InputParser.deleteParser(""));
        assertEquals(-1, InputParser.deleteParser("delete"));
        assertEquals(-1, InputParser.deleteParser("delete "));
        assertEquals(-1, InputParser.deleteParser("delete lol"));
        assertEquals(7, InputParser.deleteParser("delete 7"));
    }

    @Test
    public void test2() {
        assertEquals(null, InputParser.deadlineParser("deadline"));
        assertEquals(null, InputParser.deadlineParser("deadline /by"));
        assertEquals(null, InputParser.deadlineParser("deadline huh"));
        assertEquals(null, InputParser.deadlineParser("deadline huh /by"));

        String[] temp1 = InputParser.deadlineParser("deadline do homework /by tomorrow");
        assertEquals("do homework", temp1[0]);
        assertEquals("tomorrow", temp1[1]);


        String[] temp2 = InputParser.deadlineParser("deadline do homework /by 2026-01-31 1800");
        assertEquals("do homework", temp2[0]);
        assertEquals("2026-01-31 1800", temp2[1]);

    }

}
