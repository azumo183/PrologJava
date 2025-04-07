import java.nio.file.Files;
import java.nio.file.Paths;

import it.unibo.tuprolog.core.*;
import it.unibo.tuprolog.solve.MutableSolver;
import it.unibo.tuprolog.solve.Solution;
import it.unibo.tuprolog.solve.Solver;
import it.unibo.tuprolog.theory.Theory;
import it.unibo.tuprolog.theory.parsing.ClausesParser;
import it.unibo.tuprolog.core.Integer;

public class FactorialTuProlog {
    public static void main(String[] args) throws Exception {
        // Get clauses from file
        String factorialRule = Files.readString(Paths.get(Common.factorialFilePath));
        Theory staticKb = ClausesParser.withDefaultOperators().parseTheory(factorialRule);

        // Initialize the engine
        MutableSolver mutable = Solver.prolog().newBuilder().buildMutable();
        mutable.loadStaticKb(staticKb);
        
        // Call the factorial predicate
        int number = Common.askForNumericInput("\r\nInput a number: > ");
        Struct query = Struct.of("factorial", Integer.of(number), Var.of("Result"));
        Solution solution = mutable.solveOnce(query);

        // Fetch result
        if (solution.isYes()) {
            System.out.println("Factorial of " + number + " is: " + solution.getSubstitution().getByName("Result"));
        } else {
            System.out.println("No solution found.");
        }
    }
}