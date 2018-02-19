/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package e.fd;

import Connect.JavaConnectDB;
import java.sql.Connection;
import java.text.DecimalFormat;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import oracle.jdbc.OraclePreparedStatement;
import oracle.jdbc.OracleResultSet;

/**
 *
 * @author hites
 */
public class subject_wise_info extends javax.swing.JFrame {

    Connection con = null, con1 = null;
    OraclePreparedStatement pst = null;
    OracleResultSet rs = null;
    OracleResultSet rs1 = null;
    String branch, date;
    String semester, division, name = null, surname = null, subject, initial = null;
    int total = 0, count = 0, out = 0;
    float avg = 0;

    /**
     * Creates new form subject_wise_info
     */
    public subject_wise_info(String b) {
        branch = b;
        initComponents();
        fill();
        TableColumnModel columnModel = Results.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(20);
        columnModel.getColumn(1).setPreferredWidth(20);
        columnModel.getColumn(2).setPreferredWidth(20);
        columnModel.getColumn(3).setPreferredWidth(20);
        columnModel.getColumn(4).setPreferredWidth(100);
    }

    private void fillall() {
        con1 = JavaConnectDB.ConnectDB();
        con = JavaConnectDB.ConnectDB();
        subject = (String) subject_info.getSelectedItem();
        DefaultTableModel th_dtm = (DefaultTableModel) Results.getModel();

        while (th_dtm.getRowCount() > 0) {
            for (int i = 0; i < th_dtm.getRowCount(); i++) {
                th_dtm.removeRow(i);
            }
        }
        String sql2 = "select sum(v.total),count(v.total) from FEEDBACK_RESULT_TABLE n, table(n.th) v  where v.name_of_prof=? AND subject=? AND v.total is not null And division=? AND branch=?";
        String sql1 = "select name, surname from staff_info where initials=?";
        String sql = "select branch,division,semester,THEORY_NAME_OF_PROF,full_sub_name from subject_info where full_sub_name=? and THEORY_NAME_OF_PROF is not null and branch=?";
        try {
            pst = (OraclePreparedStatement) con1.prepareStatement(sql);
            pst.setString(1, subject);
            pst.setString(2, branch);
            rs = (OracleResultSet) pst.executeQuery();
            while (rs.next()) {
                semester = rs.getString("semester");
                division = rs.getString("division");
                initial = rs.getString("THEORY_NAME_OF_PROF");
                subject = rs.getString("full_sub_name");

                pst = (OraclePreparedStatement) con1.prepareStatement(sql1);
                pst.setString(1, initial);
                rs1 = (OracleResultSet) pst.executeQuery();
                while (rs1.next()) {
                    name = rs1.getString("name");
                    surname = rs1.getString("surname");
                    name = name.concat(" ").concat(surname);
                }
                pst = (OraclePreparedStatement) con1.prepareStatement(sql2);
                pst.setString(1, initial);
                pst.setString(2, subject);
                pst.setString(3, division);
                pst.setString(4, branch);
                rs1 = (OracleResultSet) pst.executeQuery();
                while (rs1.next()) {
                    total = rs1.getInt("sum(v.total)");
                    count = rs1.getInt("count(v.total)");
                    out = count * 16;
                    avg = ((float) total / count) / 4;
                }
                if (avg >= 0 && avg <= 100) {

                } else {
                    avg = 0;
                }
                DefaultTableModel model = (DefaultTableModel) Results.getModel();
                model.insertRow(Results.getRowCount(), new Object[]{"TH", branch, semester, division, name, new DecimalFormat("##.##").format(avg)});
                name = null;
                surname = null;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        DefaultTableModel model3 = (DefaultTableModel) Results.getModel();
        model3.insertRow(Results.getRowCount(), new Object[]{});
        String sql6 = "select sum(v.total),count(v.total) from FEEDBACK_RESULT_TABLE n, table(n.pr) v  where v.name_of_prof=? AND subject=? AND v.total is not null and division=? and branch=?";
        String sql5 = "select name, surname from staff_info where initials=?";
        String sql7 = "select branch,division,semester,PRACTICAL_NAME_OF_PROF,full_sub_name from subject_info where full_sub_name=? AND PRACTICAL_NAME_OF_PROF is not null and branch=?";
        try {
            pst = (OraclePreparedStatement) con.prepareStatement(sql7);
            pst.setString(1, subject);
            pst.setString(2, branch);
            rs = (OracleResultSet) pst.executeQuery();
            while (rs.next()) {
                semester = rs.getString("semester");
                division = rs.getString("division");
                initial = rs.getString("PRACTICAL_NAME_OF_PROF");
                subject = rs.getString("full_sub_name");

                pst = (OraclePreparedStatement) con.prepareStatement(sql5);
                pst.setString(1, initial);
                rs1 = (OracleResultSet) pst.executeQuery();
                while (rs1.next()) {
                    name = rs1.getString("name");
                    surname = rs1.getString("surname");
                    name = name.concat(" ").concat(surname);
                }

                pst = (OraclePreparedStatement) con.prepareStatement(sql6);
                pst.setString(1, initial);
                pst.setString(2, subject);
                pst.setString(3, division);
                pst.setString(4, branch);
                rs1 = (OracleResultSet) pst.executeQuery();
                while (rs1.next()) {
                    total = rs1.getInt("sum(v.total)");
                    count = rs1.getInt("count(v.total)");
                    out = count * 16;
                    avg = ((float) total / count) / 4;
                }
                if (avg >= 0 && avg <= 100) {

                } else {
                    avg = 0;
                }
                DefaultTableModel model = (DefaultTableModel) Results.getModel();
                model.insertRow(Results.getRowCount(), new Object[]{"PR", branch, semester, division, name, new DecimalFormat("##.##").format(avg)});
                name = null;
                surname = null;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void fill() {
        con = JavaConnectDB.ConnectDB();
        subject_info.removeAllItems();
        subject_info.addItem(null);
        try {
            String sql = "select distinct(full_sub_name) from SUBJECT_INFO where branch=? ";
            pst = (OraclePreparedStatement) con.prepareStatement(sql);
            pst.setString(1, branch);
            rs = (OracleResultSet) pst.executeQuery();
            while (rs.next()) {
                subject = rs.getString("full_sub_name");
                subject_info.addItem(subject);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        subject_info = new javax.swing.JComboBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        Results = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Name of  Subject");

        subject_info.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Professional Practices-II", "Management", "Entrepreneurship Development", "Computer Hardware & Maintenance", "Advanced Java Programming", "Computer Network", "Software Testing", "Object Oriented Programming", "Advanced Microprocessor", "Linux Programming", "Environmental Studies", "Microprocessor and Programming", "Computer Graphics" }));
        subject_info.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                subject_infoItemStateChanged(evt);
            }
        });
        subject_info.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                subject_infoActionPerformed(evt);
            }
        });

        Results.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "TH/PR", "Branch", "Semester", "Division", "Name", "Average"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        Results.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(Results);
        if (Results.getColumnModel().getColumnCount() > 0) {
            Results.getColumnModel().getColumn(0).setResizable(false);
            Results.getColumnModel().getColumn(1).setResizable(false);
            Results.getColumnModel().getColumn(2).setResizable(false);
            Results.getColumnModel().getColumn(3).setResizable(false);
            Results.getColumnModel().getColumn(4).setResizable(false);
            Results.getColumnModel().getColumn(5).setResizable(false);
        }

        jButton1.setText("Back");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(subject_info, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 746, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(subject_info, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 268, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void subject_infoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_subject_infoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_subject_infoActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        new Hod_E_FB_Result(branch).setVisible(true);
        this.dispose();         // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void subject_infoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_subject_infoItemStateChanged
        fillall();        // TODO add your handling code here:
    }//GEN-LAST:event_subject_infoItemStateChanged

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
            java.util.logging.Logger.getLogger(subject_wise_info.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(subject_wise_info.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(subject_wise_info.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(subject_wise_info.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                // new subject_wise_info().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable Results;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JComboBox subject_info;
    // End of variables declaration//GEN-END:variables
}
