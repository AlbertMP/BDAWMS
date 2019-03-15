package TCP;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

//tcp服务器，单线程和多线程
public class TCPServer{

    public static void main(String[] args) {
        moreserver(1234);
    }

    //读取输入文本，返回响应文本（二级制，对象）
    public static void server(int port) {
        try {
            //创建一个serversocket
            ServerSocket mysocket=new ServerSocket(port);
            //等待监听是否有客户端连接
            Socket sk = mysocket.accept();
            //接收文本信息
            BufferedReader in =new BufferedReader(new InputStreamReader(sk.getInputStream()));
            String data =in.readLine();
            System.out.println("客户端消息："+data);

//          //接收二进制字节流
//          DataInputStream dis =new DataInputStream(new BufferedInputStream(sk.getInputStream()));
//          byte[] enter =new byte[dis.available()];
//          dis.read(enter);
//          
//          //接收对象信息
//          ObjectInput ois =new ObjectInputStream(new BufferedInputStream(sk.getInputStream()));
//          Object object=ois.readObject();

            //输出文本
            PrintWriter out =new PrintWriter(new BufferedWriter(new OutputStreamWriter(sk.getOutputStream())),true);
            out.println(new Date().toString()+":"+data);   //服务器返回时间和客户发送来的消息
            out.close();

//          //输出二进制
//          DataOutputStream dos=new DataOutputStream(sk.getOutputStream());
//          byte[] back="luanpeng".getBytes();
//          dos.write(back);
//          
//          //输出对象
//          ObjectOutputStream oos=new ObjectOutputStream(sk.getOutputStream());
//          oos.writeObject(new Date());  //返回一个时间对象
//          oos.close();

        } catch (Exception e) {
            // TODO: handle exception
        }

    }



    //多线程服务器
    public static void moreserver(int port){
        try {
            System.out.println("服务器启动...............");
            ServerSocket server=new ServerSocket(port);
            while(true)
            {
                //阻塞，直到有客户连接
                Socket sk=server.accept();
                //启动服务线程
                new ServerThread(sk).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
