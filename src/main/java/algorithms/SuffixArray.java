package algorithms;

import java.util.Arrays;

public class SuffixArray {

    public static int[] build(String s) {
        int n = s.length();
        Integer[] indexes = new Integer[n];
        for (int i = 0; i < n; i++) {
            indexes[i] = i;
        }
        Arrays.sort(indexes, (a, b) -> compareSuffixes(s, a, b));
        int[] sa = new int[n];
        for (int i = 0; i < n; i++) {
            sa[i] = indexes[i];
        }
        return sa;
    }

    private static int compareSuffixes(String s, int a, int b) {
        int n = s.length();
        while (a < n && b < n) {
            char x = s.charAt(a);
            char y = s.charAt(b);
            if (x != y) {
                return x - y;
            }
            a++;
            b++;
        }
        return (n - a) - (n - b);
    }
}
