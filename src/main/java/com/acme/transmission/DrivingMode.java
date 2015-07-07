package com.acme.transmission;

public enum DrivingMode {
	ECO(1000, 1500), COMFORT(1200, 1700), SPORT(2500, 5000), SPORT_PLUS(3500, 7000);
	private int upperRpmLimit;
	private int lowerRpmLimit;

	private DrivingMode(int lowerRpmLimit, int upperRpmLimit) {
		this.upperRpmLimit = upperRpmLimit;
		this.lowerRpmLimit = lowerRpmLimit;
	}

	public int lowerLimit() {
		return lowerRpmLimit;
	}

	public int upperLimit() {
		return upperRpmLimit;
	}
}
