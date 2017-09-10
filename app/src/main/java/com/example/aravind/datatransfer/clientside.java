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
import android.widget.Toast;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class clientside extends AppCompatActivity {

    EditText serverIp,smessage;
    TextView chat;
   // Button connectPhones,sent;
    String serverIpAddress = "", msg = "", str;
    Handler handler = new Handler();
    WifiManager wmanagers;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clientside);

        wmanagers = (WifiManager) getSystemService(WIFI_SERVICE);
        String ip = Formatter.formatIpAddress(wmanagers.getConnectionInfo().getIpAddress());

        chat = (TextView) findViewById(R.id.chat);
        chat.setText("Address is "+ip);
        serverIp = (EditText) findViewById(R.id.serverip);
        smessage = (EditText) findViewById(R.id.smessage);
  /*      sent = (Button) findViewById(R.id.send_button);
        sent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread sentThread = new Thread(new sentMessage());
                sentThread.start();
                Toast.makeText(getApplicationContext(), "When send button click",
                        Toast.LENGTH_SHORT).show();

            }
        });   */
    /*    connectPhones = (Button) findViewById(R.id.contact_phne);
        connectPhones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                serverIpAddress = serverIp.getText().toString();
                if (!serverIpAddress.equals("")) {
                    Toast.makeText(getApplicationContext(), "When connect button click",
                            Toast.LENGTH_SHORT).show();
                    Thread clientThread = new Thread(new ClientThread());
                    clientThread.start();
                }
            }
        });   */
    }

    public void connect(View view) {
        serverIpAddress = serverIp.getText().toString();
        Toast.makeText(this, "When connect button click",
                Toast.LENGTH_SHORT).show();
        if (!serverIpAddress.equals("")) {
            Toast.makeText(this, "When connect button click but value null",
                    Toast.LENGTH_SHORT).show();
            Thread clientThread = new Thread(new ClientThread());
            clientThread.start();
        }
    }

    public void send(View view) {
        Toast.makeText(this, "When send button click",Toast.LENGTH_SHORT).show();
        Thread sentThread = new Thread(new sentMessage());
        sentThread.start();
    }

 /*   public void conectphne(View view) {
        serverIpAddress = serverIp.getText().toString();
        if (!serverIpAddress.equals("")) {
            Thread clientThread = new Thread(new ClientThread());
            clientThread.start();
        }
    }   */

  /*  public void send(View view) {
        Thread sentThread = new Thread(new sentMessage());
        sentThread.start();
    }  */

    class sentMessage implements Runnable{

        @Override
        public void run() {
         //   Toast.makeText(this, "When send button click",Toast.LENGTH_SHORT).show();
            try {

                InetAddress serverAddr =InetAddress.getByName(serverIpAddress);
                Socket socket = new Socket(serverAddr, 10000);
                DataOutputStream os = new DataOutputStream(socket.getOutputStream());
                str = smessage.getText().toString();
                str = str + "\n";
                msg = msg + "Client : " + str;
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        chat.setText(msg);
                    }
                });
                os.writeBytes(str);
                os.flush();
                os.close();
                socket.close();
            }
            catch (IOException e){

            }
        }
    }

    public class ClientThread implements Runnable{
        @Override
        public void run() {
            try {
                Toast.makeText(getApplicationContext(), "When contact thread work",
                        Toast.LENGTH_SHORT).show();
                while (true){
                    InetAddress serverAddr =InetAddress.getByName(serverIpAddress);
                    Socket socket = new Socket(serverAddr, 10000);
                    DataInputStream in = new DataInputStream(socket.getInputStream());
                    String line = null;
                    while ((line = in.readLine()) != null) {
                        msg = msg + "Server : " + line + "\n";
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                chat.setText(msg);
                            }
                        });
                    }
                    in.close();
                    socket.close();
                    Thread.sleep(100);
                }
            }
            catch (IOException e){

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
