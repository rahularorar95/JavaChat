



import java.io.IOException;
import javax.swing.JFrame;

/**
 *
 * @author sam
 */


public class servertest {
    public static void main(String ar[]) throws IOException, ClassNotFoundException{
        server s=new server();
        s.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        s.start();
    }
}
