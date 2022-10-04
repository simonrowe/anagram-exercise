package com.simonjamesrowe.anagramfinder;

import java.io.File;
import java.nio.file.Files;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

@Service
public class AnagramService {

    private final Map<Map<Character, Long>, Set<String>> anagrams = new LinkedHashMap<>();

    @SneakyThrows
    public void findAnagrams(final File file) {
        final Stream<String> lines = Files.lines(file.toPath());
        lines.forEach(
            line -> {
                final Map<Character, Long> characterCount = WordUtils.countCharacters(line);

                Optional.ofNullable(anagrams.putIfAbsent(characterCount, new LinkedHashSet<>(List.of(line))))
                    .ifPresent(set -> set.add(line));

            });

        anagrams.values().forEach(groupOfAnagrams -> print(groupOfAnagrams));
    }

    private void print(final Set<String> groupOfAnagrams) {
        System.out.println(groupOfAnagrams
                               .stream()
                               .sorted()
                               .collect(Collectors.joining(",")));
    }
}
