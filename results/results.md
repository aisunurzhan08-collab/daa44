## LCS and Edit Distance

| n | LCS (ms) | Edit Distance (ms) | LCS length | Edit distance |
|---|---|---|---|---|
| 100 | 1.658 | 2.200 | 63 | 56 |
| 500 | 2.903 | 3.735 | 329 | 261 |
| 1000 | 12.404 | 11.852 | 657 | 520 |

## String matching (pattern length 50)

### Scenario: present

| text length | algorithm | time (ms) | comparisons | occurrences |
|---|---|---|---|---|
| 10000 | Naive | 1.512 | 13428 | 1 |
| 10000 | KMP | 1.369 | 12574 | 1 |
| 10000 | Rabin-Karp | 1.407 | 50 | 1 |
| 50000 | Naive | 2.198 | 66504 | 1 |
| 50000 | KMP | 1.905 | 62441 | 1 |
| 50000 | Rabin-Karp | 2.641 | 50 | 1 |
| 100000 | Naive | 1.529 | 133380 | 1 |
| 100000 | KMP | 1.498 | 124967 | 1 |
| 100000 | Rabin-Karp | 2.268 | 50 | 1 |

### Scenario: absent

| text length | algorithm | time (ms) | comparisons | occurrences |
|---|---|---|---|---|
| 10000 | Naive | 0.082 | 13293 | 0 |
| 10000 | KMP | 0.097 | 12436 | 0 |
| 10000 | Rabin-Karp | 0.179 | 0 | 0 |
| 50000 | Naive | 0.308 | 66694 | 0 |
| 50000 | KMP | 0.354 | 61837 | 0 |
| 50000 | Rabin-Karp | 0.875 | 0 | 0 |
| 100000 | Naive | 0.630 | 132976 | 0 |
| 100000 | KMP | 0.705 | 123385 | 0 |
| 100000 | Rabin-Karp | 1.750 | 0 | 0 |

### Scenario: worst-case

| text length | algorithm | time (ms) | comparisons | occurrences |
|---|---|---|---|---|
| 10000 | Naive | 2.103 | 497550 | 0 |
| 10000 | KMP | 0.084 | 20048 | 0 |
| 10000 | Rabin-Karp | 0.182 | 0 | 0 |
| 50000 | Naive | 10.219 | 2497550 | 0 |
| 50000 | KMP | 0.333 | 100048 | 0 |
| 50000 | Rabin-Karp | 0.894 | 0 | 0 |
| 100000 | Naive | 12.014 | 4997550 | 0 |
| 100000 | KMP | 0.859 | 200048 | 0 |
| 100000 | Rabin-Karp | 1.788 | 0 | 0 |

## Kasai LCP

| n | time (ms) |
|---|---|
| 1000 | 0.292 |
| 5000 | 1.005 |
| 10000 | 0.714 |
