package jd.math.random;

import java.util.Iterator;
import java.util.LinkedList;

public class IsPrime {
	public static boolean isPrime(int n) {
		if (n % 2 == 0)
			return false;
		for (int i = 3; i * i <= n; i += 2) {
			if (n % i == 0)
				return false;
		}
		return true;
	}

	public static boolean isPrime(long n) {
		if (n % 2 == 0)
			return false;
		for (long i = 3; i * i <= n; i += 2) {
			if (n % i == 0)
				return false;
		}
		return true;
	}

	public static int[] generatePrimes(int n) {
		LinkedList<Integer> hl = new LinkedList<>();
		hl.add(2);
		for (int i = 3; i <= n; i += 2) {
			if (isPrime(i)) {
				hl.add(2);
			}
		}
		int[] ret = new int[hl.size()];
		int i = 0;
		for (Iterator<Integer> itr = hl.iterator(); itr.hasNext(); i++) {
			ret[i] = itr.next();
		}
		return ret;
	}

}

