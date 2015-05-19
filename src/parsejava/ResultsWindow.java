/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parsejava;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.TextArea;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author vipinsharma
 */
public class ResultsWindow {
    JList packageListPanel = new JList();
    JList classListPanel = new JList();
    public ResultsWindow(HashMap<String,ArrayList> packageClassMapping,HashMap<String,FileStatistics> classStatsMapping,HashMap<String,FileStatistics> packageStatsMapping,String title)
    {
        TextArea textArea = new TextArea(title);
        JScrollPane pane = new JScrollPane(packageListPanel);
        pane.setSize(300,300);
        pane.setPreferredSize(new Dimension(300,300));
        pane.setMaximumSize(new Dimension(300,300));
        pane.setMinimumSize(new Dimension(300,300));
        //pane.setBorder(BorderFactory.createRaisedBevelBorder() );
        pane.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), "Packages"));
        DefaultListModel packageList = new DefaultListModel();
        DefaultListModel classModelList = new DefaultListModel();
//        try {
//            tableData = DatabaseUtilities.retrieveDataFromDatabase();
//            packageClassMapping = tableData.get("packageClassMapping");
//            classStatsMapping = tableData.get("classStatsMapping");
            for(String key: packageClassMapping.keySet())
            {
                packageList.addElement(key);
////                ArrayList<String> classList = new ArrayList<String>();
////                for(String className: classList)
//                ArrayList<String> classList = packageClassMapping.get(key);
//                FileStatistics packageStats = new FileStatistics();
//                for(String className: classList)
//                {
//                    String classKey = key+className;
//                    packageStats.addObjectData(classStatsMapping.get(classKey));
//                    
//                }
//                packageStatsMapping.put(key,packageStats);
            }
//        } catch (Exception ex) {
//            Logger.getLogger(RetrieveResults.class.getName()).log(Level.SEVERE, null, ex);
//        }
        
//        packageList.addElement("vipin" + " " + "vipin2");
//        packageList.addElement("2");
//        
//        packageList.addElement("vipin");
//        packageList.addElement("2");
//        
//        packageList.addElement("vipin");
//        packageList.addElement("2");
//        
//        packageList.addElement("vipin");
//        packageList.addElement("2");
//        
        packageListPanel.setModel(packageList);
        packageListPanel.setFont(new Font("Verdana", Font.BOLD, 16));
        
        packageListPanel.setVisible(true);
        packageListPanel.addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent e) {
                System.out.println("selected text : " + packageListPanel.getSelectedValue().toString());
                String key = packageListPanel.getSelectedValue().toString();
                System.out.println("key : " + key);
                ArrayList<String> classList = packageClassMapping.get(key);
                System.out.println("size : " + classList.size());
                
                classModelList.clear();
                //classListPanel.removeListSelectionListener(this);
                classListPanel.setSelectedIndex(-1);
                System.out.println("cleared");
                for(String className: classList)
                {
                    classModelList.addElement(className);
                }
                FileStatistics temp = packageStatsMapping.get(key);
                temp.displayStatistics();
                textArea.setText(temp.convertToString().toString());
                textArea.setFont(new Font("Verdana", Font.ITALIC, 16));
                textArea.setEditable(false);
            }
        }
        
        );
        
        
        classListPanel.setModel(classModelList);
        classListPanel.setSelectedIndex(0);
        JScrollPane pane1 = new JScrollPane(classListPanel);
//        panel1.
        pane1.setPreferredSize(new Dimension(300,300));
        pane1.setMaximumSize(new Dimension(300,300));
        pane1.setMinimumSize(new Dimension(300,300));
        //pane1.setSize(250,300);
        
        pane1.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), "Classes"));
        //classListPanel.add
        classListPanel.addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent e) {
                System.out.println("class model size : " + classListPanel.getModel().getSize());
                if(classListPanel.getSelectedIndex() != -1)
                {
                    System.out.println("selected text : " + classListPanel.getSelectedValue().toString());
                    //packageList.addElement("jhjhjhjh");
                    
                    String key = packageListPanel.getSelectedValue().toString()+classListPanel.getSelectedValue().toString();
                    FileStatistics temp = classStatsMapping.get(key);
                    HalsteadMetrics halsteadMetrics = new HalsteadMetrics(temp);
                    temp.displayStatistics();
                    textArea.setText(temp.convertToString().toString() + halsteadMetrics.convertToString().toString() + temp.cyclomaticComplexityString());
                    
                    textArea.setEditable(false);
                }
            }
        });

        
        JFrame frame = new JFrame(title);
        JPanel basePanel = new JPanel();
        basePanel.setLayout(new BoxLayout(basePanel,BoxLayout.Y_AXIS));
        basePanel.setPreferredSize(new Dimension(500,500));
        basePanel.setMaximumSize(new Dimension(500,500));
        basePanel.setMinimumSize(new Dimension(500,500));
        
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel,BoxLayout.X_AXIS));
        panel.setPreferredSize(new Dimension(600,300));
        panel.setMaximumSize(new Dimension(600,300));
        panel.setMinimumSize(new Dimension(600,300));
        panel.add(pane);
        panel.add(pane1);
        basePanel.add(panel);
        
        
        textArea.setSize(250, 200);
        textArea.setPreferredSize(new Dimension(600,250));
        textArea.setMaximumSize(new Dimension(600,250));
        textArea.setMinimumSize(new Dimension(600,250));
//        
        basePanel.add(textArea);
        
        frame.add(basePanel);
//        frame.add(textArea);
        
        frame.setSize(600, 600);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
    }
}
