package algorithms;

import java.util.List;

public interface Matcher {

    List<Integer> search(String text, String pattern);

    long getComparisons();
}
