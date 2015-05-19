/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parsejava;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

/**
 *
 * @author vipinsharma
 */
public class DatabaseUtilities {
    public static void saveObjectInDatabase(FileStatistics fileStatistics) throws Exception
    {
        PreparedStatement insertRecord = null;
        Connection connect = null;
        int recordNumber;
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            connect = DriverManager.getConnection(
                    "jdbc:mysql://localhost/assignment?user=project&password=project");
            String insertStatement = "Insert into JAVA_FILE_ANALYSIS values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            insertRecord = connect.prepareStatement(insertStatement);
            recordNumber = getRecordNumber();
            insertRecord.setInt(1, recordNumber);
            insertRecord.setString(2, fileStatistics.getPackageName());
            insertRecord.setString(3, fileStatistics.getClassName());
            insertRecord.setInt(4, fileStatistics.getTotalKeywords());
            insertRecord.setInt(5, fileStatistics.getUniqKeywords());
            insertRecord.setInt(6, fileStatistics.getTotalIdentifiers());
            insertRecord.setInt(7, fileStatistics.getUniqIdentifiers());
            insertRecord.setInt(8, fileStatistics.getTotalConstants());
            insertRecord.setInt(9, fileStatistics.getUniqueConstants());
            insertRecord.setInt(10, fileStatistics.getTotalSpecialCharacters());
            insertRecord.setInt(11, fileStatistics.getUniqueSpecialCharacters());
            insertRecord.setInt(12, fileStatistics.getNumOfCharacters());
            insertRecord.setInt(13, fileStatistics.getWhiteSpaces());
            insertRecord.setInt(14, fileStatistics.getCommentCharacters());
            Timestamp currentTimestamp = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());
            insertRecord.setTimestamp(15, currentTimestamp);
            
//            insertRecord.setInt(14, 0);
//            insertRecord.setInt(15, 0);
            
            System.out.println(insertRecord.toString());
            
            int i = insertRecord.executeUpdate();

            System.out.println("Successfully inserted the record : " +i);
            //System.out.println(insertRecord.toString());
            
            String insertStatement1 = "Insert into FILE_METHOD_COMPLEXITY values (?,?,?)";
            PreparedStatement insertRecord1 = connect.prepareStatement(insertStatement1);
            HashMap<String,Integer> methodNamesAndComplexity = fileStatistics.getMethodNamesAndComplexity();
            for(String methodName : methodNamesAndComplexity.keySet() )
            {
                insertRecord1.setInt(1, recordNumber);
                insertRecord1.setString(2, methodName);
                insertRecord1.setInt(3, methodNamesAndComplexity.get(methodName));
                System.out.println(insertRecord1.toString());
                insertRecord1.executeUpdate();
            }
        }
        catch(Exception ex)
        {
            System.out.println("Exception encountered : " + ex.getMessage());
        }
        finally
        {
            connect.close();
            insertRecord.close();
        }
    }
    
    public static HashMap<String,HashMap> retrieveDataFromDatabase() throws Exception
    {
        HashMap<String,ArrayList> packageClassMapping = new HashMap<String,ArrayList>();
        HashMap<String,FileStatistics> classStatsMapping = new HashMap<String,FileStatistics>();
        HashMap<String,HashMap> tableData = new HashMap<String,HashMap>();
        Connection connect = null;
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            connect = DriverManager.getConnection(
                    "jdbc:mysql://localhost/assignment?user=project&password=project");
            String query = "select * from JAVA_FILE_ANALYSIS order by PACKAGE_NAME,CLASS_NAME;";
            Statement statement = connect.createStatement();
            ResultSet dataFromDB = statement.executeQuery(query);
            while(dataFromDB.next())
            {
                String packageName = dataFromDB.getString("PACKAGE_NAME");
                String className = dataFromDB.getString("CLASS_NAME");
                System.out.println(packageName + " " + className);
                ArrayList classList = new ArrayList();
                if(packageClassMapping.get(packageName) != null)
                {
                    classList = packageClassMapping.get(packageName);
                }
                classList.add(className);
                packageClassMapping.put(packageName, classList);
                
                //step 2:
                FileStatistics statsObject = new FileStatistics();
                statsObject.setPackageName(packageName);
                statsObject.setClassName(className);
                statsObject.setTotalKeywords(dataFromDB.getInt("KEYWORDS_TOTAL"));
                statsObject.setUniqKeywords(dataFromDB.getInt("KEYWORDS_UNIQUE"));
                statsObject.setTotalIdentifiers(dataFromDB.getInt("INDENTIFIERS_TOTAL"));
                statsObject.setUniqIdentifiers(dataFromDB.getInt("IDENTIFIERS_UNIQUE"));
                statsObject.setTotalConstants(dataFromDB.getInt("CONSTANTS_TOTAL"));
                statsObject.setUniqueConstants(dataFromDB.getInt("CONSTANTS_UNIQUE"));
                statsObject.setTotalSpecialCharacters(dataFromDB.getInt("SPECIAL_CHARS_TOTAL"));
                statsObject.setUniqueSpecialCharacters(dataFromDB.getInt("SPECIAL_CHARS_UNIQUE"));
                statsObject.setNumOfCharacters(dataFromDB.getInt("TOTAL_CHAR_IN_FILE"));
                statsObject.setWhiteSpaces(dataFromDB.getInt("WHITE_SPACES"));
                statsObject.setCommentCharacters(dataFromDB.getInt("COMMENT_CHARS"));
                
                //step 3:
                int recordNum = dataFromDB.getInt("RECORD_NUM");
                String cyclomaticComplexityQuery = "select * from FILE_METHOD_COMPLEXITY where RECORD_NUM = " + recordNum + ";";
                Statement statement1 = connect.createStatement();
                ResultSet cyclomaticComplexityDataFromDB = statement1.executeQuery(cyclomaticComplexityQuery);
                HashMap<String,Integer> methodNamesAndComplexity = new HashMap<String,Integer>();
                while(cyclomaticComplexityDataFromDB.next()){
                    methodNamesAndComplexity.put(cyclomaticComplexityDataFromDB.getString("METHOD_NAME"), cyclomaticComplexityDataFromDB.getInt("CYCLOMATIC_COMPLEXITY"));
                }
                statsObject.setMethodNamesAndComplexity(methodNamesAndComplexity);
                
                classStatsMapping.put(packageName+className, statsObject);
            }
            tableData.put("packageClassMapping",packageClassMapping);
            tableData.put("classStatsMapping", classStatsMapping);
            
        }
        catch(Exception ex)
        {
            System.out.println("Exception encountered in class DatabaseUtilities.java"
                +" Method retrieveDataFromDatabase" + ex.getMessage());
        }
        finally
        {
            connect.close();
        }
        return tableData;
    }
    
    private static int getRecordNumber()
    {
        Connection connect = null;
        int recordNum = 0;
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            connect = DriverManager.getConnection(
                    "jdbc:mysql://localhost/assignment?user=project&password=project");
            String query = "select max(RECORD_NUM) as MAX_RECORD from JAVA_FILE_ANALYSIS;";
            Statement statement = connect.createStatement();
            ResultSet dataFromDB = statement.executeQuery(query);
            if(dataFromDB.next())
                recordNum = dataFromDB.getInt("MAX_RECORD");
            System.out.println(recordNum);
        }
        catch(Exception e)
        {
            System.out.println("DatabaseUtilities -> getRecordNumber : " + e.getMessage());
            recordNum =0;
        }
        return recordNum+1;
    }
    
}
