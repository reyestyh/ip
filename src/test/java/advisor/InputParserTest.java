package advisor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class InputParserTest {

    @Test
    public void deleteParser_incorrectFormat_exceptionThrown() throws AdvisorException {
        assertThrows(AdvisorException.class, () -> InputParser.deleteParser(""));
        assertThrows(AdvisorException.class, () -> InputParser.deleteParser("delete"));
        assertThrows(AdvisorException.class, () -> InputParser.deleteParser("delete "));
        assertThrows(AdvisorException.class, () -> InputParser.deleteParser("delete lol"));
    }

    @Test
    public void deleteParser_correctFormat_correctNumberReturned() throws AdvisorException {
        assertEquals(7, InputParser.deleteParser("delete 7"));
    }

    @Test
    public void deadlineParser_incorrectFormat_advisorExceptionThrown() throws AdvisorException {
        assertThrows(AdvisorException.class, () -> InputParser.deadlineParser("deadline"));
        assertThrows(AdvisorException.class, () -> InputParser.deadlineParser("deadline /by"));
        assertThrows(AdvisorException.class, () -> InputParser.deadlineParser("deadline huh"));
        assertThrows(AdvisorException.class, () -> InputParser.deadlineParser("deadline huh /by"));
    }

    @Test
    public void deadlineParser_sampleCommand_correctParsing() throws AdvisorException {
        String[] temp1 = InputParser.deadlineParser("deadline do homework /by tomorrow");
        assertEquals("do homework", temp1[0]);
        assertEquals("tomorrow", temp1[1]);


        String[] temp2 = InputParser.deadlineParser("deadline do homework /by 2026-01-31 1800");
        assertEquals("do homework", temp2[0]);
        assertEquals("2026-01-31 1800", temp2[1]);
    }


}
