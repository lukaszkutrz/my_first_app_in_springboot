package di.example.implementations;

import java.util.List;

public interface BookReadersPresentation {

    public void showAverageBooksPerYear(List<Float> booksPerReader, List<Integer> years);

    public void showPercentOfPopulationThatNotReadAnyBook(List<Float> percentage, 
            List<Integer> years);

}
