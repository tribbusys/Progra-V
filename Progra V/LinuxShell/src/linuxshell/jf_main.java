package linuxshell;

import com.jcraft.jsch.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Properties;
import java.util.logging.Level;
import javax.swing.JOptionPane;

/*
    * @author DELL
 */
public class jf_main extends javax.swing.JFrame {

    public static String commands;    
    public static String msgReturn;
    public static String host="192.168.1.6";
    public static String user="ever";
    public static String password="4ever";
    public static int port=22;
    public JSch jsch;
    public Session session;
    public Properties config;
    public ChannelExec channel;
    public BufferedReader in;

    
    public jf_main() {
        initComponents();
        txtCommands.setText("~");
        txtCommands.setCaretColor(java.awt.Color.GREEN);
        txtCommands.requestFocus();
        txtCommands.setCaretPosition(1);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtCommands = new javax.swing.JTextArea();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jButton1.setText("SEND");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        txtCommands.setBackground(new java.awt.Color(0, 0, 0));
        txtCommands.setColumns(20);
        txtCommands.setFont(new java.awt.Font("Palatino Linotype", 0, 13)); // NOI18N
        txtCommands.setForeground(new java.awt.Color(0, 139, 0));
        txtCommands.setRows(5);
        txtCommands.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        txtCommands.setCursor(new java.awt.Cursor(java.awt.Cursor.MOVE_CURSOR));
        jScrollPane1.setViewportView(txtCommands);

        jButton2.setText("Connect");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(72, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(297, 297, 297))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 691, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(39, 39, 39))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(90, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(48, 48, 48)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(23, 23, 23))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        commands = txtCommands.getText().replace("~", "");
        String result = exeShellCommand(commands);
        txtCommands.setText(commands + "\n Output:\n " + result + "\n-----------------------------------------------------------");
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        try {
            jsch=new JSch();
            session=jsch.getSession(jf_main.user, jf_main.host, jf_main.port);
            session.setPassword(password);
            config = new Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
            session.connect();
            JOptionPane.showMessageDialog(this, "Connected to server");
            System.out.println("********************************************connected*****************************************");
            
            
        } catch (JSchException ex) {
            java.util.logging.Logger.getLogger(jf_main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    public String exeShellCommand(String command) {
        try{
            channel = (ChannelExec) session.openChannel("exec");
            channel.setCommand(command);
            channel.connect();
            
            in = new BufferedReader(new InputStreamReader(channel.getInputStream()));
            

            jf_main.msgReturn = "";
            String msg=null;
            while((msg=in.readLine())!= null){
                if (msg != null){
                    System.out.println(msg);
                    jf_main.msgReturn += "\n" + msg;
                }
            }
            
            return jf_main.msgReturn;
        }catch(Exception e){
            channel.disconnect();
            session.disconnect();
            e.printStackTrace();
            return "Fail " + e.getMessage();
        }

    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea txtCommands;
    // End of variables declaration//GEN-END:variables
}
