import org.jpl7.JPL;
import org.jpl7.Query;

public class App {
    public static void main(String[] args) throws Exception {
        JPL.init(); // Initialize JPL
        System.out.println("JPL initialized successfully!");

        String query = "consult('data.pl')."; // Load Prolog file
        Query q1 = new Query(query);
        System.out.println("Consulting Prolog file: " + (q1.hasSolution() ? "Success" : "Failed"));

        Query q2 = new Query("grandparent(X, anne).");
        while (q2.hasMoreSolutions()) {
            System.out.println(q2.nextSolution());
        }
    }
}