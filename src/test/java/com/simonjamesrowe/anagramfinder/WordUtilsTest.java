package com.simonjamesrowe.anagramfinder;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class WordUtilsTest {

    @Test
    void shouldCountCharacters() {
        final var result = WordUtils.countCharacters("aardvark");
        assertThat(result).hasSize(5);
        assertThat(result.get('a')).isEqualTo(3L);
        assertThat(result.get('r')).isEqualTo(2L);
        assertThat(result.get('d')).isEqualTo(1L);
        assertThat(result.get('v')).isEqualTo(1L);
        assertThat(result.get('k')).isEqualTo(1L);
    }

}