parent(john, mary).
parent(mary, anne).
grandparent(X, Y) :- parent(X, Z), parent(Z, Y).
