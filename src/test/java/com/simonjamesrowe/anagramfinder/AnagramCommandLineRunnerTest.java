package com.simonjamesrowe.anagramfinder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.File;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AnagramCommandLineRunnerTest {

    @Mock
    AnagramService anagramService;

    @InjectMocks
    AnagramCommandLineRunner anagramCommandLineRunner;

    @Test
    void shouldThrowExceptionWhenNoArgumentsPresent() {
        final var exception = assertThrows(Exception.class, () -> anagramCommandLineRunner.run());
        assertThat(exception.getMessage()).isEqualTo("Please ensure that the input file is provided");
        verify(anagramService, never()).findAnagrams(any());
    }

    @Test
    void shouldThrowExceptionWhenMoreThanOneArgumentIsPassed() {
        final var exception = assertThrows(Exception.class, () -> anagramCommandLineRunner.run("one", "two"));
        assertThat(exception.getMessage()).isEqualTo("Please ensure that the input file is provided");
        verify(anagramService, never()).findAnagrams(any());
    }

    @Test
    void shouldThrowExceptionWhenInputFileDoesNotExist() {
        final var exception = assertThrows(Exception.class, () -> anagramCommandLineRunner.run("notExists"));
        assertThat(exception.getMessage()).isEqualTo("notExists Does not exist");
        verify(anagramService, never()).findAnagrams(any());
    }

    @Test
    @SneakyThrows
    void shouldFindAnagramsWhenFileExists() {
        final String testFilePath = "src/test/resources/example1.txt";
        anagramCommandLineRunner.run(testFilePath);
        final ArgumentCaptor<File> argumentCaptor = ArgumentCaptor.forClass(File.class);
        verify(anagramService, times(1)).findAnagrams(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue().getName()).isEqualTo("example1.txt");
    }

}