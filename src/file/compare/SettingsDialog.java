/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package file.compare;

import java.awt.event.ItemEvent;
import javax.swing.JOptionPane;

/**
 *
 * @author Luka
 */
public class SettingsDialog extends javax.swing.JDialog {

    /**
     * Creates new form SettingsDialog
     */
    public SettingsDialog(MainFrame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        itemListeners();

        this.mainFrame = parent;
        mainFrame.setLocaleLook();
        setTitle("Settings");
        //MyFunctions.centerTitle(this, "Settings");
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        ApplyButton.setEnabled(false);

    }

    public SettingsDialog(java.awt.Frame parent, boolean modal) {

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        acfcCheckBox = new javax.swing.JCheckBox();
        CloseButton = new javax.swing.JButton();
        ApplyButton = new javax.swing.JButton();
        ResetButton = new javax.swing.JButton();
        OKButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        acfcCheckBox.setText("Always check file content (This might extend the comparation time)");
        acfcCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                acfcCheckBoxActionPerformed(evt);
            }
        });

        CloseButton.setText("Cancel");
        CloseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CloseButtonActionPerformed(evt);
            }
        });

        ApplyButton.setText("Apply");
        ApplyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ApplyButtonActionPerformed(evt);
            }
        });

        ResetButton.setText("Reset");
        ResetButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ResetButtonActionPerformed(evt);
            }
        });

        OKButton.setText("OK");
        OKButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OKButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(ResetButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(OKButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ApplyButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(CloseButton))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(acfcCheckBox)
                        .addGap(0, 33, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(acfcCheckBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 236, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(CloseButton)
                    .addComponent(ApplyButton)
                    .addComponent(OKButton)
                    .addComponent(ResetButton))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void CloseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CloseButtonActionPerformed
        // TODO add your handling code here:
        Exit();
    }//GEN-LAST:event_CloseButtonActionPerformed

    private void ResetButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ResetButtonActionPerformed
        // TODO add your handling code here:
        if (JOptionPane.showConfirmDialog(this, "Do you really want to reset all settings?", "Reset settings?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
            mainFrame.fc.setSettings(mainFrame.fc.getDefaultSettings());
            setWindowSettings(mainFrame.fc.getDefaultSettings());
        }
    }//GEN-LAST:event_ResetButtonActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // SettingsDialog closed
        Exit();

    }//GEN-LAST:event_formWindowClosing

    private void acfcCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_acfcCheckBoxActionPerformed
        // TODO add your handling code here:
        compareSettingsAndManipulateApplyButton();

    }//GEN-LAST:event_acfcCheckBoxActionPerformed

    private void ApplyButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ApplyButtonActionPerformed
        //Save settings
        mainFrame.fc.setSettings(getWindowSettings());
        compareSettingsAndManipulateApplyButton();
    }//GEN-LAST:event_ApplyButtonActionPerformed

    private void OKButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OKButtonActionPerformed
        ApplyButtonActionPerformed(evt);
        Exit();
    }//GEN-LAST:event_OKButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(SettingsDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SettingsDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SettingsDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SettingsDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                SettingsDialog dialog = new SettingsDialog(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ApplyButton;
    private javax.swing.JButton CloseButton;
    private javax.swing.JButton OKButton;
    private javax.swing.JButton ResetButton;
    private javax.swing.JCheckBox acfcCheckBox;
    // End of variables declaration//GEN-END:variables

    private void itemListeners() {
        acfcCheckBox.addItemListener(new java.awt.event.ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                compareSettingsAndManipulateApplyButton();
            }

        });
    }

    public MainFrame mainFrame;

    public void Exit() {
        Exit(false);
    }

    public void Exit(boolean direct) {

        if (direct
                || compareSettingsAndManipulateApplyButton()
                || JOptionPane.showOptionDialog(this, "Are you sure to discard changes?", "Discard changes?",
                        0, JOptionPane.QUESTION_MESSAGE, null, new String[]{"Discard", "Cancel"}, null) == 0) {
            setWindowSettings(mainFrame.fc.getSettings());
            setVisible(false);
        }

    }

    public Settings getWindowSettings() {
        Settings set = new Settings();
        set.alwaysCheckFileContent = this.acfcCheckBox.isSelected();
        return set;

    }

    public void setWindowSettings(Settings set) {
        acfcCheckBox.setSelected(set.alwaysCheckFileContent);
    }

    public boolean compareSettingsAndManipulateApplyButton() {
        boolean same = getWindowSettings().toString().equals(mainFrame.fc.settings.toString());
        ApplyButton.setEnabled(!same);
        return same;

    }
}
