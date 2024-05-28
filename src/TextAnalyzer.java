import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class TextAnalyzer {

    public static void main(String[] args) {
        String filePath = "C:/Users/filse/OneDrive/Pulpit/test.txt";

        try {
            String text = new String(Files.readAllBytes(Paths.get(filePath)));

            int totalWords = countWords(text);
            List<String> longestWords = findLongestWords(text);
            char mostCommonChar = findMostCommonChar(text);
            List<String> palindromes = findPalindromes(text);
            String encryptedText = transposeCipher(text);

            System.out.println("Liczba słów: " + totalWords);
            System.out.println("Najdłuższe słowo/a: " + String.join(", ", longestWords));
            System.out.println("Najczęściej występujący znak: " + mostCommonChar);
            System.out.println("Palindromy: " + String.join(", ", palindromes));

            // Zapis zaszyfrowanego tekstu do pliku
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("encrypted.txt"))) {
                writer.write(encryptedText);
            }
            System.out.println("Zaszyfrowany tekst został zapisany do pliku 'encrypted_text.txt'");
        } catch (IOException e) {
            System.out.println("Nie można otworzyć pliku: " + filePath);
            e.printStackTrace();
        }
    }

    public static int countWords(String text) {
        String[] words = text.split("\\s+");
        return words.length;
    }

    public static List<String> findLongestWords(String text) {
        String[] words = text.split("\\s+");
        int maxLength = Arrays.stream(words).mapToInt(String::length).max().orElse(0);
        return Arrays.stream(words).filter(word -> word.length() == maxLength).collect(Collectors.toList());
    }

    public static char findMostCommonChar(String text) {
        text = text.replaceAll("\\s+", "");
        text = text.replaceAll("\\p{Punct}", "");
        Map<Character, Long> charCount = text.chars()
                .mapToObj(c -> (char) c)
                .collect(Collectors.groupingBy(c -> c, Collectors.counting()));
        return Collections.max(charCount.entrySet(), Map.Entry.comparingByValue()).getKey();
    }

    public static List<String> findPalindromes(String text) {
        String[] words = text.split("\\s+");
        return Arrays.stream(words)
                .filter(TextAnalyzer::isPalindrome)
                .collect(Collectors.toList());
    }

    public static boolean isPalindrome(String word) {
        word = word.toLowerCase().replaceAll("\\p{Punct}", "");
        return new StringBuilder(word).reverse().toString().equals(word);
    }

    public static String transposeCipher(String text) {
        StringBuilder result = new StringBuilder();
        for (char c : text.toCharArray()) {
            if (Character.isLetter(c)) {
                if (Character.isUpperCase(c)) {
                    c = (char) ('A' + (c - 'A' + 3) % 26);
                } else {
                    c = (char) ('a' + (c - 'a' + 3) % 26);
                }
            }
            result.append(c);
        }
        return result.toString();
    }
}
