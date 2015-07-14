package trapezio;

public class trapezio {

	// eixo do x no grafico
	double x1, x2;
	// eixo y calculado pelo arctang
	// sera as bases do trapezio
	double y1, y2;

	public trapezio(double x1, double x2) {
		this.x1 = x1;
		this.x2 = x2;
	}

	public double get_area() {
		this.y1 = Math.atan( Math.pow(Math.E , x1) );
		this.y2 = Math.atan( Math.pow(Math.E , x2) );
		return (this.y1 + this.y2) * (this.x2 - this.x1) / 2;
	}

}
