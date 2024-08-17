
import java.net.*;
import java.io.*;

public class Client {
    Socket socket;
     BufferedReader br;
    PrintWriter out;


    public Client(){

   try {
    System.out.println("sending request to server...");
     socket=new Socket("127.0.0.1",7777);
     System.out.println("connection done ");




     br=new BufferedReader(new InputStreamReader( socket.getInputStream()));
     out=new PrintWriter(socket.getOutputStream());

     startReading();
     startwriting();

   } catch (Exception e) {
    // TODO: handle exception
   }




}

    public void startReading(){

        //this THREAD will read data and give us back
        Runnable r1=()->{
        System.out.println("Reader started....");

        try {    
        while(true){

           
                String msg =br.readLine();
                if (msg.equals("exit")){
                    System.out.println("Server terminated the chat");
                    socket.close();
                    break;
    
                }
                System.out.println("Server : "+msg);

            }
         } catch (Exception e) {

              // e.printStackTrace();
              System.out.println("connection  closed");
            }
            
        
        };
        new Thread(r1).start();

    }


    public void startwriting(){
        // this THREAD will take data user data and give it to client
        Runnable r2=()->{
         System.out.println("writer started");

         try {
         while (!socket.isClosed()) {
             
 
                 BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in)); 
                 
 
                 String content=br1.readLine();
                 out.println(content);
                 out.flush();
             }
             System.out.println("connection is closed"); 


            }catch (Exception e) {
                
                 e.printStackTrace();
                 //System.out.println("connection is closed");
             }
             
         
             
         };
          new Thread(r2).start();
     }

    public static void main(String[] args) {
        System.out.println("client is running");
        new Client();
    }
}
