package jd.math.matrices;

/**
 * This class offers static methods for matrix operations. It cannot be
 * instanciated.
 */

public class Matrices {

	/**
	 * This class offers static methods for matrix operations. It cannot be
	 * instanciated.
	 */

	private Matrices() {
		// cannot be instatiated.
	}

	/**
	 * It vorks haha :D
	 */

	public static long determinant(long[][] a) {
		return new Matrix(a).determinant();
	}

	/**
	 * Matrixaddition für int Matrizen. Werden nicht-passende Matrizen
	 * übergeben, so wird eine ArrayOutOfBoundsException geworfen. Ob der
	 * benutzte Algorithmus der effizientest mögliche ist, ist nicht bekannt.
	 * 
	 * @param a
	 *            ixj int Array, mit i, j > 0.
	 * @param b
	 *            ixj int Array, mit i, j > 0.
	 * @return Summe von a und b
	 * 
	 * 
	 */

	public static int[][] add(int[][] a, int[][] b) {
		int[][] c = new int[a.length][a[0].length];
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a[0].length; j++) {
				c[i][j] = a[i][j] * b[i][j];
			}
		}
		return c;
	}

	/**
	 * Matrixaddition für long Matrizen. Werden nicht-passende Matrizen
	 * übergeben, so wird eine ArrayOutOfBoundsException geworfen. Ob der
	 * benutzte Algorithmus der effizientest mögliche ist, ist nicht bekannt.
	 * 
	 * @param a
	 *            ixj long Array, mit i, j > 0.
	 * @param b
	 *            ixj long Array, mit i, j > 0.
	 * @return Summe von a und b
	 * 
	 * 
	 */

	public static long[][] add(long[][] a, long[][] b) {
		long[][] c = new long[a.length][a[0].length];
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a[0].length; j++) {
				c[i][j] = a[i][j] * b[i][j];
			}
		}
		return c;
	}

	/**
	 * Matrixmultiplikationen sind verfügbar für int und long Matrizen. Argument
	 * a soll ein ixj Array und Argument b soll ein jxk Array sein, mit i, j, k
	 * > 0. Werden nicht-passende Matrizen übergeben, so wird eine
	 * ArrayOutOfBoundsException geworfen. Ob der benutzte Algorithmus der
	 * effizientest mögliche ist, ist nicht bekannt.
	 */

	public static int[][] multiply(int[][] a, int[][] b) {
		int[][] c = new int[a.length][b[0].length];
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < b[0].length; j++) {
				for (int k = 0; k < b.length; k++) {
					c[i][j] += a[i][k] * b[k][j];
				}
			}
		}
		return c;
	}

	public static long[][] multiply(long[][] a, long[][] b) {
		long[][] c = new long[a.length][b[0].length];
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < b[0].length; j++) {
				for (int k = 0; k < b.length; k++) {
					c[i][j] += a[i][k] * b[k][j];
				}
			}
		}
		return c;
	}

	public static double[][] multiply(double[][] a, double[][] b) {
		double[][] c = new double[a.length][b[0].length];
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < b[0].length; j++) {
				for (int k = 0; k < b.length; k++) {
					c[i][j] += a[i][k] * b[k][j];
				}
			}
		}
		return c;
	}

	public static Matrix getIdentity(int dim) {
		Matrix t = new Matrix(new long[dim][dim]);
		for (int i = 0; i < dim; i++) {
			t.setValue(i, i, 1);
		}
		return t;
	}

	public static Matrix getNullMatrix(int dim) {
		return new Matrix(dim, dim);
	}

	/**
	 * Eine Matrixquadratfunktion ist verfügbar für boolean Matrizen. Gedacht
	 * ist sie vor allem zur Berechnung des Quadrates von Adjazenzmatrizen (=>
	 * Graphentheorie). Argument a soll ein ein ixi boolean Array sein mit i >
	 * 0; Wird eine nicht-passende Matrix übergeben, so wird eine
	 * ArrayOutOfBoundsException geworfen.
	 * 
	 * @param a
	 *            ixi boolean array
	 * @return Quadrat von a
	 */

	public static boolean[][] square(boolean[][] a) {
		boolean[][] b = new boolean[a.length][a.length];
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a.length; j++) {
				for (int k = 0; k < a.length; k++) {
					if (a[i][k] && a[k][j])
						b[i][j] = true;
				}
			}
		}
		return b;
	}

}
