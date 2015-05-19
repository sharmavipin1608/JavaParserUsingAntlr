/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parsejava;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

/**
 *
 * @author vipinsharma
 */
public class FileStatistics {
    private String packageName;
    private String className;
    private int whiteSpaces = 0;
    private int numOfLines = 0;
    private int numOfCharacters = 0;
    private HashMap<String,Vector> fileTokens;
    private int totalKeywords = 0;
    private int uniqKeywords = 0;
    private int totalConstants = 0;
    private int uniqueConstants = 0;
    private int totalIdentifiers = 0;
    private int uniqIdentifiers = 0;
    private int totalSpecialCharacters = 0;
    private int uniqueSpecialCharacters = 0;
    private int commentCharacters = 0;
    private float percentageWhiteSpaces = 0;
    private float percentageCommentCharacters = 0;
    private HashMap<String,Integer> methodNamesAndComplexity = new HashMap<String,Integer>();

    public HashMap<String, Integer> getMethodNamesAndComplexity() {
        return methodNamesAndComplexity;
    }

    public void setMethodNamesAndComplexity(HashMap<String, Integer> methodNamesAndComplexity) {
        this.methodNamesAndComplexity = methodNamesAndComplexity;
    }

    public float getPercentageWhiteSpaces() {
        percentageWhiteSpaces = ((float)whiteSpaces/(float)numOfCharacters)*100;
        return percentageWhiteSpaces;
    }

    public float getPercentageCommentCharacters() {
        percentageCommentCharacters = ((float)commentCharacters/(float)numOfCharacters)*100;
        return percentageCommentCharacters;
    }

    public void setTotalKeywords(int totalKeywords) {
        this.totalKeywords = totalKeywords;
    }

    public void setUniqKeywords(int uniqKeywords) {
        this.uniqKeywords = uniqKeywords;
    }

    public void setTotalConstants(int totalConstants) {
        this.totalConstants = totalConstants;
    }

    public void setTotalIdentifiers(int totalIdentifiers) {
        this.totalIdentifiers = totalIdentifiers;
    }

    public void setUniqIdentifiers(int uniqIdentifiers) {
        this.uniqIdentifiers = uniqIdentifiers;
    }

    public void setTotalSpecialCharacters(int totalSpecialCharacters) {
        this.totalSpecialCharacters = totalSpecialCharacters;
    }

    public void setUniqueSpecialCharacters(int uniqueSpecialCharacters) {
        this.uniqueSpecialCharacters = uniqueSpecialCharacters;
    }

    public int getCommentCharacters() {
        return commentCharacters;
    }

    public void setCommentCharacters(int commentCharacters) {
        this.commentCharacters = commentCharacters;
    }
    

    public int getUniqueConstants() {
        return uniqueConstants;
    }

    public void setUniqueConstants(int uniqueConstants) {
        this.uniqueConstants = uniqueConstants;
    }
    
    /**
     * @return the packageName
     */
    public String getPackageName() {
        return packageName;
    }

    /**
     * @param packageName the packageName to set
     */
    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    /**
     * @return the className
     */
    public String getClassName() {
        return className;
    }

    /**
     * @param className the className to set
     */
    public void setClassName(String className) {
        this.className = className;
    }

    /**
     * @return the whiteSpaces
     */
    public int getWhiteSpaces() {
        return whiteSpaces;
    }

    /**
     * @param whiteSpaces the whiteSpaces to set
     */
    public void setWhiteSpaces(int whiteSpaces) {
        this.whiteSpaces = whiteSpaces;
    }

    /**
     * @return the numOfLines
     */
    public int getNumOfLines() {
        return numOfLines;
    }

    /**
     * @param numOfLines the numOfLines to set
     */
    public void setNumOfLines(int numOfLines) {
        this.numOfLines = numOfLines;
    }

    /**
     * @return the numOfCharacters
     */
    public int getNumOfCharacters() {
        return numOfCharacters;
    }

    /**
     * @param numOfCharacters the numOfCharacters to set
     */
    public void setNumOfCharacters(int numOfCharacters) {
        this.numOfCharacters = numOfCharacters;
    }

    /**
     * @return the fileTokens
     */
    public HashMap<String,Vector> getFileTokens() {
        return fileTokens;
    }
    
    /**
     * @param fileTokens the fileTokens to set
     */
    public void setFileTokens(HashMap<String,Vector> fileTokens) {
        this.fileTokens = fileTokens;
        distributeKeys();
        displayStatistics();
    }
    
    /**
     * @return the totalKeywords
     */
    public int getTotalKeywords() {
        return totalKeywords;
    }

    /**
     * @return the uniqKeywords
     */
    public int getUniqKeywords() {
        return uniqKeywords;
    }

    /**
     * @return the totalConstants
     */
    public int getTotalConstants() {
        return totalConstants;
    }

    /**
     * @return the totalIdentifiers
     */
    public int getTotalIdentifiers() {
        return totalIdentifiers;
    }

    /**
     * @return the uniqIdentifiers
     */
    public int getUniqIdentifiers() {
        return uniqIdentifiers;
    }

    /**
     * @return the totalSpecialCharacters
     */
    public int getTotalSpecialCharacters() {
        return totalSpecialCharacters;
    }

    /**
     * @return the uniqueSpecialCharacters
     */
    public int getUniqueSpecialCharacters() {
        return uniqueSpecialCharacters;
    }
    
    /*
    * Open HashMap and distribute keys into individual vectors
    */
    private void distributeKeys()
    {
        Vector keywords = fileTokens.get(FileConstants.KEYWORDS_KEY);
        totalKeywords = keywords.size();
        System.out.println("Unique keywords : ");
        uniqKeywords = uniqueTokens(keywords);
        
        Vector constants = fileTokens.get(FileConstants.CONSTANTS_KEY);
        totalConstants = constants.size();
        System.out.println("Unique Constants : ");
        uniqueConstants = uniqueTokens(constants);
        
        Vector identifiers = fileTokens.get(FileConstants.IDENTIFIERS_KEY);
        totalIdentifiers = identifiers.size();
        System.out.println("Unique Identifiers : ");
        uniqIdentifiers = uniqueTokens(identifiers);
        
        Vector specialCharacters = fileTokens.get(FileConstants.SPECIAL_CHARACTERS_KEY);
        totalSpecialCharacters = specialCharacters.size();
        System.out.println("Unique Special characters : ");
        uniqueSpecialCharacters = uniqueTokens(specialCharacters);
    }
    
    private int uniqueTokens(Vector param1)
    {
//        System.out.println("here");
        Set<String> unique = new HashSet<String>();
        unique.addAll(param1);
        //displau unique items
        StringBuffer obj = new StringBuffer();
        Object[] obj1 = unique.toArray();
        for(Object i : obj1)
            obj.append(i.toString()).append(" , ");
        System.out.println(obj);
        return unique.size();
    }
    
    public void displayStatistics()
    {
        System.out.println("=================================================");
        System.out.println("Package Name : " + this.packageName);
        System.out.println("Class Name : " + this.className);
        System.out.println("Unique Keywords : " + this.getUniqKeywords());
        System.out.println("Unique UDIs : " + this.getUniqIdentifiers());
        System.out.println("Unique Constants : " + this.getUniqueConstants());
        System.out.println("Unique Special Characters : " + this.getUniqueSpecialCharacters());
        System.out.println("Total Keywords : " + this.getTotalKeywords());
        System.out.println("Total UDIs : " + this.getTotalIdentifiers());
        System.out.println("Total Constants : " + this.getTotalConstants());
        System.out.println("Total Special Characters : " + this.getTotalSpecialCharacters());
        System.out.println("\n\nTotal characters in file : " + this.getNumOfCharacters());
        System.out.println("White Spaces : " + this.getWhiteSpaces());
        System.out.println("Characters in comments  : " + this.getCommentCharacters());
        System.out.println("Percentage White Spaces : " + this.getPercentageWhiteSpaces() + "%");
        System.out.println("Percentage Comment Characters : " + this.getPercentageCommentCharacters() + "%");
        for(String methodName : methodNamesAndComplexity.keySet())
            System.out.println("Complexity of method " + methodName + " : " + methodNamesAndComplexity.get(methodName));
        System.out.println("=================================================");
    }
    
    public StringBuffer convertToString()
    {
        StringBuffer objectString = new StringBuffer();
        //objectString.append("Package Name : ").append(this.packageName);
        //objectString.append("\nClass Name : ").append(this.className);
        objectString.append("\nUnique Keywords : ").append(this.getUniqKeywords());
        objectString.append("\nUnique UDIs : ").append(this.getUniqIdentifiers());
        objectString.append("\nUnique Constants : ").append(this.getUniqueConstants());
        objectString.append("\nUnique Special Characters : ").append(this.getUniqueSpecialCharacters());
        objectString.append("\nTotal Keywords : ").append(this.getTotalKeywords());
        objectString.append("\nTotal UDIs : ").append(this.getTotalIdentifiers());
        objectString.append("\nTotal Constants : ").append(this.getTotalConstants());
        objectString.append("\nTotal Special Characters : ").append(this.getTotalSpecialCharacters());
        objectString.append("\n\nTotal characters in file : ").append(this.getNumOfCharacters());
        objectString.append("\nWhite Spaces : ").append(this.getWhiteSpaces());
        objectString.append("\nCharacters in comments  : ").append(this.getCommentCharacters());
        objectString.append("\nPercentage White Spaces : ").append(this.getPercentageWhiteSpaces()).append("%");
        objectString.append("\nPercentage Comment Characters : ").append(this.getPercentageCommentCharacters()).append("%");
        System.out.println("returning string : " + objectString);
        return objectString;
    }
    
    public StringBuffer cyclomaticComplexityString()
    {
        int totalComplexityOfFile = 0;
        StringBuffer objectString = new StringBuffer();
        if(methodNamesAndComplexity.keySet().size() != 0){
            objectString.append("\n\nMcCabe's Cyclomatic Complexity : ");
            for(String methodName : methodNamesAndComplexity.keySet()){
                objectString.append("\nMethod Name : " + methodName + "   Cyclomatic Complexity : " + methodNamesAndComplexity.get(methodName));
                totalComplexityOfFile += methodNamesAndComplexity.get(methodName);
            }

            double averageCyclomaticComplexity = (double)totalComplexityOfFile/methodNamesAndComplexity.keySet().size();
            objectString.append("\nAverage Cyclomatic complexity of class : " + averageCyclomaticComplexity);
        }
        else
            objectString.append("\n\nMcCabe's Cyclomatic Complexity not calculated for the class ");
        return objectString;
    }
    
    public void addObjectData(FileStatistics object)
    {
        whiteSpaces += object.getWhiteSpaces();
        numOfCharacters += object.getNumOfCharacters();
        totalKeywords += object.getTotalKeywords();
        uniqKeywords += object.getUniqKeywords();
        totalConstants += object.getTotalConstants();
        uniqueConstants += object.getUniqueConstants();
        totalIdentifiers += object.getTotalIdentifiers();
        uniqIdentifiers += object.getUniqIdentifiers();
        totalSpecialCharacters += object.getTotalSpecialCharacters();
        uniqueSpecialCharacters += object.getUniqueSpecialCharacters();
        commentCharacters += object.getCommentCharacters();    
    }
}
