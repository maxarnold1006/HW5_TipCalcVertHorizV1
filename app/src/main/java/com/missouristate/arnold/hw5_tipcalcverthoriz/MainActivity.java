package com.missouristate.arnold.hw5_tipcalcverthoriz;

import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import java.text.NumberFormat;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    private TipCalculator tipCalc;
    public NumberFormat money = NumberFormat.getCurrencyInstance( );
    private EditText billEditText;
    private EditText tipEditText;
    private EditText guestEditText;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Configuration config = getResources().getConfiguration();
        modifyLayout(config);
        tipCalc = new TipCalculator(0.17f, 100.0f, 100.0f);

        billEditText = (EditText) findViewById(R.id.amount_bill);
        tipEditText = (EditText) findViewById(R.id.amount_tip_percent);
        guestEditText = (EditText) findViewById(R.id.amount_guests);

        TextChangeHandler tch = new TextChangeHandler();
        billEditText.addTextChangedListener(tch);
        tipEditText.addTextChangedListener(tch);
        guestEditText.addTextChangedListener(tch);

    }

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        modifyLayout(newConfig);

        tipCalc = new TipCalculator(0.17f, 100.0f, 100.0f);

        billEditText = (EditText) findViewById(R.id.amount_bill);
        tipEditText = (EditText) findViewById(R.id.amount_tip_percent);
        guestEditText = (EditText) findViewById(R.id.amount_guests);


        TextChangeHandler tch = new TextChangeHandler();
        billEditText.addTextChangedListener(tch);
        tipEditText.addTextChangedListener(tch);
        guestEditText.addTextChangedListener(tch);

    }

    public void modifyLayout(Configuration newConfig) {
        if(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE)
            setContentView(R.layout.activity_main_landscape);
        else if(newConfig.orientation == Configuration.ORIENTATION_PORTRAIT)
            setContentView(R.layout.activity_main);
    }


    public void calculate() {
        String billString = billEditText.getText( ).toString( );
        String tipString = tipEditText.getText( ).toString( );
        String guestString = guestEditText.getText().toString();

        TextView tipTextView =
                (TextView) findViewById( R.id.amount_tip );
        TextView totalTextView =
                (TextView) findViewById( R.id.amount_total );
        TextView guestTipTextView =
                (TextView) findViewById( R.id.guests_tip_split );
        TextView guestTotalTextView =
                (TextView) findViewById( R.id.guests_total_split );

        try {
            // convert billString and tipString to floats
            float billAmount = Float.parseFloat( billString );
            int tipPercent = Integer.parseInt( tipString );
            float guestsAmount = Float.parseFloat(guestString);

            // update the Model
            tipCalc.setBill( billAmount );
            tipCalc.setTip( .01f * tipPercent );
            tipCalc.setGuests(guestsAmount);

            // ask Model to calculate tip and total amounts
            float tip = tipCalc.tipAmount();
            float total = tipCalc.totalAmount();
            float splitTip = tipCalc.splitTipAmount();
            float splitBill = tipCalc.splitBillAmount();

            // update the View with formatted tip and total amounts
            tipTextView.setText( money.format( tip ) );
            totalTextView.setText( money.format( total ) );
            guestTipTextView.setText(money.format(splitTip));
            guestTotalTextView.setText(money.format(splitBill));
        } catch( NumberFormatException nfe ) {
            // pop up an alert view here
        }
    }

    private class TextChangeHandler implements TextWatcher {
        public void afterTextChanged( Editable e ) {
            calculate();
        }

        public void beforeTextChanged( CharSequence s, int start, int count, int after ) {
        }

        public void onTextChanged( CharSequence s, int start, int before, int after ) {
        }


    }
}
