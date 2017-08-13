import java.awt.*;
import java.awt.Font;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import static java.lang.System.exit;
import java.net.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

public class client extends JFrame{
    JTextField text;
    JTextPane chatwindow;
    JTextArea online;
    JMenuBar m1;
    JMenu me1,me2;
    JMenuItem mi1,mi2;
    JButton b1;
    DataOutputStream output;
    DataInputStream input;
     Socket con;
     String chatuser;
     String onlinelist;
    String message="";
    String serverip;
   String user,accpt;
    int cnt,cnt2;
    client c;
    Vector al2;
    JScrollPane js;
  int p;
    client(String host,String u){
    
     serverip=host;
     user=u;
     text=new JTextField();
     chatwindow=new JTextPane();
     online=new JTextArea();
     b1=new JButton("<- SEND");
     cnt=1;
     cnt2=1;
    
     text.addActionListener(
             new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
             
           /*  if(cnt==1){
                 chatwindow.setText("");
                 cnt++;
             }*/
             
            
             senddata(e.getActionCommand());
             text.setText("");
         }
     }
             );
     
     setLayout(null);
     add(text); 
     text.setBounds(8,3,350,25);
     
     add(b1);
     b1.setBounds(370,3,110,25);
      b1.setFont(new Font("Lucida Console", 2, 12)); 
        b1.setForeground(Color.white);   
         b1.setBackground(new Color(150, 37, 37));
         b1.setFocusPainted(false);
       
         b1.addActionListener(
         new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            /* if(cnt==1){
                 chatwindow.setText("");
                 cnt++;
             }
             */
            
             senddata(text.getText());
             text.setText("");
             
         }
     }
         );
         
         
     JScrollPane js3=new JScrollPane(online);
     add(js3);
     js3.setBounds(490, 3, 150, 346);
     js3.setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_NEVER);
    online.setFont(new Font("SansSerif", Font.BOLD, 12));
     online.setEditable(false);
    
      
     
     
     js=new JScrollPane(chatwindow);
     add(js);
     js.setBounds(8, 30, 470, 320);
     text.setEditable(false);
     chatwindow.setFont(new Font("SansSerif", Font.BOLD, 12));
       
      
        setResizable(false);
     setVisible(true);
     setSize(670,400);
     
    al2=new Vector();
    
    m1=new JMenuBar();
    me1=new JMenu("Private chat");
    m1.add(me1);
    
           
         me2=new JMenu("Logout");
    
   
    m1.add(me2);
        setJMenuBar(m1);
        me1.addMenuListener(new MenuListener() {
         @Override
         public void menuSelected(MenuEvent e) {
            
                 chatuser=JOptionPane.showInputDialog(null, "Name Of Client To Chat With:", "Private Chat", JOptionPane.QUESTION_MESSAGE);
            
             if(getlist().contains(chatuser)){
                 
                 try {
                     output.writeUTF("privatechat "+" "+user+" "+chatuser);
                    
                 } catch (IOException ex) {
                     
                 }
             }else{
                 JOptionPane.showMessageDialog(null, "User Is Not In The List");
             }
                
            
         }

         @Override
         public void menuDeselected(MenuEvent e) {
             
         }

         @Override
         public void menuCanceled(MenuEvent e) {
             
         }

        
     });
        
        me2.addMenuListener(new MenuListener() {
         @Override
         public void menuSelected(MenuEvent e) {
           
             try {
                
                 
               
                
                      output.writeUTF("logout"+user);
                   //   System.out.println(user);
                      //  exit(0);
                 
                   
             } catch (IOException ex) {
                
             }
         }

         @Override
         public void menuDeselected(MenuEvent e) {
             
         }

         @Override
         public void menuCanceled(MenuEvent e) {
             
         }
     });
        
      /*  me1.addActionListener(new ActionListener() { 
         @Override
         public void actionPerformed(ActionEvent e) {
            // System.out.println("hellllllllloooo");
             chatuser=JOptionPane.showInputDialog(null, "Name Of Client To Chat With:", "Private Chat", JOptionPane.QUESTION_MESSAGE);
            
             if(getlist().contains(chatuser)){
                 
                 try {
                     output.writeUTF("privatechat "+" "+user+" "+chatuser);
                    
                 } catch (IOException ex) {
                     
                 }
             }
             
             
         }
     });*/
        
    }

   
    public void start() throws IOException, InterruptedException  {
       
        try {
            
            con=new Socket(serverip,4444);
        } catch (IOException ex) {
            showMessage("Server is Offline..\n");
               
        }
        String msg = "Connection accepted " + con.getInetAddress() + ":" + con.getPort()+"\n";
         
		showMessage(msg);
                
                try{
                    
                input=new DataInputStream(con.getInputStream());
                output=new DataOutputStream(con.getOutputStream());
                output.writeUTF(user+"@#");    
                   // System.out.println(user);
                }catch(Exception e){
                    
                    showMessage("Exception creating new Input/output Streams\n");
                    closeclient();
                    start();
                }
               
                listenfromserver l=new listenfromserver();
                l.start();
               
                abletotype(true);
        
    }
    
    
    
     void senddata(String message){
        
        if(p==1){
            if(message.equals("")){
                JOptionPane.showMessageDialog(null, "Enter Your Message !");
            }
            else{
                try {
                   // System.out.println(user+" "+chatuser);
                    output.writeUTF("^"+user.toUpperCase()+">> "+message+" "+chatuser);
                    output.flush();
                } catch (IOException ex) {
                    
                }
            }
            
        }else{
        
        try{
            
            
            if(message.equals("")){
                JOptionPane.showMessageDialog(null, "Enter Your Message !");
            }else{
                
        
            output.writeUTF(user.toUpperCase()+">> "+message);
            output.flush();
           
            }
        }catch(IOException io){
      
      chatwindow.replaceSelection("\\n something messed up !");
        }
        
    }
    }
    

   void closeclient() throws IOException {
        showMessage("\n Closing Down....");
        abletotype(false);   
        output.close();
        input.close();
        con.close();
 
    }

   void showMessage(String m) {
      
         String a=user.toUpperCase();
         
       m=m.replace(a,"YOU");
         chatwindow.replaceSelection(m);
       
    }
 
    void abletotype(final boolean b) {
      
       text.setEditable(b);

    }
 
    
    class listenfromserver extends Thread {

		public void run() {
			while(true)
                        {
				try {
                                    
					String msg =  input.readUTF();
                                       
                                        if(msg.contains("ONLINE CLIENTS")){
                                            String a=user;
                                           
                                            if(msg.contains(a)){
                                               
                                                msg=msg.replace("@            "+a+"\n","");
                                                
                                                online.setText("           Chat Room:\n"+msg);
                                            }else{
                                                online.setText("           Chat Room:\n"+msg);
                                            }
                                            givelist(msg);
                                        }
                                        else if(msg.contains("pri")){
                                            msg=msg.replace("pri", "");
                                           chatuser=msg;
                                          Object[] options = { "Agree", "Disagree" };

                                            int a=JOptionPane.showOptionDialog(null, msg.toUpperCase()+" wants to chat private with you !", "information",JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,null, options, options[0]);
                            
                                             if(a==0){
                                             output.writeUTF("letschatprivate"+" "+user+" "+msg);
                                                 // System.out.println(user+" "+chatuser);     
                                             }              
                                        }
                                        else if(msg.contains("connecttogether")){
                                            
                                            
                                             client c=new client(serverip,user+"_private");
                                           c.setVisible(true);
                                          c.chatuser=chatuser+"_private";
                                                  c.p=1;
                                                  c.online.setVisible(false);
                                                  c.me1.setEnabled(false);
                                                  c.setSize(500,400);
                                                  c.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
                                                  c.setLocationRelativeTo(null);
                                                  c.me2.setText("Quit");
                                           c.setTitle(user+" private chat with "+chatuser);
                                          
                                            try {
                                                
                                                c.start();
                                               
                                                
                                              
                                               
                                            } catch (InterruptedException ex) {
                                                showMessage("Server is Offline\n");
                                            }
                                        }else if(msg.equals("remove")){
                                           
                                            
                                       /*    client c=new client("127.0.0.1",user+" private");
                                           c.setVisible(true);
                                       // c.user=user+" private";
                                          //  System.out.println(c.user);
                                           c.setTitle(user+" private chat with "+chatuser);
                                            try {
                                                c.start();
                                                c.p=1;
                                                
                                                output.writeUTF("newthread");
                                            } catch (InterruptedException ex) {
                                                showMessage("Server is Offline\n");
                                            }
                                            */
                                            setTitle("Loggging offf !!");
                                            text.setEnabled(false);
                                            me1.setEnabled(false);
                                            sleep(2000);
                                            exit(0);
                                         
                                        }else if(msg.equals("removep")){
                                           output.writeUTF("tellhim"+chatuser);
                                           dispose();
                                            //JOptionPane.showMessageDialog(null, "User Not Found..!!");
                                        }else if(msg.equals("usergone")){
                                            
                                            showMessage("                              USER GONE OFFLINE\n                              CLOSING DOWN\n\n");
                                            text.setEnabled(false);
                                            me2.setEnabled(false);
                                            output.writeUTF("removeme"+user);
                                            sleep(3000);
                                            dispose();
                                        }
                                        else{
                                             
					 showMessage(msg);
                                            
                                        }
                                         
				}
				catch(IOException e) {
					showMessage("Server has close the connection\n");
                                    try {
                                        closeclient();
                                        break;
                                       
                                    } catch (IOException ex) {
                                        
                                    }
				
			}   catch (InterruptedException ex) {
                                Logger.getLogger(client.class.getName()).log(Level.SEVERE, null, ex);
                            }
		}
                     
	}

        
             
}
    
     void givelist(String msg) {
            onlinelist=msg;
            
        }
    
   String getlist() {
           
        return this.onlinelist;
        }
}