package main.util.range;

import java.io.Serializable;


@SuppressWarnings("serial")
public class Ranges<T extends Comparable<T>> implements Serializable {

		T lowerRange;
		T upperRange;
		public Ranges(T min, T max){
			this.lowerRange = min;
			this.upperRange = max;
		}
		
		public T getLowerRange() {
			return this.lowerRange;
		}
		
		public T getUpperRange() {
			return this.upperRange;
		}
		
		public T restrict(T value) {
		    T lowerrange = getLowerRange();
		    if (lowerrange.compareTo(value) > 0) {
		      return lowerrange;
		    }

		    T upperrange = getUpperRange();
		    if (upperrange.compareTo(value) < 0) {
		      return upperrange;
		    }

		    return value;
		  }
		
		
		public static <T extends Comparable<T>> Ranges<T> create(T lowerRange, T upperRange) {
		    if (lowerRange == null) {
		      throw new IllegalArgumentException("null lower bound");
		    } else if (upperRange == null) {
		      throw new IllegalArgumentException("null upper bound");
		    } else if (lowerRange.compareTo(upperRange) > 0) {
		      throw new IllegalArgumentException(
		          String.format("lower range (%s) must be below upper range (%s)", lowerRange, upperRange));
		    } else {
		      return new Ranges<T>(lowerRange, upperRange) {
		        
		        public T getLowerRange() {
		          return lowerRange;
		        }

		      
		        public T getUpperRange() {
		          return upperRange;
		        }
		      };
		    }
		  }
		
	//	public static <T extends Comparable<T>> Ranges<T> fromPair(Pair<T, T> pair) {
	//	    return create(pair.getLeft(), pair.getRight());
	//	}
		
		

}
