package com.example.aravind.datatransfer;

import android.net.wifi.WifiManager;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.Formatter;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class serverside extends AppCompatActivity {

    Button button_sent;
    EditText smessage;
    TextView chat,display_status;
    String str,msg="";
    int serverport = 10000;
    ServerSocket serverSocket;
    Socket client;
    Handler handler = new Handler();
    WifiManager wmanager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serverside);

        wmanager = (WifiManager) getSystemService(WIFI_SERVICE);
        String ip =Formatter.formatIpAddress(wmanager.getConnectionInfo().getIpAddress());

        smessage = (EditText) findViewById(R.id.editText);
        chat = (TextView) findViewById(R.id.textView);
        display_status = (TextView)findViewById(R.id.dispstatus);
        display_status.setText("Server hosted on " + ip);
        Thread serverThread = new Thread(new serverThread());
        serverThread.start();
        button_sent = (Button) findViewById(R.id.send);

        button_sent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread sentThread = new Thread(new sentMessage());
                sentThread.start();
            }
        });


    }

//    public void thread(View view) {
//        Thread sentThread = new Thread(new sentMessage());
//        sentThread.start();
//    }


 /*   class sentMessage implements Runnable{
        @Override public void run(){
            try{
                Socket client = serverSocket.accept();
                DataOutputStream os = new DataOutputStream(client.getOutputStream());
                str = smessage.getText().toString();
                msg = msg + "\n Server : " + str;
                handler.post(new Runnable(){
                    @Override public void run(){
                        chat.setText(msg);
                    }});
                os.writeBytes(str);
                os.flush();
                os.close();
                client.close();
            }
            catch(IOException e){

            }
        }
    }   */


//    class sentMessage implements Runnable {
//        @Override
//        public void run() {
//            try {
//                Socket client = serverSocket.accept();
//                DataOutputStream os = new DataOutputStream(client.getOutputStream());
//                str = smessage.getText().toString();
//                msg = msg + "\n Server : " + str;
//                handler.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        chat.setText(msg);
//                    }
//                });
//                os.writeBytes(str);
//                os.flush();
//                os.close();
//                client.close();
//            }
//            catch (IOException e){
//
//            }
//        }
//    }

    public class serverThread implements Runnable{

        @Override
        public void run() {
            try {
                while (true)
                {
                    serverSocket = new ServerSocket(serverport);
                    Socket client = serverSocket.accept();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            display_status.setText("Connected");
                        }
                    });
                    DataInputStream in = new DataInputStream(client.getInputStream());
                    String line = null;
                    while((line = in.readLine()) != null){
                    msg = msg + "\n Client " + line;
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                chat.setText(msg);
                            }
                        });
                }
                    in.close();
                    client.close();
                    Thread.sleep(100);
                }
            }
            catch (IOException e){

            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public class sentMessage implements Runnable {
        @Override public void run(){
            try{
                Socket client = serverSocket.accept();
                DataOutputStream os = new DataOutputStream(client.getOutputStream());
                str = smessage.getText().toString();
                msg = msg + "\n Server : " + str;
                handler.post(new Runnable(){
                    @Override public void run(){
                        chat.setText(msg);
                    }});
                os.writeBytes(str);
                os.flush();
                os.close();
                client.close();
            }
            catch (IOException e){

            }
            catch (NullPointerException ne){
                
            }

        }
    }
}
