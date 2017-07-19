package jd.math.fractions;

import java.math.BigInteger;

/**
 * A fraction of BigIntegers that supports various arithmethic operations. A
 * fraction always has the property that gcd(nominator, denominator) = 1, where
 * gcd(a, b) is the greatest common divisor of a and b.
 * 
 * @author Juri Dispan
 *
 */
public class BigFraction implements Comparable<BigFraction> {

	private BigInteger nom;
	private BigInteger denom;

	public static final BigFraction ONE = new BigFraction(new BigInteger("1"), new BigInteger("1"));
	public static final BigFraction TWO = new BigFraction(new BigInteger("2"), new BigInteger("1"));
	public static final BigFraction ONE_HALF = new BigFraction(new BigInteger("1"), new BigInteger("2"));
	public static final BigFraction ONE_THIRD = new BigFraction(new BigInteger("1"), new BigInteger("3"));

	/**
	 * * A fraction of BigIntegers that supports various arithmethic operations.
	 * A fraction always has the property that gcd(nominator, denominator) = 1,
	 * where gcd(a, b) is the greatest common divisor of a and b.
	 * 
	 * @param nominator
	 * @param denominator
	 */
	public BigFraction(BigInteger nominator, BigInteger denominator) {
		boolean flip = false;
		if (nominator.compareTo(BigInteger.ZERO) < 0) {
			nominator = nominator.negate();
			flip = true;
		}
		if (denominator.compareTo(BigInteger.ZERO) < 0) {
			denominator = denominator.negate();
			if (flip) {
				flip = false;
			} else {
				flip = true;
			}
		}
		BigInteger t = ggt(nominator, denominator);
		nom = flip ? nominator.divide(t).negate() : nominator.divide(t);
		denom = denominator.divide(t);
	}

	/**
	 * * A fraction of BigIntegers that supports various arithmethic operations.
	 * A fraction always has the property that gcd(nominator, denominator) = 1,
	 * where gcd(a, b) is the greatest common divisor of a and b.
	 * 
	 * @param nominator
	 * @param denominator
	 */
	public BigFraction(String nom, String denom) {
		this(new BigInteger(nom), new BigInteger(denom));
	}

	/**
	 * * A fraction of BigIntegers that supports various arithmethic operations.
	 * A fraction always has the property that gcd(nominator, denominator) = 1,
	 * where gcd(a, b) is the greatest common divisor of a and b.
	 * 
	 * @param nominator
	 * @param denominator
	 */

	public BigFraction(long nom, long denom) {
		this(new BigInteger("" + nom), new BigInteger("" + denom));
	}

	private BigInteger ggt(BigInteger nominator, BigInteger denominator) {
		return (denominator.compareTo(BigInteger.ZERO) == 0) ? nominator : ggt(denominator, nominator.mod(denominator));
	}

	public BigInteger getNominator() {
		return nom;
	}

	public BigInteger getDenominator() {
		return denom;
	}

	public BigFraction plus(BigFraction f) {
		BigInteger en = denom.multiply(f.getDenominator());
		BigInteger ez = nom.multiply(f.denom).add(f.nom.multiply(denom));
		BigInteger t = ggt(ez, en);
		ez = ez.divide(t);
		en = en.divide(t);
		BigFraction newfrac = new BigFraction(ez, en);
		return newfrac;
	}

	public BigFraction minus(BigFraction f) {
		BigFraction tmp = new BigFraction(f.getNominator().negate(), f.getDenominator());
		return this.plus(tmp);
	}

	public BigFraction multiply(BigFraction f) {
		return new BigFraction(nom.multiply(f.getNominator()), denom.multiply(f.getDenominator()));
	}

	public BigFraction divide(BigFraction f) {
		if (f.nom.equals(BigInteger.ZERO)) {
			throw new ArithmeticException("Cannot divide by zero.");
		}
		return this.multiply(new BigFraction(f.getDenominator(), f.getNominator()));
	}

	public BigFraction pow(int i) {
		return new BigFraction(this.nom.pow(i), this.denom.pow(i));
	}

	@Override
	public int compareTo(BigFraction f) {
		BigInteger tn = nom.multiply(f.getDenominator());
		BigInteger fn = f.getNominator().multiply(denom);
		return tn.compareTo(fn);
	}

	@Override
	public String toString() {
		return nom.toString() + "/" + denom.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((denom == null) ? 0 : denom.hashCode());
		result = prime * result + ((nom == null) ? 0 : nom.hashCode());
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
		if (!(obj instanceof BigFraction)) {
			return false;
		}
		BigFraction other = (BigFraction) obj;
		if (denom == null) {
			if (other.denom != null) {
				return false;
			}
		} else if (!denom.equals(other.denom)) {
			return false;
		}
		if (nom == null) {
			if (other.nom != null) {
				return false;
			}
		} else if (!nom.equals(other.nom)) {
			return false;
		}
		return true;
	}

}