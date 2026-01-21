import java.util.ArrayList;
import java.util.Scanner;

public class Advisor {

    static Scanner input = new Scanner(System.in);
    static ArrayList<String> todo = new ArrayList<>();

    public static String getInput() {
        return input.nextLine().strip().toLowerCase();
    }

    public static void updateToDoList(String task) {
        todo.add(task);
    }

    public static void main(String[] args) {
        String logo = "            _       _                \n" +
                "           | |     (_)               \n" +
                "   __ _  __| |_   ___ ___  ___  _ __ \n" +
                "  / _` |/ _` \\ \\ / / / __|/ _ \\| '__|\n" +
                " | (_| | (_| |\\ V /| \\__ \\ (_) | |   \n" +
                "  \\__,_|\\__,_| \\_/ |_|___/\\___/|_|   \n" +
                "                                     \n" +
                "                                     ";
        String name = "Advisor";
        String line = "____________________________________________________________";


        System.out.println(logo);
        System.out.println(line);
        System.out.println("Hello. I am " + name);
        System.out.println("What do you want me to do?");

        boolean endSession = false;

        while (!endSession) {
            System.out.println("Enter a command:");
            String input = getInput();

            if (input.equals("bye")) {
                System.out.println(line);
                System.out.println("End of Session. Goodbye.");
                System.out.println(line);
                endSession = true;
                return;
            } else if (input.equals("list")) {
                System.out.println(line);
                for (int i = 0; i < todo.size(); i += 1) {
                    String task = todo.get(i);
                    System.out.println((i + 1) + ". " + task);
                }
                System.out.println(line);
            } else {
                System.out.println(line);
                updateToDoList(input);
                System.out.println("added: " + input);
                System.out.println(line);
            }


        }


    }
}
