package com.bestapps.carwallet.firsttime;

import android.content.Intent;
import android.location.GnssMeasurementsEvent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

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
    private Button nextButton;
    private TextView addCarTextViewMessage;
    private TextView selectCurrencyViewMessage;
    private DatabaseHandler databaseHandler;
    private Spinner currencySpinner;
    private Currency currency;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_time);

        databaseHandler = new DatabaseHandler(this);
        fragmentManager = getSupportFragmentManager();
        startNowButton = findViewById(R.id.start_now_button);
        nextButton = findViewById(R.id.next_button);
        addCarTextViewMessage = findViewById(R.id.add_car_first_message);
        selectCurrencyViewMessage = findViewById(R.id.currency_message);
        currencySpinner = findViewById(R.id.input_currency);

        setCurrencySpinnerAdapter();
        currency = databaseHandler.findCurrency();

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Currency currency = new Currency();
                currency.setId(0);
                currency.setCurrency(currencySpinner.getSelectedItem().toString());
                databaseHandler.addCurrency(currency);
                hideCurrency();
            }
        });
        startNowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("firstTime", true);
                startActivity(intent);
                finish();
            }
        });

        currencySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i != 0) {
                    nextButton.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        if (currency == null) {
            hideAddCarMessage();
        } else {
            hideCurrency();
        }
    }

    private void hideAddCarMessage() {
        startNowButton.setVisibility(View.GONE);
        addCarTextViewMessage.setVisibility(View.GONE);
        selectCurrencyViewMessage.setVisibility(View.VISIBLE);

        if (currencySpinner.getSelectedItem().toString().equals("Select currency...")) {
            nextButton.setVisibility(View.GONE);
        } else {
            nextButton.setVisibility(View.VISIBLE);
        }
    }

    private void hideCurrency() {
        startNowButton.setVisibility(View.VISIBLE);
        addCarTextViewMessage.setVisibility(View.VISIBLE);
        selectCurrencyViewMessage.setVisibility(View.GONE);
        nextButton.setVisibility(View.GONE);
        currencySpinner.setVisibility(View.GONE);
    }

    private void setCurrencySpinnerAdapter() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.currecies, R.layout.simple_item_spinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        currencySpinner.setAdapter(adapter);
        currencySpinner.setSelection(0);
    }
}
