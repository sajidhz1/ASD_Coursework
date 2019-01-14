package expensetracker.iit.com.expensetracker;

import android.app.Activity;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.text.ParseException;
import java.util.List;

import expensetracker.iit.com.expensetracker.Model.Transaction;
import expensetracker.iit.com.expensetracker.Model.User;
import expensetracker.iit.com.expensetracker.Model.UserRegisterModel;
import expensetracker.iit.com.expensetracker.ViewModel.UserViewModel;


public class PinActivity extends AppCompatActivity {

    EditText pinPassword;
    TextView errorText;
    Button pinSubmit;
    private UserViewModel mUserViewModel;


    public static PinActivity newInstance() {
        PinActivity fragment = new PinActivity();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mUserViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        setContentView(R.layout.pin_fragment);

        pinPassword = (EditText) findViewById(R.id.pin_password);
        pinSubmit =  findViewById(R.id.pinSubmit);
        errorText = (TextView) findViewById(R.id.ErrorText);
        errorText.setVisibility(TextView.INVISIBLE);

        pinSubmit.setOnClickListener((View v) -> {

            errorText.setVisibility(TextView.INVISIBLE);
            int pin;

            //validate pin no
            try{
                pin =  Integer.parseInt(pinPassword.getText().toString());
            }catch (Exception e) {
                errorText.setText("Please enter a number for Pin");
                errorText.setVisibility(TextView.VISIBLE);
                e.printStackTrace();
                return;
            }


            User currentUser = UserRegisterModel.getCurrentUser();
            if(currentUser != null && currentUser.getPinNo() == Integer.parseInt(pinPassword.getText().toString())){
                Intent i = new Intent(PinActivity.this, MainActivity.class);
                startActivity(i);
            }
            else{
                errorText.setText("Invalid Pin Number");
                errorText.setVisibility(TextView.VISIBLE);
            }
        }) ;
        }
}
