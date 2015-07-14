package main;


public class Integral_Paralelo implements Runnable {

	private integral obj_integral;
	private double area_total;

	public Integral_Paralelo(double x1, double x2, int parts) {
		this.obj_integral = new integral(x1, x2, parts);
	}

	public void run() {
		this.area_total = obj_integral.get_valor();
	}

	public double getArea_total() {
		return area_total;
	}

}
