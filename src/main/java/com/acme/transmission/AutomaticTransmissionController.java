package com.acme.transmission;

/**
 * Controlling module of the gear box
 */
public class AutomaticTransmissionController {


	private AutomaticGearBox gearBox;
	private EngineMonitoringSystem engineMonitoringSystem;
	private DrivingMode drivingMode;

	public AutomaticTransmissionController(DrivingMode initialDrivingMode, AutomaticGearBox gearBox,
			EngineMonitoringSystem engineMonitoringSystem) {
		this.drivingMode = initialDrivingMode;
		this.gearBox = gearBox;
		this.engineMonitoringSystem = engineMonitoringSystem;
	}

	public void start() {
		// TODO check engine and start gearbox
	}

	public void stop() {
		// TODO park gearbox
	}

	public void changeMode(DrivingMode drivingMode) {
		this.drivingMode = drivingMode;
		handleChange(0, 0);
	}

	public void handleGas(double throttle) {
		handleChange(throttle, 0);
	}

	public void handleBreaks(double breakingForce) {
		handleChange(0, breakingForce);
	}

	/**
	 * maintains gear that is proper for current: driving mode, engine's RPM and
	 * given gas throttle and breaking force
	 *
	 * @param throttle
	 *            gas throttle <0,1>
	 * @param breakingForce
	 *            breaking force <0,1>
	 */
	private void handleChange(double throttle, double breakingForce) {
		// TODO check if we are actually started (Driving Mode)
		int gear = calculateGear(throttle, breakingForce);
		if (gear != gearBox.getGear()) {
			this.gearBox.changeGear(gear);
		}
	}

	/**
	 * determines gear that is proper for current: driving mode, engine's RPM
	 * and given gas throttle and breaking force
	 *
	 * @param throttle
	 *            gas throttle <0,1>
	 * @param breakingForce
	 *            breaking force <0,1>
	 * @return optimal gear
	 */
	private int calculateGear(double throttle, double breakingForce) {
		int currentRPM = engineMonitoringSystem.getCurrentRPM();
		// TODO maybe use Strategy Design Pattern/Template Method
		// Pattern to handle different driving modes
		if (kickdownCondition(throttle, currentRPM)) {
			return gearBox.getGear() - 2;
		}
		if (isPedalPressed(throttle) && nextGearCondition(currentRPM)) {
			return gearBox.getGear() + 1;
		}

		if (isPedalPressed(breakingForce) && previousGearCondition(currentRPM)) {
			return gearBox.getGear() - 1;
		}
		return gearBox.getGear();
	}

	private boolean previousGearCondition(int currentRPM) {
		return currentRPM < this.drivingMode.lowerLimit();
	}

	private boolean nextGearCondition(int currentRPM) {
		return currentRPM > this.drivingMode.upperLimit();
	}

	private boolean kickdownCondition(double throttle, int currentRPM) {
		return throttle > 0.7 && currentRPM < 2000;
	}

	private boolean isPedalPressed(double throttle) {
		return throttle > 0;
	}

}