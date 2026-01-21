import java.util.Scanner;

public class Advisor {

    static Scanner input = new Scanner(System.in);

    public static String getCommand() {
        System.out.println("Enter a command:");
        return input.nextLine().strip().toLowerCase();
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
            String command = getCommand();


            if (command.equals("bye")) {
                System.out.println(line);
                System.out.println("End of Session. Goodbye.");
                System.out.println(line);
                endSession = true;
                return;
            }

            System.out.println(line);
            System.out.println(command);
            System.out.println(line);


        }


    }
}
