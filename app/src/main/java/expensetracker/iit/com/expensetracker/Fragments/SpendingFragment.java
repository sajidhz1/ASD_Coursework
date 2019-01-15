package expensetracker.iit.com.expensetracker.Fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import expensetracker.iit.com.expensetracker.Model.Transaction;
import expensetracker.iit.com.expensetracker.R;
import expensetracker.iit.com.expensetracker.ViewModel.TransactionViewModel;

public class SpendingFragment extends BaseFragment {
    private TransactionViewModel mTransactionViewModel;
    private SpendingsAdapter spendingsAdapter;
    private SpendingsAdapter expenseAdapter;

    public static SpendingFragment newInstance() {
        SpendingFragment fragment = new SpendingFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.spending_fragment, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();

        spendingsAdapter = new SpendingsAdapter(getContext());
        spendingsAdapter.setTransactions(new ArrayList<Transaction>());
        expenseAdapter = new SpendingsAdapter(getContext());
        expenseAdapter.setTransactions(new ArrayList<Transaction>());

        ((ListView) getView().findViewById(R.id.spendingsIncomeList)).setAdapter(spendingsAdapter);
        ((ListView) getView().findViewById(R.id.spendingsExpenseList)).setAdapter(expenseAdapter);

        mTransactionViewModel = ViewModelProviders.of(this).get(TransactionViewModel.class);
        mTransactionViewModel.getTransactionByCategoryType(1).observe(this, new Observer<List<Transaction>>() {
            @Override
            public void onChanged(@Nullable final List<Transaction> transactions) {
                spendingsAdapter.setTransactions(transactions);
            }
        });

        mTransactionViewModel.getTransactionByCategoryType(2).observe(this, new Observer<List<Transaction>>() {
            @Override
            public void onChanged(@Nullable final List<Transaction> transactions) {
                expenseAdapter.setTransactions(transactions);
            }
        });
    }


    public class SpendingsAdapter extends BaseAdapter {

        private Context mContext;
        private List<Transaction> transactions;

        public SpendingsAdapter(Context context) {
            mContext = context;
        }

        public int getCount() {
            return transactions.size();
        }

        public Object getItem(int arg0) {
            return null;
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = getLayoutInflater();
            View row;
            row = inflater.inflate(R.layout.spendings_row, parent, false);
            TextView title;
            ImageView i1;
            i1 = (ImageView) row.findViewById(R.id.imgIcon);
            title = (TextView) row.findViewById(R.id.txtTitle);
            title.setText(transactions.get(position).getNote());
            return (row);
        }

        void setTransactions(List<Transaction> transactions){
            this.transactions = transactions;
            notifyDataSetChanged();
        }

    }
}
