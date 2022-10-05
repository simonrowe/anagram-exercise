package com.simonjamesrowe.anagramfinder;


import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;
import org.junit.jupiter.api.Test;

class AnagramRepositoryTest {

    final AnagramRepository anagramRepository = new AnagramRepository();

    @Test
    void shouldAddAnagram() {
        anagramRepository.addWord("hello");

        assertThat(anagramRepository.getAll()).contains(Set.of("hello"));

        anagramRepository.addWord("yolo");

        assertThat(anagramRepository.getAll()).contains(Set.of("hello"), Set.of("yolo"));

        anagramRepository.addWord("olleh");

        assertThat(anagramRepository.getAll()).contains(Set.of("hello", "olleh"), Set.of("yolo"));

        anagramRepository.addWord("llohe");

        assertThat(anagramRepository.getAll()).contains(Set.of("hello", "olleh", "llohe"), Set.of("yolo"));
    }

    @Test
    void shouldClearAnagrams() {
        shouldAddAnagram();
        anagramRepository.clear();
        assertThat(anagramRepository.getAll()).isEmpty();
    }

}