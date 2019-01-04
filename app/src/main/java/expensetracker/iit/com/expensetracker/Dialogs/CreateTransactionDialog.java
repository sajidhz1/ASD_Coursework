package expensetracker.iit.com.expensetracker.Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Window;

import expensetracker.iit.com.expensetracker.R;

public class CreateTransactionDialog extends Dialog {

    public CreateTransactionDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.create_transaction_dialog);
    }
}
