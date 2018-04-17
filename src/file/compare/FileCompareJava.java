/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package file.compare;

import java.awt.Component;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.time.Instant;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * @author Dijak
 */
public class FileCompareJava {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainFrame();
            }

        });

        //FileCompareJava fc = new FileCompareJava();
    }

    FileCompareJava() {
        //init

        fileSelected = new boolean[2];
        fileSelected[0] = false;
        fileSelected[1] = false;

        selectedFile = new File[2];
        fileName = new String[2];
        fileAbsolutePath = new String[2];

        settings = getDefaultSettings();

        continueCompare = true;

        /*dt = new DialogThread();
         dt.dialog.setVisible(true);
         dt.dialog.setVisible(false);
         */
    }

    public boolean compare() {
        if (!(fileSelected[0] && fileSelected[1])) {
            JOptionPane.showMessageDialog(mainWindow, "Please, select both files");
            return false;
        }
        if (selectedFile[0].isFile() && selectedFile[1].isFile()) {

            /*localLog("Comparing...", 3);
             localLog("SwingUtilities.isEventDispatchThread(): " + SwingUtilities.isEventDispatchThread());
             localLog(Thread.currentThread().getName());
             */
            compare2Files(selectedFile[0], selectedFile[1]);
            return true;

        } else if (selectedFile[0].isDirectory() && selectedFile[0].isDirectory()) {

            return false;
        }
        JOptionPane.showMessageDialog(mainWindow, "One of the paths is a directory and another one is a file");
        return false;

    }

    //returns 1 when same, 0 when different and -1 when sth went wrong
    public void compare2Files(File file1, File file2) {

        //dt.start();
        localLog("Compare2Files - Start", 3);
        localLog("SwingUtilities.isEventDispatchThread(): " + SwingUtilities.isEventDispatchThread());
        localLog(Thread.currentThread().getName());
        /*long start = System.currentTimeMillis();
         */

        localLog("worker init");
        worker = new compare2FilesTask(file1, file2);

        localLog("worker: " + worker);
        worker.execute();

        localLog("process dialog init");
        localLog(mainWindow);
        localLog(mainWindow.processDialog);
        mainWindow.processDialog.setVisible(true);
        /*
         int compare;
         long end = System.currentTimeMillis();
         long diff = end - start;
         this.localLog("time needed: " + (end - start) + "ms", 3);
         mainWindow.processDialog.setVisible(false);
         */
//dt.terminate();
        localLog("Compare2Files - End", 3);
        localLog("SwingUtilities.isEventDispatchThread(): " + SwingUtilities.isEventDispatchThread());
        localLog(Thread.currentThread().getName());

    }

    public boolean compare2FilesByDate(File file1, File file2) {
        Date date1 = new Date(file1.lastModified());
        Date date2 = new Date(file2.lastModified());
        localLog(date1.toString());
        localLog(date2.toString());
        return date1.compareTo(date2) == 0;
    }

    //returns 1 when same, 0 when different, -1 when sth went wrong, -2 when aborted
    //------------------------------------------------------
    //IT RUNS IN ANOTHER THREAD, WATCH OUT FOR INTERFERENCE-
    //------------------------------------------------------
    /**
     *
     */
    public class compare2FilesTask extends SwingWorker<Integer, Integer> {

        public File file1;
        public File file2;
        public FileInputStream Fis1;
        public FileInputStream Fis2;
        public int retVal;
        public boolean fStreamSuccess;

        public compare2FilesTask(File argfile1, File argfile2) {
            retVal = -2;
            file1 = argfile1;
            file2 = argfile2;
            try {
                Fis1 = new FileInputStream(argfile1);
                Fis2 = new FileInputStream(argfile2);
                fStreamSuccess = true;

            } catch (IOException ex) {
                Logger.getLogger(FileCompareJava.class.getName()).log(Level.SEVERE, null, ex);
                retVal = -1;
                fStreamSuccess = false;

            }
        }

        @Override
        protected Integer doInBackground() throws Exception {
            if (compare2FilesBySize(file1, file2) && compare2FilesByDate(file1, file2) && !settings.alwaysCheckFileContent) {
                retVal = 1;
            } else {
                /*
                 long start1 = System.currentTimeMillis();
                 localLog("Starting C compare");
                 int compare = compare2FilesByContentC(file1.getAbsolutePath(), file2.getAbsolutePath()); //compare2FilesByContent(file1, file2);//COMPARE BY CONTENT
                 localLog("Ending C compare");
                 long end1 = System.currentTimeMillis();
                 localLog("time needed C: " + (end1 - start1) + "ms" + "\n" + "result: " + compare, 3);

                 long start2 = System.currentTimeMillis();
                 localLog("Starting Java compare");
                 compare = compare2FilesTask(file1, file2);//COMPARE BY CONTENT
                 localLog("Ending Java compare");
                 long end2 = System.currentTimeMillis();
                 localLog("time needed Java: " + (end2 - start2) + "ms" + "\n" + "result: " + compare, 3);
                 */
                int offset = 0;

                //1 MB
                byte[] array1 = new byte[allocationUnitSize];
                byte[] array2 = new byte[allocationUnitSize];

                long index = 0;
                long length = file1.length();

                while (continueCompare) {
                    int progress = (int) (index * ((long) (allocationUnitSize)) * 100 / length);
                    publish(progress);

                    localLog(index + "\n" + index * ((long) (allocationUnitSize)) * 100 + "\n" + length);
                    index++;
                    try {
                        int read1 = Fis1.read(array1);//, offset, allocationUnitSize);
                        int read2 = Fis2.read(array2);//, offset, allocationUnitSize);

                        if (read1 != read2) { //če nista v istem stanju, nista enaka
                            retVal = 0;
                            break;
                        } else {
                            if (read1 == -1) { //če smo končali s fajloma, potem sta bila zagotovo enaka
                                retVal = 1;
                                break;
                            } else if (!Arrays.equals(array1, array2)) { //če obstaja neujemanje, smo končali (nista enaka)
                                retVal = 0;
                                break;
                            } else if (read1 < allocationUnitSize) { //smo na koncu fajla, ni bilo neujemanja
                                retVal = 1;
                                break;
                            }
                            /*else {                              //trenutni porciji fajlov sta enaki, nismo pa še na koncu, zato nadaljujemo
                             offset += allocationUnitSize;

                             }*/

                        }
                    } catch (Exception ex) {
                        localLog(offset);
                        Logger.getLogger(FileCompareJava.class
                                .getName()).log(Level.SEVERE, null, ex);

                        retVal = -1;
                    }

                }

                continueCompare = true;

            }
            return retVal;
        }

        @Override
        protected void done() {

            try {
                Fis1.close();
                Fis2.close();
            } catch (IOException ex) {
                Logger.getLogger(FileCompareJava.class.getName()).log(Level.SEVERE, null, ex);
            }
            mainWindow.processDialog.setVisible(false);
            int compare;
            if (!isCancelled()) {
                try {
                    compare = get();
                } catch (InterruptedException ex) {
                    Logger.getLogger(FileCompareJava.class.getName()).log(Level.SEVERE, null, ex);
                    compare = -2;
                } catch (CancellationException ex) {
                    Logger.getLogger(FileCompareJava.class.getName()).log(Level.SEVERE, null, ex);
                    compare = -2;
                } catch (ExecutionException ex) {
                    Logger.getLogger(FileCompareJava.class.getName()).log(Level.SEVERE, null, ex);
                    compare = -1;
                }
            } else {
                compare = -2;
            }

            switch (compare) {
                case 1:
                    JOptionPane.showMessageDialog(mainWindow, "The files are the same");
                    break;
                case -1:
                    //An error occured
                    //DO STH ABOUT IT!!
                    localLog("STH WENT WRONG");
                    JOptionPane.showMessageDialog(mainWindow, "Something went wrong");
                    break;
                case -2:
                    localLog("ABORTED");
                    JOptionPane.showMessageDialog(mainWindow, "Aborted!", "Abort", JOptionPane.OK_OPTION);
                    break;
                default:
                    JOptionPane.showMessageDialog(mainWindow, "The files are not the same");
            }

        }

        @Override
        protected void process(List<Integer> list) {
            int val = list.get(list.size() - 1);
            mainWindow.processDialog.progress(val);
        }

    }

    public native int compare2FilesByContentC(String file1, String file2);

    public boolean compare2FilesBySize(File file1, File file2) {
        localLog(file1.length());
        localLog(file2.length());
        return file1.length() == file2.length();
    }

    //select a file and save it into this. returns true when file is selected, false otherwise.  
    public boolean selectFile(int which) {

        int returnVal = mainWindow.fileChooser().showOpenDialog(mainWindow);
        if (returnVal == JFileChooser.APPROVE_OPTION) {

            selectedFile[which] = mainWindow.fileChooser().getSelectedFile();
            fileName[which] = selectedFile[which].getName();
            fileAbsolutePath[which] = selectedFile[which].getAbsolutePath();
            fileSelected[which] = true;
            this.localLog(fileName[which]);
            this.localLog(fileAbsolutePath[which]);
            if (selectedFile[which].isDirectory()) {
                DefaultMutableTreeNode root = new DefaultMutableTreeNode();

                if (which == 1) {
                    if (mainWindow.jScrollPane1.getComponent(0) != null) {

                    }

                } else {
                    mainWindow.jScrollPane2.getComponent(0);
                }
            }

            return true;
        } else {
            this.localLog("No file was selected", 2);
            return false;
        }
    }

    public boolean continueCompare = true;

    public MainFrame mainWindow;
    public File[] selectedFile;
    public boolean[] fileSelected;
    public String[] fileName;
    public String[] fileAbsolutePath;

    public Settings settings;

    public static Settings getDefaultSettings() {
        Settings set = new Settings(true);
        return set;
    }

    public Settings getSettings() {
        //naloži shranjene nastavitve
        return settings;
    }

    public void setSettings(Settings set) {
        settings = set;
    }

    public int MB_size = 1048576; //1 MB
    public int allocationUnitSize = MB_size / 4;
    public compare2FilesTask worker;
    //LOG LEVELS:
    /* 
     0: Logs only errors
    
     1: logs also things that went wrong
    
     2: logs also important notifications
    
     3: logs everything
     */
    public int logLevel = 3;

    public void localLog(Object o, int level) {
        if (level <= logLevel) {
            System.out.print(Instant.now() + ": ");
            System.out.println(o);
        }
    }

    public void localLog(Object o) {
        this.localLog(o, 3);
    }
    /*
     public void localLog(Object o, String msg, int level) {
     if (level <= logLevel) {
     Logger.getLogger(FileCompareJava.class.getName()).log(Level.SEVERE, msg, o);
     }
     }
     public void localLog(Object o, String msg) {
     this.localLog(o, msg, 3);
     }
     public void localLog(Object o, int level) {
     this.localLog(o, null,  level);
     }
     public void localLog(Object o) {
     this.localLog(o, null,  3);
     }
    
     */

    /*
     if using C compare
     static {
     try {
            
     String pathToJar = new File(".").getCanonicalPath();
     String dllPath = pathToJar + "\\..\\FileCompareNative\\FileCompareNative.dll";
     String dllPathCanonical = new File(dllPath).getCanonicalPath();
     System.load(dllPathCanonical);
     System.out.println(dllPathCanonical);
            
     } catch (IOException ex) {
     Logger.getLogger(FileCompareJava.class.getName()).log(Level.SEVERE, null, ex);
     }
     }
     */
    /*
     public class DialogThread extends Thread {

     private Thread t;
     private boolean running = false;
     public ProcessDialog dialog;

     public DialogThread() {
     dialog = new ProcessDialog(mainWindow, true);
     localLog(dialog);
     }

     @Override
     public void start() {
     if (t == null) {
     this.running = true;
     this.dialog.canceled = false;
     t = new Thread(this, "dialog");
     t.start();
     localLog(dialog);
     }
     }

     @Override
     public void run() {
     running = true;
     this.dialog.canceled = false;
     dialog.setVisible(true);
     localLog(dialog);

     }

     public void terminate() {
     dialog.setVisible(false);
     }

     }
     //public DialogThread dt;
     */
}
