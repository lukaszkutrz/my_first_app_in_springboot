package di.example.implementations;

import java.util.List;

public class ShowAsText implements BookReadersPresentation {

    public void showAverageBooksPerYear(List<Float> booksPerReader, List<Integer> years) {

        System.out.println("Average amount of read books (per person): ");

        for (int i = 0; i < years.size(); i++) {
            System.out.println(years.get(i) + " -> " + booksPerReader.get(i));
        }
        System.out.println("===================================");
    }

    public void showPercentOfPopulationThatNotReadAnyBook(List<Float> percentage, List<Integer> years) {

        System.out.println("Percentage of Population that did not read any book: ");
        for (int i = 0; i < years.size(); i++) {
            System.out.println(years.get(i) + " -> " + percentage.get(i));
        }
        System.out.println("===================================");
    }

}
