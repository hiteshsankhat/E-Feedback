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
import oracle.jdbc.OraclePreparedStatement;
import oracle.jdbc.OracleResultSet;

/**
 *
 * @author hites
 */
public class Staff_E_FB_Result extends javax.swing.JFrame {

    Connection con = null;
    OraclePreparedStatement pst = null;
    OracleResultSet rs = null;
    OracleResultSet rs1 = null;
    String name, date;
    String b, s, sub, div;
    int out, total, count;
    float th_avg, pr_avg, avg;

    /**
     * Creates new form Staff_E_FB_Result
     *
     * @param n
     */
    public Staff_E_FB_Result(String n) {
        name = n;
        con = JavaConnectDB.ConnectDB();
        initComponents();

        re();
        res();
    }

    private void re() {
        con = JavaConnectDB.ConnectDB();
        DefaultTableModel th_dtm = (DefaultTableModel) Theory_result_table.getModel();
        while (th_dtm.getRowCount() > 0) {
            for (int i = 0; i < th_dtm.getRowCount(); i++) {
                th_dtm.removeRow(i);
            }
        }
        String sql = "select branch,semester,division ,subject from subject_info where THEORY_NAME_OF_PROF=? AND theory_name_of_prof is not null";
        String sql1 = "select branch,semester,division ,subject from subject_info where PRACTICAL_NAME_OF_PROF=? AND PRACTICAL_NAME_OF_PROF is not null";
        try {
            pst = (OraclePreparedStatement) con.prepareStatement(sql);
            pst.setString(1, name);
            rs1 = (OracleResultSet) pst.executeQuery();
            while (rs1.next()) {
                b = rs1.getString("branch");
                s = rs1.getString("semester");
                div = rs1.getString("division");
                sub = rs1.getString("subject");
                String sql4 = "select sum(v.total),count(v.total) from FEEDBACK_RESULT_TABLE n ,table(n.th) v where v.NAME_OF_PROF=? AND v.branch=? AND v.semester=? AND v.Division=?";
                pst = (OraclePreparedStatement) con.prepareStatement(sql4);
                pst.setString(1, name);
                pst.setString(2, b);
                pst.setString(3, s);
                pst.setString(4, div);
                rs = (OracleResultSet) pst.executeQuery();
                while (rs.next()) {
                    total = rs.getInt("sum(v.total)");
                    count = rs.getInt("count(v.total)");
                    th_avg = ((float) total / count) / 4;
                    if (th_avg >= 0 && th_avg <= 100) {
                    } else {
                        th_avg = 0;
                    }
                    out = count * 16;
                }
                DefaultTableModel model = (DefaultTableModel) Theory_result_table.getModel();
                model.insertRow(Theory_result_table.getRowCount(), new Object[]{"TH", b, s, div, sub, new DecimalFormat("##.##").format(th_avg)});
                out = 0;
            }
            if (th_avg >= 1 && th_avg <= 4) {
                DefaultTableModel model = (DefaultTableModel) Theory_result_table.getModel();
                model.insertRow(Theory_result_table.getRowCount(), new Object[]{});
            }
            pst = (OraclePreparedStatement) con.prepareStatement(sql1);
            pst.setString(1, name);
            rs1 = (OracleResultSet) pst.executeQuery();
            while (rs1.next()) {
                b = rs1.getString("branch");
                s = rs1.getString("semester");
                div = rs1.getString("division");
                sub = rs1.getString("subject");
                String sql4 = "select sum(v.total),count(v.total) from FEEDBACK_RESULT_TABLE n ,table(n.pr) v where v.NAME_OF_PROF=? AND v.branch=? AND v.semester=? AND v.Division=?";
                pst = (OraclePreparedStatement) con.prepareStatement(sql4);
                pst.setString(1, name);
                pst.setString(2, b);
                pst.setString(3, s);
                pst.setString(4, div);
                rs = (OracleResultSet) pst.executeQuery();
                while (rs.next()) {
                    total = rs.getInt("sum(v.total)");
                    count = rs.getInt("count(v.total)");
                    pr_avg = ((float) total / count) / 4;
                    if (pr_avg >= 0 && pr_avg <= 100) {
                    } else {
                        pr_avg = 0;
                    }
                    out = count * 16;
                }
                DefaultTableModel model1 = (DefaultTableModel) Theory_result_table.getModel();
                model1.insertRow(Theory_result_table.getRowCount(), new Object[]{"PR", b, s, div, sub, new DecimalFormat("##.##").format(pr_avg)});
                out = 0;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void res() {
        con = JavaConnectDB.ConnectDB();
        DefaultTableModel th_dtm = (DefaultTableModel) Th_Pr.getModel();
        while (th_dtm.getRowCount() > 0) {
            for (int i = 0; i < th_dtm.getRowCount(); i++) {
                th_dtm.removeRow(i);
            }
        }
        try {
            String sql = "select branch,semester,division ,subject from subject_info where THEORY_NAME_OF_PROF=? AND theory_name_of_prof is not null or PRACTICAL_NAME_OF_PROF=? AND PRACTICAL_NAME_OF_PROF is not null";
            pst = (OraclePreparedStatement) con.prepareStatement(sql);
            pst.setString(1, name);
            pst.setString(2, name);
            rs1 = (OracleResultSet) pst.executeQuery();
            while (rs1.next()) {
                b = rs1.getString("branch");
                s = rs1.getString("semester");
                div = rs1.getString("division");
                sub = rs1.getString("subject");

                String sql4 = "select sum(v.total),count(v.total) from FEEDBACK_RESULT_TABLE n ,table(n.th) v where v.NAME_OF_PROF=? AND v.branch=? AND v.semester=? AND v.Division=?";
                pst = (OraclePreparedStatement) con.prepareStatement(sql4);
                pst.setString(1, name);
                pst.setString(2, b);
                pst.setString(3, s);
                pst.setString(4, div);
                rs = (OracleResultSet) pst.executeQuery();
                while (rs.next()) {
                    total = rs.getInt("sum(v.total)");
                    int count = rs.getInt("count(v.total)");
                    th_avg = ((float) total / count) / 4;
                    out = count * 16;
                    if (th_avg >= 0 && th_avg <= 100) {
                    } else {
                        th_avg = 0;
                    }
                }

                String sql5 = "select sum(v.total),count(v.total) from FEEDBACK_RESULT_TABLE n ,table(n.pr) v where v.NAME_OF_PROF=? AND v.branch=? AND v.semester=? AND v.Division=?";
                pst = (OraclePreparedStatement) con.prepareStatement(sql5);
                pst.setString(1, name);
                pst.setString(2, b);
                pst.setString(3, s);
                pst.setString(4, div);
                rs = (OracleResultSet) pst.executeQuery();
                while (rs.next()) {
                    total = rs.getInt("sum(v.total)");
                    int count = rs.getInt("count(v.total)");
                    pr_avg = ((float) total / count) / 4;
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
                DefaultTableModel model = (DefaultTableModel) Th_Pr.getModel();
                model.insertRow(Th_Pr.getRowCount(), new Object[]{b, s, div, sub, new DecimalFormat("##.##").format(th_avg), new DecimalFormat("##.##").format(pr_avg), new DecimalFormat("##.##").format(avg)});
                out = 0;
                avg = 0;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        Theory_result_table = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        Th_Pr = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Theory And Practical", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 18), new java.awt.Color(0, 0, 204))); // NOI18N

        Theory_result_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "TH/PR", "Branch", "Semester", "Division", "Subject", "Average"
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
        Theory_result_table.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(Theory_result_table);
        if (Theory_result_table.getColumnModel().getColumnCount() > 0) {
            Theory_result_table.getColumnModel().getColumn(0).setResizable(false);
            Theory_result_table.getColumnModel().getColumn(1).setResizable(false);
            Theory_result_table.getColumnModel().getColumn(2).setResizable(false);
            Theory_result_table.getColumnModel().getColumn(3).setResizable(false);
            Theory_result_table.getColumnModel().getColumn(4).setResizable(false);
            Theory_result_table.getColumnModel().getColumn(5).setResizable(false);
        }

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 696, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jButton1.setText("Exit");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "TH + PR", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 18), new java.awt.Color(0, 0, 153))); // NOI18N

        Th_Pr.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Branch", "Semester", "Division", "Subject", "TH", "PR", "Average"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        Th_Pr.getTableHeader().setReorderingAllowed(false);
        jScrollPane3.setViewportView(Th_Pr);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1)
                        .addGap(0, 624, Short.MAX_VALUE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(5, 5, 5)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        new FirstPage().setVisible(true);
        this.dispose();        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

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
            java.util.logging.Logger.getLogger(Staff_E_FB_Result.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Staff_E_FB_Result.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Staff_E_FB_Result.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Staff_E_FB_Result.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                // new Staff_E_FB_Result().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable Th_Pr;
    private javax.swing.JTable Theory_result_table;
    private javax.swing.JButton jButton1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    // End of variables declaration//GEN-END:variables
}
