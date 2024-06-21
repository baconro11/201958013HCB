import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import javax.swing.*;

public class memo extends JFrame implements ActionListener {
    JTextArea textArea;
    JMenuBar menuBar;
    JMenu fileMenu;
    JMenuItem newFile, openFile, saveFile, close;

    public memo() {
        setTitle("Simple Notepad");
        

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        setSize(800, 600);
        
        textArea = new JTextArea();
        add(new JScrollPane(textArea), BorderLayout.CENTER);
        
        menuBar = new JMenuBar();
        
        fileMenu = new JMenu("File");
        
        newFile = new JMenuItem("New");
        openFile = new JMenuItem("Open");
        saveFile = new JMenuItem("Save");
        close = new JMenuItem("Close");
        
        newFile.addActionListener(this);
        openFile.addActionListener(this);
        saveFile.addActionListener(this);
        close.addActionListener(this);
        
        fileMenu.add(newFile);
        fileMenu.add(openFile);
        fileMenu.add(saveFile);
        fileMenu.add(close);
        
        menuBar.add(fileMenu);
        
        setJMenuBar(menuBar);
    }

    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        
        switch (command) {
            case "New":
                textArea.setText("");
                break;
                
            case "Open":
                JFileChooser fileChooser = new JFileChooser();
                int openOption = fileChooser.showOpenDialog(this);
                if (openOption == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                        textArea.read(reader, null);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
                break;
                
            case "Save":
                fileChooser = new JFileChooser();
                int saveOption = fileChooser.showSaveDialog(this);
                if (saveOption == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    if (!file.getName().endsWith(".txt")) {
                        file = new File(file.getAbsolutePath() + ".txt");
                    }
                    try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                        textArea.write(writer);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
                break;
                
            case "Close":
                System.exit(0);
                break;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            memo notepad = new memo();
            notepad.setVisible(true);
        });
    }
}
