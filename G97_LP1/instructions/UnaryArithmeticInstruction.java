package instructions;

import numlib.BigNumber;
import engine.RunTimeEnvironment;

/**
 * UnaryArithmeticInstruction - Class to store the and perform Unary Arithmetic Instruction
 * @author rameshsuthan
 *
 */
public class UnaryArithmeticInstruction extends Instruction{
	public String lVal;
	public String operator;
	public String operand;
	
	public UnaryArithmeticInstruction(int lineNo,String lVal,String operand,String operator){
		super(lineNo);
		this.lVal=lVal;
		this.operand=operand;
		this.operator=operator;
	}

	@Override
	public int execute(RunTimeEnvironment rtEnv) {
	
		BigNumber operand1=rtEnv.getFromMemoryMap(operand);
		BigNumber result=null;
				
		//perform the arithmetic expression
		switch(operator)
		{
		case "!":
			result=BigNumber.factorial(operand1);
			break;
		case "~":
			result=BigNumber.squareRoot(operand1);
			break;
		default:
			//unknown Operation - rise exception
			throw new RuntimeException("Unknown operation" + this);	
		}
		
		//update the result into the memory
		//System.out.println("updating the result"+result);
		rtEnv.updateMemoryMap(lVal, result);
		rtEnv.incrementInstrPtr();

		return 0;
	}
	
	@Override
	public String toString() {
		String str="["+lineNo+"] "+lVal + "<-" + operand + operator; 
		return str;
	}
	

}
