package com.tw.intergalactic;

import java.util.List;
import java.util.ArrayList;
import java.util.regex.Matcher;

public class RomanNumberSystem{
	private enum RomanNumbers{
		I (1),
		V (5),
		X(10),
		L(50),
		C (100),
		D (500),
		M(1000);

		private final int decimalValue;

		private RomanNumbers(int value) {
			this.decimalValue = value;
		}

		public int getDecimalValue() {
			return decimalValue;
		}
	}
	
	private LineClassifier lineClassifier = LineClassifier.getInstance();
	
	public int toNumber(String romanNum){

		List<Integer> numbers = new ArrayList<Integer>();
		int returnValue=0;
		if(checkRomanRules(romanNum)){
			numbers = getSignCorrectedRomanToDecimalExpansion(romanNum);
			if(checkRomanSubstractionRules(numbers)){
				for(int i : numbers)
				{
					returnValue += i;
				}
				return returnValue;	
			}
		}

		return returnValue;


	}

	/**
	 * As defined the repetitions are checked for
	 * @param romanNum
	 * @return
	 */
	private boolean checkRomanRules(String romanNum){
		String regex ="";
		String [] repetitionRegexTests = {"(IIII+)","(XXXX+)", "(CCCC+)", "(MMMM+)", "(DD+)", "(LL+)", "(VV+)"}; 

		for(int i =0 ; i < repetitionRegexTests.length; i ++)
		{
			regex = repetitionRegexTests[i];
			Matcher m = lineClassifier.getMatcher(romanNum, regex);

			if(m.matches())
			{
				System.out.println("The number entered violates Roman Number repitition constraints.");
				return false;
			}
		}
		return true;
	}

	/**
	 * The subtraction rules as defined by problem.
	 * @param numbers
	 * @return false if any rule is broken
	 */
	private boolean checkRomanSubstractionRules(List<Integer> numbers){
		int currentValue;
		int nextValue;
		for(int i=0;i<numbers.size()-1;i++){
			currentValue = numbers.get(i);
			nextValue = numbers.get(i+1);

			if( currentValue == - RomanNumbers.V.getDecimalValue() 
					|| currentValue == -RomanNumbers.L.getDecimalValue()
					|| currentValue == - RomanNumbers.D.getDecimalValue())
			{
				return false;
			}

			switch(Math.abs(currentValue)){
			case 1://Case I
				if(nextValue>RomanNumbers.I.getDecimalValue()
						&& nextValue!=RomanNumbers.V.getDecimalValue()
						&& nextValue!=RomanNumbers.X.getDecimalValue()){
					return false;
				}
				break;
			case 10:
				if(nextValue>RomanNumbers.X.getDecimalValue()
						&& nextValue!=RomanNumbers.L.getDecimalValue()
						&& nextValue!=RomanNumbers.C.getDecimalValue()){
					return false;
				}
				break;
			case 100:
				if(nextValue>RomanNumbers.C.getDecimalValue()
						&& nextValue!=RomanNumbers.D.getDecimalValue()
						&& nextValue!=RomanNumbers.M.getDecimalValue()){
					return false;
				}
				break;
			}
		}
		return true;
	}

	/**
	 * 
	 * @param romanNum
	 * @return the array of individual Roman numbers converted to their
	 * decimal equivalent with the appropriate sign based on the rules
	 */
	private List<Integer> getSignCorrectedRomanToDecimalExpansion(String romanNum){
		char[] romanNumerals = romanNum.toCharArray();
		List<Integer> numbers = new ArrayList<Integer>();
		for(char c:romanNumerals){
			switch(c){
			case 'I':
				numbers.add(RomanNumbers.I.getDecimalValue());
				break;
			case 'V':
				numbers.add(RomanNumbers.V.getDecimalValue());
				break;
			case 'X':
				numbers.add(RomanNumbers.X.getDecimalValue());
				break;
			case 'L':
				numbers.add(RomanNumbers.L.getDecimalValue());
				break;
			case 'C':
				numbers.add(RomanNumbers.C.getDecimalValue());
				break;
			case 'D':
				numbers.add(RomanNumbers.D.getDecimalValue());
				break;
			case 'M':
				numbers.add(RomanNumbers.M.getDecimalValue());
				break;
			}
		}
		for(int i=0;i<numbers.size()-1;i++){
			int currentValue = numbers.get(i);
			int nextValue = numbers.get(i+1);
			if(currentValue<nextValue){
				numbers.set(i, -currentValue);
			}
		}
		return numbers;
	}
}
