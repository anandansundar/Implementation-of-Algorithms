package shuntingYard;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

import numlib.BigNumber;

/**
 * 
 * @author RahulAravind
 * 
 * Shunting Yard algorithm.
 * 
 * Converting infix expressions to postfix
 * 
 * Assuming all expressions will be valid expressions
 *
 */
public class Parser {
	
	Stack<Character> stack;
	Queue<Character> queue;
	BigNumber bignum = new BigNumber();
	
	public Parser() {
		stack = new Stack<Character>();
		queue = new LinkedList<Character>();
	}
	
	public Grammar getPriority(char ch) {
		
		switch(ch) {
		
		case '(' :
			return Grammar.leftparen;
		case ')' :
			return Grammar.rightparen;
		case '+':
			return Grammar.sum;
		case '-':
			return Grammar.difference;
		case '*':
			return Grammar.product;
		case '/':
			return Grammar.division;
		case '%':
			return Grammar.mod;
		case '^':
			return Grammar.exponentiation;
		case '!':
			return Grammar.factorial;
		case '~':
			return Grammar.sqaure_root;
		default:
			return Grammar.number;
		}
	}
	
	public String convertInfix2Postfix(String expression) {
		
		/**
		 * 
		 * shunting yard algorithm
		 * 
		 */
		
		String postfix = "";
		
		Grammar curPriority;
		for(int i = 0; i < expression.length(); i++) {
			curPriority = getPriority(expression.charAt(i));
			
			if(curPriority == Grammar.number) {
				queue.add(expression.charAt(i));
			}
			
			else if(curPriority == Grammar.rightparen) {
				while(getPriority(stack.peek()) != Grammar.leftparen) {
					queue.add(stack.pop());
				}
				
				stack.pop();
			}
			
			else {
				while(!stack.isEmpty()) {
					
					// Right - associativity
					if(curPriority == Grammar.exponentiation && getPriority(stack.peek()).getPriority() > curPriority.getPriority()) {
						queue.add(stack.pop());
					}
					// left - associativity
					else if(curPriority != Grammar.exponentiation && getPriority(stack.peek()).getPriority() >= curPriority.getPriority()) {
						queue.add(stack.pop());
					}
					else {
						break;
					}
					
				}
				
				stack.push(expression.charAt(i));
			}
		}
		
		while(!stack.isEmpty()) {
			queue.add(stack.pop());
		}
		
		System.out.println(queue);
		
		return postfix;
	}
	
	public BigNumber evaluatePostfix() {
		BigNumber res = new BigNumber();
		
		Stack<BigNumber> st = new Stack<BigNumber>();
		
		while(!queue.isEmpty()) {
			Character token = queue.poll();
			Grammar priority = getPriority(token);
			
			if(priority == Grammar.number) {
				Long val = (long) Character.getNumericValue(token.charValue());
				st.push(new BigNumber(val));
			}
			
			else {
				
				BigNumber tempRes = new BigNumber();
				BigNumber b = null;
				BigNumber a = null;
				
				if(priority == Grammar.factorial) {
					b = st.pop();
					tempRes = bignum.factorial(b);
				}
				
				else if(priority == Grammar.sqaure_root) {
					b = st.pop();
					tempRes = bignum.squareRoot(b);
				}
				
				else {
				
					b = st.pop();
					a = st.pop();
					
					if(priority == Grammar.sum) {
						tempRes = bignum.add(a, b);
					}
					else if(priority == Grammar.difference) {
						tempRes = bignum.subtract(a, b);
					}
					else if(priority == Grammar.product) {
						tempRes = bignum.product(a, b);
					}
					else if(priority == Grammar.division) {
						tempRes = bignum.divide(a, b);
					}
					else if(priority == Grammar.mod) {
						tempRes = bignum.mod(a, b);
					}
					else if(priority == Grammar.exponentiation) {
						tempRes = bignum.power(a, b);
					}
				}
				
				st.push(tempRes);
			}
		}
		
			if(st.size() == 1) return st.pop();
			
			return null; // invalid postfix expression
	}
	
	public static void main(String args[]) {
		Parser p = new Parser();
		String expression = "(1!+5~)*3";
		
		p.convertInfix2Postfix(expression);
		
		System.out.println(p.evaluatePostfix());
	}

}