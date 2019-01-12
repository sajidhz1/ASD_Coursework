package expensetracker.iit.com.expensetracker.Dialogs;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import expensetracker.iit.com.expensetracker.Common.Constants;
import expensetracker.iit.com.expensetracker.Model.Category;
import expensetracker.iit.com.expensetracker.Model.Transaction;
import expensetracker.iit.com.expensetracker.R;

public class CreateCategoryDialog extends Dialog {

    EditText nameEditText, budgetEditText;
    Spinner typeSpinner;
    Button saveButton, cancelButton;
    Category category;

    private OnCategoryAddListener onCategoryAddListener;

    public CreateCategoryDialog(@NonNull Context context, OnCategoryAddListener onCategoryAddListener, Category category) {
        super(context, R.style.full_screen_dialog);
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT);

        this.onCategoryAddListener = onCategoryAddListener;
        this.category = category;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_category_dialog);

        nameEditText = findViewById(R.id.name);
        typeSpinner = findViewById(R.id.type);
        budgetEditText = findViewById(R.id.budget);
        saveButton = findViewById(R.id.btnSave);
        cancelButton = findViewById(R.id.btnCancel);

        if (category != null) {
            nameEditText.setText(category.getName());
        }

        saveButton.setOnClickListener((View v) -> {
            SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT, Locale.US);
            try {
                onCategoryAddListener.AddCategory(
                        new Category(nameEditText.getText().toString().trim(),
                                0,
                                new Date()),
                        Double.parseDouble(budgetEditText.getText().toString().trim())
                );
            } catch (Exception e) {
                e.printStackTrace();
            }

            dismiss();
        });

        cancelButton.setOnClickListener((View v) -> {
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

    public interface OnCategoryAddListener {
        public void AddCategory(Category category, double budgetAmount);

        public void UpdateCategory(Category category);

    }
}
