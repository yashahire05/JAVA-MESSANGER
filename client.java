import java.lang.*;
import java.net.*;
import java.io.*;
public class client
{
    public static void main(String[] args)throws Exception
    {
        System.out.println("client Application is running...");
        String s1,s2;
        Socket S=new  Socket("localhost",1100);
        
        BufferedReader brk = new BufferedReader(new InputStreamReader(System.in));
        BufferedReader br = new BufferedReader(new InputStreamReader(S.getInputStream()));
        PrintStream ps = new PrintStream(S.getOutputStream());


        while(!(s1 = brk.readLine()).equals("gn"))
        {
            ps.println(s1);
            s2=br.readLine();
            System.out.println("server says :" +s2);
            System.out.println("Enter Message for server :");
        }
        S.close();
        br.close();;
        brk.close();
        ps.close();

    }
}
