package di.example.runners;

import java.util.Arrays;
import java.util.List;

import di.example.implementations.ShowAsText; 

public class MainBadCode {

public static void main(String[] args) {
        
        ShowAsText dataPresenter = new ShowAsText();

        showDataAboutBooksPerPersonForEachYear(dataPresenter);
        showDataAboutPersonsWhoHaventReadAnyBook(dataPresenter);
        showDataAboutBooksPerPersonForEachYearDataSet2(dataPresenter);
        showDataAboutPersonsWhoHaventReadAnyBookDataSet2(dataPresenter);
    }
    
    public static void showDataAboutBooksPerPersonForEachYear(ShowAsText readersData) {
        List<Float> avgBooksPerYear = Arrays.asList(3.0f, 2.0f, 2.4f, 2.2f, 1.9f, 1.9f, 1.8f, 1.7f, 2.8f, 3.0f, 3.7f, 3.2f);
        List<Integer> year = Arrays.asList(2010, 2011, 2012, 2013, 2014, 2015, 2016, 2017, 2018, 2019, 2020, 2021);

        readersData.showAverageBooksPerYear(avgBooksPerYear, year);
    }

    public static void showDataAboutPersonsWhoHaventReadAnyBook(ShowAsText readersData) {
        List<Float> percentageWhoDidNotReadBook = Arrays.asList(50.0f, 35.0f, 52.0f, 57.0f, 48.9f, 41.9f, 60.8f, 66.2f,
                60.8f, 60.0f, 59.0f, 58.0f);
        List<Integer> year = Arrays.asList(2010, 2011, 2012, 2013, 2014, 2015, 2016, 2017, 2018, 2019, 2020, 2021);

        readersData.showPercentOfPopulationThatNotReadAnyBook(percentageWhoDidNotReadBook, year);
    }
    
    public static void showDataAboutBooksPerPersonForEachYearDataSet2(ShowAsText readersData) {
        List<Float> avgBooksPerYear = Arrays.asList(3.0f, 2.0f, 2.4f, 1.2f, 1.9f, 1.9f, 1.8f, 1.7f, 2.8f, 2.0f, 1.7f, 2.2f);
        List<Integer> year = Arrays.asList(2010, 2011, 2012, 2013, 2014, 2015, 2016, 2017, 2018, 2019, 2020, 2021);

        readersData.showAverageBooksPerYear(avgBooksPerYear, year);
    }

    public static void showDataAboutPersonsWhoHaventReadAnyBookDataSet2(ShowAsText readersData) {
        List<Float> percentageWhoDidNotReadBook = Arrays.asList(50.0f, 45.0f, 52.0f, 57.0f, 48.9f, 31.9f, 60.8f, 66.2f,
                60.8f, 60.0f, 59.0f, 58.0f);
        List<Integer> year = Arrays.asList(2010, 2011, 2012, 2013, 2014, 2015, 2016, 2017, 2018, 2019, 2020, 2021);

        readersData.showPercentOfPopulationThatNotReadAnyBook(percentageWhoDidNotReadBook, year);
    }
}
