package com.example.aravind.datatransfer;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toast.makeText(this, "check",Toast.LENGTH_SHORT).show();

    }

    public void client(View view) {
       // Toast.makeText(getApplicationContext(), "Enter client",
       //         Toast.LENGTH_SHORT).show();
        Toast.makeText(this, "Enter client",
                Toast.LENGTH_SHORT).show();
        Intent client = new Intent(MainActivity.this , clientside.class);
        startActivity(client);
    }

    public void server(View view) {
        Intent server = new Intent(MainActivity.this , serverside.class);
        startActivity(server);
    }


}
