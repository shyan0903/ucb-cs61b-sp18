package hw3.hash;

import java.util.List;

public class OomageTestUtility {
    public static boolean haveNiceHashCodeSpread(List<Oomage> oomages, int M) {
        int[] buckets = new int[M];
        for (Oomage o : oomages) {
            int bucketNum = (o.hashCode() & 0x7FFFFFFF) % M;
            buckets[bucketNum] += 1;
        }
        for (int num : buckets) {
            if (oomages.size() / 50 > num || num > (int) oomages.size() / 2.5) {
                return false;
            }
        }
        return true;
    }
}
