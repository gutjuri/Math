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

	private final BigInteger nom;
	private final BigInteger denom;

	public static final BigFraction ONE = new BigFraction(new BigInteger("1"), new BigInteger("1"));
	public static final BigFraction TWO = new BigFraction(new BigInteger("2"), new BigInteger("1"));
	public static final BigFraction ONE_HALF = new BigFraction(new BigInteger("1"), new BigInteger("2"));
	public static final BigFraction ONE_THIRD = new BigFraction(new BigInteger("1"), new BigInteger("3"));

	/**
	 * * A fraction of BigIntegers that supports various arithmethic operations. A
	 * fraction always has the property that gcd(nominator, denominator) = 1, where
	 * gcd(a, b) is the greatest common divisor of a and b.
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
		BigInteger t = gcd(nominator, denominator);
		nom = flip ? nominator.divide(t).negate() : nominator.divide(t);
		denom = denominator.divide(t);
	}

	/**
	 * * A fraction of BigIntegers that supports various arithmethic operations. A
	 * fraction always has the property that gcd(nominator, denominator) = 1, where
	 * gcd(a, b) is the greatest common divisor of a and b.
	 * 
	 * @param nominator
	 * @param denominator
	 */
	public BigFraction(String nom, String denom) {
		this(new BigInteger(nom), new BigInteger(denom));
	}

	/**
	 * * A fraction of BigIntegers that supports various arithmethic operations. A
	 * fraction always has the property that gcd(nominator, denominator) = 1, where
	 * gcd(a, b) is the greatest common divisor of a and b.
	 * 
	 * @param nominator
	 * @param denominator
	 */

	public BigFraction(long nom, long denom) {
		this(new BigInteger("" + nom), new BigInteger("" + denom));
	}

	/**
	 * Returns the greatest common integer divisor of a and b using the euclidian
	 * algorithm.
	 * 
	 * @param a
	 * @param b
	 * @return gcd(a, b)
	 */
	private static BigInteger gcd(BigInteger a, BigInteger b) {
		return (b.compareTo(BigInteger.ZERO) == 0) ? a : gcd(b, a.mod(b));
	}

	/**
	 * Returns the nominator of this fraction.
	 */
	public BigInteger getNominator() {
		return nom;
	}

	/**
	 * Returns the denominator of this fraction.
	 */
	public BigInteger getDenominator() {
		return denom;
	}

	/**
	 * Adds this fraction to an other fraction and returns the result. Neither this
	 * fraction nor the other fraction are mutated.
	 * 
	 * @param f
	 *            The other fraction
	 * @return this + f
	 */
	public BigFraction plus(BigFraction f) {
		BigInteger tNominator = denom.multiply(f.getDenominator());
		BigInteger tDenominator = nom.multiply(f.denom).add(f.nom.multiply(denom));
		BigInteger gcd = gcd(tDenominator, tNominator);
		tDenominator = tDenominator.divide(gcd);
		tNominator = tNominator.divide(gcd);
		BigFraction newfrac = new BigFraction(tDenominator, tNominator);
		return newfrac;
	}

	/**
	 * Adds a value k to this fraction and returns the result. This fraction won't
	 * be mutated.
	 * 
	 * @param k
	 *            The value to add.
	 * @return this + k
	 */
	public BigFraction plus(long k) {
		return plus(new BigFraction(k, 1));
	}

	/**
	 * Subtracts an other fraction from this fraction and returns the result.
	 * Neither this fraction nor the other fraction are mutated.
	 * 
	 * @param f
	 *            The other fraction
	 * @return this - f
	 */
	public BigFraction minus(BigFraction f) {
		BigFraction tmp = new BigFraction(f.getNominator().negate(), f.getDenominator());
		return this.plus(tmp);
	}

	/**
	 * Subtracts a value k from this fraction and returns the result. This fraction
	 * won't be mutated.
	 * 
	 * @param k
	 *            The value to subtract.
	 * @return this - k
	 */
	public BigFraction minus(long k) {
		return minus(new BigFraction(k, 1));
	}

	/**
	 * Multiplies this fraction and an other fraction and returns the result.
	 * Neither this fraction nor the other fraction are mutated.
	 * 
	 * @param f
	 *            The other fraction
	 * @return this * f
	 */
	public BigFraction multiply(BigFraction f) {
		return new BigFraction(nom.multiply(f.getNominator()), denom.multiply(f.getDenominator()));
	}

	/**
	 * Multiplies this fraction by a value k. This fraction won't be mutated.
	 * 
	 * @param k
	 * @return k * this
	 */
	public BigFraction multiply(long k) {
		return multiply(new BigFraction(k, 1));
	}

	/**
	 * Divides this fraction by an other fraction and returns the result. Neither
	 * this fraction nor the other fraction are mutated.
	 * 
	 * @param f
	 *            The other fraction
	 * @return this + f
	 */
	public BigFraction divide(BigFraction f) {
		if (f.nom.equals(BigInteger.ZERO)) {
			throw new ArithmeticException("Cannot divide by zero.");
		}
		return this.multiply(new BigFraction(f.getDenominator(), f.getNominator()));
	}

	/**
	 * Divides this fraction by a value k. This fraction won't be mutated.
	 * 
	 * @param k
	 * @return this / k
	 */
	public BigFraction divide(long k) {
		return divide(new BigFraction(k, 1));
	}

	/**
	 * Raises this fraction to a power and returns the result. This fraction won't
	 * be mutated.
	 * 
	 * @param i
	 *            The power to raise this fraction to.
	 * @return this^i
	 */
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