package expensetracker.iit.com.expensetracker.Dialogs;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import expensetracker.iit.com.expensetracker.Common.Constants;
import expensetracker.iit.com.expensetracker.Model.Category;
import expensetracker.iit.com.expensetracker.Model.Transaction;
import expensetracker.iit.com.expensetracker.R;
import expensetracker.iit.com.expensetracker.ViewModel.CategoryViewModel;

public class CreateTransactionDialog extends Dialog {

    EditText dateEditText, noteEditText, amountEditText;
    CheckBox recurrentCheckBox;
    Button saveButton, cancelButton;
    Transaction transaction;
    Context mContext;
    Spinner categoriesSpinner;

    final Calendar calendar = Calendar.getInstance();
    private OnTransactionAddListener onTransactionAddListener;
    private List<Category> categories;

    public void setCategories(List<Category> categories)
    {
        this.categories = categories;
    }

    public CreateTransactionDialog(@NonNull Context context, OnTransactionAddListener onTransactionAddListener, Transaction transaction) {
        super(context, R.style.full_screen_dialog);
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT);

        this.mContext = context;
        this.onTransactionAddListener = onTransactionAddListener;
        this.transaction = transaction;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_transaction_dialog);


        dateEditText = (EditText) findViewById(R.id.date);
        categoriesSpinner = (Spinner) findViewById(R.id.spinner);
        noteEditText = findViewById(R.id.note);
        amountEditText = findViewById(R.id.amount);
        recurrentCheckBox = findViewById(R.id.checkRecurrent);
        saveButton = findViewById(R.id.btn_yes);
        cancelButton = findViewById(R.id.btn_no);

        if(categories != null)
        {
            SpinAdapter spinnerArrayAdapter = new SpinAdapter(getContext(),android.R.layout.simple_spinner_item, categories);
            categoriesSpinner.setAdapter(spinnerArrayAdapter);
        }

        if(transaction != null)
        {
            updateDate(transaction.getAddedDate());
            noteEditText.setText(transaction.getNote());
            amountEditText.setText(transaction.getAmount() +"");
            recurrentCheckBox.setChecked(transaction.isRecurring());
        }

        saveButton.setOnClickListener((View v) -> {
            SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT, Locale.US);
            try {
                if(transaction == null)
                {
                    onTransactionAddListener.AddTransaction(new Transaction(Double.parseDouble(amountEditText.getText().toString()), 1, noteEditText.getText().toString(), recurrentCheckBox.isChecked(), sdf.parse(dateEditText.getText().toString())));
                }
                else {
                    transaction.setNote(noteEditText.getText().toString());
                    transaction.setAddedDate(sdf.parse(dateEditText.getText().toString()));
                    transaction.setAmount(Double.parseDouble(amountEditText.getText().toString()));
                    transaction.setRecurring(recurrentCheckBox.isChecked());
                    onTransactionAddListener.UpdateTransaction(transaction);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }

            dismiss();
        });

        cancelButton.setOnClickListener((View v) -> {
            dismiss();
        });


        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateDate(null);
            }

        };


        dateEditText.setOnClickListener((View v) -> {
            new DatePickerDialog(getContext(), date, calendar
                    .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)).show();
        });
    }

    private void updateDate(Date d) {
        if(d != null)
        {
            SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT, Locale.US);
            dateEditText.setText(sdf.format(d));
        }
        else {
            SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT, Locale.US);
            dateEditText.setText(sdf.format(calendar.getTime()));
        }
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

    public interface OnTransactionAddListener {
        public void AddTransaction(Transaction transaction);
        public void UpdateTransaction(Transaction transaction);
    }

    public class SpinAdapter extends ArrayAdapter<Category>{

        private Context context;
        private List<Category> values;

        public SpinAdapter(Context context, int textViewResourceId,
                           List<Category> values) {
            super(context, textViewResourceId, values);
            this.context = context;
            this.values = values;
        }

        @Override
        public int getCount(){
            return values.size();
        }

        @Override
        public Category getItem(int position){
            return values.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView label = (TextView) super.getView(position, convertView, parent);
            label.setTextColor(Color.BLACK);
            label.setText(values.get(position).getName());
            return label;
        }

        @Override
        public View getDropDownView(int position, View convertView,
                                    ViewGroup parent) {
            TextView label = (TextView) super.getDropDownView(position, convertView, parent);
            label.setTextColor(Color.BLACK);
            label.setText(values.get(position).getName());

            return label;
        }
    }
}
