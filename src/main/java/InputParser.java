public class InputParser {

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
        return temp.split(" /by ");
    }

    public static String[] eventParser(String input) {
        String[] tmp = input.substring(6).split(" /from ");
        String taskDesc = tmp[0];
        String[] times = tmp[1].split(" /to ");
        String startTime = times[0];
        String endTime = times[1];
        return new String[]{taskDesc, startTime, endTime};
    }

}
