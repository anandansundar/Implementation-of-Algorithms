package instructions;

import numlib.BigNumber;
import engine.RunTimeEnvironment;

/**
 * BranchingInstruction - Class to store the branching instruction and perfom branching operations  
 * @author rameshsuthan
 *
 */
public class BranchingInstruction extends Instruction{
	public String lVal;
	public Integer nonZeroAddr;
	public Integer zeroAddr;
	private final BigNumber zero=new BigNumber(0L);
	
	public BranchingInstruction(int lineNo,String lVal,int nonZeroAddr,int zeroAddr){
		super(lineNo);
		this.lVal=lVal;
		this.zeroAddr=zeroAddr;
		this.nonZeroAddr=nonZeroAddr;
	}
	
	public BranchingInstruction(int lineNo,String lVal,int nonZeroAddr){
		super(lineNo);
		this.lVal=lVal;
		this.nonZeroAddr=nonZeroAddr;
		this.zeroAddr=null;
	}
	

	/* (non-Javadoc)
	 * @see instructions.Instruction#execute(engine.RunTimeEnvironment)
	 */
	@Override
	public int execute(RunTimeEnvironment rtEnv) {
		BigNumber operand1=rtEnv.getFromMemoryMap(lVal);
		Integer goToAddress=null;
		
		//System.out.println("Branch check"+lVal+"="+operand1+BigNumber.isEqual(operand1, zero));
		// if operand is non zero 
		if(!BigNumber.isEqual(operand1, zero)){
			goToAddress=rtEnv.getInstructionAddress(nonZeroAddr);
			
			if(goToAddress==null){
				throw new RuntimeException("Invalid Branch instruction:"+this);	

			}
			rtEnv.updateInstrPtr(goToAddress);
		}
		
		else if(zeroAddr!=null){//if operand is Zero and has goto address.
			goToAddress=rtEnv.getInstructionAddress(zeroAddr);
			
			if(goToAddress==null){
				throw new RuntimeException("Invalid Branch instruction:"+this);
			}
			rtEnv.updateInstrPtr(goToAddress);
		}else{//if operand is Zero and has no goto address.
			rtEnv.incrementInstrPtr();
		}
		//System.out.println("Executing the instruction: "+this+" Jumping to address"+rtEnv.currentInstrPtr);
		return 0;
	}
	
	@Override
	public String toString() {
 
		String str="";
		if(zeroAddr!=null){
		str="["+lineNo+"] "+"if "+lVal+"!=0 goto lineno:" +nonZeroAddr+", else goto lineno:"+zeroAddr;
		}
		else{
		str="["+lineNo+"] "+"if "+lVal+"!=0 goto lineno:" +nonZeroAddr+", else goto next instructions";
		}

		
		return str;
	}
	

}
