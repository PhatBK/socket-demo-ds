/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bankingclient;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JComboBox;
import java.net.*;
import java.io.*;
import java.util.Scanner;
import javax.swing.JOptionPane;
import org.apache.commons.lang.NumberUtils;

/**
 *
 * @author this
 */
public class ChonThaoTacFrame extends javax.swing.JFrame {

    /**
     * Creates new form ChonThaoTacFrame
     */
    private NewOrOldAccFrame noAcc;
    private List<String> accList;
    private List<String> cusList;
    private Thong_Tin_TK tt;
    
    public ChonThaoTacFrame(NewOrOldAccFrame acc) {
        initComponents();
        jTextField1.setText("");
        
        jLabel4.setVisible(false);
        jComboBox2.setVisible(false);
        jLabel2.setVisible(false);
        jLabel3.setVisible(false);
        jTextField1.setVisible(false);
        jBt_xn1.setVisible(false);
        jBt_xn2.setVisible(false);
        
        this.accList = null;
        this.cusList = null;
        this.noAcc = acc;
        this.tt = new Thong_Tin_TK(this);
        this.setVisible(false);
        
        
        jBt_xn1.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if(NumberUtils.isNumber(jTextField1.getText()) && (Long.parseLong(jTextField1.getText()) > 0)){
                    long currentMoney = 0;
                    try{
                        Socket client = new Socket("113.22.46.207",6013);


                        DataOutputStream dout = new DataOutputStream(client.getOutputStream());
                        dout.writeByte(8);
                        dout.writeUTF((String)jComboBox1.getSelectedItem());
                        dout.flush();


                        DataInputStream din = new DataInputStream(client.getInputStream());
                        Scanner lineScanner = new Scanner(din.readUTF());
                        currentMoney = Long.parseLong(lineScanner.nextLine());
                        System.out.println(currentMoney);

                    }catch(Exception ex){
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(rootPane,"Lỗi kết nối mạng,bạn cần kiểm tra kết nối");
                    }

                    if(jCheck_gt.isSelected()){
                        try{
                            Socket client = new Socket("113.22.46.207",6013);
                            DataOutputStream dout = new DataOutputStream(client.getOutputStream());
                            dout.writeByte(5);
                            dout.writeUTF((String)jComboBox1.getSelectedItem()+"\n"+jTextField1.getText()+"\n"+(noAcc.getCustomer()));
                            dout.flush();

                        }catch(Exception ex){
                            ex.printStackTrace();
                            JOptionPane.showMessageDialog(rootPane,"Có Lỗi Kết Nối Xảy ra....");
                        }
                        JOptionPane.showMessageDialog(rootPane,"Gửi Tiền Thành Công...");
                    }

                    if(jCheck_rt.isSelected()){
                        if((Long.parseLong(jTextField1.getText()) <= currentMoney)){
                            try{
                                Socket client = new Socket("113.22.46.207",6013);
                                DataOutputStream dout = new DataOutputStream(client.getOutputStream());
                                dout.writeByte(6);
                                dout.writeUTF((String)jComboBox1.getSelectedItem()+"\n"+jTextField1.getText()+"\n"+(noAcc.getCustomer()));
                                dout.flush();

                            }catch(Exception ex){
                                ex.printStackTrace();
                                JOptionPane.showMessageDialog(rootPane,"Có Lỗi Kết Nối Xảy Ra.....");
                            }
                            JOptionPane.showMessageDialog(rootPane,"Rút Tiền Thành Công ...");
                        }else{
                            System.out.println("Không đủ Tiền Trong tài khoản.." + currentMoney);
                            JOptionPane.showMessageDialog(null,"Tài Khoản Không Đủ Để Rút ...");
                        }
                    }


                    noAcc.setVisible(true);
                    ChonThaoTacFrame.this.setVisible(false);
                }else{
                    JOptionPane.showMessageDialog(rootPane, "Cần Nhập Lại Số Tiền Cần Gửi Hoặc Rút..");
                }
                
            }
        });
        
        jBt_tt.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                tt.setTk((String)jComboBox1.getSelectedItem());
                tt.hienTenTk();
                long currentMoney = 0;
                try{
                    Socket client = new Socket("113.22.46.207",6013);
                    
                    
                    DataOutputStream dout = new DataOutputStream(client.getOutputStream());
                    dout.writeByte(8);
                    dout.writeUTF((String)jComboBox1.getSelectedItem());
                    dout.flush();
                    
                    
                    DataInputStream din = new DataInputStream(client.getInputStream());
                    Scanner lineScanner = new Scanner(din.readUTF());
                    currentMoney = Long.parseLong(lineScanner.nextLine());
//                    System.out.println(currentMoney);
                  
                }catch(Exception ex){
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(rootPane,"Lỗi kết nối mạng,bạn cần kiểm tra kết nối");
                }
                tt.hienSoDu(((Long)currentMoney).toString());
                tt.setVisible(true);
                try{
                    Socket client = new Socket("113.22.46.207",6013);
                    
                    DataOutputStream dout = new DataOutputStream(client.getOutputStream());
                    dout.writeByte(10);
                    dout.writeUTF((String)jComboBox1.getSelectedItem());
                    dout.flush();
                    
                    DataInputStream din = new DataInputStream(client.getInputStream());
                    Scanner cusScanner = new Scanner(din.readUTF());
                    while(cusScanner.hasNextLine()){
                        tt.addCus(cusScanner.nextLine());
                    }
                }catch(Exception ee){
                    ee.printStackTrace();
                }
                tt.hienChuTaiKhoan();
                try{
                    Socket client = new Socket("113.22.46.207",6013);
                    DataOutputStream dout = new DataOutputStream(client.getOutputStream());
                    dout.writeByte(12);
                    dout.writeUTF((String)jComboBox1.getSelectedItem());
                    dout.flush();
                    
                    DataInputStream din = new DataInputStream(client.getInputStream());
                    Scanner dateScanner  = new Scanner(din.readUTF());
                    int day = Integer.parseInt(dateScanner.nextLine());
                    int month = Integer.parseInt(dateScanner.nextLine());
                    int year = Integer.parseInt(dateScanner.nextLine());
                    String date = (day+"-"+month+"-"+year);
                    tt.hienNgayLapTaiKhoan(date);
                    while(dateScanner.hasNextLine()){
//                        System.out.println("aaa");
                        tt.addGiaoDich(dateScanner.nextLine());
                    }
                    tt.hienGiaoDich();
                }catch(Exception ex){
                    ex.printStackTrace();
                }
                
                ChonThaoTacFrame.this.setVisible(false);
            }
        });
        
        jBt_xn2.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if(jCheck_tctk.isSelected()){
                    try{
                        Socket client = new Socket("113.22.46.207",6013);
                        DataOutputStream dout = new DataOutputStream(client.getOutputStream());
                        dout.writeByte(7);
                        dout.writeUTF((String)jComboBox1.getSelectedItem()+"\n"+(String)jComboBox2.getSelectedItem());
                        dout.flush();
                    }catch(Exception ex){
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(rootPane,"Có Lỗi Kết Nối Xảy Ra\n Thêm Chủ Thất Bại...");
                    }
                    JOptionPane.showMessageDialog(rootPane,"Thêm Chủ Tài Khoản Thành Công..");
                }else{
                    System.out.println("nothing to do...");
                }
            }
        });
        
        jBt_xtk.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                try{
                    Socket client = new Socket("113.22.46.207",6013);
                    DataOutputStream dout = new DataOutputStream(client.getOutputStream());
                    dout.writeByte(11);
                    String sent = (String)(jComboBox1.getSelectedItem()) + "\n" +noAcc.getCustomer();
                    dout.writeUTF(sent);
                    dout.flush();
                    DataInputStream din = new DataInputStream(client.getInputStream());
                    byte check = din.readByte();
                    if(check == 1){
                        JOptionPane.showMessageDialog(rootPane, "xoa tai khoan thanh cong");
                    }else{
                        JOptionPane.showMessageDialog(rootPane, "<html>xoa tai khoan <b>khong</b> thanh cong <br> chi chu chinh moi co the xoa tai khoan</html>"); 
                    }
                    
                }catch(Exception ee){
                    ee.printStackTrace();
                    JOptionPane.showMessageDialog(rootPane,"Lỗi Kết Nối ,Vui Lòng Kiểm Tra Lại..");
                }
            }
        });
        
        /*dont touch*/
        jBt_ql.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                noAcc.setVisible(true);
                ChonThaoTacFrame.this.setVisible(false);
            }
        });
        /*dont touch*/
        
        
        jComboBox1.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                
            }
        });
        
        jComboBox2.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                
            }
        });
        
        /*dont touch jcheckbox*/
        jCheck_tctk.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if(jCheck_tctk.isSelected()){
                    jLabel4.setVisible(true);
                    jComboBox2.setVisible(true);
                    jBt_xn2.setVisible(true);
                }else{
                    jLabel4.setVisible(false);
                    jComboBox2.setVisible(false);
                    jBt_xn2.setVisible(false);
                }
            }
        });
        jCheck_gt.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if(jCheck_gt.isSelected()){    
                    if(jCheck_rt.isSelected()){
                        jCheck_rt.setSelected(false);
                    }
                    jLabel2.setVisible(true);
                    jLabel3.setVisible(true);
                    jTextField1.setVisible(true);
                    jBt_xn1.setVisible(true);
                }else{
                    jLabel2.setVisible(false);
                    jLabel3.setVisible(false);
                    jTextField1.setVisible(false);
                    jBt_xn1.setVisible(false);
                }
            }
        });
        jCheck_rt.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if(jCheck_rt.isSelected()){    
                    if(jCheck_gt.isSelected()){
                        jCheck_gt.setSelected(false);
                    }
                    jLabel2.setVisible(true);
                    jLabel3.setVisible(true);
                    jTextField1.setVisible(true);
                    jBt_xn1.setVisible(true);
                }else{
                    jLabel2.setVisible(false);
                    jLabel3.setVisible(false);
                    jTextField1.setVisible(false);
                    jBt_xn1.setVisible(false);
                }
            }
        });
        /*dont touch jcheckbox*/
    }
    
    public void setAccList(List<String> list){
        this.accList = list;
    }
    public void setCusList(List<String> list){
        this.cusList = list;
    }
    public JComboBox getJCB1(){
        return this.jComboBox1;
    }
    public JComboBox getJCB2(){
        return this.jComboBox2;
    }
    
    public void resetUI(){
        jTextField1.setText("");
        jCheck_gt.setSelected(false);
        jCheck_rt.setSelected(false);
        jCheck_tctk.setSelected(false);
        jLabel4.setVisible(false);
        jComboBox2.setVisible(false);
        jLabel2.setVisible(false);
        jLabel3.setVisible(false);
        jTextField1.setVisible(false);
        jBt_xn1.setVisible(false);
        jBt_xn2.setVisible(false);
        
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
        jTextArea1 = new javax.swing.JTextArea();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jCheck_gt = new javax.swing.JCheckBox();
        jCheck_rt = new javax.swing.JCheckBox();
        jLabel2 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jCheck_tctk = new javax.swing.JCheckBox();
        jLabel4 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox<>();
        jBt_ht = new javax.swing.JButton();
        jBt_ql = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jBt_xn1 = new javax.swing.JButton();
        jBt_xn2 = new javax.swing.JButton();
        jBt_xtk = new javax.swing.JButton();
        jBt_tt = new javax.swing.JButton();

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(204, 204, 255));

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel1.setText("Chọn Tài Khoản");

        jCheck_gt.setText("Gửi Tiền");

        jCheck_rt.setText("Rút Tiền");

        jLabel2.setText("Số Tiền(Gửi hoặc Rút)");

        jLabel3.setText("(VND)");

        jCheck_tctk.setText("Thêm Chủ Tài Khoản");
        jCheck_tctk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheck_tctkActionPerformed(evt);
            }
        });

        jLabel4.setText("Chọn Chủ Tài Khoản");

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox2ActionPerformed(evt);
            }
        });

        jBt_ht.setText("Hoàn Tất");
        jBt_ht.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBt_htActionPerformed(evt);
            }
        });

        jBt_ql.setText("Quay Lại");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 153, 0));
        jLabel5.setText("Thao Tác Với Tài Khoản");

        jBt_xn1.setText("Xác Nhận");

        jBt_xn2.setText("Xác Nhận");
        jBt_xn2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBt_xn2ActionPerformed(evt);
            }
        });

        jBt_xtk.setText("Xóa Tài Khoản");

        jBt_tt.setText("Thông Tin TK");
        jBt_tt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBt_ttActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(18, 18, 18)
                                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(78, 78, 78)
                                .addComponent(jBt_tt))
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jBt_xtk))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jCheck_gt)
                                .addGap(28, 28, 28)
                                .addComponent(jCheck_rt))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jCheck_tctk)
                                .addGap(56, 56, 56)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextField1)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel4)
                                        .addGap(33, 33, 33)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jBt_ql, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(0, 0, Short.MAX_VALUE)))))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jBt_xn1, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jBt_xn2, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jBt_ht, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(86, 86, 86))
            .addGroup(layout.createSequentialGroup()
                .addGap(211, 211, 211)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addGap(70, 70, 70)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBt_xtk)
                    .addComponent(jBt_tt))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCheck_gt)
                    .addComponent(jCheck_rt))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(jBt_xn1))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCheck_tctk)
                    .addComponent(jLabel4)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBt_xn2))
                .addGap(64, 64, 64)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBt_ql)
                    .addComponent(jBt_ht))
                .addGap(44, 44, 44))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jBt_htActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBt_htActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jBt_htActionPerformed

    private void jBt_ttActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBt_ttActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_jBt_ttActionPerformed

    private void jBt_xn2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBt_xn2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jBt_xn2ActionPerformed

    private void jComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox2ActionPerformed

    private void jCheck_tctkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheck_tctkActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCheck_tctkActionPerformed

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBt_ht;
    private javax.swing.JButton jBt_ql;
    private javax.swing.JButton jBt_tt;
    private javax.swing.JButton jBt_xn1;
    private javax.swing.JButton jBt_xn2;
    private javax.swing.JButton jBt_xtk;
    private javax.swing.JCheckBox jCheck_gt;
    private javax.swing.JCheckBox jCheck_rt;
    private javax.swing.JCheckBox jCheck_tctk;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
