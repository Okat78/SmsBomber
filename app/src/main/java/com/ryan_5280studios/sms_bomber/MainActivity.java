package com.ryan_5280studios.sms_bomber;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.rey.material.widget.EditText;

import com.github.lzyzsd.circleprogress.DonutProgress;
import com.rey.material.widget.Spinner;

import javax.mail.MessagingException;

import butterknife.ButterKnife;
import butterknife.InjectView;
import de.greenrobot.event.EventBus;


public class MainActivity extends AppCompatActivity {
    @InjectView(R.id.number)
    EditText number;
    @InjectView(R.id.carrier)
    Spinner carriers;
    @InjectView(R.id.messages)
    EditText messages;
    @InjectView(R.id.message)
    EditText message;
    @InjectView(R.id.donut_progress)
    DonutProgress donutProgress;
    SentEvent event;

    int max;
    boolean debug;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        String[] cmd = getResources().getStringArray(R.array.carrier_arrays);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.row_spn, cmd);
        adapter.setDropDownViewResource(R.layout.row_spn_dropdown);
        carriers.setAdapter(adapter);
        PreferenceManager.init(this);
        debug = Boolean.valueOf(PreferenceManager.loadPreference(Secret.DEV_KEY));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.donate) {
            startActivity(new Intent(this, DonationsActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void send(View view) {
        if(debug){
            sendBomb();
        }else{
            if(Integer.parseInt(String.valueOf(messages.getText().toString()
            )) >= 50){
                Toast.makeText(this, "To many messages must be below 50!", Toast.LENGTH_LONG).show();
            }else if((number.getText().toString().equals(Secret.BLACKLIST))){
                Toast.makeText(this, "That number is blacklisted!", Toast.LENGTH_LONG).show();
            }else{
                sendBomb();
            }

        }



    }

    private void sendBomb() {
        try {
            Email email = new Email(number.getText().toString(),
                    carriers.getSelectedItem().toString(),
                    Integer.parseInt(String.valueOf(messages.getText().toString()
                    )),
                    message.getText().toString());

            email.email();
            max = Integer.parseInt(String.valueOf(messages.getText().toString()));

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public void lookup(View view) {
        Intent intent = new Intent(MainActivity.this,
                Lookup.class);
        startActivity(intent);
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    public void onEvent(SentEvent e) {
        event = e;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                donutProgress.setProgress(event.num);
            }
        });


    }

}
