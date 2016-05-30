package engine;

import instructions.AssignmentInstruction;
import instructions.BinaryArithmeticInstruction;
import instructions.BranchingInstruction;
import instructions.DisplayInstruction;
import instructions.Instruction;
import instructions.ParseException;
import instructions.UnaryArithmeticInstruction;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import numlib.BigNumber;

/**
 * CommandLineParser - Class to parse the commands
 * @author rameshsuthan
 *
 */
public class CommandLineParser {

	/**
	 * 
	 * Method to parse the command 
	 * 
	 * @param lineNo - line number provided by the user 
	 * @param cmd - command to parse
	 * @param rtEnviron - RunTimeEnvironment
	 * @throws ParseException
	 */
	public static void commandParser(int lineNo, String cmd,
			RunTimeEnvironment rtEnviron) throws ParseException {
		cmd = cmd.trim();
		if (cmd.isEmpty()) {
			return;
		}

		// assignment or arithmetic operations
		if (cmd.contains("=")) {
			String[] statementPart = cmd.split("=");

			if (statementPart.length != 2) {
				throw new ParseException(cmd);
			}

			String lpart = statementPart[0];
			String rpart = statementPart[1];

			// for numbers
			String patternToMatch = "[^0-9]";
			Pattern p = Pattern.compile(patternToMatch);
			Matcher m = p.matcher(rpart);
			boolean specialOperatorFound = m.find();

			// Simple Assignment Instruction
			if (!specialOperatorFound) {
				AssignmentInstruction instr = new AssignmentInstruction(lineNo,
						lpart, rpart);
				rtEnviron.addInstruction(instr);
			}
			// Arithmetic Operations
			else {
				// Binary Operations
				patternToMatch = "[*/+%^-]+";
				p = Pattern.compile(patternToMatch);
				m = p.matcher(rpart);
				boolean binaryOperatorFound = m.find();

				if (binaryOperatorFound) {

					String lOperand = rpart.substring(0, m.start());
					String operator = rpart.substring(m.start(), m.end());
					String rOperand = rpart.substring(m.end());

					if (lOperand == null || rOperand == null
							|| lOperand.isEmpty() || rOperand.isEmpty()) {
						throw new ParseException(cmd);
					}

					BinaryArithmeticInstruction binaryArithmeticInstruction = new BinaryArithmeticInstruction(
							lineNo, lpart, lOperand, operator, rOperand);
					rtEnviron.addInstruction(binaryArithmeticInstruction);
					return;

				}

				// Unary Operations
				patternToMatch = "[!~?]+";
				p = Pattern.compile(patternToMatch);
				m = p.matcher(rpart);
				boolean unaryOperatorFound = m.find();

				if (unaryOperatorFound) {
					// System.out.println("unary operation instruction found");
					String operand = rpart.substring(0, m.start());
					String operator = rpart.substring(m.start(), m.end());

					if (operand == null || operand.isEmpty()) {
						throw new ParseException(cmd);
					}
					UnaryArithmeticInstruction unaryArithmeticInstruction = new UnaryArithmeticInstruction(
							lineNo, lpart, operand, operator);
					rtEnviron.addInstruction(unaryArithmeticInstruction);
					return;

				}else{
					throw new ParseException(cmd+" not a valid instruction");
				}

			}

		}
		// branching instruction
		else if (cmd.contains("?")) {
			String[] statementPart = cmd.split("\\?");

			if (statementPart.length != 2) {
				throw new ParseException(cmd);
			}

			String lpart = statementPart[0].trim();
			String rpart = statementPart[1].trim();

			if (lpart == null || rpart == null || lpart.isEmpty()
					|| rpart.isEmpty()) {
				throw new ParseException(cmd);
			}

			int nonZeroAddr;
			int zeroAddr;
			BranchingInstruction branchingInstruction;

			// contains address for zero condition
			if (rpart.contains(":")) {
				String addr[] = rpart.split(":");
				if (addr.length != 2) {
					throw new ParseException(cmd);
				}
				try {
					nonZeroAddr = Integer.parseInt(addr[0].trim());
					zeroAddr = Integer.parseInt(addr[1].trim());
				} catch (NumberFormatException e) {
					throw new ParseException(cmd + " Non-Integer Value found");
				}
				branchingInstruction = new BranchingInstruction(lineNo, lpart,
						nonZeroAddr, zeroAddr);

			} else {
				nonZeroAddr = Integer.parseInt(rpart);
				branchingInstruction = new BranchingInstruction(lineNo, lpart,
						nonZeroAddr);
			}
			rtEnviron.addInstruction(branchingInstruction);
			return;
		} else {
			DisplayInstruction displayInstruction;
			if (cmd.contains(")")) {
				String[] statementPart = cmd.split("\\)");
				String lpart = statementPart[0];
				displayInstruction = new DisplayInstruction(lineNo, lpart, ")");
			} else {
				displayInstruction = new DisplayInstruction(lineNo, cmd);
			}

			rtEnviron.addInstruction(displayInstruction);
		}

	}

	/**
	 * 
	 * Method to Read commands form the command Line
	 * @param rtEnv - RunTimeEnvironment
	 * @throws ParseException
	 */
	public static void readCommands(RunTimeEnvironment rtEnv) throws ParseException {
		int linenum;
		String cmd;
		Scanner in = new Scanner(System.in);
		
		System.out.println("Input>");
		while (in.hasNext()) {
			linenum = in.nextInt();
			if (linenum < 0)
				break;
			cmd = in.next();
			// System.out.println("|" + linenum + "|" + cmd + "|");
			commandParser(linenum, cmd, rtEnv);
		}
		System.out.println();
		System.out.println(rtEnv.instrList);
	}

	public static void main(String[] args) throws ParseException {
		RunTimeEnvironment runTimeEnvironment = new RunTimeEnvironment();
		readCommands(runTimeEnvironment);
		runTimeEnvironment.startExecution();
		System.out.println();
	}
}
