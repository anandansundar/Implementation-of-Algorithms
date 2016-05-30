package instructions;

import engine.RunTimeEnvironment;

/**
 * Instruction - Abstract class for instructions
 * @author rameshsuthan
 *
 */
public abstract class Instruction {
	public int lineNo;
	
	public Instruction(int lineNo){
		this.lineNo=lineNo;
	}
	
	/**
	 * Method to execute the instruction
	 * @param runTimeEnvironment
	 * @return - 0 on success
	 * @throws RuntimeException
	 */
	public abstract int execute(RunTimeEnvironment runTimeEnvironment) throws RuntimeException;

}
