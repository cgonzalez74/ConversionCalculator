package com.example.conversioncalculator;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toolbar;

public class MainActivity extends AppCompatActivity{

    public static final int TYPE_SELECTION = 1;
    private UnitsConverter.LengthUnits from_unit_length = UnitsConverter.LengthUnits.Meters;
    private UnitsConverter.LengthUnits to_unit_length = UnitsConverter.LengthUnits.Yards;
    private UnitsConverter.VolumeUnits from_unit_volume = UnitsConverter.VolumeUnits.Liters;
    private UnitsConverter.VolumeUnits to_unit_volume = UnitsConverter.VolumeUnits.Gallons;
    private  TextView from_label;
    private TextView to_label;
    public static String UnitType = "Length";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Toolbar mTopToolbar = findViewById(R.id.Settings);

        TextView title = (TextView) findViewById(R.id.title);
        EditText from_input = (EditText) findViewById(R.id.top_input);
        EditText to_input = (EditText) findViewById(R.id.bottom_input);
        Button calculate = (Button) findViewById(R.id.calculate);
        Button clear = (Button) findViewById(R.id.clear);
        Button mode = (Button) findViewById(R.id.mode);
        Button settings = findViewById(R.id.settings_button);
        from_label = (TextView) findViewById(R.id.top_label);
        to_label = (TextView) findViewById(R.id.bottom_label);

        title.setText("Length Converter");


        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivityForResult(intent, TYPE_SELECTION);
            }
            });

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
        }
    }


}
