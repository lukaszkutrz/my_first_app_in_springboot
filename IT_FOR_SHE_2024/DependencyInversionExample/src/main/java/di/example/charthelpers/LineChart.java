package di.example.charthelpers;

import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;


public class LineChart extends ApplicationFrame {

    public LineChart(ChartLabels labels, List<Integer> years, List<Float> data) {
        super(labels.title);
        DefaultCategoryDataset dataset = createDataset(years, data);

        JFreeChart lineChart = ChartFactory.createLineChart(labels.title, labels.xAxisLabel, 
                labels.yAxisLabel, dataset, PlotOrientation.VERTICAL, true, true, false);

        ChartPanel chartPanel = new ChartPanel(lineChart);
        chartPanel.setPreferredSize(new java.awt.Dimension(560, 367));
        setContentPane(chartPanel);
    }

    private DefaultCategoryDataset createDataset(List<Integer> years, List<Float> data) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        
        for(int i=0; i< years.size(); i++) {
            dataset.addValue(data.get(i), "dataset", years.get(i));
        }
 
        return dataset;
    }
 
}