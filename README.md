# Anagram Finder
A simple command line utility for finding anagrams in a specified file

## Software required to run this
* Java 17

## Building and Running the tests
```
./gradlew clean build
```

## Running the program
```
./gradlew bootRun --args="example2.txt" 
```
where example2.txt is the text file that we want to search for anagrams

### Design Decisions
Spring Boot used for following reasons:
* Quick and Easy bootstrapping of a project, with test dependencies.
* IoC and dependency injection. 
* Nice utils like CommandLineRunner

Main Components:
--------

* [AnagramCommandLineRunner](src/main/java/com/simonjamesrowe/anagramfinder/AnagramCommandLineRunner.java)
validates program arguments then passes a Stream<String> to AnagramService to perform the orchestration of finding anagrams.
* [AnagramService](src/main/java/com/simonjamesrowe/anagramfinder/AnagramService.java), Streams through the lines in the file, filtering out any
empty or blank lines, and trimming whitespace. Uses AnagramRepository to add each words to an anagram group, and then once the word length has changed 
 write the resilts using AnagramResultWriter.
* [AnagramRepository](src/main/java/com/simonjamesrowe/anagramfinder/AnagramRepository.java) takes each word and counts frequency of each character in the word.
So for example abacba and acbbaa would be a=3,b=2, c=1). And we store this operation as the key in a Map in order to group anagrams together. Complexity of calculating charatcer frequency is O(n). I did consider
simply ordering sorting each word so abacba and acbbaa would be aaabbcc and using this as a key, but this would have been O (n log n), although maybe slightly easier to read.
* [AnagramResultWriter](src/main/java/com/simonjamesrowe/anagramfinder/AnagramResultWriter.java) writes results to System.out.
