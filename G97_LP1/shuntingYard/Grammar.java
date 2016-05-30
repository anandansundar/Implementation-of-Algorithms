package shuntingYard;

public enum Grammar {
	leftparen(0),
	rightparen(1),
	
	sum(2),
	difference(2),
	
	division(4),
	product(4),
	mod(4),
	
	exponentiation(5),
	
	factorial(6),
	sqaure_root(6),
	
	number(7);
	
	int priority;
	
	Grammar(int priority) {
		this.priority = priority;
	}
	
	public int getPriority() {
		return priority;
	}

}