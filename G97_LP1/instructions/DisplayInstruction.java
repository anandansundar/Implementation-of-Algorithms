package instructions;

import numlib.BigNumber;
import engine.RunTimeEnvironment;

/**
 * DisplayInstruction - Class to perform the display 
 * @author rameshsuthan
 *
 */

public class DisplayInstruction extends Instruction{
	public String lVal;
	public String operator;
	public DisplayInstruction(int lineNo,String lVal,String operator){
		super(lineNo);
		this.lVal=lVal;
		this.operator=operator;
	}
	
	public DisplayInstruction(int lineNo,String lVal){
		super(lineNo);
		this.lVal=lVal;
		this.operator=null;
	}
	

	@Override
	public int execute(RunTimeEnvironment rtEnv) {
		BigNumber operand1=rtEnv.getFromMemoryMap(lVal);
		if(operator!=null&&operator.equals(")")){
			operand1.printList();
			System.out.println();
			
		}else{
			System.out.println(operand1);
		}
		//System.out.println();
		rtEnv.incrementInstrPtr();
		return 0;
	}
	
	@Override
	public String toString() {
		String str="";
		
		if(operator==null){
		str="["+lineNo+"] print("+lVal+")";
		}else{
		str="["+lineNo+"] printList("+lVal+")";
		}
		return str;
	}
	

}
