package ru.netology.murin.multithreading.lesson3.task1;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;


//Задача 1. Генерация никнеймов
public class Main {

    private static final AtomicInteger fine3letters = new AtomicInteger(0);
    private static final AtomicInteger fine4letters = new AtomicInteger(0);
    private static final AtomicInteger fine5letters = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {


        Random random = new Random();
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }

        Thread t1 = new Thread(() -> countSameLettersTexts(texts));
        Thread t2 = new Thread(() -> countLettersIncreasingTexts(texts));
        Thread t3 = new Thread(() -> countPalyndromTexts(texts));

        t1.start();
        t2.start();
        t3.start();

        t1.join();
        t2.join();
        t3.join();

        System.out.println("Красивых слов с длиной 3: " + fine3letters.get() + " шт");
        System.out.println("Красивых слов с длиной 4: " + fine4letters.get() + " шт");
        System.out.println("Красивых слов с длиной 5: " + fine5letters.get() + " шт");
    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

    private static void countSameLettersTexts(String[] texts) {
        for (String s : texts) {
            if (isSameLetter(s)) {
                switch (s.length()) {
                    case 3:
                        fine3letters.incrementAndGet();
                        break;
                    case 4:
                        fine4letters.incrementAndGet();
                        break;
                    case 5:
                        fine5letters.incrementAndGet();
                        break;
                }
            }
        }
    }

    private static void countLettersIncreasingTexts(String[] texts) {
        for (String s : texts) {
            if(isSameLetter(s)){
                continue;
            }

            boolean matches = true;

            for (int i = 1; i < s.length(); i++) {
                if (s.charAt(i) < s.charAt(i - 1)) {
                    matches = false;
                    break;
                }
            }

            if (matches) {
                switch (s.length()) {
                    case 3:
                        fine3letters.incrementAndGet();
                        break;
                    case 4:
                        fine4letters.incrementAndGet();
                        break;
                    case 5:
                        fine5letters.incrementAndGet();
                        break;
                }
            }
        }
    }

    private static void countPalyndromTexts(String[] texts) {
        for (String s : texts) {
            if(isSameLetter(s)){
                continue;
            }

            boolean matches = true;

            int left = 0;
            int right = s.length() - 1;
            while (left < right) {
                if (s.charAt(left) != s.charAt(right)) {
                    matches = false;
                    break;
                }
                left++;
                right--;
            }


            if (matches) {
                switch (s.length()) {
                    case 3:
                        fine3letters.incrementAndGet();
                        break;
                    case 4:
                        fine4letters.incrementAndGet();
                        break;
                    case 5:
                        fine5letters.incrementAndGet();
                        break;
                }
            }
        }
    }

    private static boolean isSameLetter(String s) {
        for (char c : s.toCharArray()) {
            if (c != s.charAt(0)) {
                return false;

            }
        }
        return true;
    }
}
