package com.simonjamesrowe.anagramfinder;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class WordUtils {

    public static Map<Character, Long> countCharacters(final String word) {
        return word.chars()
            .mapToObj(c -> (char) c)
            .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    }
}
