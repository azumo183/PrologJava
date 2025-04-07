public class App {
    public static void main(String[] args) throws Exception {
        System.out.println(System.getProperty("user.dir") + "\\thermostat.pl");
        while (true) {
            String option = Common.askForInput("\r\nSeleccione una opción:\r\n  1. Ejempo JPL\r\n  2. Ejemplo tuProlog\r\n  3. Salir\r\n> ");
            if(option.equals("1")) FactorialJPL.main(args);
            else if(option.equals("2")) FactorialTuProlog.main(args);
            else if(option.equals("3")) break;
            else System.out.println("La opción ingresada no es válida.");
        }
    }
}