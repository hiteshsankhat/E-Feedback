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
public class Staff_Wise_Result extends javax.swing.JFrame {

    Connection con = null, con1 = null;
    OraclePreparedStatement pst = null;
    OracleResultSet rs = null;
    OracleResultSet rs1 = null;
    String branch, division, surname, name, initial, NB, n, subject, date;
    int sem, th1, th2, th3, th4, count, total, out;
    float pr1, pr2, pr3, pr4, avg;
    float th_avg, pr_avg;
    String b, s, sub, div;

    /**
     * Creates new form Staff_Wise_Result
     */
    public Staff_Wise_Result(String b) {
        branch = b;
        initComponents();
        fillall();
        TableColumnModel columnModel = Result.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(15);
        columnModel.getColumn(1).setPreferredWidth(15);
        columnModel.getColumn(2).setPreferredWidth(15);
        columnModel.getColumn(3).setPreferredWidth(15);
        columnModel.getColumn(4).setPreferredWidth(150);
        columnModel.getColumn(9).setPreferredWidth(15);
        TableColumnModel columnModel1 = Th_Pr.getColumnModel();
        columnModel1.getColumn(0).setPreferredWidth(20);
        columnModel1.getColumn(1).setPreferredWidth(20);
        columnModel1.getColumn(2).setPreferredWidth(20);
        columnModel1.getColumn(3).setPreferredWidth(150);
    }

    private void th() {
        String sub;
        con = JavaConnectDB.ConnectDB();
        DefaultTableModel th_dtm = (DefaultTableModel) Th_Pr.getModel();
        while (th_dtm.getRowCount() > 0) {
            for (int i = 0; i < th_dtm.getRowCount(); i++) {
                th_dtm.removeRow(i);
            }
        }
        n = (String) n_box.getSelectedItem();
        String[] parts = n.split(" ");
        name = parts[0];
        surname = parts[1];
        try {
            String sql2 = " select initials from staff_info where name=? and surname=?";
            pst = (OraclePreparedStatement) con.prepareStatement(sql2);
            pst.setString(1, name);
            pst.setString(2, surname);
            rs = (OracleResultSet) pst.executeQuery();
            while (rs.next()) {
                initial = rs.getString("initials");
            }
            String sql = "select branch,semester,division ,subject,FULL_SUB_NAME from subject_info where THEORY_NAME_OF_PROF=? AND theory_name_of_prof is not null or PRACTICAL_NAME_OF_PROF=? AND PRACTICAL_NAME_OF_PROF is not null";
            pst = (OraclePreparedStatement) con.prepareStatement(sql);
            pst.setString(1, initial);
            pst.setString(2, initial);
            rs1 = (OracleResultSet) pst.executeQuery();
            while (rs1.next()) {
                b = rs1.getString("branch");
                s = rs1.getString("semester");
                div = rs1.getString("division");
                sub = rs1.getString("subject");
                subject = rs1.getString("FULL_SUB_NAME");

                String sql4 = "select sum(v.total),count(v.total) from FEEDBACK_RESULT_TABLE n ,table(n.th) v where v.NAME_OF_PROF=? AND v.branch=? AND v.semester=? AND v.Division=?";
                pst = (OraclePreparedStatement) con.prepareStatement(sql4);
                pst.setString(1, initial);
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
                pst.setString(1, initial);
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
                model.insertRow(Th_Pr.getRowCount(), new Object[]{b, s, div, subject, new DecimalFormat("##.##").format(th_avg), new DecimalFormat("##.##").format(pr_avg), new DecimalFormat("##.##").format(avg)});
                out = 0;
                avg = 0;
                pr_avg = 0;
                th_avg = 0;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void start() {
        String sub;
        con1 = JavaConnectDB.ConnectDB();
        con = JavaConnectDB.ConnectDB();
        DefaultTableModel th_dtm = (DefaultTableModel) Result.getModel();
        while (th_dtm.getRowCount() > 0) {
            for (int i = 0; i < th_dtm.getRowCount(); i++) {
                th_dtm.removeRow(i);
            }
        }
        n = (String) n_box.getSelectedItem();
        String[] parts = n.split(" ");
        name = parts[0];
        surname = parts[1];
        String sql2 = " select initials from staff_info where name=? and surname=?";
        try {
            pst = (OraclePreparedStatement) con1.prepareStatement(sql2);
            pst.setString(1, name);
            pst.setString(2, surname);
            rs = (OracleResultSet) pst.executeQuery();
            while (rs.next()) {
                initial = rs.getString("initials");
            }

        } catch (Exception e) {
        }
        String sql1 = "select sum(x.total), count(x.punctuality),sum(x.punctuality), sum(x.planning_of_lessons), sum(x.effective_communication),sum(x.involvement_of_students) from FEEDBACK_RESULT_TABLE n,  table(n.th) x where x.name_of_prof=?  AND x.semester=? AND x.division=?";
        String sql = "select v.branch,v.semester,v.division,v.subject,u.initials,v.FULL_SUB_NAME  from subject_info v ,staff_info u where u.name =? AND u.surname=? and v.THEORY_NAME_OF_PROF=? AND v.THEORY_NAME_OF_PROF is not null ";
        try {
            pst = (OraclePreparedStatement) con1.prepareStatement(sql);
            pst.setString(1, name);
            pst.setString(2, surname);
            pst.setString(3, initial);
            rs = (OracleResultSet) pst.executeQuery();
            while (rs.next()) {
                sem = rs.getInt("semester");
                division = rs.getString("division");
                sub = rs.getString("subject");
                b = rs.getString("branch");
                subject = rs.getString("FULL_SUB_NAME");
                pst = (OraclePreparedStatement) con1.prepareStatement(sql1);
                pst.setString(1, initial);
                pst.setInt(2, sem);
                pst.setString(3, division);
                rs1 = (OracleResultSet) pst.executeQuery();
                while (rs1.next()) {
                    th1 = rs1.getInt("sum(x.punctuality)");
                    th2 = rs1.getInt("sum(x.planning_of_lessons)");
                    th3 = rs1.getInt("sum(x.effective_communication)");
                    th4 = rs1.getInt("sum(x.involvement_of_students)");
                    count = rs1.getInt("count(x.punctuality)");
                    if (count == 0) {
                        break;
                    }
                    total = rs1.getInt("sum(x.total)");
                    try {
                        pr1 = (float) ((float) th1 / (count * 4)) * 4;
                        pr2 = (float) ((float) th2 / (count * 4)) * 4;
                        pr3 = (float) ((float) th3 / (count * 4)) * 4;
                        pr4 = (float) ((float) th4 / (count * 4)) * 4;
                        avg = ((float) total / count) / 4;
                        out = count * 16;
                    } catch (Exception ex) {
                        out = 0;
                    }
                }
                DefaultTableModel model = (DefaultTableModel) Result.getModel();
                model.insertRow(Result.getRowCount(), new Object[]{"TH", b, sem, division, subject, new DecimalFormat("##.##").format(pr1), new DecimalFormat("##.##").format(pr2), new DecimalFormat("##.##").format(pr3), new DecimalFormat("##.##").format(pr4), new DecimalFormat("##.##").format(avg)});
                avg = 0;
                count = 0;
                pr1 = 0;
                pr2 = 0;
                pr3 = 0;
                pr4 = 0;
                out = 0;
                total = 0;
            }
            avg = 0;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        DefaultTableModel model2 = (DefaultTableModel) Result.getModel();
        model2.insertRow(Result.getRowCount(), new Object[]{" "});
        String sql4 = " select initials from staff_info where name=? and surname=?";
        try {
            pst = (OraclePreparedStatement) con.prepareStatement(sql4);
            pst.setString(1, name.trim());
            pst.setString(2, surname.trim());
            rs = (OracleResultSet) pst.executeQuery();
            while (rs.next()) {
                initial = rs.getString("initials");
            }
        } catch (Exception e) {
        }
        String sql5 = "select sum(x.total), count(x.punctuality),sum(x.punctuality), sum(x.planning_of_lessons), sum(x.effective_communication),sum(x.involvement_of_students) from FEEDBACK_RESULT_TABLE n,  table(n.pr) x where x.name_of_prof=?  AND x.semester=? AND x.division=?";
        String sql6 = "select v.branch,v.semester,v.division,v.subject,u.initials ,v.FULL_SUB_NAME from subject_info v ,staff_info u where u.name =? AND u.surname=? and v.PRACTICAL_NAME_OF_PROF=? AND v.PRACTICAL_NAME_OF_PROF is not null ";
        try {
            pst = (OraclePreparedStatement) con.prepareStatement(sql6);
            pst.setString(1, name);
            pst.setString(2, surname);
            pst.setString(3, initial);
            rs = (OracleResultSet) pst.executeQuery();
            while (rs.next()) {
                sem = rs.getInt("semester");
                division = rs.getString("division");
                sub = rs.getString("subject");
                b = rs.getString("branch");
                subject = rs.getString("FULL_SUB_NAME");
                pst = (OraclePreparedStatement) con.prepareStatement(sql5);
                pst.setString(1, initial);
                pst.setInt(2, sem);
                pst.setString(3, division);
                rs1 = (OracleResultSet) pst.executeQuery();
                while (rs1.next()) {
                    th1 = rs1.getInt("sum(x.punctuality)");
                    th2 = rs1.getInt("sum(x.planning_of_lessons)");
                    th3 = rs1.getInt("sum(x.effective_communication)");
                    th4 = rs1.getInt("sum(x.involvement_of_students)");
                    count = rs1.getInt("count(x.punctuality)");
                    if (count == 0) {
                        break;
                    }
                    total = rs1.getInt("sum(x.total)");
                    try {
                        pr1 = (float) ((float) th1 / (count * 4)) * 4;
                        pr2 = (float) ((float) th2 / (count * 4)) * 4;
                        pr3 = (float) ((float) th3 / (count * 4)) * 4;
                        pr4 = (float) ((float) th4 / (count * 4)) * 4;
                        avg = ((float) total / count) / 4;
                        out = count * 16;
                    } catch (Exception ex) {
                        out = 0;
                    }
                }
                DefaultTableModel model3 = (DefaultTableModel) Result.getModel();
                model3.insertRow(Result.getRowCount(), new Object[]{"PR", b, sem, division, subject, new DecimalFormat("##.##").format(pr1), new DecimalFormat("##.##").format(pr2), new DecimalFormat("##.##").format(pr3), new DecimalFormat("##.##").format(pr4), new DecimalFormat("##.##").format(avg)});
                avg = 0;
                count = 0;
                pr1 = 0;
                pr2 = 0;
                pr3 = 0;
                pr4 = 0;
                out = 0;
                total = 0;
            }
            avg = 0;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void fillall() {
        con = JavaConnectDB.ConnectDB();
        String sql = "select name,surname,initials from staff_info where branch=?";
        try {
            pst = (OraclePreparedStatement) con.prepareStatement(sql);
            pst.setString(1, branch);
            rs = (OracleResultSet) pst.executeQuery();
            while (rs.next()) {
                name = rs.getString("name");
                surname = rs.getString("surname");
                name = name.concat(" ").concat(surname);
                n_box.addItem(name);
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

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        Result = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        n_box = new javax.swing.JComboBox();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        Th_Pr = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Name:-");

        Result.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "TH/PR", "Branch", "Semester", "Division", "Subject", "Punctuality", "Planning of Lessons", "Effective Communication", "Involvement of Students", "Average"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false
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
            Result.getColumnModel().getColumn(9).setResizable(false);
        }
        Result.getAccessibleContext().setAccessibleParent(this);

        jButton1.setText("Back");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        n_box.setModel(new javax.swing.DefaultComboBoxModel(new String[] { " " }));
        n_box.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                n_boxItemStateChanged(evt);
            }
        });
        n_box.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                n_boxActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "TH+PR", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 14), new java.awt.Color(0, 0, 255))); // NOI18N

        Th_Pr.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Branch", "Semester", "Division", "Subject", "TH_Average", "PR_Average", "Average"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
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
        jScrollPane2.setViewportView(Th_Pr);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 1279, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(n_box, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1)
                    .addComponent(n_box, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        new Hod_E_FB_Result(branch).setVisible(true);
        this.dispose();// TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void n_boxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_n_boxItemStateChanged
        start();

        th();
// TODO add your handling code here:
    }//GEN-LAST:event_n_boxItemStateChanged

    private void n_boxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_n_boxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_n_boxActionPerformed

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
            java.util.logging.Logger.getLogger(Staff_Wise_Result.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Staff_Wise_Result.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Staff_Wise_Result.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Staff_Wise_Result.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                // new Staff_Wise_Result().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable Result;
    private javax.swing.JTable Th_Pr;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JComboBox n_box;
    // End of variables declaration//GEN-END:variables
}
