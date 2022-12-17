package testing;

import org.openjdk.jmh.annotations.*;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

@State(Scope.Thread)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
public class DotProduct {

    private float[] a;
    private float[] b;

    //@Param({"1", "4", "6", "8", "13", "16", "25", "32", "64", "100", "128", "207", "256", "300", "512", "702", "1024"})
    @Param({"1024"})
    int size;

    @Setup(Level.Trial)
    public void init() {
        a = new float[size];
        b = new float[size];
        for (int i = 0; i < size; ++i) {
            a[i] = ThreadLocalRandom.current().nextFloat();
            b[i] = ThreadLocalRandom.current().nextFloat();
        }
    }

    @Benchmark
    public float dotProductNew() {
        if (a.length != b.length) {
            throw new IllegalArgumentException("vector dimensions differ: " + a.length + "!=" + b.length);
        }
        float res = 0f;
        /*
         * If length of vector is larger than 8, we use unrolled dot product to accelerate the
         * calculation.
         */
        int i;
        for (i = 0; i < a.length % 8; i++) {
            res += b[i] * a[i];
        }
        if (a.length < 8) {
            return res;
        }
        float res1 = 0f;
        float res2 = 0f;
        float res3 = 0f;
        float res4 = 0f;
        for (; i + 31 < a.length; i += 32) {
            res1 += b[i + 0] * a[i + 0] + b[i + 1] * a[i + 1] + b[i + 2] * a[i + 2] + b[i + 3] * a[i + 3] +
                    b[i + 4] * a[i + 4] + b[i + 5] * a[i + 5] + b[i + 6] * a[i + 6] + b[i + 7] * a[i + 7];
            res2 += b[i + 8] * a[i + 8] + b[i + 9] * a[i + 9] + b[i + 10] * a[i + 10] + b[i + 11] * a[i + 11] +
                    b[i + 12] * a[i + 12] + b[i + 13] * a[i + 13] + b[i + 14] * a[i + 14] + b[i + 15] * a[i + 15];
            res3 += b[i + 16] * a[i + 16] + b[i + 17] * a[i + 17] + b[i + 18] * a[i + 18] + b[i + 19] * a[i + 19] +
                    b[i + 20] * a[i + 20] + b[i + 21] * a[i + 21] + b[i + 22] * a[i + 22] + b[i + 23] * a[i + 23];
            res4 += b[i + 24] * a[i + 24] + b[i + 25] * a[i + 25] + b[i + 26] * a[i + 26] + b[i + 27] * a[i + 27] +
                    b[i + 28] * a[i + 28] + b[i + 29] * a[i + 29] + b[i + 30] * a[i + 30] + b[i + 31] * a[i + 31];
        }
        res += res1 + res2 + res3 + res4;
        for (; i + 7 < a.length; i += 8) {
            res += b[i + 0] * a[i + 0] + b[i + 1] * a[i + 1] + b[i + 2] * a[i + 2] + b[i + 3] * a[i + 3] +
                    b[i + 4] * a[i + 4] + b[i + 5] * a[i + 5] + b[i + 6] * a[i + 6] + b[i + 7] * a[i + 7];
        }
        return res;
    }


    @Benchmark
    public float dotProductOld() {
        if (a.length != b.length) {
            throw new IllegalArgumentException("vector dimensions differ: " + a.length + "!=" + b.length);
        }
        float res = 0f;
        /*
         * If length of vector is larger than 8, we use unrolled dot product to accelerate the
         * calculation.
         */
        int i;
        for (i = 0; i < a.length % 8; i++) {
            res += b[i] * a[i];
        }
        if (a.length < 8) {
            return res;
        }
        for (; i + 31 < a.length; i += 32) {
            res += b[i + 0] * a[i + 0] + b[i + 1] * a[i + 1] + b[i + 2] * a[i + 2] + b[i + 3] * a[i + 3] +
                    b[i + 4] * a[i + 4] + b[i + 5] * a[i + 5] + b[i + 6] * a[i + 6] + b[i + 7] * a[i + 7];
            res += b[i + 8] * a[i + 8] + b[i + 9] * a[i + 9] + b[i + 10] * a[i + 10] + b[i + 11] * a[i + 11] +
                    b[i + 12] * a[i + 12] + b[i + 13] * a[i + 13] + b[i + 14] * a[i + 14] + b[i + 15] * a[i + 15];
            res += b[i + 16] * a[i + 16] + b[i + 17] * a[i + 17] + b[i + 18] * a[i + 18] + b[i + 19] * a[i + 19] +
                    b[i + 20] * a[i + 20] + b[i + 21] * a[i + 21] + b[i + 22] * a[i + 22] + b[i + 23] * a[i + 23];
            res += b[i + 24] * a[i + 24] + b[i + 25] * a[i + 25] + b[i + 26] * a[i + 26] + b[i + 27] * a[i + 27] +
                    b[i + 28] * a[i + 28] + b[i + 29] * a[i + 29] + b[i + 30] * a[i + 30] + b[i + 31] * a[i + 31];
        }
        for (; i + 7 < a.length; i += 8) {
            res += b[i + 0] * a[i + 0] + b[i + 1] * a[i + 1] + b[i + 2] * a[i + 2] + b[i + 3] * a[i + 3] +
                    b[i + 4] * a[i + 4] + b[i + 5] * a[i + 5] + b[i + 6] * a[i + 6] + b[i + 7] * a[i + 7];
        }
        return res;
    }
}
