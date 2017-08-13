import java.io.IOException;
import java.net.UnknownHostException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**

 * @author sam
 */
public class clienttest extends JFrame implements ActionListener{
    
    String ip="";
    JLabel username,l1;
    JLabel host;
    JLabel port;
    JTextField t1,t2,t3;
    JButton b1;
    clienttest(){
        super("Login");
        username=new JLabel();
        host=new JLabel();
        port=new JLabel();
        l1=new JLabel();
        t1=new JTextField();
        t2=new JTextField();
        t3=new JTextField();
        b1=new JButton();
        username.setText("Username:");
       add(username);
        username.setBounds(32, 91, 62, 14);

        t1.setFont(new Font("Tahoma", 0, 12)); 
        
        add(t1);
        t1.setBounds(118, 84, 232, 27);

        b1.setBackground(new Color(150, 37, 37));
        b1.setFont(new Font("Lucida Console", 2, 18)); 
        b1.setForeground(Color.white);   
        b1.setText("Sign In");
       
        add(b1);
        b1.setBounds(52, 237, 308, 41);

        host.setText("IP Address:");
        add(host);
        host.setBounds(32, 139, 66, 14);

        t2.setFont(new Font("Tahoma", 0, 12));
        t2.setText("127.0.0.1");
       add(t2);
        t2.setBounds(118, 132, 232, 27);

        port.setText("Port:");
       add(port);
        port.setBounds(64, 187, 34, 14);

        t3.setFont(new Font("Tahoma", 0, 12));
       t3.setText("4444");
       add(t3);
       t3.setBounds(118, 180, 232, 27);

      l1.setFont(new Font("SansSerif", 3, 16));
        l1.setText("Please Login Here");
        add(l1);
        l1.setBounds(131, 37, 140, 19);

       
        setLayout(null);
        setSize(400, 380);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        b1.addActionListener(this);
   
               t1.addActionListener(this);
    }
    public static void main(String a[]) throws UnknownHostException, IOException, ClassNotFoundException, InterruptedException{
       
        
        clienttest ct=new clienttest();
      /*  client c=new client("127.0.0.1");
        c.setDefaultCloseOperation(EXIT_ON_CLOSE);
        c.start();*/
      
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
       String s=t1.getText();
       String s2=t2.getText();
        if(s.isEmpty()){
            
            JOptionPane.showMessageDialog(null, "Username is Empty");
      }else{
        
            dispose();
            
            try{
                client c=new client(s2,s);
            c.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
            c.start();
            c.setLocationRelativeTo(null);
            c.setTitle(s.toUpperCase()+" !  you are connected..");
             }catch(Exception ex){
               
            }
        
    }}
}


