/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package e.fd;

import Connect.JavaConnectDB;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.text.DecimalFormat;
import java.util.Set;
import java.util.TreeMap;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import oracle.jdbc.OraclePreparedStatement;
import oracle.jdbc.OracleResultSet;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author hites
 */
public class pralll extends javax.swing.JFrame {

    Connection con = null, con1 = null;
    OraclePreparedStatement pst = null;
    OracleResultSet rs = null, rs2 = null, rs5 = null;
    OracleResultSet rs1 = null, rs3 = null, rs6 = null, rs7 = null;
    String name, surname, initial, n, sr, in;
    int sem, th1, th2, th3, th4, count, total, out, c;
    float pr1, pr2, pr3, pr4, avg, sum, avgr;
    float th_avg, pr_avg;
    String b, s, sub, div;

    /**
     * Creates new form staff_whole
     */
    public pralll() {
        initComponents();
        con = JavaConnectDB.ConnectDB();
        con1 = JavaConnectDB.ConnectDB();
        tab.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        TableColumnModel columnModel = tab.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(20);
        columnModel.getColumn(1).setPreferredWidth(150);
        columnModel.getColumn(2).setPreferredWidth(20);
        view();
    }

    private void view() {
        DefaultTableModel th_dtm = (DefaultTableModel) tab.getModel();
        while (th_dtm.getRowCount() > 0) {
            for (int i = 0; i < th_dtm.getRowCount(); i++) {
                th_dtm.removeRow(i);
            }
        }

        try {
            String sql2 = "select * from staff_info";
            pst = (OraclePreparedStatement) con.prepareStatement(sql2);
            rs2 = (OracleResultSet) pst.executeQuery();
            while (rs2.next()) {
                name = rs2.getString("name");
                surname = rs2.getString("surname");
                initial = rs2.getString("initials");
                String i = rs2.getString("initials");
                String sql = "select branch,semester,division ,subject from subject_info where THEORY_NAME_OF_PROF=? AND theory_name_of_prof is not null or PRACTICAL_NAME_OF_PROF=? AND PRACTICAL_NAME_OF_PROF is not null";
                pst = (OraclePreparedStatement) con.prepareStatement(sql);
                pst.setString(1, initial);
                pst.setString(2, initial);
                rs1 = (OracleResultSet) pst.executeQuery();
                while (rs1.next()) {
                    s = rs1.getString("semester");
                    div = rs1.getString("division");
                    sub = rs1.getString("subject");
                    String sql4 = "select sum(v.total),count(v.total) from FEEDBACK_RESULT_TABLE n ,table(n.th) v where v.NAME_OF_PROF=?  AND v.semester=? AND v.Division=?";
                    pst = (OraclePreparedStatement) con.prepareStatement(sql4);
                    pst.setString(1, initial);
                    pst.setString(2, s);
                    pst.setString(3, div);
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
                    String sql5 = "select sum(v.total),count(v.total) from FEEDBACK_RESULT_TABLE n ,table(n.pr) v where v.NAME_OF_PROF=?  AND v.semester=? AND v.Division=?";
                    pst = (OraclePreparedStatement) con.prepareStatement(sql5);
                    pst.setString(1, initial);
                    pst.setString(2, s);
                    pst.setString(3, div);
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
                String sql8 = "insert into staff_result values (?,?,?)";
                pst = (OraclePreparedStatement) con.prepareStatement(sql8);
                pst.setString(1, n.toUpperCase());
                pst.setFloat(2, avgr);
                pst.setString(3, b);
                rs5 = (OracleResultSet) pst.executeQuery();
                sum = 0;
                c = 0;
                avgr = 0;
            }
            String sql9 = "select * from staff_result order by markz desc";
            pst = (OraclePreparedStatement) con.prepareStatement(sql9);
            rs6 = (OracleResultSet) pst.executeQuery();
            int cn = 1;
            while (rs6.next()) {
                n = rs6.getString("name");
                in = rs6.getString("markz");

                float inn = Float.parseFloat(in.toString());
                if (in.equals("0")) {
                } else {
                    DefaultTableModel model = (DefaultTableModel) tab.getModel();
                    model.insertRow(tab.getRowCount(), new Object[]{cn, n, new DecimalFormat("##.##").format(inn)});
                    cn++;
                }
            }
            String sql0 = "delete from staff_result";
            pst = (OraclePreparedStatement) con.prepareStatement(sql0);
            rs5 = (OracleResultSet) pst.executeQuery();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void wte(String p) {
        String path = p;
        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet ws = wb.createSheet();

        TreeMap<String, Object[]> data = new TreeMap<>();
        data.put("0", new Object[]{tab.getColumnName(0), tab.getColumnName(1), tab.getColumnName(2)});
        String aaa = null;
        for (int i = 0; i < tab.getRowCount(); i++) {
            int z = i + 1;
            aaa = aaa + z;
            data.put(aaa, new Object[]{tab.getValueAt(i, 0), tab.getValueAt(i, 1), tab.getValueAt(i, 2)});
        }
        Set<String> ids = data.keySet();
        XSSFRow row;
        int rowID = 0;
        for (String key : ids) {
            row = ws.createRow(rowID++);
            Object[] values = data.get(key);
            int cellID = 0;
            for (Object o : values) {
                XSSFCell cell = row.createCell(cellID++);
                cell.setCellValue(o.toString());
            }
        }
        try {
            FileOutputStream fos = new FileOutputStream(new File(path));
            wb.write(fos);

            JOptionPane.showMessageDialog(null, "Successfully Save");
            fos.close();
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

        jScrollPane1 = new javax.swing.JScrollPane();
        tab = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        save = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        tab.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Sr. No.", "Name", "Avg"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tab.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(tab);

        jButton1.setText("Back");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        save.setText("Save");
        save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addComponent(jButton1)
                        .addGap(151, 151, 151)
                        .addComponent(save, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 337, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(14, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(save))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 472, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void saveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveActionPerformed
        JFileChooser jf = new JFileChooser();
        jf.setDialogTitle("Save Excel File");
        int res = jf.showSaveDialog(null);
        if (res == JFileChooser.APPROVE_OPTION) {
            String Excelpath = jf.getSelectedFile().getAbsolutePath();
            wte(Excelpath + ".xlsx");
        }
    }//GEN-LAST:event_saveActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        new principal().setVisible(true);
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
            java.util.logging.Logger.getLogger(pralll.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(pralll.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(pralll.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(pralll.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new pralll().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton save;
    private javax.swing.JTable tab;
    // End of variables declaration//GEN-END:variables
}
