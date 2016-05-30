package instructions;

import numlib.BigNumber;
import engine.RunTimeEnvironment;

/**
 * BinaryArithmeticInstruction - Class to store the and perform Binary Arithmetic Instruction
 * @author rameshsuthan
 *
 */
public class BinaryArithmeticInstruction extends Instruction{
	public String lVal;
	public String operator;
	public String lOperand;
	public String rOperand;
	
	public BinaryArithmeticInstruction(int lineNo,String lVal,String lOperand,String operator,String rOperand){
		super(lineNo);
		this.lVal=lVal;
		this.lOperand=lOperand;
		this.operator=operator;
		this.rOperand=rOperand;
	}

	/* (non-Javadoc)
	 * @see instructions.Instruction#execute(engine.RunTimeEnvironment)
	 */
	@Override
	public int execute(RunTimeEnvironment rtEnv) {
		//put into map
		BigNumber operand1=rtEnv.getFromMemoryMap(lOperand);
		BigNumber operand2=rtEnv.getFromMemoryMap(rOperand);
		BigNumber result=null;
		//System.out.println(operand1+" "+operand2);
				
		//perform the arithmetic expression
		switch(operator)
		{
		case "+":
			result=BigNumber.add(operand1, operand2);
			break;
		case "-":
			result=BigNumber.subtract(operand1, operand2);
			break;
		case "/":
			result=BigNumber.divide(operand1, operand2);
			break;
		case "*":
			result=BigNumber.product(operand1, operand2);
			break;
		case "%":
			result=BigNumber.mod(operand1, operand2);
			break;
		case "^":
			result=BigNumber.power(operand1, operand2);
			break;
		default:
			//unknown Operation - rise exception
			//System.err.println();
			throw new RuntimeException("Unknown operation" + this);	
		}
		
		//update the result into the memory
		rtEnv.updateMemoryMap(lVal, result);
		rtEnv.incrementInstrPtr();
		return 0;
	}
	
	@Override
	public String toString() {
		String str="["+lineNo+"] "+lVal + "<-" + lOperand + operator + rOperand; 
		return str;
	}
	

}
