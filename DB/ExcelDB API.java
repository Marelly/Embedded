

public class ExcelDB {

RM: I think you are missing the notion of a Table class and objects.
	You should define this class and use it everywhere.
	For example - when I open a table or create a table, I think I should get a Table object.
	If not, I should be able to work with table names in all methods.
	For example, the insertRow does not refer to a table so you don't know to which table the row is inserted.
	I can go on and on but I think you got the main idea.
	Two options:
	1. Work with Table objects
	2. Have the table name appear in all methods that refer to tables.
	
	As much as I write, although I like clean OO design, I think that in order to emulate better the work with a DB, we should just use the table name
	in all the methods and work with only one DB object which we will make as a singelton lik the MouseHandler.

K&A: As you requested we now have the table name in the functions siganture and the DB is singleton

    public static ExcelDB getInstance(){
	
	//returns a single instance of the DB

    }

    public void openTableFromExcel(String tableName){
    	
    	// work with table based on an Excel file that is already in the project directory
    	// looks for tableName.xls and creates a table based on it 
    	// if the file does not exist throw exception
    	
    }

    public void createTable(String tableName, String[] headings) throws Exception {
    	
    	// create new excel file in the directory and a new table based on it, the excel filename will be tableName.xls
    	// the first line will be the column headings according to the headings[] 
    	// throws exception if already exists such file in the project
    	
    }
RM: Do I commit all the changes to the DB in one commit or do I commit each table upon its changes?
    public void commit(){
    	
    	// commits all the tables in the DB
    	
    }


    public void insertRow(String tableName, String key, String[] values) throws Exception {
        // insert a new row to the table
    	// sets the key (the primary key) to the first column,  and values[] to the next columns
    	// the key must be exclusive  (there must not be a line in the excel with this key) 
    	// otherwise throws already exists exception
    	
    	// NOTICE: the changes will not be in the excel file until the commit() is executed
    }

    public void insertRow(String tableName, String[] row) throws Exception {
    	// insert a new row 
    	// sets the first element in the list (row[0]) to be the key (the primary key, the first column),  and the next values to the next columns in the table
    	// the key (row[0]) must be exclusive  (there must not be a line in the excel with this key) 
    	// otherwise throws already exists exception
    	
    	// NOTICE: the changes will not be in the excel file until the commit() is executed
    }

    public void updateRow(String tableName, String key, String[] values) throws Exception {
    	// updates a apecific row , the row that has the key, with the values[]
    	// if no such row exist throws exception
    	
    	// NOTICE: the changes will not be in the excel file until the commit() is executed
    }

    public void updateRow(String tableName, String[] row) throws Exception {
    	
    	// sets the first element in the list to be the key and the others as values 
    	// updates a apecific row , the row that has the key row[0] , with the values[]
    	// if no such row exist throws exception
     
    	// NOTICE: the changes will not be in the excel file until the commit() is executed
    }

    public void deleteRow(String tableName, String key){

        // deletes the desired row from the table 
        // if no such row exist throws exception

    }
    
    public String[] selectRow(String tableName, String key){
     
    	// returns the values of the line with the specific key 
    	// returns null if no such key exist
    	
    	}

    public String selectValue(String tableName, String key, int index){
    	
    	// returns the specific value of index in the line with the specific key 
    	// returns null if no such key exist
    	
    }

    public void sortByValue(String tableName, int index)
    {
        //sorts the table according to the value[index] column
    	// NOTICE: the changes will not be in the excel file until the commit() is executed
    }

    public void sortByKey(String tableName)
    {
        // sorts the table according to the first column (the primary key)
    	// NOTICE: the changes will not be in the excel file until the commit() is executed
    }


    public String[][] getTop(int top, int index){
        // returns a 2-dimentional array with the first TOP pairs of (key, value[index]) fom the table
        // a specific approach for getting the top score players 
    }

    public String[][] getTable(String tableName){
    	// returns the whole table in a 2-dimentional array
    }

}
