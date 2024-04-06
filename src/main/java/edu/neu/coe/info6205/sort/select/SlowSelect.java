package edu.neu.coe.info6205.sort.select;

import java.util.Arrays;
import java.util.Random;

/**
 * Class SlowSelect
 *
 * @param <X> the underlying comparable type.
 */
public class SlowSelect <X extends Comparable<X>>{
    public final X[] xa;
    public final int k;

    /**
     *
     * @param xa the array of elements
     * @param k represents how many smaller elements there are in the array.
     */
    public SlowSelect(X[] xa, int k) {
        this.xa = xa;
        this.k = k;
    }

    /***
     *
     * @param a the array
     * @param k the kth smallest index
     * @return kth smallest value
     */
    public X select( X[] a, int k) {
        shuffle(a);

        //@SuppressWarnings({"unchecked", "ConstantConditions"})
        //X[] kArray =  (X[]) new Object[k];
        //X[] kArray =  (X[]) Array.newInstance(a.getClass(), k);
        X[] kArray = Arrays.copyOf(a, k);

        for (int i = 0; i < k; i++) {
            kArray[i] = null;
        }
        for (X x : a) {
            for (int i = k - 1; i >= 0; i--) {
                if (kArray[i] == null || kArray[i].compareTo(x) > 0) {
                    if (i < k - 1) {
                        kArray[i + 1] = kArray[i];
                    }
                    kArray[i] = x;
                } else {
                    break;
                }
            }
        }
        return kArray[k - 1];
    }

    /***
     * Knuth shuffle
     * In iteration i, pick integer r between 0 and i uniformly at random
     * Swap a[i] and a[r].
     *
     * @param a the array
     */
    public static void shuffle(Object[] a) {
        Random random = new Random();
        int N = a.length;
        for (int i = 0; i < N; i++) {
            int r = random.nextInt(i + 1);
            swap(a, i, r);
        }
    }

    /**
     * exchange a[i] and a[r]
     *
     * @param a the array.
     * @param i one index.
     * @param r other index.
     */
    private static void swap(Object[] a, int i, int r) {
        Object temp = a[i];
        a[i] = a[r];
        a[r] = temp;
    }

}
