/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backupdatabase;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.sql.Connection;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author Mr-Tuy
 */
public class BackUpDatabase {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Timer timer = new Timer();
        TimerTask myTask = new TimerTask() {
            @Override
            public void run() {
                try{
                    Socket client = new Socket("113.22.46.207",6013);
                    DataOutputStream dout = new DataOutputStream(client.getOutputStream());
                    dout.writeByte(13);
                    dout.flush();
                    DataInputStream din = new DataInputStream(client.getInputStream());
                    Scanner scannerBackUp = new Scanner(din.readUTF());
                    while(scannerBackUp.hasNextLine()){
                        try{
                            Connection con = ConnectToDatabase.connect();
                            con.createStatement().executeUpdate(scannerBackUp.nextLine());
                            con.close();
                        }catch(Exception exx){
                            exx.printStackTrace();
                        }
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        };

        timer.schedule(myTask, 30000, 30000);
    }
    
}
