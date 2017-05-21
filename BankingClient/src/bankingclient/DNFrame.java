/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bankingclient;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import org.apache.commons.lang.NumberUtils;

/**
 *
 * @author this
 */
public class DNFrame extends javax.swing.JFrame {

    /**
     * Creates new form DNFrame
     */
    private MainFrame main;
    private NewOrOldAccFrame noAcc;
    
    public DNFrame(MainFrame vmain) {
        
        initComponents();
        
        this.jTextField1.setText("");
        this.jTextField2.setText("");
        this.jTextField_cmt.setText("");
        this.jTextField_sdt.setText("");
        
        this.main = vmain;
        noAcc = new NewOrOldAccFrame(this);
        this.setVisible(false);
        jL_sdt.setVisible(false);
        jL_cmtnd.setVisible(false);
        jTextField_cmt.setVisible(false);
        jTextField_sdt.setVisible(false);
        
        jBt_dn.addActionListener(new ActionListener(){
            
            public void actionPerformed(ActionEvent e){
                if(!jCheck_qmk.isSelected()){
                    try{
                        Socket client = new Socket("113.22.46.207",6013);
                        DataOutputStream dout = new DataOutputStream(client.getOutputStream());
                        dout.writeByte(2);
                        dout.writeUTF(jTextField1.getText()+"\n"+jTextField2.getText());
                        dout.flush();
                        while(true){
                            break;
                        }
                        DataInputStream din = new DataInputStream(client.getInputStream());
                        byte check = din.readByte();
                        if(check==1){
                            noAcc.setVisible(true);
                            DNFrame.this.setVisible(false);
                            noAcc.setMainCustomer(jTextField1.getText());

                        }else{
                            JOptionPane.showMessageDialog(new JFrame(),"Tên Đang Nhập Không Tồn Tại, hoac mat khau sai");
                        }
                        
                    }catch(Exception ex){
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(rootPane,"Có Lỗi Kết Nối Mạng....");
                    }
                }else if((!jTextField_cmt.getText().equals("")) && (!jTextField_sdt.getText().equals("")) && (NumberUtils.isNumber(jTextField_cmt.getText())) && (NumberUtils.isNumber(jTextField_sdt.getText()))){
                    try{
                        Socket client = new Socket("113.22.46.207",6013);
                        DataOutputStream dout = new DataOutputStream(client.getOutputStream());
                        dout.writeByte(9);
                        dout.writeUTF(jTextField1.getText() + "\n" + jTextField_sdt.getText() + "\n" + jTextField_cmt.getText());
                        dout.flush();
                        DataInputStream din = new DataInputStream(client.getInputStream());
                        byte check = din.readByte();
                        if(check==1){
                            noAcc.setVisible(true);
                            DNFrame.this.setVisible(false);
                            noAcc.setMainCustomer(jTextField1.getText());
                        }else{
                            JOptionPane.showMessageDialog(new JFrame(),"Khong dang nhap duoc, thong tin sai");
                        }
                    }catch(Exception ex){
                        ex.printStackTrace();
                    }
                }else{
                    JOptionPane.showMessageDialog(new JFrame(),"Can dien day du thong tin va dung mau");
                }
            }
        });
        jBt_ql.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                main.setVisible(true);
                DNFrame.this.setVisible(false);
            }
        });
        jCheck_qmk.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(jCheck_qmk.isSelected()){
                   jL_sdt.setVisible(true);
                   jL_cmtnd.setVisible(true);
                   jTextField_cmt.setVisible(true);
                   jTextField_sdt.setVisible(true);
                }else{
                   jL_sdt.setVisible(false);
                   jL_cmtnd.setVisible(false);
                   jTextField_cmt.setVisible(false);
                   jTextField_sdt.setVisible(false);
                }
            }
        });
    }
    
    public void resetUI(){
        this.jTextField1.setText("");
        this.jTextField2.setText("");
        this.jTextField_cmt.setText("");
        this.jTextField_sdt.setText("");
        jCheck_qmk.setSelected(false);
        jL_sdt.setVisible(false);
        jL_cmtnd.setVisible(false);
        jTextField_cmt.setVisible(false);
        jTextField_sdt.setVisible(false);
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
        jLabel2 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jBt_dn = new javax.swing.JButton();
        jBt_ql = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jCheck_qmk = new javax.swing.JCheckBox();
        jL_sdt = new javax.swing.JLabel();
        jTextField_sdt = new javax.swing.JTextField();
        jL_cmtnd = new javax.swing.JLabel();
        jTextField_cmt = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Tên Đăng Nhập");

        jLabel2.setText("Mật Khẩu");

        jBt_dn.setText("Đăng Nhập");

        jBt_ql.setText("Quay Lại");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 153, 0));
        jLabel3.setText("Đăng Nhập Hệ Thống");

        jCheck_qmk.setText("Quên Mật Khẩu");
        jCheck_qmk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheck_qmkActionPerformed(evt);
            }
        });

        jL_sdt.setText("Sdt");

        jL_cmtnd.setText("cmtnd");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(119, Short.MAX_VALUE)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(87, 87, 87))
            .addGroup(layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jBt_ql)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jBt_dn)
                        .addGap(52, 52, 52))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jL_cmtnd, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jL_sdt, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextField2, javax.swing.GroupLayout.DEFAULT_SIZE, 171, Short.MAX_VALUE)
                            .addComponent(jTextField1)
                            .addComponent(jCheck_qmk)
                            .addComponent(jTextField_sdt)
                            .addComponent(jTextField_cmt))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(41, 41, 41)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jCheck_qmk)
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField_sdt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jL_sdt))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jL_cmtnd)
                    .addComponent(jTextField_cmt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 63, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBt_ql)
                    .addComponent(jBt_dn))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jCheck_qmkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheck_qmkActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCheck_qmkActionPerformed

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBt_dn;
    private javax.swing.JButton jBt_ql;
    private javax.swing.JCheckBox jCheck_qmk;
    private javax.swing.JLabel jL_cmtnd;
    private javax.swing.JLabel jL_sdt;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField_cmt;
    private javax.swing.JTextField jTextField_sdt;
    // End of variables declaration//GEN-END:variables
}
