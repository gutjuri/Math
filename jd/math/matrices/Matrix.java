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
	                                   |_|          			*/
/**
 * 
 * A matrix of long values that supports various artihmethic operations.
 * 
 * @author Juri Dispan
 *
 */

public class Matrix {

	/** Number of rows */
	private int x;

	/** Number of columns */
	private int y;

	/** Actual values in the Matrix */
	private long[][] vals;

	/**
	 * Creates a Matrix from the values in the argument
	 * 
	 * @param values
	 *            A long[][] representing a matrix, needs to be "rectangular".
	 */
	public Matrix(long[][] values) {
		if (values.length == 0)
			throw new IllegalArgumentException("long[][] int argument must not have size zero.");
		int k = values[0].length;
		for (int i = 0; i < values.length; i++) {
			if (values[i].length != k)
				throw new IllegalArgumentException("long[][] in argument is not rectangular.");
		}
		this.vals = values.clone();
		this.x = vals.length;
		this.y = vals[0].length;

	}

	/** Creates a matrix with all entries set to zero. */
	public Matrix(int rows, int cols) {
		this.x = rows;
		this.y = cols;
		this.vals = new long[this.x][this.y];
	}

	/**
	 * Changes the entry of the matrix at the specified position to the
	 * specified value.
	 */
	public void setValue(int row, int col, long val) {
		this.vals[row][col] = val;
	}

	/** Returns the value of the matrix at the specified position. */
	public long getValue(int row, int col) {
		return this.vals[row][col];
	}

	/**
	 * @return A matrix, in which all entries have the same absolute value and
	 *         opposite signum as the entries at the corresponding position of
	 *         this matrix.
	 */
	public Matrix negate() {
		Matrix r = new Matrix(this.x, this.y);
		for (int i = 0; i < this.x; i++) {
			for (int j = 0; j < this.y; j++) {
				r.setValue(i, j, -this.vals[i][j]);
			}
		}
		return r;
	}

	public Matrix add(Matrix b) {
		if (this.x != b.x || this.y != b.y)
			throw new IllegalArgumentException("Sizes don't match");
		Matrix r = new Matrix(this.x, this.y);
		for (int i = 0; i < this.x; i++) {
			for (int j = 0; j < this.y; j++) {
				r.setValue(i, j, this.vals[i][j] + b.getValue(i, j));
			}
		}
		return r;
	}

	public Matrix multiply(long a) {
		Matrix r = new Matrix(this.x, this.y);
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

	public Matrix multiply(Matrix b) {
		if (this.y != b.x)
			throw new IllegalArgumentException("Sizes don't match");
		long[][] c = new long[this.x][b.y];
		for (int i = 0; i < this.x; i++) {
			for (int j = 0; j < b.y; j++) {
				for (int k = 0; k < b.x; k++) {
					c[i][j] += this.vals[i][k] * b.vals[k][j];
				}
			}
		}
		return new Matrix(c);
	}

	/**
	 * Calculates the determinant of the matrix, using the LaPlace procedure. I
	 * don't recommend using it on big matrices, might lead to stack overflow.
	 * 
	 * @throws ArithmethicException
	 *             if the matrix is not quadratic.
	 */

	public long determinant() {
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

		long det = 0;

		for (int i = 0, sign = 1; i < this.x; i++, sign *= -1) {
			if (this.vals[0][i] == 0)
				continue;
			long[][] tmp = new long[this.x - 1][this.x - 1];
			for (int j = 1; j < this.x; j++) {
				for (int k = 0, l = 0; k < this.x; k++) {
					if (k == i)
						continue;
					tmp[j - 1][l] = this.vals[j][k];
					l++;
				}
			}
			Matrix t = new Matrix(tmp);
			det += sign * this.vals[0][i] * t.determinant();
		}

		return det;

	}

	private long ggt(long a, long b) {
		return b == 0 ? a : ggt(b, a % b);
	}

	private long kgv(long a, long b) {
		return a / ggt(a, b) * b;
	}

	/**
	 * Solves the Matrix for the Matrix in the argument, which needs to have
	 * only one column.
	 * 
	 * @param b
	 *            The Vector b, where A*x = b
	 * @return The Vector x, where A*x = b
	 */

	public Matrix solve(Matrix b) {
		if (b.y != 1)
			throw new IllegalArgumentException("Matrix in Argument needs to be a Vector");
		if (this.x != b.x)
			throw new IllegalArgumentException("Sizes don't match");
		long[][] h = new long[this.x][this.y + 1];
		for (int i = 0; i < this.x; i++) {
			for (int j = 0; j < this.y; j++) {
				h[i][j] = this.vals[i][j];
			}
			h[i][this.y] = b.getValue(0, i);
		}

		for (int curr = 0; curr < h.length; curr++) {
			long kgv = 1;
			for (int i = curr; i < h.length; i++) {
				kgv = kgv(kgv, h[i][curr]);
			}
			for (int i = curr; i < h.length; i++) {
				multiplyRow(h, i, kgv / h[i][curr]);
			}
			for (int i = curr + 1; i < h.length; i++) {
				subtractRow(h, i, curr);
			}
			for (int i = 0; i < h.length; i++) {
				minimizeRow(h[i]);
			}
		}

		return toCoeff(h);

	}

	private void minimizeRow(long[] g) {
		long ggt = 0;
		for (int i = 0; i < g.length; i++) {
			ggt = ggt(ggt, g[i]);
		}
		if (ggt == 0)
			return;
		for (int i = 0; i < g.length; i++) {
			g[i] /= ggt;
		}
	}

	private void subtractRow(long[][] g, int minuendRow, int subtrahendRow) {
		for (int i = 0; i < g[minuendRow].length; i++)
			g[minuendRow][i] -= g[subtrahendRow][i];
	}

	private void multiplyRow(long[][] g, int row, long factor) {
		for (int i = 0; i < g[row].length; i++) {
			g[row][i] *= factor;
		}
	}

	private Matrix toCoeff(long[][] m) {
		long[][] sol = new long[m.length][1];
		for (int i = m.length - 1; i >= 0; i--) {
			long zs = m[i][m.length] / m[i][i];
			for (int j = 0; j < i; j++) {
				m[j][m.length] -= zs * m[j][i];
			}
			sol[i][0] = zs;
		}
		return new Matrix(sol);
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
		if (!(obj instanceof Matrix)) {
			return false;
		}
		Matrix other = (Matrix) obj;
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
