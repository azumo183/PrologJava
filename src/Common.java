public class Common {
    public static String factorialFilePath = "factorial.pl";

    public static String askForInput(String head) {
        System.out.print(head);
        String input = System.console().readLine();
        return input;
    }

    public static int askForNumericInput(String head) {
        while (true) {
            String input = Common.askForInput(head);
            try {
                int number = Integer.parseInt(input);
                return number;
            } catch (Exception e) {
                System.out.println("Not a valid number, please try again.");
            }
        }
    }
}
