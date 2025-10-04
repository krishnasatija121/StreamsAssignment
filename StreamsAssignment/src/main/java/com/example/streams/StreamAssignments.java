package com.example.streams;

import com.example.streams.model.*;
import java.util.*;
import java.util.stream.*;
import static java.util.stream.Collectors.*;

public class StreamAssignments {
    public static void main(String[] args) {
        List<Fruit> fruits = Arrays.asList(
            new Fruit("Apple", 95, 120, "Red"),
            new Fruit("Banana", 105, 30, "Yellow"),
            new Fruit("Cherry", 50, 200, "Red"),
            new Fruit("Orange", 85, 60, "Orange"),
            new Fruit("Grapes", 70, 90, "Green")
        );

        List<News> newsList = Arrays.asList(
            new News(1, "Alice", "Bob", "The budget is great"),
            new News(1, "Alice", "Charlie", "I love this budget"),
            new News(2, "David", "Eve", "No budget this year"),
            new News(3, "Frank", "Bob", "Interesting news"),
            new News(3, "Frank", "Charlie", "Budget again?")
        );

        List<Trader> traders = Arrays.asList(
            new Trader("Krishna", "Pune"),
            new Trader("Arjun", "Delhi"),
            new Trader("Radha", "Pune"),
            new Trader("Meera", "Indore")
        );

        List<Transaction> transactions = Arrays.asList(
            new Transaction(traders.get(0), 2011, 300),
            new Transaction(traders.get(1), 2012, 1000),
            new Transaction(traders.get(2), 2011, 400),
            new Transaction(traders.get(3), 2012, 710),
            new Transaction(traders.get(1), 2011, 700)
        );

        // 1
        fruits.stream()
            .filter(f -> f.getCalories() < 100)
            .sorted(Comparator.comparingInt(Fruit::getCalories).reversed())
            .map(Fruit::getName)
            .forEach(System.out::println);

        // 2
        fruits.stream()
            .collect(groupingBy(Fruit::getColor, mapping(Fruit::getName, toList())))
            .forEach((color, names) -> System.out.println(color + ": " + names));

        // 3
        fruits.stream()
            .filter(f -> f.getColor().equalsIgnoreCase("Red"))
            .sorted(Comparator.comparingInt(Fruit::getPrice))
            .forEach(System.out::println);

        // 4
        newsList.stream()
            .collect(groupingBy(News::getNewsId, counting()))
            .entrySet().stream()
            .max(Map.Entry.comparingByValue())
            .ifPresent(e -> System.out.println("Max comments newsId: " + e.getKey()));

        // 5
        long budgetCount = newsList.stream()
            .filter(n -> n.getComment().toLowerCase().contains("budget"))
            .count();
        System.out.println("Budget word count: " + budgetCount);

        // 6
        newsList.stream()
            .collect(groupingBy(News::getCommentByUser, counting()))
            .entrySet().stream()
            .max(Map.Entry.comparingByValue())
            .ifPresent(e -> System.out.println("Most comments by: " + e.getKey()));

        // 7
        newsList.stream()
            .collect(groupingBy(News::getCommentByUser, counting()))
            .forEach((user, count) -> System.out.println(user + ": " + count));

        // 8
        transactions.stream()
            .filter(t -> t.getYear() == 2011)
            .sorted(Comparator.comparingInt(Transaction::getValue))
            .forEach(System.out::println);

        // 9
        traders.stream()
            .map(Trader::getCity)
            .distinct()
            .forEach(System.out::println);

        // 10
        traders.stream()
            .filter(t -> t.getCity().equalsIgnoreCase("Pune"))
            .sorted(Comparator.comparing(Trader::getName))
            .forEach(System.out::println);

        // 11
        String traderNames = traders.stream()
            .map(Trader::getName)
            .sorted()
            .collect(Collectors.joining(", "));
        System.out.println("Traders: " + traderNames);

        // 12
        boolean anyIndore = traders.stream()
            .anyMatch(t -> t.getCity().equalsIgnoreCase("Indore"));
        System.out.println("Any trader in Indore? " + anyIndore);

        // 13
        transactions.stream()
            .filter(t -> t.getTrader().getCity().equalsIgnoreCase("Delhi"))
            .map(Transaction::getValue)
            .forEach(System.out::println);

        // 14
        transactions.stream()
            .map(Transaction::getValue)
            .max(Integer::compare)
            .ifPresent(v -> System.out.println("Highest value: " + v));

        // 15
        transactions.stream()
            .min(Comparator.comparingInt(Transaction::getValue))
            .ifPresent(System.out::println);
    }
}