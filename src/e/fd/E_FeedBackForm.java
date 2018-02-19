/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package e.fd;

import Connect.JavaConnectDB;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import oracle.jdbc.OraclePreparedStatement;
import oracle.jdbc.OracleResultSet;

/**
 *
 * @author hites
 */
public class E_FeedBackForm extends javax.swing.JFrame {

    Connection con = null;
    OraclePreparedStatement pst = null;
    OracleResultSet rs = null, rs1 = null, rs2;
    String sem, branch, division, nam, sur, su, date;

    /**
     * Creates new form E_FeedBackForm
     */
    public E_FeedBackForm(String b, String d, String s, String w) {
        branch = b;
        sem = s;
        division = d;
        su = w;
        initComponents();
        con = JavaConnectDB.ConnectDB();
        start();
    }

    private void start() {
        con = JavaConnectDB.ConnectDB();

        DefaultTableModel th_dtm = (DefaultTableModel) TH_FeedBack.getModel();
        DefaultTableModel pr_dtm = (DefaultTableModel) PR_FeedBack.getModel();
        while (th_dtm.getRowCount() > 0) {
            for (int i = 0; i < th_dtm.getRowCount(); i++) {
                th_dtm.removeRow(i);
            }
        }
        while (pr_dtm.getRowCount() > 0) {
            for (int i = 0; i < pr_dtm.getRowCount(); i++) {
                pr_dtm.removeRow(i);
            }
        }
        String n, r, nm, sr, nme = null;
        String sql = "select FULL_sub_name,theory_name_of_prof from subject_info where branch=? AND division=? AND semester=? AND theory_name_of_prof is not null";
        String sql1 = "select full_sub_name,practical_name_of_prof from subject_info where branch=? AND division=? AND semester=? AND practical_name_of_prof is not null";
        try {
            pst = (OraclePreparedStatement) con.prepareStatement(sql);
            pst.setString(1, branch);
            pst.setString(2, division);
            pst.setString(3, sem);
            rs = (OracleResultSet) pst.executeQuery();
            while (rs.next()) {
                n = rs.getString("full_sub_name");
                r = rs.getString("theory_name_of_prof");
                String sql4 = "select * from staff_info where INITIALS=?";
                pst = (OraclePreparedStatement) con.prepareStatement(sql4);
                pst.setString(1, r);
                rs1 = (OracleResultSet) pst.executeQuery();
                while (rs1.next()) {
                    nm = rs1.getString("NAME");
                    sr = rs1.getString("surname");
                    nme = nm.concat(" ").concat(sr);
                }
                DefaultTableModel model = (DefaultTableModel) TH_FeedBack.getModel();
                model.insertRow(TH_FeedBack.getRowCount(), new Object[]{n, nme});
            }
            pst = (OraclePreparedStatement) con.prepareStatement(sql1);
            pst.setString(1, branch);
            pst.setString(2, division);
            pst.setString(3, sem);
            rs = (OracleResultSet) pst.executeQuery();
            while (rs.next()) {
                n = rs.getString("full_sub_name");
                r = rs.getString("practical_name_of_prof");
                String sql4 = "select * from staff_info where INITIALS=?";
                pst = (OraclePreparedStatement) con.prepareStatement(sql4);
                pst.setString(1, r);
                rs1 = (OracleResultSet) pst.executeQuery();
                while (rs1.next()) {
                    nm = rs1.getString("NAME");
                    sr = rs1.getString("surname");
                    nme = nm.concat(" ").concat(sr);
                }
                DefaultTableModel model = (DefaultTableModel) PR_FeedBack.getModel();
                model.insertRow(PR_FeedBack.getRowCount(), new Object[]{n, nme});
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        TH_FeedBack = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        PR_FeedBack = new javax.swing.JTable();
        SUBMIT = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Theory", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Cambria Math", 1, 24), new java.awt.Color(0, 0, 255))); // NOI18N

        TH_FeedBack.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Subjects", "Name", "Punctuality", "Planning of Lessons", "Effective Communication", "Involvement Of students"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                true, false, true, true, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TH_FeedBack.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_LAST_COLUMN);
        TH_FeedBack.getTableHeader().setReorderingAllowed(false);
        TH_FeedBack.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                TH_FeedBackKeyTyped(evt);
            }
        });
        jScrollPane2.setViewportView(TH_FeedBack);
        if (TH_FeedBack.getColumnModel().getColumnCount() > 0) {
            TH_FeedBack.getColumnModel().getColumn(0).setResizable(false);
            TH_FeedBack.getColumnModel().getColumn(1).setResizable(false);
            TH_FeedBack.getColumnModel().getColumn(2).setResizable(false);
            TH_FeedBack.getColumnModel().getColumn(3).setResizable(false);
            TH_FeedBack.getColumnModel().getColumn(4).setResizable(false);
            TH_FeedBack.getColumnModel().getColumn(5).setResizable(false);
        }

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Practical", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Cambria Math", 1, 24), new java.awt.Color(0, 0, 204))); // NOI18N

        PR_FeedBack.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Subjets", "Name", "Puntuality", "Planning Of Lessons", "Effective Communication", "Involvement Of Students"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, true, true, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        PR_FeedBack.getTableHeader().setReorderingAllowed(false);
        PR_FeedBack.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                PR_FeedBackKeyTyped(evt);
            }
        });
        jScrollPane4.setViewportView(PR_FeedBack);
        if (PR_FeedBack.getColumnModel().getColumnCount() > 0) {
            PR_FeedBack.getColumnModel().getColumn(0).setResizable(false);
            PR_FeedBack.getColumnModel().getColumn(1).setResizable(false);
            PR_FeedBack.getColumnModel().getColumn(2).setResizable(false);
            PR_FeedBack.getColumnModel().getColumn(3).setResizable(false);
            PR_FeedBack.getColumnModel().getColumn(4).setResizable(false);
            PR_FeedBack.getColumnModel().getColumn(5).setResizable(false);
        }

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 882, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE)
        );

        SUBMIT.setText("SUBMIT");
        SUBMIT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SUBMITActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Give the marks according to the following \"System of Scores\" : 4 for Excellent, 3 for Very Good, 2 for Statisfactory, 1 for Not Quite Statisfactory.");
        jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jLabel2.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Enter the Marks in Range Of 1 to 4");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(SUBMIT, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(SUBMIT, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void SUBMITActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SUBMITActionPerformed
        con = JavaConnectDB.ConnectDB();
        String th_s = null, th_n = null, pr_s = null, pr_n = null, name, surname;
        int th_total = 0, pr_total = 0, th1 = 0, th2 = 0, th3 = 0, th4 = 0, pr1 = 0, pr2 = 0, pr3 = 0, pr4 = 0;
        float th_avg = 0, pr_avg = 0;
        int row_no1 = TH_FeedBack.getRowCount();
        int row_no2 = PR_FeedBack.getRowCount();
        try {
            for (int i = 0; i < row_no1; i++) {
                th1 = (int) TH_FeedBack.getValueAt(i, 2);
                th2 = (int) TH_FeedBack.getValueAt(i, 3);
                th3 = (int) TH_FeedBack.getValueAt(i, 4);
                th4 = (int) TH_FeedBack.getValueAt(i, 5);
                if ((th1 > 4) || (th2 > 4) || (th3 > 4) || (th4 > 4)) {
                    throw new myEx("Please enter values in range of 1 to 4 in the " + i + 1 + " row of theory table");
                }
            }
            for (int j = 0; j < row_no2; j++) {
                pr1 = (int) PR_FeedBack.getValueAt(j, 2);
                pr2 = (int) PR_FeedBack.getValueAt(j, 3);
                pr3 = (int) PR_FeedBack.getValueAt(j, 4);
                pr4 = (int) PR_FeedBack.getValueAt(j, 5);
                if ((pr1 > 4) || (pr2 > 4) || (pr3 > 4) || (pr4 > 4)) {
                    throw new myEx("Please enter values in range of 1 to 4 in the " + j + 1 + " row of practical table");
                }
            }
            for (int i = 0; i < row_no1; i++) {
                th_s = (String) TH_FeedBack.getValueAt(i, 0);
                th_n = (String) TH_FeedBack.getValueAt(i, 1);
                th1 = (int) TH_FeedBack.getValueAt(i, 2);
                th2 = (int) TH_FeedBack.getValueAt(i, 3);
                th3 = (int) TH_FeedBack.getValueAt(i, 4);
                th4 = (int) TH_FeedBack.getValueAt(i, 5);
                th_total = th1 + th2 + th3 + th4;
                th_avg = ((float) th_total / 4);
                String[] parts = th_n.split(" ");
                name = parts[0];
                surname = parts[1];
                String sql3 = "Select * from staff_info where name=? and surname=?";
                pst = (OraclePreparedStatement) con.prepareStatement(sql3);
                pst.setString(1, name);
                pst.setString(2, surname);
                rs2 = (OracleResultSet) pst.executeQuery();
                while (rs2.next()) {
                    th_n = rs2.getString("INITIALS");
                }
                String sql = "insert into feedback_result_table(th) values(staff_skills_th_table(staff_skills(?,?,?,?,?,?,?,?,?,?,?)))";
                pst = (OraclePreparedStatement) con.prepareStatement(sql);
                pst.setString(1, branch);
                pst.setString(2, sem);
                pst.setString(3, division);
                pst.setString(4, th_s);
                pst.setString(5, th_n);
                pst.setInt(6, th1);
                pst.setInt(7, th2);
                pst.setInt(8, th3);
                pst.setInt(9, th4);
                pst.setInt(10, th_total);
                pst.setFloat(11, th_avg);
                rs = (OracleResultSet) pst.executeQuery();
            }
            for (int j = 0; j < row_no2; j++) {
                pr_s = (String) PR_FeedBack.getValueAt(j, 0);
                pr_n = (String) PR_FeedBack.getValueAt(j, 1);
                pr1 = (int) PR_FeedBack.getValueAt(j, 2);
                pr2 = (int) PR_FeedBack.getValueAt(j, 3);
                pr3 = (int) PR_FeedBack.getValueAt(j, 4);
                pr4 = (int) PR_FeedBack.getValueAt(j, 5);
                pr_total = pr1 + pr2 + pr3 + pr4;
                pr_avg = ((float) pr_total / 4);
                String[] parts = pr_n.split(" ");
                name = parts[0];
                surname = parts[1];
                String sql5 = "Select * from staff_info where name=? and surname=?";
                pst = (OraclePreparedStatement) con.prepareStatement(sql5);
                pst.setString(1, name);
                pst.setString(2, surname);
                rs2 = (OracleResultSet) pst.executeQuery();
                while (rs2.next()) {
                    pr_n = rs2.getString("INITIALS");
                }
                String sql1 = "insert into feedback_result_table(pr) values(staff_skills_pr_table(staff_skills(?,?,?,?,?,?,?,?,?,?,?)))";
                pst = (OraclePreparedStatement) con.prepareStatement(sql1);
                pst.setString(1, branch);
                pst.setString(2, sem);
                pst.setString(3, division);
                pst.setString(4, pr_s);
                pst.setString(5, pr_n);
                pst.setInt(6, pr1);
                pst.setInt(7, pr2);
                pst.setInt(8, pr3);
                pst.setInt(9, pr4);
                pst.setInt(10, pr_total);
                pst.setFloat(11, pr_avg);
                rs = (OracleResultSet) pst.executeQuery();
            }
            if (rs.next()) {
                JOptionPane.showMessageDialog(null, "Sucessfully Entered");
                new FirstPage().setVisible(true);
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(null, "Invalid Details");
            }
            if (branch.equals("CO")) {
                String sql1 = "insert into LOGIN_DATA(co_username) values(?)";
                pst = (OraclePreparedStatement) con.prepareStatement(sql1);
                pst.setString(1, su.toUpperCase());
                rs = (OracleResultSet) pst.executeQuery();
            } else if (branch.equals("EJ")) {
                String sql2 = "insert into LOGIN_DATA(ej_username) values(?)";
                pst = (OraclePreparedStatement) con.prepareStatement(sql2);
                pst.setString(1, su.toUpperCase());
                rs = (OracleResultSet) pst.executeQuery();
            } else if (branch.equals("IF")) {
                String sql3 = "insert into LOGIN_DATA(if_username) values(?)";
                pst = (OraclePreparedStatement) con.prepareStatement(sql3);
                pst.setString(1, su.toUpperCase());
                rs = (OracleResultSet) pst.executeQuery();
            }
        } catch (myEx ex) {
            JOptionPane.showMessageDialog(null, ex);
        } catch (NullPointerException ne) {
            JOptionPane.showMessageDialog(null, "Please enter first values");
        } catch (SQLException | HeadlessException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_SUBMITActionPerformed

    private void PR_FeedBackKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PR_FeedBackKeyTyped
        char ch = evt.getKeyChar();
        if ((ch >= (char) 53) && (ch <= (char) 57) || (ch == (char) 48)) {
            JOptionPane.showMessageDialog(null, "Please enter the marks in range of 1 to 4");
        }// TODO add your handling code here:
    }//GEN-LAST:event_PR_FeedBackKeyTyped

    private void TH_FeedBackKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TH_FeedBackKeyTyped
        char ch = evt.getKeyChar();
        if ((ch >= (char) 53) && (ch <= (char) 57) || (ch == (char) 48)) {
            JOptionPane.showMessageDialog(null, "Please enter the marks in range of 1 to 4");
        }      // TODO add your handling code here:
    }//GEN-LAST:event_TH_FeedBackKeyTyped

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
            java.util.logging.Logger.getLogger(E_FeedBackForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(E_FeedBackForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(E_FeedBackForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(E_FeedBackForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                // new E_FeedBackForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable PR_FeedBack;
    private javax.swing.JButton SUBMIT;
    public javax.swing.JTable TH_FeedBack;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    // End of variables declaration//GEN-END:variables

    private void TH_FeedBackActionPerformed(ActionEvent evt) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}

class myEx extends Exception {

    myEx(String msg) {
        super(msg);
    }
}
