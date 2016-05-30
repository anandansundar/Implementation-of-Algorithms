package instructions;

public class InstructionProcessor {
	
	public static void binaryInstruction(String lVal,String lOperand,String operator,String rOperand){
		operator=operator.trim();
		
		switch(operator)
		{
		case "+":
			//perform addition operaion
			break;
		case "-":
			//perform addition operaion
			break;
		case "/":
			//perform addition operaion
			break;
		case "*":
			//perform addition operaion
			break;
		case "%":
			//perform addition operaion
			break;
		default:
			//unknown Operation - rise exception
			System.err.println("Unknown operation");
			break;
			
		}
		
	}

}
