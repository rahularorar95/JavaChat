

/**
 *
 * @author sam
 */
import java.io.*;
import java.net.*;
import java.awt.*;
import java.util.*;
import javax.swing.*;

public class server extends JFrame{
     JTextArea text;
    JTextArea chatwindow;
DataOutputStream output;
    DataInputStream input;
   
     static int uniqueid;
   ArrayList al;
    ArrayList privatechat;
    Vector al2,privatechatu;
   String user;
   boolean ko2;
   int r=0;
    public server(){
        super("server");
        text=new JTextArea();
      
       chatwindow=new JTextArea();
        
                
        setLayout(null);
        
        JScrollPane js2=new JScrollPane(text);
     add(js2); 
     js2.setBounds(10,5,470,200);
     text.setFont(new Font("SansSerif", Font.BOLD, 12));
     text.setEditable(false);
     text.setText("                                             ONLINE CLIENTS\n");
     JScrollPane js=new JScrollPane(chatwindow);
     add(js);
     js.setBounds(8, 220, 470, 230);
    chatwindow.setEditable(false);
     setVisible(true);
     setSize(500,500);
        setLocationRelativeTo(null);
     al=new ArrayList();
     al2=new Vector();
     privatechat=new ArrayList();
     privatechatu=new Vector();
    }
    
    server(String s){
        
    }
    
    Vector getlist(){
        return this.al2;
    }
    
    public void start() throws IOException, ClassNotFoundException{
        
        ko2=true;
        
            ServerSocket ss=new ServerSocket(4444);
            while(ko2){
                setTitle("Waiting for someone to connect");
                    Socket con=ss.accept();
                    showMessage("Now connected to "+con.getInetAddress().getHostName()+"\n"); 
                    
                     setTitle("Server");
                    
                     
                      
           
           
                    Clientth th=new Clientth(con);
                    th.start();
                    
                     al.add(th);       
                    
                    
                  
                    
            }
                                 
                              ss.close();
				for(int i = 0; i < al.size(); ++i) {
					Clientth tc = (Clientth) al.get(i);
					try {
					tc.sinput.close();
					tc.soutput.close();
					tc.con.close();
					}
					catch(IOException ioE) {
					
					}
                }
     
                                
        
    }
      
    
    public void showlist(Vector a){
       text.setText("                                              ONLINE CLIENTS\n");
        for(int i=0;i<a.size();i++){
            if(!(a.elementAt(i).toString().contains("private"))){
        text.append("@"+"            "+(String) a.elementAt(i)+"\n");
        
        }
        }
       
    }
    
    
    void showMessage(String mes){
    
         chatwindow.append(mes);
    }
    
   public void abletotype(final boolean b) {
      

         text.setEditable(b);
    }

     
 
     
     
    class Clientth extends Thread{
            Socket con;
		DataInputStream sinput;
		DataOutputStream soutput;
                String mes,user;
                 int id;
                
        public Clientth(Socket con) {
                try {
                    this.con=con;
                   
                    id=++uniqueid;
                   
                    sinput=new DataInputStream(con.getInputStream());
                    soutput=new DataOutputStream(con.getOutputStream());
                  
                  
                } catch (IOException ex) {
                    showMessage("Exception : " + ex);
				
                }
        }
        
        
        public void run() {
            boolean ko=true;
          StringTokenizer st;
            while(ko){
                try {
                    
                    
                     mes=sinput.readUTF(); 
                     
                     if(mes.contains("privatechat")){
                         
                         int flag=0;
                          st=new StringTokenizer(mes);
                          st.nextToken();
                          String user=st.nextToken();
                          String rec=st.nextToken();
                          
                          for(int i=0;i<al.size();i++){
                              String abc=(String) al2.elementAt(i);
                              if(abc.equals(rec)){  
                                 flag=1;
                              break;
                          }
                          }
                          
                          
                          if(flag==1){
                              System.out.println("sgerge");
                               for(int i=0;i<al.size();i++){
                              String abc=(String) al2.elementAt(i);
                              if(abc.equals(rec)){  
                                
                              Clientth tc=(Clientth) al.get(i);
                              tc.soutput.writeUTF("pri"+user);
                            
                              break;
                          }
                          }
                          }else{
                              
                               System.out.println("sgerge");
                          }
                          
                         
                             
                         
                          
                          
                     }
                     else if(mes.contains("@#")){
                         user=mes.replace("@#", "");
                         //sleep(500);
                        al2.add(user);
                         showlist(al2);
                        broadcast(text.getText());
                     }
                     
                     else if(mes.contains("letschatprivate")){
                        
                        // System.out.println("helllo");
                        
                     st=new StringTokenizer(mes);
                     st.nextToken();
                     String useraccepted=st.nextToken();
                     String userrequested=st.nextToken();
                         //System.out.println(useraccepted+" "+userrequested);
                        /*
                         for(int i=0;i<al.size();i++){
                              String abc=(String) al2.elementAt(i);
                              if(abc.equals(useraccepted)){   
                              Clientth tc1=(Clientth) al.get(i);
                             // privatechat.add(al.get(i));
                                 // System.out.println(al.get(i));
                              //privatechatu.add(useraccepted);
                                //  removeid(tc1.id);
                                //  System.out.println(privatechat.get(i));
                          }
                          }
                       
                         for (int i = 0; i < al.size(); i++) {
                             
                              String abc=(String) al2.elementAt(i);
                             if(abc.equals(userrequested)){
                                  Clientth tc=(Clientth) al.get(i);
                                 //  System.out.println(al.get(i));
                                  tc.soutput.writeUTF("ok");
                                //  privatechat.add(al.get(i));
                                  //privatechatu.add(userrequested);
                                 // removeid(tc.id);
                               
                                //  System.out.println(privatechat.get(i));
                              }
                             
                         }
                         
                         
                          
                          
                         
                           
                         //update list at useraccepted
                      /*   for(int i=0;i<privatechat.size();i++){
                              String abc=(String) privatechatu.elementAt(i);
                              
                              
                              if(abc.equals(useraccepted)){   
                                  
                              Clientth tc=(Clientth) privatechat.get(i);
                                tc.soutput.writeUTF(text.getText());
                                
                                  tc.soutput.writeUTF("connecttogether");
                                  break;
                          }
                          }*/
                        
                        
                        for(int i=0;i<al.size();i++){
                            String abc=(String) al2.elementAt(i);
                            if(abc.equals(userrequested)){
                                Clientth tc=(Clientth) al.get(i);
                                tc.soutput.writeUTF("connecttogether");
                               
                            }
                        }
                        
                        
                        for(int i=0;i<al.size();i++){
                            String abc=(String) al2.elementAt(i);
                            if(abc.equals(useraccepted)){
                                Clientth tc=(Clientth) al.get(i);
                                tc.soutput.writeUTF("connecttogether");
                                
                                
                            }
                        }
                         
                        
                       
                         
                     }
                     else if(mes.contains("^")){
                         
                         
                         String usertosend,msg,a;
                         mes=mes.replace("^", "");
                         
                         
                         st=new StringTokenizer(mes);
                        // System.out.println(mes);
                         a=st.nextToken();
                         msg=a+" "+st.nextToken();
                         usertosend=st.nextToken();
                        // System.out.println(usertosend);
                         
                           for (int i = 0; i < al.size(); i++) {
                             Clientth tc=(Clientth) al.get(i);
                             if(usertosend.equals(al2.elementAt(i))){
                                 tc.soutput.writeUTF(msg+"\n");
                                 break;
                             }
                         }
                         
                      //   broadcastp(mes+"\n");
                         
                     }else if(mes.contains("logout")){
                         
                         mes=mes.replace("logout", "");
                        
                         for (int i = 0; i < al.size(); i++) {
                             Clientth tc=(Clientth) al.get(i);
                             String abc=(String) al2.elementAt(i);
                             if(abc.equals(mes)){
                                
                                 if(abc.contains("private")){
                                         tc.soutput.writeUTF("removep");
                                       removeid(tc.id);
                                       break;
                                                  }else{
                                      tc.soutput.writeUTF("remove");
                                       removeid(tc.id);
                                       break;
                                 }
                                
                                
                                 
                             }
                             
                             
                         }
                         
                         
                     }else if(mes.contains("removeme")){
                         mes=mes.replace("removeme", "");
                         for (int i = 0; i < al.size(); i++) {
                             
                             Clientth tc=(Clientth) al.get(i);
                             String abc=(String) al2.elementAt(i);
                             if(abc.equals(mes)){
                                 removeid(tc.id);
                                 break;
                             }
                         }
                         
                     }
                     
                     else if(mes.contains("tellhim")){
                         
                         mes=mes.replace("tellhim", "");
                         for (int i = 0; i < al.size(); i++) {
                             
                             Clientth tc=(Clientth) al.get(i);
                             String abc=(String) al2.elementAt(i);
                             if(abc.equals(mes)){
                                 tc.soutput.writeUTF("usergone");
                                 break;
                             }
                         }
                     }
                       
                       
                       /*
                         for (int i = 0; i < al.size(); i++) {
                             String abc=(String) al2.elementAt(i);
                             if(abc.contains("private")){
                                Clientth tc=(Clientth) al.get(i);
                                 privatechat.add(al.get(i));
                                 privatechatu.add(abc);
                                 removeid(tc.id);
                               //break;
                             }
                         }*/
                         /*
                        for (int i = 0; i < al.size(); i++) {
                             String abc=(String) al2.elementAt(i);
                             if(abc.contains("private")){
                                Clientth tc=(Clientth) al.get(i);
                                 privatechat.add(al.get(i));
                                 privatechatu.add(abc);
                                 removeid(tc.id);
                                 break;
                             }
                         }
                        */
                         
                        
                         
                        
                         
                     
                         else{
                     broadcast(mes+"\n");
                     }
                } catch (IOException ex) {
                    ko=false;
                   
                } 
                
               // broadcast(username +" :"+mes+"\n");
                
            }
            
            removeid(id);
        
            close();
        }

        
       
        
         void broadcast(String string) {
           //  System.out.println(al.size()+" "+al2.size());
            for(int i = al.size(); --i >= 0;) {
			Clientth ct = (Clientth) al.get(i);
			    
                        if(!al2.elementAt(i).toString().contains("private")){
                             
			if(!ct.writeMsg(string)) {
				al.remove(i);   
                                al2.remove(i);
				showMessage(ct.user + " removed from list.");
			}
            }
		}
            
        }
        
        
        
        
        
        public void removeid(int id) {
            
            for(int i=0; i < al.size(); ++i) {
			Clientth ct = (Clientth) al.get(i);
			
			if(ct.id == id) {
				al.remove(i);
                                al2.remove(i);
                               showlist(al2);
                               broadcast(text.getText());
                            
                                showMessage(ct.user + " removed from list.\n");
                                if(al.size()<=0){
                         setTitle("Waiting For Someone to connect");
                     }else{
                         setTitle("Server");
                     }
				return;
			}
		}
        }
        
        private void close() {
		
			try {
				if(soutput != null) soutput.close();
			}
			catch(Exception e) {}
			try {
				if(sinput != null) sinput.close();
			}
			catch(Exception e) {};
			try {
				if(con != null) con.close();
			}
			catch (Exception e) {}
		}

      boolean writeMsg(String string) {
            
            if(!con.isConnected()) {
				
				return false;
			}
			
			try {
                            
				soutput.writeUTF(string);
                                soutput.flush();
			}
			
			catch(IOException e) {
				showMessage("Error sending message to " + user+"\n");
				//ko2=false;
                                
			}
			return true;
        }

        

    }
    
    
    
   

}
