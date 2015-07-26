import java.math.BigDecimal;
import java.util.LinkedList;

public class N2W {

	private final static String[] digits = {"one","two","three","four","five","six","seven","eight","nine"};
	private final static String[] teens = {"ten","eleven","twelve","thirteen","fourteen","fifteen","sixteen","seventeen","eighteen","nineteen"};
	private final static String[] tens = {"ten","twenty","thirty","forty","fifty","sixty","seventy","eighty","ninety"};
	private final static String[] magnitudes = {"hundred","thousand","million","billion","trillion","quadrillion","quintillion","quintillion"};
	private final static String[] fractions = {"tenths","hundredths","thousandths","ten thousandths","hundred thousandths",
		"millionths","ten millionths","hundred millionths",
		"billionths","ten billionths","hundred billionths",
		"trillionths","ten trillionths","hundred trillionths"};


	public static String convert(long num) {
		if (num == 0) {
			return "zero";
		}

		String result = "";
		LinkedList<Long> stack = new LinkedList<Long>();

		if (num < 0) {
			result = "negative";
			num = -num;
		}

		stack = makeStack(num);
		result = result + " " + toWords(stack);
		return result.trim();
	}

	public static String convert(Double num) {
		if (num == 0) {
			return "zero";
		}

		long left = 0;
		long right = 0;
		int decimalPlaces = 0;
		String result = "";
		String numString = "";
		LinkedList<Long> stack = new LinkedList<Long>();

		left = num.longValue();

		if (left < 0) {
			result = "negative";
			left = -left;
		}

		if (left != 0) {
			stack = makeStack(left);
			result = result + " " + toWords(stack);
		}

		if (num % 1 != 0) {
			numString = new BigDecimal(num.toString()).stripTrailingZeros().toPlainString();
			numString = numString.substring(numString.indexOf('.') + 1);
			right = Long.parseLong(numString);
			decimalPlaces = numString.length() - 1;

			stack = makeStack(right);
			if (left != 0) {
				result = result + " and";
			}
			result = result + " " + toWords(stack) + " " + fractions[decimalPlaces];
		}
		return result.trim();
	}

	public static String convert(String numStringParam) {
		String result = "";
		try {

			BigDecimal bd1 = new BigDecimal(numStringParam);
			Double num = Double.parseDouble(numStringParam);
			BigDecimal bd2 = new BigDecimal(num.toString());

			if (bd1.compareTo(bd2) != 0) {
				return "out of range";
			}

			if (num == 0) {
				return "zero";
			}

			long left = 0;
			long right = 0;
			int decimalPlaces = 0;
			String numString = "";
			LinkedList<Long> stack = new LinkedList<Long>();

			left = num.longValue();

			if (left < 0) {
				result = "negative";
				left = -left;
			}

			if (left != 0) {
				stack = makeStack(left);
				result = result + " " + toWords(stack);
			}

			if (num % 1 != 0) {
				numString = new BigDecimal(num.toString()).stripTrailingZeros().toPlainString();
				numString = numString.substring(numString.indexOf('.') + 1);
				right = Long.parseLong(numString);
				decimalPlaces = numString.length() - 1;

				stack = makeStack(right);
				if (left != 0) {
					result = result + " and";
				}
				result = result + " " + toWords(stack) + " " + fractions[decimalPlaces];
			}

		} catch (NumberFormatException e) {
			return "Error: Input is not numeric.";
		}

		return result.trim();
	}

	private static LinkedList<Long> makeStack(long num) {
		LinkedList<Long> stack = new LinkedList<Long>();
		while (num > 0) {
			stack.push(num % 10);
			num = num / 10;
		}
		return stack;
	}

	private static String toWords(LinkedList<Long> stack) {
		int pop;
		boolean endsWithMagnitude = false;
		String result = "";

		while (!stack.isEmpty()) {
			pop = stack.pop().intValue() - 1;

			if (stack.size() == 0 && pop != -1) {
				result = result + " " + digits[pop];
			}
			else if (stack.size() == 1 && pop != -1) {
				if (pop == 0) {
					pop = stack.pop().intValue();
					result = result + " " + teens[pop];
				}
				else {
					result = result + " " + tens[pop];
				}
			}		
			else if (stack.size() % 3 == 0) {
				if (pop != -1) {
					result = result + " " + digits[pop];
				}
				if (result.endsWith("thousand")
						|| result.endsWith("million")
						|| result.endsWith("billion")
						|| result.endsWith("trillion")
						|| result.endsWith("quadrillion")
						|| result.endsWith("ullion")) {
					endsWithMagnitude = true;
				}
				if (stack.size() > 0 && !endsWithMagnitude) {
					result = result + " " + magnitudes[stack.size() / 3];
				}
			}
			else if (stack.size() % 3 == 1 && pop != -1) {
				result = result + " " + tens[pop];
			}
			else if (stack.size() % 3 == 2 && pop != -1) {
				result = result + " " + digits[pop] + " " + magnitudes[0];
			}
		}
		return result.trim();
	}
}