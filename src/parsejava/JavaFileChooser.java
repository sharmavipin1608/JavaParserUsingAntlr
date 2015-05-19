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
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class JavaFileChooser extends JPanel
        implements ActionListener {

    JFileChooser chooser;
    String choosertitle;

    public Vector<String> filelist = new Vector<String>(5, 5);

    public JavaFileChooser() {
        JButton parseButton = new JButton("Parse Files");
        parseButton.addActionListener(this);
        add(parseButton);

        JButton database = new JButton("Stored Results");
        database.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                RetrieveResults storedResults = new RetrieveResults();
            }
        });
        add(database);
    }

    public void actionPerformed(ActionEvent e) {
        chooser = new JFileChooser();
        chooser.setCurrentDirectory(new java.io.File("."));
        chooser.setDialogTitle(choosertitle);
        chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        filelist.removeAllElements();
        //
        // disable the "All files" option.
        //
        chooser.addChoosableFileFilter(new FileNameExtensionFilter("Java Files", "java"));
        chooser.setAcceptAllFileFilterUsed(false);
        //    
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            String dir = chooser.getSelectedFile().getAbsolutePath();
            if (chooser.getSelectedFile().isDirectory()) {
                displayDirectoryContents(chooser.getSelectedFile());
            } else {
                filelist.add(dir);
            }
            System.out.println("size of the vector : " + filelist.size());
            
            //add functionality to put all the results in hashmaps
            //send the results in hashmaps to 
            HashMap<String,ArrayList> packageClassMapping = new HashMap<String,ArrayList>();
            HashMap<String,FileStatistics> classStatsMapping = new HashMap<String,FileStatistics>();
            HashMap<String,FileStatistics> packageStatsMapping = new HashMap<String,FileStatistics>();
            
            
            for (String path : filelist) {
                String[] filename = path.split("/");
                FileStatistics stats = ParseJavaFile.displayStats(path);
                
                ArrayList<String> classList = new ArrayList<>();
                String packageName = stats.getPackageName();
                String className = stats.getClassName();
                if(packageClassMapping.get(packageName) != null)
                {
                    classList = packageClassMapping.get(packageName);
                    packageStatsMapping.get(packageName).addObjectData(stats);
                }
                else
                {
                    packageStatsMapping.put(packageName,stats);
                }
                classList.add(className);
                packageClassMapping.put(packageName, classList);
                classStatsMapping.put(packageName+className, stats);
                
            }
            ResultsWindow resultsWindow = new ResultsWindow(packageClassMapping,classStatsMapping,packageStatsMapping,"Parsing Results");
        } else {
            System.out.println("No Selection ");
        }
        
        //pass the hashmaps here
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(200, 200);
    }

    public void displayDirectoryContents(File dir) {

        try {
            File[] files = dir.listFiles();
            for (File file : files) {
                if (file.isDirectory()) {
                    displayDirectoryContents(file);
                } else {
                    if (file.getName().endsWith(".java")) {
                        filelist.add(file.getCanonicalPath());
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
