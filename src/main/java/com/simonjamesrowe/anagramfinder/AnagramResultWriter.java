package com.simonjamesrowe.anagramfinder;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.stereotype.Component;

@Component
public class AnagramResultWriter {

    public void writeResults(final Stream<Set<String>> results) {
        results.forEach(this::print);
    }

    private void print(final Set<String> groupOfAnagrams) {
        if (groupOfAnagrams.isEmpty()) {
            return;
        }
        System.out.println(groupOfAnagrams
                               .stream()
                               .sorted()
                               .collect(Collectors.joining(",")));
    }
}
