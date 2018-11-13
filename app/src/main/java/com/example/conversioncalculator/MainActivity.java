package com.example.conversioncalculator;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toolbar;

import com.example.conversioncalculator.dummy.HistoryContent;

import org.joda.time.DateTime;

public class MainActivity extends AppCompatActivity{

    public static final int TYPE_SELECTION = 1;
    public static final int HISTORY_RESULT = 2;
    private UnitsConverter.LengthUnits from_unit_length = UnitsConverter.LengthUnits.Meters;
    private UnitsConverter.LengthUnits to_unit_length = UnitsConverter.LengthUnits.Yards;
    private UnitsConverter.VolumeUnits from_unit_volume = UnitsConverter.VolumeUnits.Liters;
    private UnitsConverter.VolumeUnits to_unit_volume = UnitsConverter.VolumeUnits.Gallons;
    private  TextView from_label;
    private TextView to_label;
    private TextView title;
    public static String UnitType = "Length";

    private EditText from_input;
    private EditText to_input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Toolbar mTopToolbar = findViewById(R.id.Settings);

        title = (TextView) findViewById(R.id.title);
        from_input = (EditText) findViewById(R.id.top_input);
        to_input = (EditText) findViewById(R.id.bottom_input);
        Button calculate = (Button) findViewById(R.id.calculate);
        Button clear = (Button) findViewById(R.id.clear);
        Button mode = (Button) findViewById(R.id.mode);
        from_label = (TextView) findViewById(R.id.top_label);
        to_label = (TextView) findViewById(R.id.bottom_label);

        title.setText("Length Converter");


        calculate.setOnClickListener(v -> {

                    if (to_input.length() == 0) {
                        String fromVal = from_input.getText().toString();
                        if (UnitType == "Length") {
                            Double value = UnitsConverter.convert(Double.parseDouble(fromVal), from_unit_length, to_unit_length);
                            to_input.setText(value.toString());
                        } else {
                            Double value = UnitsConverter.convert(Double.parseDouble(fromVal), from_unit_volume, to_unit_volume);
                            to_input.setText(value.toString());
                        }
                    } else {
                        String toVal = to_input.getText().toString();
                        if (UnitType == "Length") {
                            Double value = UnitsConverter.convert(Double.parseDouble(toVal), to_unit_length, from_unit_length);
                            from_input.setText(value.toString());
                        } else {
                            Double value = UnitsConverter.convert(Double.parseDouble(toVal), to_unit_volume, from_unit_volume);
                            from_input.setText(value.toString());
                        }

                    }

                    HistoryContent.HistoryItem Output = new HistoryContent.HistoryItem
                            (Double.parseDouble(from_input.getText().toString()),
                                    Double.parseDouble(to_input.getText().toString()),
                                    UnitType, from_label.getText().toString(), to_label.getText().toString(), DateTime.now());

                    HistoryContent.addItem(Output);

                   InputMethodManager inputManager = (InputMethodManager)
                            getSystemService(Context.INPUT_METHOD_SERVICE);

                    inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
                }
            );

        clear.setOnClickListener(v -> {

                to_input.setText("");
                from_input.setText("");

            InputMethodManager inputManager = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);

            inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);

                }
        );

        mode.setOnClickListener(v ->{

            if(UnitType == "Length"){
                UnitType = "Volume";
                to_label.setText(to_unit_volume.toString());
                from_label.setText(from_unit_volume.toString());
                title.setText("Volume Converter");
            }
            else {
                UnitType = "Length";
                to_label.setText(to_unit_length.toString());
                from_label.setText(from_unit_length.toString());
                title.setText("Length Converter");
            }

            InputMethodManager inputManager = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);

            inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);

        });

        to_input.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                from_input.setText("");
            }
        });

        from_input.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                to_input.setText("");
            }
        });

    }


   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.Settings) {
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivityForResult(intent, TYPE_SELECTION);
            return true;
        }else if(item.getItemId() == R.id.action_history) {
            Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
            startActivityForResult(intent, HISTORY_RESULT);
            return true;
        }
        return false;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode == TYPE_SELECTION){
            String toString = data.getStringExtra("to_select");
            String fromString = data.getStringExtra("from_select");
            if(UnitType == "Length") {
                to_unit_length = UnitsConverter.LengthUnits.valueOf(toString);
                from_unit_length = UnitsConverter.LengthUnits.valueOf(fromString);
            }
            else{
                to_unit_volume = UnitsConverter.VolumeUnits.valueOf(toString);
                from_unit_volume= UnitsConverter.VolumeUnits.valueOf(fromString);
            }
            to_label.setText(toString);
            from_label.setText(fromString);
        }else if (resultCode == HISTORY_RESULT){
            String[] vals = data.getStringArrayExtra("item");
            this.from_input.setText(vals[0]);
            this.to_input.setText(vals[1]);
            this.UnitType = UnitType.valueOf(vals[2]);
            this.from_label.setText(vals[3]);
            this.to_label.setText(vals[4]);
            this.title.setText(UnitType + " Converter");
        }

    }
    }



