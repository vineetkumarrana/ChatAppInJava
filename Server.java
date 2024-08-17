
import java.net.*;
import java.io.*;



class Server {
    

    ServerSocket server;
    Socket socket;
    BufferedReader br;
    PrintWriter out;
    //constructor
    public Server(){


           try {
            server = new ServerSocket(7777);
            System.out.println("server is ready to accept connection");
            System.out.println("waiting......");
            socket=server.accept();

            br=new BufferedReader(new InputStreamReader( socket.getInputStream()));
            out=new PrintWriter(socket.getOutputStream());

             startReading();
             startwriting();
           } catch (Exception e) {
            
              e.printStackTrace();
           }
    }

    public void startReading(){

        //this THREAD will read data and give us back
        Runnable r1=()->{
        System.out.println("Reader started....");

    try {

        while(!socket.isClosed()){

            
                String msg =br.readLine();
                if (msg.equals("exit")){
                    System.out.println("client terminated the chat");
                    socket.close();
                    break;
    
                }
                System.out.println("client : "+msg);

            }    
           
        }  catch(Exception e)
            {
                //e.printStackTrace();
                System.out.println("connection  closed"); 
            };

        };
        new Thread(r1).start();

    }


    public void startwriting(){
       // this THREAD will take data user data and give it to client
       Runnable r2=()->{
        System.out.println("writer started");

     try {    
        while(true) {
            

                BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in)); 
                

                String content=br1.readLine();
                out.println(content);
                out.flush();

                if (content.equals("exit")){
                    socket.close();
                    break;
                }
            }
         } catch (Exception e) {
               
                //e.printStackTrace();
                System.out.println("connection is closed"); 
            }
             
            
        };
            
        
         new Thread(r2).start();
    }








    public static void main(String[] args) {
        System.out.println("java server running");

        new Server();
    }

    
}