/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package oopies;
import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;
import org.antlr.runtime.*;
/**
 *
 * @author Daddy
 */
public class OOPIES {

     //static Set<String> uniqueKeywords = new LinkedHashSet<String>();
     //static Set<String> uniqueIdentifiers = new LinkedHashSet<String>();
     static OOPIESControl oopiesControl;// = new OOPIESView();
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, RecognitionException, Exception {
        // TODO code application logic here
        //JavaJavaLexer lexer = new JavaJavaLexer(new ANTLRFileStream(args[0]));
        
	//CommonTokenStream tokens = new CommonTokenStream(lexer);
        
	//JavaJavaParser parser = new JavaJavaParser(tokens);
        
	//parser.compilationUnit();
	/*
        System.out.print("Parsing done...Class count = ");
	System.out.println(parser.classCount);
        o("White Space = " + lexer.ws);
        o("Comments = " + lexer.commentcount);
        o("Identifiers = " + lexer.identcount);
        o("Keyword count = " + parser.keywordCount);
        o("contents of set " + uniqueKeywords.toString());
        o("unique ids " + uniqueIdentifiers.toString());
        o("Package is " + parser.packageName);
        o("Class is " + parser.className);
        */
        oopiesControl = new OOPIESControl();
        //oopiesView.createMainFrame();
    }
    static void o(String s) {
        System.out.println(s);
    }
}
