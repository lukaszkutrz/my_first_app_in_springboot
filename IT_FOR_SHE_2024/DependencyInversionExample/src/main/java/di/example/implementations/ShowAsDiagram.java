package di.example.implementations;

import java.util.List;

import di.example.charthelpers.ChartLabels;
import di.example.charthelpers.LineChart;

public class ShowAsDiagram implements BookReadersPresentation {

    public void showAverageBooksPerYear(List<Float> booksPerReader, List<Integer> years) {
        ChartLabels labels = new ChartLabels();
        labels.title ="Amount of books per reader";
        labels.xAxisLabel ="Years";
        labels.yAxisLabel ="Avarage amount of books";
        
        LineChart chart = new LineChart(labels, years, booksPerReader);

        chart.pack();
        chart.setVisible(true);
    }

    public void showPercentOfPopulationThatNotReadAnyBook(List<Float> percentage, List<Integer> years) {
        ChartLabels labels = new ChartLabels();
        labels.title ="Population % that did not read any book per year";
        labels.xAxisLabel ="Years";
        labels.yAxisLabel ="Percentage of non-readers";
        
        LineChart chart = new LineChart(labels, years, percentage);

        chart.pack();
        chart.setVisible(true);        
    }
    
}
