package Main;

import java.io.*;
import java.net.*;

public class Server {

    public static final int PORT = 8080;

    public static void main(String[] args)  {

    ServerSocket s = null;
        try {
            s = new ServerSocket(PORT);

            System.out.println("Started: " + s);
            try {
                System.out.println("Waiting for a client...");
                while (true) {
                    Socket socket = s.accept();
                    System.out.println("Connection accepted: " + socket);
                    try {
                        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        PrintWriter out = new PrintWriter(new BufferedWriter(
                                new OutputStreamWriter(socket.getOutputStream())), true);

                        String str = in.readLine();//получили название файла
                        System.out.println(str);


                        try {
                           out.print("HTTP/1.1 200 OK \r\n\r\n"); //сработает - всё ок
                            String adress = parse(str); //возвратили адресс index.htm
                          //  out.println(adress);
                            BufferedReader reader = new BufferedReader(new FileReader(adress));
                            String b;
                            b = reader.readLine();
                            while (b != null) {
                                System.out.println(b);
                                out.println(b);// клиенту
                                b = reader.readLine();
                            }
                            out.flush();//сlose write
                            reader.close();
                        } catch (Exception ex) {
                                /*System.out.println("Couldn't read file");*/
                        }


                    } finally {
                        socket.close();
                    }
                }
            } finally {
                s.close();//всё не слушай


            }
        }
        catch (IOException e) {
            e.printStackTrace();
            System.out.println("Couldn't listen to port" + PORT);
        }
        }



    public static String parse(String str) {
        String[] arr = str.split(" ");
        String a = arr[1].substring(1);
        return a;
    }
}

