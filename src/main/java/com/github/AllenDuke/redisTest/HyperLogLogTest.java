package com.github.AllenDuke.redisTest;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author 杜科
 * @description
 * @contact AllenDuke@163.com
 * @date 2020/8/23
 */
public class HyperLogLogTest {

    static class BitKeeper {
        private int maxbits;
        public void random(long value) {
            int bits = lowZeros(value);
            if (bits > this.maxbits) {
                this.maxbits = bits;
            }
        }

        private int lowZeros(long value) {
            int i = 1;
            for (; i < 32; i++) {
                if (value >> i << i != value) {
                    break;
                }
            }
            return i - 1;
        }
    }
    static class Experiment {
        private int n;
        private int k;
        private BitKeeper[] keepers;
        public Experiment(int n) {
            this(n, 1024);
        }
        public Experiment(int n, int k) {
            this.n = n;
            this.k = k;
            this.keepers = new BitKeeper[k];
            for (int i = 0; i < k; i++) {
                this.keepers[i] = new BitKeeper();
            }
        }
        public void work() {
            for (int i = 0; i < this.n; i++) {
                long m = ThreadLocalRandom.current().nextLong(1L << 32);
                BitKeeper keeper = keepers[(int) (((m & 0xfff0000) >> 16) % keepers.length)];
                keeper.random(m);
            }
        }
        public double estimate() {
            double sumbitsInverse = 0.0;
            for (BitKeeper keeper : keepers) {
                sumbitsInverse += 1.0 / (float) keeper.maxbits;
            }
            double avgBits = (float) keepers.length / sumbitsInverse;
            return Math.pow(2, avgBits) * this.k;
        }
    }

    public static void main(String[] args) {
        for (int i = 100000; i < 1000000; i += 100000) {
            Experiment exp = new Experiment(i);
            exp.work();
            double est = exp.estimate();
            System.out.printf("%d %.2f %.2f\n", i, est, Math.abs(est - i) / i);
        }
    }


}
