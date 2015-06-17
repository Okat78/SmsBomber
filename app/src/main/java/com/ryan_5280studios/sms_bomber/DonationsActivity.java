package com.ryan_5280studios.sms_bomber;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.EditText;

import org.sufficientlysecure.donations.DonationsFragment;

public class DonationsActivity extends FragmentActivity {




    /**
     * Bitcoin
     */
    private static final String BITCOIN_ADDRESS = "12dNJkaWsYQFBo5N6UQ4EHDEvbWqZYxYGR";

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.donations_activity);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        DonationsFragment donationsFragment;

            donationsFragment = DonationsFragment.newInstance(BuildConfig.DEBUG, false, null, null, null, false, "",
                    "", "USD", false, "", "", true, BITCOIN_ADDRESS);


        ft.replace(R.id.donations_activity_container, donationsFragment, "donationsFragment");
        ft.commit();
    }

    /**
     * Needed for Google Play In-app Billing. It uses startIntentSenderForResult(). The result is not propagated to
     * the Fragment like in startActivityForResult(). Thus we need to propagate manually to our Fragment.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag("donationsFragment");
        if (fragment != null) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void dev(View view) {
        EditText d = (EditText) findViewById(R.id.dev);
        if(d.getText().toString().equals(Secret.DEV)){
            PreferenceManager.savePreference(Secret.DEV_KEY,"true");
        }
    }
}