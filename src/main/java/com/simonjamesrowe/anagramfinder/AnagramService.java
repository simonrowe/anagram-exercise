package com.simonjamesrowe.anagramfinder;

import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class AnagramService {

    private final AnagramRepository anagramRepository;
    private final AnagramResultWriter anagramResultWriter;

    @SneakyThrows
    public void findAnagrams(final Stream<String> lines) {
        final AtomicReference<Integer> lastWordLength = new AtomicReference<>(0);
        lines
            .filter(StringUtils::hasText)
            .map(StringUtils::trimWhitespace)
            .forEach(word -> {
                if (lastWordLength.get() > 0 && word.length() > lastWordLength.get()) {
                    anagramResultWriter.writeResults(anagramRepository.getAll());
                    anagramRepository.clear();
                }
                lastWordLength.set(word.length());
                anagramRepository.addWord(word);
            });

        anagramResultWriter.writeResults(anagramRepository.getAll());
    }


}
