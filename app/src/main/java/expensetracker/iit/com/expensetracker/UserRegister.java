package expensetracker.iit.com.expensetracker;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.ParseException;
import java.util.List;

import expensetracker.iit.com.expensetracker.Model.User;
import expensetracker.iit.com.expensetracker.Model.UserRegisterModel;
import expensetracker.iit.com.expensetracker.ViewModel.UserViewModel;

public class UserRegister extends AppCompatActivity {

    TextView valdation;
    EditText userName, pinNo;
    Button saveButton;
    private UserViewModel mUserViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mUserViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        setContentView(R.layout.user_registration);

        pinNo = (EditText) findViewById(R.id.pinNo);
        userName = (EditText) findViewById(R.id.regUserName);
        saveButton =  findViewById(R.id.regSubmit);
        valdation = (TextView) findViewById(R.id.validation);

        valdation.setVisibility(TextView.INVISIBLE);

        saveButton.setOnClickListener((View v) -> {

            valdation.setVisibility(TextView.INVISIBLE);
            int pin;

            // validate user name
            if(userName.getText() == null || userName.getText().toString().equals("")){
                valdation.setText("Please enter the User Name");
                valdation.setVisibility(TextView.VISIBLE);
                return;
            }

            //validate pin no
            try{
                pin =  Integer.parseInt(pinNo.getText().toString());
            }catch (Exception e) {
                valdation.setText("Please enter a number for Pin");
                valdation.setVisibility(TextView.VISIBLE);
                e.printStackTrace();
                return;
            }

            User user = new User(userName.getText().toString(), pin);
            mUserViewModel.insert(user);
        });

        UserRegisterModel.setCurrentUser(null);
        mUserViewModel.getAll().observe(this, new Observer<List<User>>() {

            @Override
            public void onChanged(@Nullable List<User> users) {
                UserRegisterModel.setCurrentUser(null);
                if(users.size() > 0){
                    UserRegisterModel.setCurrentUser(users.get(0));
                    Intent i = new Intent(UserRegister.this, PinActivity.class);
                    startActivity(i);
                }
            }
        });
    }
}
