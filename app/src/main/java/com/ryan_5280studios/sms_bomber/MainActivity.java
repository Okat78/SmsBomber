package com.ryan_5280studios.sms_bomber;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import javax.mail.MessagingException;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class MainActivity extends AppCompatActivity {
    @InjectView(R.id.number)
    EditText number;
    @InjectView(R.id.carrier)
    Spinner carriers;
    @InjectView(R.id.messages)
    Spinner messages;
    @InjectView(R.id.message)
    EditText message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void send(View view) {
        try {
            new Email().sendEmail();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
//        new Email().send(number.getText().toString(),
//                carriers.getSelectedItem().toString(),
//                Integer.parseInt(String.valueOf(messages.getSelectedItem())),
//                message.getText().toString());

    }
}
