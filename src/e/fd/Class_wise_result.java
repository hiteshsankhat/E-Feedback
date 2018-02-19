/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package e.fd;

import Connect.JavaConnectDB;
import java.sql.Connection;
import java.text.DecimalFormat;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import oracle.jdbc.OraclePreparedStatement;
import oracle.jdbc.OracleResultSet;

/**
 *
 * @author hites
 */
public class Class_wise_result extends javax.swing.JFrame {

    Connection con = null, con1 = null, con2 = null;
    OraclePreparedStatement pst = null;
    OracleResultSet rs = null, rs4;
    OracleResultSet rs1 = null;
    String branch = null, date;
    String semester, div, name = null, surname = null, subject, initial = null, sub = null;
    int total = 0, count = 0, out = 0;
    float avg = 0;

    /**
     * Creates new form Class_wise_result
     */
    public Class_wise_result(String b) {
        branch = b;
        initComponents();
        TableColumnModel columnModel = Result.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(20);
        columnModel.getColumn(1).setPreferredWidth(180);
        columnModel.getColumn(2).setPreferredWidth(50);
        columnModel.getColumn(3).setPreferredWidth(50);
        columnModel.getColumn(4).setPreferredWidth(20);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        Result.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        Result.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        Result.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        Result.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
        Result.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);
    }

    private void pr() {
        con = JavaConnectDB.ConnectDB();
        con1 = JavaConnectDB.ConnectDB();
        con2 = JavaConnectDB.ConnectDB();
        if (Student_Div_A.isSelected()) {
            div = Student_Div_A.getText();
        }
        if (Student_Div_B.isSelected()) {
            div = Student_Div_B.getText();
        }
        if (Student_Div_C.isSelected()) {
            div = Student_Div_C.getText();
        }
        semester = (String) semester_box.getSelectedItem();
        String sql = "select full_sub_name,PRACTICAL_NAME_OF_PROF, subject from SUBJECT_INFO where branch=? AND division=? AND semester=? AND PRACTICAL_NAME_OF_PROF is not null";
        String sql1 = "select name, surname from staff_info where initials=?";
        String sql2 = "select sum(v.total),count(v.total) from FEEDBACK_RESULT_TABLE n, table(n.pr) v  where v.name_of_prof=? AND subject=? AND v.total is not null and division=?";
        try {
            pst = (OraclePreparedStatement) con.prepareStatement(sql);
            pst.setString(1, branch);
            pst.setString(2, div);
            pst.setString(3, semester);
            rs = (OracleResultSet) pst.executeQuery();
            while (rs.next()) {
                initial = rs.getString("PRACTICAL_NAME_OF_PROF");
                sub = rs.getString("full_sub_name");
                subject = rs.getString("subject");

                pst = (OraclePreparedStatement) con1.prepareStatement(sql1);
                pst.setString(1, initial);
                rs1 = (OracleResultSet) pst.executeQuery();
                while (rs1.next()) {
                    name = rs1.getString("name");
                    surname = rs1.getString("surname");
                }

                pst = (OraclePreparedStatement) con2.prepareStatement(sql2);
                pst.setString(1, initial);
                pst.setString(2, sub);
                pst.setString(3, div);
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
                DefaultTableModel model1 = (DefaultTableModel) Result.getModel();
                model1.insertRow(Result.getRowCount(), new Object[]{"PR", sub, name, surname, new DecimalFormat("##.##").format(avg)});
                name = null;
                surname = null;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void th() {
        con1 = JavaConnectDB.ConnectDB();
        con = JavaConnectDB.ConnectDB();
        con2 = JavaConnectDB.ConnectDB();
        if (Student_Div_A.isSelected()) {
            div = Student_Div_A.getText();
        }
        if (Student_Div_B.isSelected()) {
            div = Student_Div_B.getText();
        }
        if (Student_Div_C.isSelected()) {
            div = Student_Div_C.getText();
        }
        semester = (String) semester_box.getSelectedItem();
        DefaultTableModel th_dtm = (DefaultTableModel) Result.getModel();

        while (th_dtm.getRowCount() > 0) {
            for (int i = 0; i < th_dtm.getRowCount(); i++) {
                th_dtm.removeRow(i);
            }
        }
        String sql = "select full_sub_name,THEORY_NAME_OF_PROF, subject from SUBJECT_INFO where branch=? AND division=? AND semester=? AND THEORY_NAME_OF_PROF is not null";
        String sql1 = "select name, surname from staff_info where initials=?";
        String sql2 = "select sum(v.total),count(v.total) from FEEDBACK_RESULT_TABLE n, table(n.th) v  where v.name_of_prof=? AND subject=? AND v.total is not null and division=? ";
        try {
            pst = (OraclePreparedStatement) con.prepareStatement(sql);
            pst.setString(1, branch);
            pst.setString(2, div);
            pst.setString(3, semester);
            rs = (OracleResultSet) pst.executeQuery();
            while (rs.next()) {
                initial = rs.getString("THEORY_NAME_OF_PROF");
                sub = rs.getString("full_sub_name");
                subject = rs.getString("subject");

                pst = (OraclePreparedStatement) con1.prepareStatement(sql1);
                pst.setString(1, initial);
                rs1 = (OracleResultSet) pst.executeQuery();
                while (rs1.next()) {
                    name = rs1.getString("name");
                    surname = rs1.getString("surname");
                }

                pst = (OraclePreparedStatement) con2.prepareStatement(sql2);
                pst.setString(1, initial);
                pst.setString(2, sub);
                pst.setString(3, div);
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
                DefaultTableModel model = (DefaultTableModel) Result.getModel();
                model.insertRow(Result.getRowCount(), new Object[]{"TH", sub, name, surname, new DecimalFormat("##.##").format(avg)});
                name = null;
                surname = null;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        semester_box = new javax.swing.JComboBox();
        Student_Div_A = new javax.swing.JRadioButton();
        jLabel5 = new javax.swing.JLabel();
        Student_Div_B = new javax.swing.JRadioButton();
        Student_Div_C = new javax.swing.JRadioButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        Result = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Semester:-");

        semester_box.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1", "2", "3", "4", "5", "6" }));

        buttonGroup1.add(Student_Div_A);
        Student_Div_A.setText("A");
        Student_Div_A.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Student_Div_AActionPerformed(evt);
            }
        });

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Division:-");

        buttonGroup1.add(Student_Div_B);
        Student_Div_B.setText("B");
        Student_Div_B.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Student_Div_BActionPerformed(evt);
            }
        });

        buttonGroup1.add(Student_Div_C);
        Student_Div_C.setText("C");
        Student_Div_C.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Student_Div_CActionPerformed(evt);
            }
        });

        Result.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "TH/PR", "Subject", "Name ", "Surname", "Average"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        Result.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(Result);
        if (Result.getColumnModel().getColumnCount() > 0) {
            Result.getColumnModel().getColumn(0).setResizable(false);
            Result.getColumnModel().getColumn(1).setResizable(false);
            Result.getColumnModel().getColumn(2).setResizable(false);
            Result.getColumnModel().getColumn(3).setResizable(false);
            Result.getColumnModel().getColumn(4).setResizable(false);
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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addGap(18, 18, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(semester_box, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 189, Short.MAX_VALUE)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Student_Div_A)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Student_Div_B)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Student_Div_C)
                .addContainerGap(32, Short.MAX_VALUE))
            .addComponent(jScrollPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(semester_box, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Student_Div_A, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Student_Div_B, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Student_Div_C, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 343, Short.MAX_VALUE)
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {Student_Div_A, Student_Div_B, Student_Div_C, jLabel1, jLabel5, semester_box});

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        new Hod_E_FB_Result(branch).setVisible(true);
        this.dispose();         // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void Student_Div_AActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Student_Div_AActionPerformed
        th();
        DefaultTableModel model = (DefaultTableModel) Result.getModel();
        model.insertRow(Result.getRowCount(), new Object[]{});
        pr();        // TODO add your handling code here:
    }//GEN-LAST:event_Student_Div_AActionPerformed

    private void Student_Div_BActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Student_Div_BActionPerformed
        th();
        DefaultTableModel model = (DefaultTableModel) Result.getModel();
        model.insertRow(Result.getRowCount(), new Object[]{});
        pr();// TODO add your handling code here:
    }//GEN-LAST:event_Student_Div_BActionPerformed

    private void Student_Div_CActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Student_Div_CActionPerformed
        th();
        DefaultTableModel model = (DefaultTableModel) Result.getModel();
        model.insertRow(Result.getRowCount(), new Object[]{});
        pr();// TODO add your handling code here:
    }//GEN-LAST:event_Student_Div_CActionPerformed

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
            java.util.logging.Logger.getLogger(Class_wise_result.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Class_wise_result.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Class_wise_result.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Class_wise_result.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                //new Class_wise_result().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable Result;
    private javax.swing.JRadioButton Student_Div_A;
    private javax.swing.JRadioButton Student_Div_B;
    private javax.swing.JRadioButton Student_Div_C;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JComboBox semester_box;
    // End of variables declaration//GEN-END:variables
}
