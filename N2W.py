class N2W:
	
	digits = ["one","two","three","four","five","six","seven","eight","nine"];
	teens = ["ten","eleven","twelve","thirteen","fourteen","fifteen","sixteen","seventeen","eighteen","nineteen"];
	tens = ["ten","twenty","thirty","forty","fifty","sixty","seventy","eighty","ninety"];
	magnitudes = ["hundred","thousand","million","billion","trillion","quadrillion","quintillion","quintillion"];
	

	@staticmethod
	def convert(number):
		if number == 0:
			return "zero"

		result = " "
		if number < 0:
			result = "negative"
			number *= -1

		stack = []
		while number:
			digit = number % 10
			stack.append(digit)
			number /= 10

		pop = 0
		endsWithMagnitude = False
		while stack:
			pop = stack.pop() - 1
			if len(stack) == 0 and pop != -1:
				result += " " + N2W.digits[pop]
			elif len(stack) == 1 and pop != -1:
				if pop == 0:
					pop = stack.pop()
					result += " " + N2W.teens[pop]
				else:
					result += " " + N2W.tens[pop]
			elif len(stack) % 3 == 0:
				if pop != -1:
					result += " " + N2W.digits[pop]
				if result.endswith("thousand") or \
					result.endswith("million") or \
					result.endswith("billion") or \
					result.endswith("trillion") or \
					result.endswith("quadrillion") or \
					result.endswith("quintillion"):

					endsWithMagnitude = True
				if len(stack) > 0 and endsWithMagnitude == False:
					result += " " + N2W.magnitudes[len(stack) / 3]
			elif len(stack) % 3 == 1 and pop != -1:
				result += " " + N2W.tens[pop]
			elif len(stack) % 3 == 2 and pop != -1:
				result += " " + N2W.digits[pop] + " " + N2W.magnitudes[0]



		return result.strip()