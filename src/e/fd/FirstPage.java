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
import oracle.jdbc.OraclePreparedStatement;
import oracle.jdbc.OracleResultSet;

/**
 *
 * @author hites
 */
public class FirstPage extends javax.swing.JFrame {

    Connection con = null, con1 = null;
    OraclePreparedStatement pst = null;
    OracleResultSet rs = null, rs2 = null, rs1 = null;
    String name, surname, initial, n, sr, in, b, s, sub, div, date;
    int sem, th1, th2, th3, th4, count, total, out, c;
    float pr1, pr2, pr3, pr4, avg, sum, avgr, th_avg, pr_avg;

    /**
     * Creates new form FirstPage
     */
    private void start() {
        con = JavaConnectDB.ConnectDB();
        con1 = JavaConnectDB.ConnectDB();
        try {
            String dt = "select * from current_yr";
            pst = (OraclePreparedStatement) con.prepareStatement(dt);
            rs = (OracleResultSet) pst.executeQuery();
            while (rs.next()) {
                date = rs.getString("YEAR");
            }
            String rm = "delete from YEARWISE where yr=?";
            pst = (OraclePreparedStatement) con.prepareStatement(rm);
            pst.setString(1, date);
            rs = (OracleResultSet) pst.executeQuery();

            String sql2 = "select * from staff_info";
            pst = (OraclePreparedStatement) con.prepareStatement(sql2);
            rs2 = (OracleResultSet) pst.executeQuery();
            while (rs2.next()) {
                b = rs2.getString("branch");
                name = rs2.getString("name");
                surname = rs2.getString("surname");
                initial = rs2.getString("initials");
                String i = rs2.getString("initials");
                String sql = "select branch,semester,division ,subject,FULL_SUB_NAME from subject_info where THEORY_NAME_OF_PROF=? AND theory_name_of_prof is not null or PRACTICAL_NAME_OF_PROF=? AND PRACTICAL_NAME_OF_PROF is not null";
                pst = (OraclePreparedStatement) con.prepareStatement(sql);
                pst.setString(1, initial);
                pst.setString(2, initial);
                rs1 = (OracleResultSet) pst.executeQuery();
                while (rs1.next()) {
                    s = rs1.getString("semester");
                    div = rs1.getString("division");
                    sub = rs1.getString("FULL_SUB_NAME");
                    String sql4 = "select sum(v.average),count(v.total) from FEEDBACK_RESULT_TABLE n ,table(n.th) v where v.NAME_OF_PROF=?  AND v.semester=? AND v.Division=? and v.subject=?";
                    pst = (OraclePreparedStatement) con.prepareStatement(sql4);
                    pst.setString(1, initial);
                    pst.setString(2, s);
                    pst.setString(3, div);
                    pst.setString(4, sub);
                    rs = (OracleResultSet) pst.executeQuery();
                    while (rs.next()) {
                        total = rs.getInt("sum(v.average)");
                        int count = rs.getInt("count(v.total)");
                        th_avg = ((float) total / count);
                        out = count * 16;
                        if (th_avg >= 0 && th_avg <= 100) {
                        } else {
                            th_avg = 0;
                        }
                    }
                    String sql5 = "select sum(v.average),count(v.total) from FEEDBACK_RESULT_TABLE n ,table(n.pr) v where v.NAME_OF_PROF=?  AND v.semester=? AND v.Division=? and v.subject=?";
                    pst = (OraclePreparedStatement) con.prepareStatement(sql5);
                    pst.setString(1, initial);
                    pst.setString(2, s);
                    pst.setString(3, div);
                    pst.setString(4, sub);
                    rs = (OracleResultSet) pst.executeQuery();
                    while (rs.next()) {
                        total = rs.getInt("sum(v.average)");
                        int count = rs.getInt("count(v.total)");
                        pr_avg = ((float) total / count);
                        out = count * 16;
                        if (pr_avg >= 0 && pr_avg <= 100) {
                        } else {
                            pr_avg = 0;
                        }
                    }
                    if (th_avg == 0.0) {
                        avg = pr_avg;
                    } else if (pr_avg == 0.0) {
                        avg = th_avg;
                    } else {
                        avg = (th_avg + pr_avg) / 2;
                    }
                    if (i.equals(initial)) {
                        if (avg == 0) {
                        } else {
                            c++;
                            sum = sum + avg;
                            avgr = sum / c;
                        }
                    }
                    out = 0;
                    avg = 0;
                    pr_avg = 0;
                    th_avg = 0;
                }
                n = name.concat(" ").concat(surname);
                String inrt = "insert into YEARWISE values (?,?,?,?,?)";
                pst = (OraclePreparedStatement) con.prepareStatement(inrt);
                pst.setString(1, date);
                pst.setString(2, n.toUpperCase());
                pst.setString(3, new DecimalFormat("##.##").format(avgr));
                pst.setString(4, initial);
                pst.setString(5, b);
                rs = (OracleResultSet) pst.executeQuery();
                sum = 0;
                c = 0;
                avgr = 0;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public FirstPage() {
        initComponents();
        start();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        Exit = new javax.swing.JButton();
        HoD = new javax.swing.JButton();
        Student = new javax.swing.JButton();
        Staff = new javax.swing.JButton();
        Principal = new javax.swing.JButton();
        Admin = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "E-FeedBack", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Cambria Math", 1, 24), new java.awt.Color(0, 0, 255))); // NOI18N

        Exit.setFont(new java.awt.Font("Cambria Math", 1, 12)); // NOI18N
        Exit.setText("Exit");
        Exit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ExitActionPerformed(evt);
            }
        });

        HoD.setFont(new java.awt.Font("Cambria Math", 1, 12)); // NOI18N
        HoD.setText("HoD Login");
        HoD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                HoDActionPerformed(evt);
            }
        });

        Student.setFont(new java.awt.Font("Cambria Math", 1, 12)); // NOI18N
        Student.setText("Student Login");
        Student.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                StudentActionPerformed(evt);
            }
        });

        Staff.setFont(new java.awt.Font("Cambria Math", 1, 12)); // NOI18N
        Staff.setText("Staff Login");
        Staff.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                StaffActionPerformed(evt);
            }
        });

        Principal.setFont(new java.awt.Font("Cambria Math", 1, 12)); // NOI18N
        Principal.setText("Principal Login");
        Principal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PrincipalActionPerformed(evt);
            }
        });

        Admin.setFont(new java.awt.Font("Cambria Math", 1, 12)); // NOI18N
        Admin.setText("Admin");
        Admin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AdminActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(Exit, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Staff, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Student, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(HoD, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Principal, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Admin, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Student, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Staff, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(HoD, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Principal, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Admin, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Exit, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void HoDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_HoDActionPerformed
        new HoDLoginPage().setVisible(true);
        this.dispose();// TODO add your handling code here:
    }//GEN-LAST:event_HoDActionPerformed

    private void PrincipalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PrincipalActionPerformed
        new PrincipalLogin().setVisible(true);
        this.dispose();// TODO add your handling code here:
    }//GEN-LAST:event_PrincipalActionPerformed

    private void ExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ExitActionPerformed
        this.dispose();        // TODO add your handling code here:
    }//GEN-LAST:event_ExitActionPerformed

    private void StudentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_StudentActionPerformed
        StudentLoginPage student = new StudentLoginPage();
        student.setVisible(true);
        this.dispose();
// TODO add your handling code here:
    }//GEN-LAST:event_StudentActionPerformed

    private void StaffActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_StaffActionPerformed
        new StaffLoginPage().setVisible(true);
        this.dispose();// TODO add your handling code here:
    }//GEN-LAST:event_StaffActionPerformed

    private void AdminActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AdminActionPerformed
        new Admin_login_page().setVisible(true);
        this.dispose();// TODO add your handling code here:
    }//GEN-LAST:event_AdminActionPerformed

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
            java.util.logging.Logger.getLogger(FirstPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FirstPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FirstPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FirstPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FirstPage().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Admin;
    private javax.swing.JButton Exit;
    private javax.swing.JButton HoD;
    private javax.swing.JButton Principal;
    private javax.swing.JButton Staff;
    private javax.swing.JButton Student;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
