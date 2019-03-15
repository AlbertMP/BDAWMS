package TCP;

import java.io.*;
import java.util.*;
import java.net.*;

//客户端
public class TCPClient {

    public static void main(String [] args) throws IOException {
        connect("localhost", 1234);
    }

    //远程连接
    public static void connect(String host,int port){
        try {
            System.out.println("客户端启动...............");
            new Menu().show();
            Socket sock = new Socket(host, port);
            // 创建一个写线程
            new TelnetWriter(sock.getOutputStream()).start();
            // 创建一个读线程
            new TelnetReader(sock.getInputStream()).start();
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
}

//从控制台读取用户输入命令   线程类
class TelnetWriter extends Thread {
    private PrintStream out;
    
    public TelnetWriter(OutputStream out) {
        this.out = new PrintStream(out);
    }
    public void run() {
        try {
            // 包装控制台输入流
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            // 反复将控制台输入写到Telnet服务程序
            while (true) {
            	String sysin = in.readLine();
            	out.println(sysin);
            	switch (sysin) {
				case "1":
					out.println(new insertOne().combine());
					break;
				case "2":
					out.println(new searchOne().combine());
					break;
				case "4":
					System.out.println("！！此为敏感操作，请向上级核实后再操作！！️");
					out.println(new editOne().combine());
					break;
				case "5":
					out.println(new deleteRecord().combine());
					break;
				default:
					break;
				}
            	
            }
        } catch (IOException exc) {
            exc.printStackTrace();
        }
    }
}

//将响应数据打印到控制台   线程类
class TelnetReader extends Thread {
    private InputStreamReader in;

    public TelnetReader(InputStream in) {
        this.in = new InputStreamReader(in);
    }
    public void run() {
        try {
            // 反复将Telnet服务程序的反馈信息显示在控制台屏幕上
            while (true) {
                // 从Telnet服务程序读取数据
                int b = in.read();
                if (b == -1)  
                    System.exit(0);
                // 将数据显示在控制台屏幕上
                System.out.print((char) b);
            }
        } catch (IOException exc) {
            exc.printStackTrace();
        }
    }
}
