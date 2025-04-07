//package it.unibo.tuprolog.examples.solve;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

import it.unibo.tuprolog.core.*;
import it.unibo.tuprolog.core.Integer;
import it.unibo.tuprolog.solve.Solution;
import it.unibo.tuprolog.solve.Solver;
import it.unibo.tuprolog.solve.flags.TrackVariables;
import it.unibo.tuprolog.solve.primitive.Solve;
import it.unibo.tuprolog.solve.primitive.UnaryPredicate;
import it.unibo.tuprolog.theory.Theory;
import it.unibo.tuprolog.theory.parsing.ClausesParser;

@SuppressWarnings({"rawtypes", "FieldMayBeFinal", "unchecked", "NullableProblems", "ConstantConditions"})
public class EjemploTuProlog extends Thread{
        private int coldThreshold;
        private int hotThreshold;
        private int temperature;
    
        public EjemploTuProlog(String name, int coldThreshold, int hotThreshold, int temperature) {
            super(name);
            this.coldThreshold = coldThreshold;
            this.hotThreshold = hotThreshold;
            this.temperature = temperature;
        }
    
        public int getTemperature() {
            return temperature;
        }
    
        private UnaryPredicate getTemp = new UnaryPredicate.Functional("get_temp") {
            protected Substitution computeOneSubstitution(Solve.Request request, Term first) {
                ensuringArgumentIsVariable(request, 0);
                return request.getUnificator().mgu(first, Integer.of(temperature));
            }
        };
    
        private UnaryPredicate push = new UnaryPredicate.Predicative("push") {
            @Override
            protected boolean compute(Solve.Request request, Term first) {
                ensuringAllArgumentsAreInstantiated(request);
                ensuringArgumentIsAtom(request, 0);
                String firstValue = first.castToAtom().getValue();
                if ("hot".equals(firstValue)) {
                    temperature++;
                } else if ("cold".equals(firstValue)) {
                    temperature--;
                } else {
                    return false;
                }
                return true;
            }
        };
    
        private String agentProgram() throws IOException {
            // opens the file in examples/src/main/resources/it/unibo/tuprolog/examples/solve
            File file = new File("thermostat.pl");
            try (InputStream inputStream = new FileInputStream(file)) {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                    return reader.lines().collect(Collectors.joining("\n"))
                            .replace("__COLD_THRESHOLD__", coldThreshold + "")
                            .replace("__HOT_THRESHOLD__", hotThreshold + "");
                }
            }
        }
    
        private ClausesParser prologParser = ClausesParser.withDefaultOperators();
    
        @Override
        public void run() {
            try {
                Theory theory = prologParser.parseTheory(agentProgram());
                Solver solver = Solver.prolog().newBuilder()
                        .staticKb(theory)
                        .flag(TrackVariables.INSTANCE, TrackVariables.ON)
                        .library("libs.agency.thermostat", getTemp, push)
                        .build();
                Solution solution = solver.solveOnce(Atom.of("start"));
                if (solution.isYes()) {
                    System.out.println("Reached target temperature: " + getTemperature());
                } else if (solution.isNo()) {
                    System.out.println("Failure in logic program");
                } else {
                    System.out.println("Error in logic program:");
                    for (Struct entry : solution.getException().getLogicStackTrace()) {
                        System.out.println("\tin " + entry);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    
        public static void main(String[] args) throws InterruptedException {
            int startingTemp = Common.askForNumericInput("Input starting temperature (number): > ");
            EjemploTuProlog agent = new EjemploTuProlog("thermostat", 20, 24, startingTemp);
            agent.start();
            agent.join();
        }
}