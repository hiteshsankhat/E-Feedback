/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package e.fd;

import Connect.JavaConnectDB;
import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import oracle.jdbc.OraclePreparedStatement;
import oracle.jdbc.OracleResultSet;

/**
 *
 * @author hites
 */
public class Modify extends javax.swing.JFrame {

    Connection con = null;
    OraclePreparedStatement pst = null;
    OracleResultSet rs = null;
    OracleResultSet rs1 = null;
    String branch, name, surname, None = null;
    String semester, div, th_n = null, pr_n, th_s, pr_s = null, subject, initial = null, sub = null;
    int total = 0, count = 0, out = 0;
    float avg = 0;
    String WHITESPACE = " ", n;

    /**
     * Creates new form Modify
     */
    public Modify() {
        initComponents();
        fillall();
    }

    private void fillall() {
        con = JavaConnectDB.ConnectDB();
        th_name.removeAllItems();
        pr_name.removeAllItems();
        th_name.addItem(None);
        pr_name.addItem(None);
        String sql = "select name,surname,initials from staff_info";
        try {
            pst = (OraclePreparedStatement) con.prepareStatement(sql);
            rs = (OracleResultSet) pst.executeQuery();
            while (rs.next()) {
                name = rs.getString("name");
                surname = rs.getString("surname");
                n = name.concat(WHITESPACE).concat(surname);
                th_name.addItem(n);
                pr_name.addItem(n);
            }
        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void fill() {
        con = JavaConnectDB.ConnectDB();
        sub_box.removeAllItems();
        try {

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
            branch = (String) b_box.getSelectedItem();
            String sql = "select full_sub_name from SUBJECT_INFO where branch=? AND Division=? AND semester=? ";
            pst = (OraclePreparedStatement) con.prepareStatement(sql);
            pst.setString(1, branch);
            pst.setString(2, div);
            pst.setString(3, semester);
            rs = (OracleResultSet) pst.executeQuery();
            while (rs.next()) {
                sub = rs.getString("full_sub_name");
                sub_box.addItem(sub);
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
        semester_box = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        Student_Div_A = new javax.swing.JRadioButton();
        Student_Div_B = new javax.swing.JRadioButton();
        Student_Div_C = new javax.swing.JRadioButton();
        jLabel2 = new javax.swing.JLabel();
        sub_box = new javax.swing.JComboBox();
        jPanel1 = new javax.swing.JPanel();
        th_name = new javax.swing.JComboBox();
        jLabel4 = new javax.swing.JLabel();
        th_submit = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        pr_name = new javax.swing.JComboBox();
        jLabel10 = new javax.swing.JLabel();
        pr_submit = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        b_box = new javax.swing.JComboBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        semester_box.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1", "2", "3", "4", "5", "6" }));
        semester_box.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                semester_boxActionPerformed(evt);
            }
        });

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Semester:-");

        jButton1.setText("Back");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Division:-");

        buttonGroup1.add(Student_Div_A);
        Student_Div_A.setText("A");
        Student_Div_A.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Student_Div_AActionPerformed(evt);
            }
        });

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

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Subject");

        sub_box.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sub_boxActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Theory", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 3, 24))); // NOI18N

        th_name.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                th_nameActionPerformed(evt);
            }
        });

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Name");

        th_submit.setText("Submit");
        th_submit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                th_submitActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(th_name, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(63, Short.MAX_VALUE)
                .addComponent(th_submit, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(104, 104, 104))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(th_name, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(th_submit, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Practical", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 3, 24))); // NOI18N

        pr_name.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pr_nameActionPerformed(evt);
            }
        });

        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("Name");

        pr_submit.setText("Submit");
        pr_submit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pr_submitActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(pr_name, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(65, 65, 65)
                .addComponent(pr_submit, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pr_name, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pr_submit, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Branch:-");

        b_box.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "CO", "EJ", "IF" }));
        b_box.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_boxActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(Student_Div_A)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(Student_Div_B)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(Student_Div_C))
                            .addComponent(sub_box, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(b_box, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(semester_box, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(0, 33, Short.MAX_VALUE))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jButton1, jLabel1, jLabel2, jLabel5, semester_box});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(semester_box, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(b_box, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Student_Div_A, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Student_Div_B, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Student_Div_C, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sub_box, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jButton1, jLabel1, jLabel2, jLabel5, semester_box});

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void semester_boxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_semester_boxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_semester_boxActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        new AdminPage().setVisible(true);
        this.dispose();        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void sub_boxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sub_boxActionPerformed

// TODO add your handling code here:
    }//GEN-LAST:event_sub_boxActionPerformed

    private void Student_Div_AActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Student_Div_AActionPerformed
        fill();// TODO add your handling code here:
    }//GEN-LAST:event_Student_Div_AActionPerformed

    private void Student_Div_BActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Student_Div_BActionPerformed
        fill();      // TODO add your handling code here:
    }//GEN-LAST:event_Student_Div_BActionPerformed

    private void Student_Div_CActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Student_Div_CActionPerformed
        fill();        // TODO add your handling code here:
    }//GEN-LAST:event_Student_Div_CActionPerformed

    private void th_nameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_th_nameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_th_nameActionPerformed

    private void pr_nameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pr_nameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_pr_nameActionPerformed

    private void th_submitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_th_submitActionPerformed
        con = JavaConnectDB.ConnectDB();

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
        subject = (String) sub_box.getSelectedItem();

        n = (String) th_name.getSelectedItem();
        String[] parts = n.split(" ");
        name = parts[0];
        surname = parts[1];
        String sql2 = "select initials from staff_info where name=? and surname=?";
        try {
            pst = (OraclePreparedStatement) con.prepareStatement(sql2);
            pst.setString(1, name);
            pst.setString(2, surname);
            rs = (OracleResultSet) pst.executeQuery();
            while (rs.next()) {
                initial = rs.getString("initials");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        String sql = "update SUBJECT_INFO set THEORY_NAME_OF_PROF=? where FULL_SUB_NAME=? and division=? and branch=? and semester=?";
        try {
            pst = (OraclePreparedStatement) con.prepareStatement(sql);
            pst.setString(1, initial);
            pst.setString(2, subject);
            pst.setString(3, div);
            pst.setString(4, branch);
            pst.setString(5, semester);
            rs = (OracleResultSet) pst.executeQuery();
            if (rs.next()) {
                JOptionPane.showMessageDialog(null, "Successfully updated");
            }

        } catch (SQLException | HeadlessException e) {
            JOptionPane.showMessageDialog(null, e);
        }

// TODO add your handling code here:
    }//GEN-LAST:event_th_submitActionPerformed

    private void pr_submitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pr_submitActionPerformed
        con = JavaConnectDB.ConnectDB();

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
        subject = (String) sub_box.getSelectedItem();

        n = (String) pr_name.getSelectedItem();
        String[] parts = n.split(" ");
        name = parts[0];
        surname = parts[1];
        String sql2 = "select initials from staff_info where name=? and surname=?";
        try {
            pst = (OraclePreparedStatement) con.prepareStatement(sql2);
            pst.setString(1, name);
            pst.setString(2, surname);
            rs = (OracleResultSet) pst.executeQuery();
            while (rs.next()) {
                initial = rs.getString("initials");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        String sql = "update SUBJECT_INFO set Practical_NAME_OF_PROF=? where FULL_SUB_NAME=? and division=? and branch=? and semester=?";
        try {
            pst = (OraclePreparedStatement) con.prepareStatement(sql);
            pst.setString(1, initial);
            pst.setString(2, subject);
            pst.setString(3, div);
            pst.setString(4, branch);
            pst.setString(5, semester);
            rs = (OracleResultSet) pst.executeQuery();
            if (rs.next()) {
                JOptionPane.showMessageDialog(null, "Successfully updated");
            }

        } catch (SQLException | HeadlessException e) {
            JOptionPane.showMessageDialog(null, e);
        }  // TODO add your handling code here:
    }//GEN-LAST:event_pr_submitActionPerformed

    private void b_boxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b_boxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_b_boxActionPerformed

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
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Modify.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new Modify().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JRadioButton Student_Div_A;
    private javax.swing.JRadioButton Student_Div_B;
    private javax.swing.JRadioButton Student_Div_C;
    private javax.swing.JComboBox b_box;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JComboBox pr_name;
    private javax.swing.JButton pr_submit;
    private javax.swing.JComboBox semester_box;
    private javax.swing.JComboBox sub_box;
    private javax.swing.JComboBox th_name;
    private javax.swing.JButton th_submit;
    // End of variables declaration//GEN-END:variables
}
