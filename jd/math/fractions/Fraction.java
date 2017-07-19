package jd.math.fractions;

/**
 * A fraction of long values that supports various arithmethic operations. A
 * fraction always has the property that gcd(nominator, denominator) = 1, where
 * gcd(a, b) is the greatest common divisor of a and b.
 * 
 * @author Juri Dispan
 *
 */

public class Fraction implements Comparable<Fraction> {

	private long nom;

	private long denom;

	public static final Fraction ONE = new Fraction(1, 1);
	public static final Fraction TWO = new Fraction(2, 1);
	public static final Fraction ONE_HALF = new Fraction(1, 2);
	public static final Fraction ONE_THIRD = new Fraction(1, 3);

	/**
	 * A fraction that supports various arithmethic operations. A fraction
	 * always has the property that gcd(nominator, denominator) = 1, where
	 * gcd(a, b) is the greatest common divisor of a and b.
	 * 
	 * @param nominator
	 * @param denominator
	 */

	public Fraction(long nominator, long denominator) {
		boolean negativ = nominator < 0;
		if (negativ)
			nominator *= -1;
		negativ = (negativ ^ denominator < 0);
		if (denominator < 0)
			denominator *= -1;
		long t = ggt(nominator, denominator);
		nom = negativ ? -1 * nominator / t : nominator / t;
		denom = denominator / t;

	}

	private long ggt(long a, long b) {
		return (b == 0) ? a : ggt(b, a % b);
	}

	public long getNominator() {
		return nom;
	}

	public long getDenominator() {
		return denom;
	}

	/**
	 * The fractional part of this fraction.
	 * 
	 * @return new Fraction((nominator mod denominator) , denominator)
	 */
	public Fraction fractionalPart() {
		boolean negativ = nom < 0;
		long c = negativ ? -1 * nom : nom;
		while (c > denom) {
			c -= denom;
		}
		Fraction newfrac = new Fraction(negativ ? -1 * c : c, denom);
		return newfrac;
	}

	public long wholePart() {
		return this.minus(this.fractionalPart()).getNominator();
	}

	public Fraction plus(Fraction f) {
		long en = denom * f.denom;
		long ez = nom * f.denom + f.nom * denom;
		boolean negativ = ez < 0;
		if (negativ)
			ez *= -1;
		negativ = (negativ ^ en < 0);
		if (en < 0)
			en *= -1;
		long t = ggt(ez, en);
		ez = negativ ? -1 * ez / t : ez / t;
		en = en / t;
		Fraction newfrac = new Fraction(ez, en);
		return newfrac;

	}

	public Fraction minus(Fraction f) {
		Fraction tmp = new Fraction(-f.getNominator(), f.getDenominator());
		return this.plus(tmp);
	}

	public Fraction multiply(Fraction f) {
		long en = denom * f.denom;
		long ez = nom * f.nom;
		boolean negativ = ez < 0;
		if (negativ)
			ez *= -1;
		negativ = (negativ ^ en < 0);
		if (en < 0)
			en *= -1;
		long t = ggt(ez, en);
		ez = negativ ? -1 * ez / t : ez / t;
		en = en / t;
		Fraction newfrac = new Fraction(ez, en);
		return newfrac;
	}

	public Fraction divide(Fraction f) {
		if (f.nom == 0) {
			throw new ArithmeticException("Cannot divide by zero.");
		}
		Fraction h = new Fraction(f.denom, f.nom);
		return multiply(h);
	}

	public long toWholeNumber() {
		if (denom != 1) {
			throw new ArithmeticException("Fraction cannot be converted to a whole number.");
		}
		return nom;
	}

	/**
	 * 
	 * @param i
	 *            Exponent
	 * @return This fraction to the ith power
	 */
	public Fraction pow(int i) {
		Fraction b = new Fraction(1, 1);
		for (int j = 0; j < i; j++) {
			b = this.multiply(b);
		}
		return b;
	}

	public double decimalValue() {
		return (nom + 0.0) / denom;
	}

	@Override
	public int compareTo(Fraction o) {
		long k = this.minus(o).getNominator();
		if (k > 0)
			return 1;
		if (k < 0)
			return -1;
		return 0;
	}

	@Override
	public String toString() {
		return nom + "/" + denom;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (denom ^ (denom >>> 32));
		result = prime * result + (int) (nom ^ (nom >>> 32));
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
		if (!(obj instanceof Fraction)) {
			return false;
		}
		Fraction other = (Fraction) obj;
		if (denom != other.denom) {
			return false;
		}
		if (nom != other.nom) {
			return false;
		}
		return true;
	}
}
