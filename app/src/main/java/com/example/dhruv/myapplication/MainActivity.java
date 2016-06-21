package com.example.dhruv.myapplication;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {
    private DatePicker datePicker;
    private Calendar calendar;
    private TextView dateView;
    private int year, month, day;

    private RadioGroup radioshirt;
    private TextView txtSize;
    Spinner eyeColor;
    private EditText name;
    private SeekBar pantSize,shirtSize,shoeSize;
    private TextView txtPant,txtShirt,txtShoe;
    CheckBox checkbox1;

    Button btnArchive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dateView = (TextView) findViewById(R.id.date);
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);

        checkbox1 = (CheckBox) findViewById(R.id.checkBox);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        showDate(year, month+1, day);

        radioshirt = (RadioGroup)findViewById(R.id.radioShirt);
        txtSize = (TextView)findViewById(R.id.txtSize);
        //set change listener
        radioshirt.setOnCheckedChangeListener(this);

        txtShirt = (TextView)findViewById(R.id.txtShirt);
        txtPant = (TextView)findViewById(R.id.txtPant);
        txtShoe = (TextView)findViewById(R.id.txtShoe);
        shirtSize = (SeekBar)findViewById(R.id.shirtSize);
        pantSize = (SeekBar)findViewById(R.id.pantSize);
        shoeSize = (SeekBar)findViewById(R.id.shoeSize);
        btnArchive = (Button) findViewById(R.id.btnArchive);
        name = (EditText) findViewById(R.id.name);
        eyeColor = (Spinner)findViewById(R.id.eyeColor);

        pantSize.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                String seekbarValue = String.valueOf(i);

                txtPant.setText(seekbarValue);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        shirtSize.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                String seekbarValue = String.valueOf(i);

                txtShirt.setText(seekbarValue);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        shoeSize.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int i , boolean b) {

                String seekbarValue = String.valueOf(i+4);

                txtShoe.setText(seekbarValue);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        this.loadSetting();

    }



    @SuppressWarnings("deprecation")
    public void setDate(View view) {
        showDialog(999);
        //Toast.makeText(getApplicationContext(), "ca", Toast.LENGTH_SHORT)
          //      .show();
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this, myDateListener, year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
            // TODO Auto-generated method stub
            // arg1 = year
            // arg2 = month
            // arg3 = day
            showDate(arg1, arg2+1, arg3);
        }
    };

    private void showDate(int year, int month, int day) {
        dateView.setText(new StringBuilder().append(day).append("/")
                .append(month).append("/").append(year));


    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        RadioButton radioButton = (RadioButton)group. findViewById(checkedId);
        switch(checkedId){
            case R.id.rbtnl:
                txtSize.setText("Large");
                break;
            case R.id.rbtnm:
                txtSize.setText("Medium");
                break;
            case R.id.rbtns:
                txtSize.setText("Small");
                break;
            case R.id.rbtnxl:
                txtSize.setText("Extra Large");
                break;
            case R.id.rbtnxs:
                txtSize.setText("Extra Small");
                break;
            case R.id.rbtnxxl:
                txtSize.setText("Double Extra Large");
                break;
        }

    }

    private void loadSetting()  {
        SharedPreferences sharedPreferences= this.getSharedPreferences("appSetting", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();



        if(sharedPreferences!= null) {

            int checkedRadioButtonId = sharedPreferences.getInt("checkedRadioButtonId", R.id.rbtns);

            String nameText = sharedPreferences.getString("name",null);
            String PantText = sharedPreferences.getString("PantText","0");
            String ShoeText = sharedPreferences.getString("ShoeText","0");
            String ShirtText = sharedPreferences.getString("ShirtText","0");

            boolean remember = sharedPreferences.getBoolean("remember",checkbox1.isChecked());

            this.txtPant.setText(PantText);
            this.txtShoe.setText(ShoeText);
            this.txtShirt.setText(ShirtText);

            this.checkbox1.setChecked(remember);

            this.shirtSize.setProgress(Integer.parseInt(ShirtText) );
            this.pantSize.setProgress(Integer.parseInt(PantText) );
            this.shoeSize.setProgress(Integer.parseInt(ShoeText)-4 );
            this.radioshirt.check(checkedRadioButtonId);

            this.name.setText(nameText);


            eyeColor.setSelection(sharedPreferences.getInt("spinnerSelection",0));

            //String currentDate = (day)+("/")+(month+1)+("/")+(year);

            String dte = sharedPreferences.getString("dte","Select Birth date");
            dateView.setText(dte);



        } else {
            Toast.makeText(this,"Use the default setting",Toast.LENGTH_LONG).show();
        }

    }

    public void doSave(View view)  {
        // The created file can only be accessed by the calling application
        // (or all applications sharing the same user ID).
        SharedPreferences sharedPreferences= this.getSharedPreferences("appSetting", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();


        //editor.putInt("pantSize", this.pantSize.getProgress());
        //editor.putInt("shirtSize", this.shirtSize.getProgress());
        //editor.putInt("shoeSize ", this.shoeSize.getProgress());

        // Checked RadioButton ID.
        int checkedRadioButtonId = radioshirt.getCheckedRadioButtonId();

        editor.putInt("checkedRadioButtonId", checkedRadioButtonId);
        // Edit Text
        String nameText = name.getText().toString();
        editor.putString("name",nameText);

        //Textview
        String PantText = txtPant.getText().toString();
        String ShoeText = txtShoe.getText().toString();
        String ShirtText = txtShirt.getText().toString();
        String dte = dateView.getText().toString();

        editor.putString("PantText",PantText);
        editor.putString("ShoeText",ShoeText);
        editor.putString("ShirtText",ShirtText);
        editor.putString("dte",dte);

        if (checkbox1.isChecked()){

            editor.putBoolean("remember", true);
        }
        else {

            editor.putBoolean("remember", false);
        }

        int spinnerSelection = eyeColor.getSelectedItemPosition();
        editor.putInt("spinnerSelection", spinnerSelection);

        // Save.
        editor.apply();

        Toast.makeText(this,"Setting saved!",Toast.LENGTH_LONG).show();
    }
}
