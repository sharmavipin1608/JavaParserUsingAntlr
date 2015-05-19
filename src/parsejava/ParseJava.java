/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parsejava;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
//run with argument /Users/vipinsharma/Documents/output/ParseJava.java
/**
 *
 * @author vipinsharma
 */
public class ParseJava 
{
    public static void main(String[] args) 
    {
        try 
        {
            //Start JFileChooser
            JFrame frame = new JFrame("");
            JavaFileChooser panel = new JavaFileChooser();
            frame.addWindowListener(
                new WindowAdapter() {
                    public void windowClosing(WindowEvent e) {
                        System.exit(0);
                    }
                }
            );
            
            frame.getContentPane().add(panel, "Center");
            frame.setSize(panel.getPreferredSize());
            frame.setVisible(true);
            
            for(String path : panel.filelist)
            {
                String[] filename = path.split("/");
                System.out.println(path);
                System.out.println(filename[filename.length-1]);
            }
            //End JFileChooser
            
//            ANTLRInputStream input = new ANTLRInputStream(new FileInputStream(args[0]));
//            JavaLexer lexer = new JavaLexer(input);
//               
//            CommonTokenStream tokens = new CommonTokenStream(lexer);
//            JavaParser parser = new JavaParser(tokens);
//            parser.compilationUnit();
//            
//            HashMap<String,Vector> tokenMap = new HashMap<String,Vector>(lexer.hm);
//            tokenMap.putAll(parser.hm);
//            
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
//            
//            Scanner input1 = new Scanner(new FileInputStream(args[0]));
//            int number_of_lines = 0,length = 0;
//            String line;
//
//            while (input1.hasNextLine()) 
//            {
//                line = input1.nextLine();
//                System.out.println(length + " : " + line.toLowerCase());
//                length += line.length();
//                number_of_lines++;
//            }
//
//            System.out.println("total num of characters : " + length);
//            System.out.println("total number of lines : " + number_of_lines);
//            System.out.println("total number of spaces : " + lexer.whiteSpaces);
        } 
        catch (Exception ex) 
        {
            System.out.println(ex.getStackTrace() + ex.getMessage());
        }
    }
    
}
