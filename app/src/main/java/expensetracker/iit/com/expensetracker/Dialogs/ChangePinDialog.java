package expensetracker.iit.com.expensetracker.Dialogs;

import android.app.Dialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import expensetracker.iit.com.expensetracker.Fragments.SettingsFragment;
import expensetracker.iit.com.expensetracker.Model.User;
import expensetracker.iit.com.expensetracker.Model.UserRegisterModel;
import expensetracker.iit.com.expensetracker.R;
import expensetracker.iit.com.expensetracker.ViewModel.UserViewModel;

public class ChangePinDialog extends Dialog {

    Button saveButton, cancelButton;
    User currentUser;
    TextView valdation;
    EditText pinNo;

    private UserViewModel mUserViewModel;
    private SettingsFragment settingsFragment;

    public ChangePinDialog(@NonNull Context context, SettingsFragment setting) {
        super(context, R.style.full_screen_dialog);
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT);

        settingsFragment = setting;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.change_pin);

        mUserViewModel = ViewModelProviders.of(settingsFragment).get(UserViewModel.class);
        saveButton = findViewById(R.id.pinChangeSave);
        cancelButton = findViewById(R.id.pinChangeCancel);
        valdation = (TextView) findViewById(R.id.pinChangeValidation);
        pinNo = findViewById(R.id.ChangePinNo);

        currentUser = UserRegisterModel.getCurrentUser();

        valdation.setVisibility(TextView.INVISIBLE);

        cancelButton.setOnClickListener((View v) -> {
            dismiss();
        });

        saveButton.setOnClickListener((View v) -> {

            valdation.setVisibility(TextView.INVISIBLE);
            int pin;

            //validate pin no
            try{
                pin =  Integer.parseInt(pinNo.getText().toString());
            }catch (Exception e) {
                valdation.setText("Please enter a number for Pin");
                valdation.setVisibility(TextView.VISIBLE);
                e.printStackTrace();
                return;
            }

            currentUser.setPinNo(pin);
            mUserViewModel.update(currentUser);

            UserRegisterModel.setCurrentUser(currentUser);
            dismiss();
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Dialog dialog = this;
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
        }
    }

}
