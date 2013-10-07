import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Stack;


public class main {
	private static String variableArray[] = {"there","is","some","things","that","time", "will","only","tell","me","psy","cho","master","sun", "good", "doctor", "running", "man"};
	private static String operatorArray[] = {"+","-","*"};
	private static Stack<String> bracketStack;
	private static Stack<String> memoryStack;
	private static int depthLimit = 15;
	private static int minLine = 1000;
	private static int lineNumber = 1;
	private static int constantRange = 100;
	private static boolean canContinue = false;
	private static Random randomGenerator;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		randomGenerator = new Random();
		bracketStack = new Stack<String>();
		memoryStack = new Stack<String>();
		canContinue = true; 
		String filename = "SourceCode";
		try {
			FileWriter fw = new FileWriter(filename + ".txt");
			while(canContinue){
				String stmt = getStmt();
				fw.write(stmt);
				if(lineNumber > minLine && bracketStack.isEmpty()){
					canContinue = false;
				}
			}
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static String getStmt() {
		int randomInt = randomGenerator.nextInt(15);
		switch(randomInt){
		case 0: case 1:
			return insertExpression();
		case 2:
			return insertIf();
		case 3:
			return insertWhile();
		default:
			return insertCloseBracket();
		}
	}
	
	private static String insertExpression(){
		StringBuffer sb = new StringBuffer();
		int randomInt = randomGenerator.nextInt(variableArray.length);
		sb.append(variableArray[randomInt]);
		sb.append(" = ");
		int numCount = randomGenerator.nextInt(5) + 1;
		while (numCount != 0){
			boolean typeBool = randomGenerator.nextBoolean();
			int typeObj;
			if(typeBool){
				typeObj = randomGenerator.nextInt(variableArray.length);
				sb.append(variableArray[typeObj]);
			} else {
				int constant = randomGenerator.nextInt(constantRange) + 1;
				sb.append(String.valueOf(constant));
			}

			numCount --;
			if(numCount != 0){
				typeObj = randomGenerator.nextInt(operatorArray.length);
				sb.append(" ");
				sb.append(operatorArray[typeObj]);
				sb.append(" ");
			}
		}
		sb.append(";\t//"); 
		sb.append(Integer.toString(lineNumber));
		sb.append("\n");
		lineNumber ++;
		if(!memoryStack.isEmpty() && !memoryStack.peek().equals("expression")){
			memoryStack.push("expression");
		}
		return sb.toString();
	}
	
	private static String insertIf(){
		if (bracketStack.size() > depthLimit){
			return "";
		}
		StringBuffer sb = new StringBuffer();
		bracketStack.push("if");
		memoryStack.push("if");
		int randomInt = randomGenerator.nextInt(variableArray.length);
		sb.append("if ");
		sb.append(variableArray[randomInt]);
		sb.append(" then { \t//");
		sb.append(Integer.toString(lineNumber));
		sb.append("\n");
		lineNumber ++;
		return sb.toString();
	}

	private static String insertWhile(){
		if (bracketStack.size() > depthLimit){
			return "";
		}
		StringBuffer sb = new StringBuffer();
		bracketStack.push("while");
		memoryStack.push("while");
		int randomInt = randomGenerator.nextInt(variableArray.length);
		sb.append("while ");
		sb.append(variableArray[randomInt]);
		sb.append(" { \t//");
		sb.append(Integer.toString(lineNumber));
		sb.append("\n");
		lineNumber ++;
		return sb.toString();
	}
	
	private static String insertCloseBracket(){
		if(bracketStack.isEmpty()){
			return "";
		} 
		if(!memoryStack.peek().equals("expression")){
			return "";
		}
		memoryStack.pop();
		memoryStack.pop();
		
		if(!memoryStack.isEmpty() && !memoryStack.peek().equals("expression")){
			memoryStack.push("expression");
		}
		
		String bracketType = bracketStack.pop();
		if(bracketType.equals("if")){
			bracketStack.push("else");
			memoryStack.push("else");
			return "} else {\n";
		} else {
			return "}\n";
		}
	}
}
