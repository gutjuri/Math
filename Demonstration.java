import java.util.function.Function;

import jd.math.calc.CalcUtils;
import jd.math.fractions.Fraction;
import jd.math.matrices.Matrix;

public class Demonstration {

	public static void main(String[] args) {

		// Fractions
		Fraction testFractionA = new Fraction(3, 6);
		System.out.println("testFractionA = " + testFractionA);
		Fraction testFractionB = testFractionA.multiply(Fraction.ONE_THIRD);
		System.out.println("testFractionB = " + testFractionB);
		Fraction testFractionC = testFractionB.divide(new Fraction(1, 100)).minus(2);
		System.out.println("testFractionC = " + testFractionC);

		System.out.println();

		// Matrices
		Matrix testMatrixA = new Matrix(new long[][] { { 1, 2 }, { 3, 4 } });
		System.out.println("testMatrixA:\n" + testMatrixA);
		System.out.println();

		Matrix testMatrixB = testMatrixA.multiply(3);
		System.out.println("testMatrixB:\n" + testMatrixB);
		System.out.println();

		Matrix testMatrixC = testMatrixB.multiply(testMatrixA);
		System.out.println("testMatrixC:\n" + testMatrixC);
		System.out.println();

		long det = testMatrixC.determinant();
		System.out.println("det(testMatrixC) = " + det);
		System.out.println();

		// Calculus
		Function<Double, Double> testFunction = x -> Math.exp(x);
		double area = CalcUtils.integrate(testFunction, 0, 1, 100);
		System.out.println("The integral of exp(x) from 0 to 1 is " + area);
		System.out.println("(Precise value is e-1: " + (Math.E - 1) + ")");
	}

}
