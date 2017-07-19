package jd.math.matrices;

import java.util.Arrays;

/* 
							      _            _   _____  _                       
							     | |          (_) |  __ \(_)                      
							     | |_   _ _ __ _  | |  | |_ ___ _ __   __ _ _ __  
							 _   | | | | | '__| | | |  | | / __| '_ \ / _` | '_ \ 
							| |__| | |_| | |  | | | |__| | \__ \ |_) | (_| | | | |
							 \____/ \__,_|_|  |_| |_____/|_|___/ .__/ \__,_|_| |_|
							                                   | |                
							                                   |_|          							*/
/**
 * 
 * A matrix of double values that supports various artihmethic operations.
 * 
 * @author Juri Dispan
 *
 */

public class DoubleMatrix {

	private int x; // rows
	private int y; // columns
	private double[][] vals;

	/**
	 * The following format is expected: double[rows][columns].
	 * 
	 * @param values
	 *            The values of the new matrix.
	 */
	public DoubleMatrix(double[][] values) {
		this.vals = values.clone();
		this.x = vals.length;
		this.y = x == 0 ? 0 : vals[0].length;

	}

	public DoubleMatrix(int rows, int cols) {
		this.x = rows;
		this.y = cols;
		this.vals = new double[this.x][this.y];
	}

	public void setValue(int row, int col, double val) {
		this.vals[row][col] = val;
	}

	public double getValue(int row, int col) {
		return this.vals[row][col];
	}

	public DoubleMatrix negate() {
		DoubleMatrix r = new DoubleMatrix(this.x, this.y);
		for (int i = 0; i < this.x; i++) {
			for (int j = 0; j < this.y; j++) {
				r.setValue(i, j, -this.vals[i][j]);
			}
		}
		return r;
	}

	public DoubleMatrix add(DoubleMatrix b) {
		if (this.x != b.x || this.y != b.y)
			throw new IllegalArgumentException("Sizes don't match");
		DoubleMatrix r = new DoubleMatrix(this.x, this.y);
		for (int i = 0; i < this.x; i++) {
			for (int j = 0; j < this.y; j++) {
				r.setValue(i, j, this.vals[i][j] + b.getValue(i, j));
			}
		}
		return r;
	}

	public DoubleMatrix multiply(long a) {
		DoubleMatrix r = new DoubleMatrix(this.x, this.y);
		for (int i = 0; i < this.x; i++) {
			for (int j = 0; j < this.y; j++) {
				r.setValue(i, j, a * this.vals[i][j]);
			}
		}
		return r;
	}

	/**
	 * Multiply this matrix by an other matrix.
	 * 
	 * @param b
	 *            The matrix to be multiplied on the right side of this.
	 * @return Matrix product (this*b)
	 * @throws IllegalArgumentException
	 *             if b's dimensions are illegal.
	 */
	public DoubleMatrix multiply(DoubleMatrix b) {
		if (this.y != b.x)
			throw new IllegalArgumentException("Sizes don't match");
		double[][] c = new double[this.x][b.y];
		for (int i = 0; i < this.x; i++) {
			for (int j = 0; j < b.y; j++) {
				for (int k = 0; k < b.x; k++) {
					c[i][j] += this.vals[i][k] * b.vals[k][j];
				}
			}
		}
		return new DoubleMatrix(c);
	}

	/**
	 * Calculates the determinant of the matrix, using the LaPlace procedure. I
	 * don't recommend using it on big matrices, might lead to stack overflow.
	 * 
	 * @throws ArithmethicException
	 *             if the matrix is not quadratic.
	 */

	public double determinant() {
		if (this.x != this.y)
			throw new ArithmeticException("Matrix is not quadratic.");
		if (this.x == 1)
			return this.vals[0][0];
		if (this.x == 2)
			return this.vals[0][0] * this.vals[1][1] - this.vals[0][1] * this.vals[1][0];
		if (this.x == 3)
			return this.vals[0][0] * this.vals[1][1] * this.vals[2][2]
					+ this.vals[0][1] * this.vals[1][2] * this.vals[2][0]
					+ this.vals[0][2] * this.vals[1][0] * this.vals[2][1]
					- this.vals[0][2] * this.vals[1][1] * this.vals[2][0]
					- this.vals[0][1] * this.vals[1][0] * this.vals[2][2]
					- this.vals[0][0] * this.vals[1][2] * this.vals[2][1];

		double det = 0;

		for (int i = 0, sign = 1; i < this.x; i++, sign *= -1) {
			if (this.vals[0][i] == 0)
				continue;
			double[][] tmp = new double[this.x - 1][this.x - 1];
			for (int j = 1; j < this.x; j++) {
				for (int k = 0, l = 0; k < this.x; k++) {
					if (k == i)
						continue;
					tmp[j - 1][l] = this.vals[j][k];
					l++;
				}
			}
			DoubleMatrix t = new DoubleMatrix(tmp);
			det += sign * this.vals[0][i] * t.determinant();
		}

		return det;

	}

	/**
	 * Solve this matrix for vector x
	 * 
	 * @param Vector
	 *            x, represented as a DoubleMatrix.
	 * @throws IllegalArgumentException
	 *             If the dimensions of this matrix is not quadratic or x's
	 *             dimensions are illegal.
	 * @throws ArithmeticException
	 *             If this matrix is singular.
	 * @return Vector y that solves (this * y) = x.
	 */
	public DoubleMatrix solveForVector(DoubleMatrix x) {
		if (this.x != this.y || x.x != this.y || x.y != 1) {
			throw new IllegalArgumentException("Illegal matrix dimensions.");
		}

		DoubleMatrix A = new DoubleMatrix(this.vals);
		DoubleMatrix b = new DoubleMatrix(x.vals);

		for (int i = 0; i < A.y; i++) {
			int max = i;
			for (int j = i + 1; j < A.y; j++) {
				if (Math.abs(A.vals[j][i]) > Math.abs(A.vals[max][i])) {
					max = j;
				}
			}

			A.swapRows(i, max);
			b.swapRows(i, max);

			if (A.vals[i][i] == 0.0)
				throw new ArithmeticException("Matrix is singular.");

			for (int j = i + 1; j < A.y; j++) {
				b.vals[j][0] -= b.vals[i][0] * A.vals[j][i] / A.vals[i][i];
			}

			for (int j = i + 1; j < A.y; j++) {
				for (int k = i + 1; k < A.y; k++) {
					A.vals[j][k] -= A.vals[i][k] * A.vals[i][j] / A.vals[i][i];
				}
				A.vals[j][i] = 0;
			}
			// A.reduceWith(b);
		}

		DoubleMatrix lsg = new DoubleMatrix(A.y, 1);
		for (int i = A.y - 1; i >= 0; i--) {
			double t = 0;
			for (int j = i + 1; j < A.y; j++) {
				t += A.vals[i][j] * lsg.vals[j][0];
			}
			lsg.vals[i][0] = (b.vals[i][0] - t) / A.vals[i][i];
		}
		return lsg;
	}

	private void swapRows(int a, int b) {
		double[] tmp = this.vals[a];
		this.vals[a] = this.vals[b];
		this.vals[b] = tmp;
	}

	@Override
	public String toString() {
		String s = "";
		for (int i = 0; i < this.x; i++) {
			for (int j = 0; j < this.y; j++) {
				s += j == this.y - 1 ? this.vals[i][j] : this.vals[i][j] + "\t";
			}
			if (i < this.x - 1)
				s += "\n";
		}
		return s;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.deepHashCode(vals);
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof DoubleMatrix)) {
			return false;
		}
		DoubleMatrix other = (DoubleMatrix) obj;
		if (!Arrays.deepEquals(vals, other.vals)) {
			return false;
		}
		if (x != other.x) {
			return false;
		}
		if (y != other.y) {
			return false;
		}
		return true;
	}

}
