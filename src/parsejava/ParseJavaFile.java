/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parsejava;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Vector;
import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.CommonTokenStream;

/**
 *
 * @author vipinsharma
 */
public class ParseJavaFile {
    public static FileStatistics displayStats(String file)
    {
        try
        {
            ANTLRInputStream input = new ANTLRInputStream(new FileInputStream(file));
            JavaLexer lexer = new JavaLexer(input);
               
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            JavaParser parser = new JavaParser(tokens);
            parser.compilationUnit();
            
            HashMap<String,Vector> tokenMap = new HashMap<String,Vector>(lexer.hm);
            tokenMap.putAll(parser.hm);
            
            System.out.println("Size : " + parser.methodNamesAndComplexity.keySet().size());
            for(String methodName : parser.methodNamesAndComplexity.keySet())
                System.out.println(methodName);
//            Iterator iter = tokenMap.keySet().iterator();
//                
//            String key;
//                
//            while(iter.hasNext())
//            {
//                key = iter.next().toString();
//                System.out.println(key);
//                
//                Vector finalVector = tokenMap.get(key);
//            
//                for(int i=0;i<finalVector.size();i++)
//                {
//                    System.out.println(key + " : " + finalVector.get(i));
//                }
//            }
            
            Scanner input1 = new Scanner(new FileInputStream(file));
//            File trial = new File(file);
//            System.out.println("=========" + trial.length());
            int number_of_lines = 0,length = 0;
            String line;

            while (input1.hasNextLine()) 
            {
                line = input1.nextLine();
                //System.out.println(length + " : " + line.toLowerCase());
                length += line.length();
                number_of_lines++;
            }

            System.out.println("total num of characters : " + length);
            System.out.println("total number of lines : " + number_of_lines);
            System.out.println("total number of spaces : " + lexer.whiteSpaces);
            
            //Set value in object
            FileStatistics fileStatistics = new FileStatistics();
            fileStatistics.setPackageName(parser.packageName);
            fileStatistics.setClassName(parser.className);
            fileStatistics.setWhiteSpaces(lexer.whiteSpaces);
            fileStatistics.setCommentCharacters(lexer.comment);
            fileStatistics.setMethodNamesAndComplexity(parser.methodNamesAndComplexity);
            fileStatistics.setNumOfLines(number_of_lines);
            fileStatistics.setNumOfCharacters(length);
            fileStatistics.setFileTokens(tokenMap);
            
            System.out.println("Save record to datacase");
            
            //Uncomment to save results into database
            DatabaseUtilities.saveObjectInDatabase(fileStatistics);
            
            //fileStatistics.displayStatistics();
            
            return fileStatistics;
        } 
        catch (Exception ex) 
        {
            System.out.println(ex.getStackTrace() + ex.getMessage());
        }
        return null;
    }
}
