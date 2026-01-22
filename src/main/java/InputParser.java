import java.util.Arrays;

public class InputParser {

    public static Integer deleteParser(String input) {
        try {
            return Integer.parseInt(input.substring(7));
        } catch (NumberFormatException | StringIndexOutOfBoundsException e) {
            return -1;
        }
    }

    public static Integer markParser(String input) {
        try {
            return Integer.parseInt(input.substring(5));
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public static Integer unmarkParser(String input) {
        try {
            return Integer.parseInt(input.substring(7));
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public static String todoParser(String input) {
        try {
            return input.substring(5);
        } catch (StringIndexOutOfBoundsException e) {
            return "";
        }
    }

    public static String[] deadlineParser(String input) {
        String temp = "";
        try {
            temp = input.substring(9);
        } catch (StringIndexOutOfBoundsException e) {
            return null;
        }
        String[] tt = temp.split("/by");
        if (tt.length != 2) {
            return null;
        }
        try {
            tt[0] = tt[0].strip();
            tt[1] = tt[1].strip();
        } catch (ArrayIndexOutOfBoundsException e) {
            return null;
        }
        if (tt[0].isEmpty() || tt[1].isEmpty()) {
            return null;
        }
        return tt;
    }

    public static String[] eventParser(String input) {
        String[] temp = null;
        try {
            temp = input.substring(6).split(" /from ");
        } catch (StringIndexOutOfBoundsException e) {
            return null;
        }
        String taskDesc = temp[0];
        if (temp.length != 2) {
            return null;
        }

        String[] times = null;
        try {
            times = temp[1].split(" /to ");
        } catch (ArrayIndexOutOfBoundsException e) {
            return null;
        }
        if (times.length != 2) {
            return null;
        }

        String startTime = times[0];
        String endTime = "";

        try {
            endTime = times[1];
        } catch (ArrayIndexOutOfBoundsException e) {
            return null;
        }
        return new String[]{taskDesc, startTime, endTime};
    }

}
