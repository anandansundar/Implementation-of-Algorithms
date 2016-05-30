package instructions;

import numlib.BigNumber;
import engine.RunTimeEnvironment;


/**
 * AssignmentInstruction - Class to store and perform the Assignment operation
 * @author rameshsuthan
 *
 */
public class AssignmentInstruction extends Instruction{
	public String lVal;
	public String rVal;
	
	public AssignmentInstruction(int lineNo,String lVal,String rVal){
		super(lineNo);
		this.lVal=lVal;
		this.rVal=rVal;
	}

	/* (non-Javadoc)
	 * @see instructions.Instruction#execute(engine.RunTimeEnvironment)
	 */
	@Override
	public int execute(RunTimeEnvironment rtEnv) {
		//put into map
		rtEnv.updateMemoryMap(lVal, new BigNumber(rVal));
		rtEnv.incrementInstrPtr();
		return 0;
	}
	
	@Override
	public String toString() {
		String str="["+lineNo+"] "+lVal + "<-" + rVal; 
		return str;
	}
	

}
