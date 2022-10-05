package com.simonjamesrowe.anagramfinder;

import java.io.File;
import java.nio.file.Files;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
@RequiredArgsConstructor
public class AnagramCommandLineRunner implements CommandLineRunner {

    private final AnagramService anagramService;

    @Override
    public void run(final String... args) throws Exception {
        Assert.isTrue(args.length == 1, "Please ensure that the input file is provided");

        final File file = new File(args[0]);
        Assert.isTrue(file.exists(), args[0] + " Does not exist");

        try (final Stream<String> lines = Files.lines(file.toPath())) {
            anagramService.findAnagrams(lines);
        }
    }
}
