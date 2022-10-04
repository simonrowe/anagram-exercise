package com.simonjamesrowe.anagramfinder;

import java.io.File;
import java.nio.file.Files;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class AnagramService {

    private final AnagramRepository anagramRepository;

    @SneakyThrows
    public void findAnagrams(final File file) {
        final Stream<String> lines = Files.lines(file.toPath());
        final AtomicReference<Integer> lastWordLength = new AtomicReference<>(0);
        lines
            .filter(StringUtils::hasText)
            .map(StringUtils::trimWhitespace)
            .forEach(word -> {
                if (lastWordLength.get() > 0 && word.length() > lastWordLength.get()) {
                    anagramRepository.getAll().forEach(this::print);
                    anagramRepository.clear();
                }
                anagramRepository.add(word);
            });

        anagramRepository.getAll().forEach(this::print);
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
