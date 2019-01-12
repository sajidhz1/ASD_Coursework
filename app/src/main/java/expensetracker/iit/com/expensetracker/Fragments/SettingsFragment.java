package expensetracker.iit.com.expensetracker.Fragments;

import android.app.AlertDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import expensetracker.iit.com.expensetracker.Dialogs.ChangePinDialog;
import expensetracker.iit.com.expensetracker.Model.User;
import expensetracker.iit.com.expensetracker.Model.UserRegisterModel;
import expensetracker.iit.com.expensetracker.R;
import expensetracker.iit.com.expensetracker.UserRegister;
import expensetracker.iit.com.expensetracker.ViewModel.BudgetViewModel;
import expensetracker.iit.com.expensetracker.ViewModel.CategoryViewModel;
import expensetracker.iit.com.expensetracker.ViewModel.TransactionViewModel;
import expensetracker.iit.com.expensetracker.ViewModel.UserViewModel;

public class SettingsFragment extends BaseFragment {

    public static SettingsFragment newInstance() {
        SettingsFragment fragment = new SettingsFragment();
        return fragment;
    }

    User currentUser = null;
    TextView username, pinNo;
    Button clearData, changePin;
    private TransactionViewModel mTransactionViewModel;
    private UserViewModel mUserViewModel;
    private CategoryViewModel mCategoryViewModel;
    private BudgetViewModel mBudgetViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.settings_fragment, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();

        username = getView().findViewById(R.id.settingUsername);
        pinNo = getView().findViewById(R.id.settingPin);
        clearData = getView().findViewById(R.id.settingClearData);
        changePin = getView().findViewById(R.id.settingChangePin);

        currentUser = UserRegisterModel.getCurrentUser();
        username.setText("User Name : " + currentUser.getUserName());
        pinNo.setText("Pin No    : " + currentUser.getPinNo());

        mTransactionViewModel = ViewModelProviders.of(this).get(TransactionViewModel.class);
        mUserViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        mCategoryViewModel = ViewModelProviders.of(this).get(CategoryViewModel.class);
        mBudgetViewModel = ViewModelProviders.of(this).get(BudgetViewModel.class);

        clearData.setOnClickListener((View v) -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setMessage("Are you sure you want to clear data? This proccess is not reversible").setPositiveButton("Yes", dialogClickListener)
                    .setNegativeButton("No", dialogClickListener).show();
        });

        changePin.setOnClickListener((View v) -> {
            ChangePinDialog cdd = new ChangePinDialog(getActivity(), this);
            cdd.show();

            cdd.setOnDismissListener((DialogInterface dialog) -> {
                pinNo.setText("Pin No    : " + currentUser.getPinNo());
            });
        });
    }

    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which){
                case DialogInterface.BUTTON_POSITIVE:
                    //clear the table data
                    mTransactionViewModel.deleteAll();
                    mUserViewModel.deleteAll();
                    mCategoryViewModel.deleteAll();
                    mBudgetViewModel.deleteAll();

                    Intent i = new Intent(getActivity().getApplication(), UserRegister.class);
                    startActivity(i);
                    break;

                case DialogInterface.BUTTON_NEGATIVE:
                    //No button clicked
                    break;
            }
        }
    };
}
