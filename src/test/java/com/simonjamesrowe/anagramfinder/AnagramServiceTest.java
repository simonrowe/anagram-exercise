package com.simonjamesrowe.anagramfinder;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Set;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AnagramServiceTest {

    @Mock
    AnagramRepository anagramRepository;

    @Mock
    AnagramResultWriter anagramResultWriter;

    @InjectMocks
    AnagramService anagramService;

    @Test
    void shouldFindAnagrams() {
        final Stream<String> input = Stream.of("abc", "bac", "cab", "aaa");
        final Stream<Set<String>> results = Stream.of(Set.of("abc", "bac", "cab"), Set.of("aaa"));
        doReturn(results).when(anagramRepository).getAll();

        anagramService.findAnagrams(input);

        verify(anagramRepository).addWord("abc");
        verify(anagramRepository).addWord("bac");
        verify(anagramRepository).addWord("cab");

        verify(anagramResultWriter).writeResults(results);
    }

    @Test
    void shouldIgnoreBlankLines() {
        final Stream<String> input = Stream.of("abc", "bac", "cab", "aaa", null, "", " ");
        final Stream<Set<String>> results = Stream.of(Set.of("abc", "bac", "cab"), Set.of("aaa"));
        doReturn(results).when(anagramRepository).getAll();

        anagramService.findAnagrams(input);

        verify(anagramRepository).addWord("abc");
        verify(anagramRepository).addWord("bac");
        verify(anagramRepository).addWord("cab");
        verify(anagramRepository).addWord("aaa");
        verify(anagramRepository, never()).addWord("");
        verify(anagramRepository, never()).addWord(null);

        verify(anagramResultWriter).writeResults(results);
    }

    @Test
    void shouldTrimWhitespace() {
        final Stream<String> input = Stream.of("abc", " bac", "cab  ", " aaa", null, "", " ");
        final Stream<Set<String>> results = Stream.of(Set.of("abc", "bac", "cab"), Set.of("aaa"));
        doReturn(results).when(anagramRepository).getAll();

        anagramService.findAnagrams(input);

        verify(anagramRepository).addWord("abc");
        verify(anagramRepository).addWord("bac");
        verify(anagramRepository).addWord("cab");
        verify(anagramRepository).addWord("aaa");
        verify(anagramRepository, never()).addWord("");
        verify(anagramRepository, never()).addWord(null);

        verify(anagramResultWriter).writeResults(results);
    }

    @Test
    void shouldWriteResultsWhenWordLengthChanges() {
        final Stream<String> input = Stream.of("abc", "bac", "cab", "aaa", "bega", "bigger");
        final Stream<Set<String>> threeLetterResults = Stream.of(Set.of("abc", "bac", "cab"), Set.of("aaa"));
        final Stream<Set<String>> fourLetterResults = Stream.of(Set.of("bega"));
        final Stream<Set<String>> fiveLetterResults = Stream.of(Set.of("bigger"));
        doReturn(threeLetterResults, fourLetterResults, fiveLetterResults).when(anagramRepository).getAll();

        anagramService.findAnagrams(input);

        verify(anagramRepository).addWord("abc");
        verify(anagramRepository).addWord("bac");
        verify(anagramRepository).addWord("cab");
        verify(anagramResultWriter).writeResults(threeLetterResults);

        verify(anagramRepository).addWord("bega");
        verify(anagramResultWriter).writeResults(fourLetterResults);
        verify(anagramRepository).addWord("bigger");
        verify(anagramResultWriter).writeResults(fiveLetterResults);
        verify(anagramRepository, times(2)).clear();

    }
}