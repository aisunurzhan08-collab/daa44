package app;

import algorithms.EditDistance;
import algorithms.KMP;
import algorithms.Kasai;
import algorithms.LCS;
import algorithms.Matcher;
import algorithms.NaiveMatcher;
import algorithms.RabinKarp;
import algorithms.SuffixArray;
import util.Generator;
import util.Plot;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Main {

    static final int RUNS = 5;
    static final int PATTERN_LENGTH = 50;
    static long sink;

    static double averageMs(Runnable task) {
        task.run();
        long total = 0;
        for (int i = 0; i < RUNS; i++) {
            long start = System.nanoTime();
            task.run();
            total += System.nanoTime() - start;
        }
        return total / (double) RUNS / 1_000_000.0;
    }

    public static void main(String[] args) throws IOException {
        Files.createDirectories(Paths.get("results"));
        StringBuilder md = new StringBuilder();

        int[] dpSizes = {100, 500, 1000};
        double[] sizes = new double[dpSizes.length];
        double[] lcsTimes = new double[dpSizes.length];
        double[] editTimes = new double[dpSizes.length];
        md.append("## LCS and Edit Distance\n\n");
        md.append("| n | LCS (ms) | Edit Distance (ms) | LCS length | Edit distance |\n");
        md.append("|---|---|---|---|---|\n");
        for (int i = 0; i < dpSizes.length; i++) {
            int n = dpSizes[i];
            String a = Generator.random(n, 42);
            String b = Generator.random(n, 43);
            sizes[i] = n;
            lcsTimes[i] = averageMs(() -> sink += LCS.length(a, b));
            editTimes[i] = averageMs(() -> sink += EditDistance.distance(a, b));
            md.append(String.format("| %d | %.3f | %.3f | %d | %d |%n", n, lcsTimes[i], editTimes[i],
                    LCS.length(a, b), EditDistance.distance(a, b)));
        }
        Plot.save("results/plot_dp.png", "LCS and Edit Distance", "string length n", sizes,
                new String[]{"LCS", "Edit Distance"}, new double[][]{lcsTimes, editTimes});

        int[] textLengths = {10_000, 50_000, 100_000};
        double[] xs = new double[textLengths.length];
        for (int i = 0; i < textLengths.length; i++) {
            xs[i] = textLengths[i];
        }
        String[] scenarios = {"present", "absent", "worst-case"};
        String[] names = {"Naive", "KMP", "Rabin-Karp"};
        md.append("\n## String matching (pattern length ").append(PATTERN_LENGTH).append(")\n");
        for (String scenario : scenarios) {
            md.append("\n### Scenario: ").append(scenario).append("\n\n");
            md.append("| text length | algorithm | time (ms) | comparisons | occurrences |\n");
            md.append("|---|---|---|---|---|\n");
            double[][] times = new double[3][textLengths.length];
            for (int i = 0; i < textLengths.length; i++) {
                int len = textLengths[i];
                String text = Generator.random(len, 42);
                String pattern;
                if (scenario.equals("present")) {
                    pattern = text.substring(len / 2, len / 2 + PATTERN_LENGTH);
                } else if (scenario.equals("absent")) {
                    long seed = 100;
                    pattern = Generator.random(PATTERN_LENGTH, seed);
                    while (text.contains(pattern)) {
                        seed++;
                        pattern = Generator.random(PATTERN_LENGTH, seed);
                    }
                } else {
                    text = "a".repeat(len);
                    pattern = "a".repeat(PATTERN_LENGTH - 1) + "b";
                }
                final String t = text;
                final String p = pattern;
                Matcher[] matchers = {new NaiveMatcher(), new KMP(), new RabinKarp()};
                List<Integer> reference = null;
                for (int k = 0; k < matchers.length; k++) {
                    Matcher matcher = matchers[k];
                    times[k][i] = averageMs(() -> sink += matcher.search(t, p).size());
                    List<Integer> found = matcher.search(t, p);
                    if (reference == null) {
                        reference = found;
                    } else if (!reference.equals(found)) {
                        throw new IllegalStateException("Algorithms disagree");
                    }
                    md.append(String.format("| %d | %s | %.3f | %d | %d |%n", len, names[k], times[k][i],
                            matcher.getComparisons(), found.size()));
                }
            }
            Plot.save("results/plot_matching_" + scenario + ".png", "String matching: " + scenario,
                    "text length", xs, names, times);
        }

        int[] kasaiSizes = {1000, 5000, 10000};
        double[] kx = new double[kasaiSizes.length];
        double[] kTimes = new double[kasaiSizes.length];
        md.append("\n## Kasai LCP\n\n| n | time (ms) |\n|---|---|\n");
        for (int i = 0; i < kasaiSizes.length; i++) {
            String s = Generator.random(kasaiSizes[i], 42);
            int[] sa = SuffixArray.build(s);
            kx[i] = kasaiSizes[i];
            kTimes[i] = averageMs(() -> sink += Kasai.buildLcp(s, sa)[1]);
            md.append(String.format("| %d | %.3f |%n", kasaiSizes[i], kTimes[i]));
        }
        Plot.save("results/plot_kasai.png", "Kasai LCP", "string length n", kx,
                new String[]{"Kasai"}, new double[][]{kTimes});

        Files.writeString(Paths.get("results/results.md"), md.toString());
        System.out.println(md);
    }
}
