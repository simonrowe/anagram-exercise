package com.simonjamesrowe.anagramfinder;

import java.io.File;
import java.nio.file.Files;
import java.util.Set;
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
        lines
            .filter(StringUtils::hasText)
            .forEach(anagramRepository::add);

        anagramRepository.getAll().forEach(this::print);
    }

    private void print(final Set<String> groupOfAnagrams) {
        System.out.println(groupOfAnagrams
                               .stream()
                               .sorted()
                               .collect(Collectors.joining(",")));
    }
}
