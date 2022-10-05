package com.simonjamesrowe.anagramfinder;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;

@ExtendWith(OutputCaptureExtension.class)
class AnagramResultWriterTest {

    final AnagramResultWriter anagramResultWriter = new AnagramResultWriter();

    @Test
    void shouldWriteResults(final CapturedOutput capturedOutput) {
        final LinkedHashSet<String> line1 = new LinkedHashSet<>(List.of("ab", "ba"));
        final LinkedHashSet<String> line2 = new LinkedHashSet<>(List.of("ggh", "ghg", "hgg"));
        final LinkedHashSet<String> line3 = new LinkedHashSet<>(List.of("adwq"));

        anagramResultWriter.writeResults(Stream.of(line1, line2, line3));
        assertThat(capturedOutput.getOut()).contains("ab,ba");
        assertThat(capturedOutput.getOut()).contains("ggh,ghg,hgg");
        assertThat(capturedOutput.getOut()).contains("adwq");
    }

    @Test
    void shouldNotWriteResultsForEmptyStream(final CapturedOutput capturedOutput) {
        anagramResultWriter.writeResults(Stream.of());
        assertThat(capturedOutput).isBlank();
    }
}