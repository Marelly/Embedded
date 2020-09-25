package DB;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.*;

public class ExcelTable {

    private HashMap<String, String[]> excelMap = new HashMap<String, String[]>();
    private String filename;
    private String[] headings;
    private final String EMPTY_FILE = "Game.db";

    public ExcelTable(String tableName){
        try {
            filename = "db_tables\\" + tableName+".xlsx";
            File file = new File(filename);   //creating a new file instance
            FileInputStream fis = new FileInputStream(file);   //obtaining bytes from the file
            //creating Workbook instance that refers to .xlsx file
            XSSFWorkbook wb = new XSSFWorkbook(fis);
            XSSFSheet sheet = wb.getSheetAt(0);     //creating a Sheet object to retrieve object
            //iterating over excel file
            boolean isFirstRow = true;
            for (Row row : sheet) {
                Iterator<Cell> columnsItr = row.cellIterator();   //iterating over each column
                boolean isFirstColumn = true; // to separate the key from the values
                String key = null;
                String[] tempValues = new String[100];
                int i = 0;
                // iterating over the columns
                // mind that reading string is different func. from reading a number
                while (columnsItr.hasNext()) {
                    Cell cell = columnsItr.next();
                    if (isFirstColumn) {
                        isFirstColumn = false;
                        switch (cell.getCellType()) {
                            case STRING:    //field that represents string cell type
                                key = cell.getStringCellValue();
                                break;
                            case NUMERIC:    //field that represents number cell type
                                key = NumberToTextConverter.toText(cell.getNumericCellValue());
                                break;
                            default:
                        }
                    } else {
                        switch (cell.getCellType()) {
                            case STRING:    //field that represents string cell type
                                tempValues[i] = cell.getStringCellValue();
                                break;
                            case NUMERIC:    //field that represents number cell type
                                tempValues[i] = NumberToTextConverter.toText(cell.getNumericCellValue());
                                break;
                            default:
                        }
                        i++;
                    }
                }
                String[] values = new String[i];
                if (i >= 0) System.arraycopy(tempValues, 0, values, 0, i);
                if (isFirstRow){
                    String[] headings = new String[i+1];
                    headings[0] = key;
                    System.arraycopy(values,0,headings,1,values.length);
                    this.headings = headings;
                    isFirstRow = false;
                }
                else
                    excelMap.put(key, values);
            }
        }
        // if no excel file found or cant be accessed
        catch(Exception e)
        {
            e.printStackTrace();
            System.out.println("No file found or file is not accessible");
        }
    }

    public ExcelTable(String tableName, String[] headings) throws Exception {
        filename = "db_tables\\" + tableName+".xlsx";
        File file = new File(filename);   //creating a new file instance
        try {
            FileInputStream fis = new FileInputStream(file);
            throw new Exception("File is already exist!");
        } catch (FileNotFoundException e) {
            this.headings = headings;
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet(EMPTY_FILE);

            Row row = sheet.createRow(0);
            int columnCount = 0;
            for (String heading : headings) {
                Cell cell = row.createCell(columnCount++);
                cell.setCellValue(heading);
            }

            try (FileOutputStream outputStream = new FileOutputStream(filename)) {
                workbook.write(outputStream);
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        }
    }


    // insert a new row of key+values to the table
    // throws exception if the key is already exist
    public void insertRow(String[] row) throws Exception {
        String key = row[0];
        String[] values = new String[row.length-1];
        System.arraycopy(row, 1, values, 0, row.length-1);
        if (excelMap.putIfAbsent(key, values) != null)
            throw new Exception("Primary key already exist!");
    }

    // updates a row in the table
    // throws exception if the key is absent
    public void updateRow(String[] row) throws Exception {
        String key = row[0];
        String[] values = new String[row.length-1];
        System.arraycopy(row, 1, values, 0, row.length-1);
        if (excelMap.get(key) != null)
            excelMap.put(key, values);
        else
            throw new Exception("Can't find primary key in table!");
    }

    public String[] getFields(String key){
        String[] row = new String[headings.length];
        row[0] = key;
        System.arraycopy(excelMap.get(key), 0, row, 1, row.length-1);
        return row;
    }

    public String[] getFields(String key, int[] index){
        String[] row = new String[index.length+1];
        row[0] = key;
        for (int i = 0; i < index.length; i++) {
            row[i+1] = excelMap.get(key)[index[i]-1];
        }
        return row;
    }

    public void deleteRow(String key) {
        excelMap.remove(key);
    }

    public void deleteAllRows() {
        excelMap.clear();
    }

    // internal methods for sorting and comparing

    private <K, V extends Comparable<? super String[]>> Comparator<Map.Entry<String, String[]>> compareByKey() {
        return (Comparator<Map.Entry<String, String[]>> & Serializable)
                (c1, c2) -> c1.getKey().compareTo(c2.getKey());
    }

    private <K, V extends Comparable<? super String[]>> Comparator<Map.Entry<String, String[]>> compareStrByXValue(int index) {
        return (Comparator<Map.Entry<String, String[]>> & Serializable)
                (c1, c2) -> c1.getValue()[index].compareTo(c2.getValue()[index]);
    }

    private <K, V extends Comparable<? super String[]>> Comparator<Map.Entry<String, String[]>> compareIntByXValue(int index) {
        return (Comparator<Map.Entry<String, String[]>> & Serializable)
                (c1, c2) -> (Integer.valueOf(c1.getValue()[index])).compareTo( Integer.valueOf(c2.getValue()[index]));
    }

    private <K, V extends Comparable<? super String[]>> Comparator<Map.Entry<String, String[]>> compareIntByKey() {
        return (Comparator<Map.Entry<String, String[]>> & Serializable)
                (c1, c2) -> (Integer.valueOf(c1.getKey())).compareTo( Integer.valueOf(c2.getKey()));
    }


    // checks if a string is a number
    private boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");  //match a number with optional '-' and decimal.
    }

    // sort the table by the specified column
    // if the column is string the soft is from A to Z
    // if the column is numeric the sort depends on the isDescending parameter

    public void sortTable(int index){
        sortTable(index, false);
    }

    public void sortTable(int index, boolean isDescending)
    {
        // Create a list from elements of HashMap
        List<Map.Entry<String, String[]> > list = new LinkedList<Map.Entry<String, String[]> >(excelMap.entrySet());

        // Sort the list
        if (index == 0)
            if (isNumeric(list.get(0).getKey())) {
                list.sort(compareIntByKey());
            }
            else {
                list.sort(compareByKey());
            }
        else
            if (isNumeric(list.get(0).getValue()[index-1])) {
            list.sort(compareIntByXValue(index-1));
            // Reverse order to return items from high to low
            if (isDescending) Collections.reverse(list);
            }
            else
            list.sort(compareStrByXValue(index-1));

        // put data from sorted list to hashmap
        HashMap<String, String[]> tempMap = new LinkedHashMap<String, String[]>();
        for (Map.Entry<String, String[]> mapEntry : list) {
            tempMap.put(mapEntry.getKey(), mapEntry.getValue());
        }
        this.excelMap = tempMap;
    }

    // sort the table by the primary keys (A to Z)
    public void sortByKey() {
        sortTable(0, false);
    }


    // returns the table's data (without the headings) as a nXm matrix
    public String[][] getTableAsMatrix(){
        String[][] table = new String[excelMap.size()][headings.length];
        int rowCount = 0;
        for (String key : excelMap.keySet()) {
            table[rowCount] = getFields(key);
            rowCount++;

        }
        return table;
    }

    // returns the table's data of the key and the selected columns
    public String[][] getTableAsMatrix(int[] index){
        String[][] table = new String[excelMap.size()][index.length+1];
        int rowCount = 0;
        for (String key : excelMap.keySet()) {
            table[rowCount] = getFields(key, index);
            rowCount++;
        }
        return table;
    }

    public void WriteToFile() {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet(EMPTY_FILE);

        Row firstRow = sheet.createRow(0);
        int firstRowColumn = 0;
        for (String heading : headings) {
            Cell keyCell = firstRow.createCell(firstRowColumn);
            keyCell.setCellValue(heading);
            firstRowColumn++;
        }
        int rowCount = 1;

        for (String key : excelMap.keySet()) {
            Row row = sheet.createRow(rowCount++);

            int columnCount = 0;
            Cell keyCell = row.createCell(columnCount++);
            keyCell.setCellValue(key);
            for (String value : excelMap.get(key)) {
                Cell valueCell = row.createCell(columnCount++);
                valueCell.setCellValue(value);
            }

        }
        try (FileOutputStream outputStream = new FileOutputStream(filename)) {
            workbook.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
