package com.bestapps.carwallet.firsttime;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.bestapps.carwallet.MainActivity;
import com.bestapps.carwallet.R;
import com.bestapps.carwallet.database.DatabaseHandler;
import com.bestapps.carwallet.model.Currency;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class FirstTimeActivity extends AppCompatActivity {
    private FragmentManager fragmentManager;
    private Button startNowButton;
    private DatabaseHandler databaseHandler;
    private Spinner currencySpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_time);
        databaseHandler = new DatabaseHandler(this);
        fragmentManager = getSupportFragmentManager();
        setCurrencySpinnerAdapter();
        Currency currency = databaseHandler.findCurrency();
        if (currency == null) {
            if (currencySpinner.getSelectedItem().toString().equals("Select currency...")) {

            }
        } else {
            startNowButton = findViewById(R.id.start_now_button);
            startNowButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.putExtra("firstTime", true);
                    startActivity(intent);
                    finish();
                }
            });
        }
    }

    private void setCurrencySpinnerAdapter() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.fuelTypes, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        currencySpinner.setAdapter(adapter);
        currencySpinner.setSelection(0);
    }
}
