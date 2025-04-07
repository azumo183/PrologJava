import org.jpl7.Query;
import org.jpl7.Term;

public class FactorialJPL {
    public static void main(String[] args) throws Exception {
        // Initialize Prolog file
        String consultQuery = "consult('" + Common.factorialFilePath + "')";
        
        // Consult the Prolog file
        if (new Query(consultQuery).hasSolution()) {
            System.out.println(Common.factorialFilePath + " loaded successfully");
        } else {
            System.out.println("Failed to load " + Common.factorialFilePath);
            return;
        }
        
        // Call the factorial predicate
        int number = Common.askForNumericInput("\r\nInput a number: > "); // Number to calculate factorial for
        String prologQuery = "factorial(" + number + ", Result)";
        Query query = new Query(prologQuery);

        // Fetch result
        if (query.hasSolution()) {
            Term result = query.oneSolution().get("Result");
            System.out.println("Factorial of " + number + " is: " + result);
        } else {
            System.out.println("No solution found");
        }
    }
}
