package expensetracker.iit.com.expensetracker;

import android.app.Activity;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import java.text.ParseException;
import java.util.List;

import expensetracker.iit.com.expensetracker.Model.Transaction;
import expensetracker.iit.com.expensetracker.Model.User;
import expensetracker.iit.com.expensetracker.ViewModel.UserViewModel;


public class PinActivity extends AppCompatActivity {

    EditText userName, pinNo;
    Button saveButton;
    private UserViewModel mUserViewModel;


    public static PinActivity newInstance() {
        PinActivity fragment = new PinActivity();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mUserViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        setContentView(R.layout.user_registration);

        pinNo = (EditText) findViewById(R.id.pinNo);
        userName = (EditText) findViewById(R.id.regUserName);
        saveButton =  findViewById(R.id.regSubmit);

        saveButton.setOnClickListener((View v) -> {
            User user = new User(userName.getText().toString(), Integer.parseInt(pinNo.getText().toString()));
            mUserViewModel.insert(user);

            LiveData<List<User>> userArr = mUserViewModel.getAll();
            Intent i = new Intent(PinActivity.this, MainActivity.class);
            startActivity(i);
        });

        }

    public void pinSubmit(View v){
        Intent i = new Intent(PinActivity.this, MainActivity.class);
        startActivity(i);
    }

    public void registerUser(View v){


    }
}
