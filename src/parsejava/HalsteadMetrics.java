/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parsejava;

/**
 *
 * @author vipinsharma
 */
public class HalsteadMetrics {
    private int n1;
    private int n2;
    private int N1;
    private int N2;
    private int programLength;
    private int programVocabulary;
    private double volume;
    private double programLevel;
    private double estimatedDifficulty;
    private double effort;
    private double time;
    
    HalsteadMetrics(FileStatistics fileStats)
    {
        n1 = fileStats.getUniqKeywords()+fileStats.getUniqueSpecialCharacters();
        N1 = fileStats.getTotalKeywords()+fileStats.getTotalSpecialCharacters();
        n2 = fileStats.getUniqueConstants()+fileStats.getUniqIdentifiers();
        N2 = fileStats.getTotalConstants()+fileStats.getTotalIdentifiers();
        calculateMetrics();
    }
    
    /*
    1.	Program Length (N) = N1+N2
    2.	Program Vocabulary (n) = n1+n2
    3.	Volume of a Program (V) = N*log2n
    4.	Program Level (L) = L=V* /V=( 2/ n1)*(n2/ N2)
    5.	Estimated Difficulty (D) = 1/L = n1N2/2n2
    6.	Effort(E)=V/L=V*D=(n1 xN2)/2n2
    7.	Time (T) = E/S [“S” is Stroud number and represents the speed of a programmer. The value “S” is 18] 
    */
    private void calculateMetrics()
    {
        programLength = N1 + N2;
        programVocabulary = n1 + n2;
        volume = programLength * Math.log(programVocabulary);
        programLevel = (double)(2*n2)/(n1*N2);
        estimatedDifficulty = 1/programLevel;
        effort = volume * estimatedDifficulty;
        time = effort/18;
    }
    
    public StringBuffer convertToString()
    {
        StringBuffer objectString = new StringBuffer();
        objectString.append("\n\nHalstead's Metrics");
        objectString.append("\nProgram Length : ").append(programLength);
        objectString.append("\nProgram Vocabulary : ").append(programVocabulary);
        objectString.append("\nVolume : ").append(volume);
        objectString.append("\nProgram Level : ").append(programLevel);
        objectString.append("\nEstimated difficulty : ").append(estimatedDifficulty);
        objectString.append("\nEffort : ").append(effort);
        objectString.append("\nTime : ").append(time);
        return objectString;
    }
}
