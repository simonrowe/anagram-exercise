package com.simonjamesrowe.anagramfinder;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;
import org.springframework.stereotype.Repository;

@Repository
public class AnagramRepository {

    private final Map<Map<Character, Long>, Set<String>> anagrams = new LinkedHashMap<>();

    public void addWord(final String word) {
        final Map<Character, Long> characterCount = WordUtils.countCharacters(word);
        Optional.ofNullable(anagrams.putIfAbsent(characterCount, new LinkedHashSet<>(List.of(word))))
            .ifPresent(set -> set.add(word));
    }

    public void clear() {
        anagrams.clear();
    }

    public Stream<Set<String>> getAll() {
        return anagrams.values().stream();
    }


}
