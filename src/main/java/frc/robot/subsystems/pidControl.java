package frc.robot.subsystems;

public class pidControl {
  public double proportionalOutput, integralOutput, integralTop, integralBottom, desired, error, kP, kI;

  public void caculateOutputs() {
    integralTop = desired * 1.34;
    integralBottom = desired - (desired * 1.34);
    getPidOut();
  }

  public void getPidOut() {
    proportionalOutput = error * kP;
    integralOutput = error * kI;
  }

  public double Output() {
    if (proportionalOutput > integralBottom && proportionalOutput < integralTop) {
      return proportionalOutput + integralOutput;
    } else {
      return proportionalOutput;
    }
  }
}