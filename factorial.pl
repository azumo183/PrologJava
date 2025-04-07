% Define factorial predicate
factorial(0, 1). % Base case
factorial(N, Result) :-
    N > 0,
    N1 is N - 1,
    factorial(N1, Result1),
    Result is N * Result1.