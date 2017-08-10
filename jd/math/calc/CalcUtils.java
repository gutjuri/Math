package jd.math.calc;

import java.util.function.Function;

public class CalcUtils {

	private CalcUtils() {
	}

	/**
	 * Computes an integral.
	 * 
	 * @param f
	 *            Function (e.g. x -> x*x)
	 * @param a
	 *            Upper bound
	 * @param b
	 *            Lower bound
	 * @param n
	 *            Precision. Depending on your bounds and your wishes. I
	 *            recommend about (b-a)*100 for good precision.
	 * @return Integral from a to b over f.
	 */
	public static double integrate(Function<Double, Double> f, double a, double b, int n) {
		double[] partitions = new double[n + 1];
		double h = (b - a) / n;
		for (int i = 0; i < partitions.length; i++) {
			partitions[i] = f.apply(i * h + a);

		}
		double integral = (partitions[0] + partitions[n]) / 2;

		for (int i = 1; i < n; i++) {
			integral += partitions[i];
		}
		return integral * h;
	}

	/**
	 * Derives a function a certain point.
	 * 
	 * @param f
	 *            Function (e.g. x -> x*x)
	 * @param a
	 *            The value of x where f shall be differentiated.
	 * @return The slope of f at point a
	 */
	public static double derive(Function<Double, Double> f, double a) {
		double prec = 0.001;
		return (f.apply(a + prec) - f.apply(a - prec)) / (2 * prec);

	}
}
