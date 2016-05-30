package engine;

import instructions.Instruction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import numlib.BigNumber;

/**
 * RunTimeEnvironment - Class provides the runtime environment for execution of the program
 * @author rameshsuthan
 *
 */
public class RunTimeEnvironment {
	public HashMap<String, BigNumber> memoryMap;
	public int currentInstrPtr;
	public HashMap<Integer,Integer> mapLineNoToInstr;
	public ArrayList<Instruction> instrList;
	
	public RunTimeEnvironment() {
		this.memoryMap=new HashMap<String, BigNumber>();
		this.mapLineNoToInstr=new HashMap<Integer, Integer>();
		this.instrList=new ArrayList<Instruction>();
		this.currentInstrPtr=0;		
	}
	
	public void updateMemoryMap(String lVal,BigNumber value){
		memoryMap.put(lVal, value);
		
	}
	
	/**
	 * Method to start the execution of the stored instruction
	 */
	public void startExecution(){
		System.out.println("output>");
		this.resetInstrPtr();
		Instruction instr=null;
		int progLen=instrList.size();
		while( currentInstrPtr < progLen && (instr=instrList.get(currentInstrPtr))!=null){
			//System.out.println(currentInstrPtr+" Executing: "+instr);
			instr.execute(this);
		}
		
		
		//System.out.println(memoryMap);
	
	}
	
	/**
	 * Method to Get the current instructon pointer address for the given Line number
	 * @param lineNo - Line number provided by user
	 * @return
	 */
	public int getInstructionAddress(int lineNo){
		return mapLineNoToInstr.get(lineNo);
	}
	
	/**
	 * Method to get values from the memory
	 * @param key
	 * @return
	 */
	public BigNumber getFromMemoryMap(String key){
		return memoryMap.get(key);
	}
	
	/**
	 * Method to add the instruction into the RuntTimeEnvironment
	 * @param instr
	 */
	public void addInstruction(Instruction instr){
		//adding the instruction
		instrList.add(instr);
		//adding the mapping between line no -> instruction No
		mapLineNoToInstr.put(instr.lineNo, currentInstrPtr);
		currentInstrPtr++;
	}
	
	/**
	 * Method to reset the instruction pointer to the intial location 
	 */
	public void resetInstrPtr(){
		currentInstrPtr=0;
	}
	
	/**
	 * Method to increment the instruction pointer to the  
	 */
	public void incrementInstrPtr(){
		currentInstrPtr++;
	}
	
	public void updateInstrPtr(int address){
		currentInstrPtr=address;
	}
	
	

}
