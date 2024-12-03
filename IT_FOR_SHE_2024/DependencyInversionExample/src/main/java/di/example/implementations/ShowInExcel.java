package di.example.implementations;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ShowInExcel implements BookReadersPresentation {

    private static final String DESTINATION_PATH = "C:\\Users\\elukkut\\OneDrive - Ericsson\\Desktop\\MY_EXCEL\\sheetXXXXXXX.xlsx";
    private static final String PLACEHOLDER = "XXXXXXX";

    static int num = 1;

    public void showAverageBooksPerYear(List<Float> booksPerReader, List<Integer> years) {
        num++;
        saveData(booksPerReader, years, "showAverageBooksPerYear" + (num/2), "avg books per person in year");
    }

    public void showPercentOfPopulationThatNotReadAnyBook(List<Float> percentage, List<Integer> years) {
        num++;
        saveData(percentage, years, "showPercentOfPopulationThatNotReadAnyBook" + (num/2),
                "people who did not read book %");
    }

    private static void saveData(List<Float> yValues, List<Integer> xValues, String name, String yAxisDesc) {

        Workbook workbook = new XSSFWorkbook();

        Sheet sheet = workbook.createSheet("BookReaders");

        sheet.setColumnWidth(0, 2000);
        sheet.setColumnWidth(1, 7000);

        Row header = sheet.createRow(0);

        XSSFFont font = ((XSSFWorkbook) workbook).createFont();
        font.setFontName("Arial");

        Cell headerCell = header.createCell(0);
        headerCell.setCellValue("year");

        headerCell = header.createCell(1);
        headerCell.setCellValue(yAxisDesc);

        for (int i = 1; i < xValues.size(); i++) {
            Row row = sheet.createRow(i);
            Cell cell1 = row.createCell(0);
            cell1.setCellValue("" + xValues.get(i));
            Cell cell2 = row.createCell(1);
            cell2.setCellValue("" + yValues.get(i));
        }

        saveFile(workbook, DESTINATION_PATH.replace(PLACEHOLDER, name));
    }

    private static void saveFile(Workbook workbook, String path) {
        FileOutputStream outputStream;
        try {
            outputStream = new FileOutputStream(path);

            workbook.write(outputStream);
            workbook.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
