package com.acme.calc;

public class Calculator {

	public Double add(Double augend, Double addend) {
		return augend + addend;
	}

	public Double subtract(Double minuend, Double subtrahend) {
		return minuend 
				- subtrahend;
	}
	
	public Double multiply(Double multiplicand, Double multipiler) {
		return multiplicand * multipiler;
	}

	public Double divide(Double divident, Double divisor) {
		if (divisor == 0) {
			throw new DivisorCannotBeZeroException("Do you want to blow up the entire world?!");
		}
		return divident / divisor;
	}
}
