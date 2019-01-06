package expensetracker.iit.com.expensetracker.Fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import expensetracker.iit.com.expensetracker.Dialogs.CreateTransactionDialog;
import expensetracker.iit.com.expensetracker.Model.Transaction;
import expensetracker.iit.com.expensetracker.R;
import expensetracker.iit.com.expensetracker.ViewModel.TransactionViewModel;

public class TransactionsFragment extends BaseFragment implements CreateTransactionDialog.OnTransactionAddListener {

    private ListView transactionsList;
    private TransactionViewModel mTransactionViewModel;

    public static TransactionsFragment newInstance() {
        TransactionsFragment fragment = new TransactionsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getActivity() instanceof OnCreateListener)
        {
            ((OnCreateListener) getActivity()).SetAddButtonVisibility(true);
            //((OnCreateListener) getActivity()).SetEditButtonVisibility(true);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.transactions_fragment, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();

        RecyclerView recyclerView = (RecyclerView) getView().findViewById(R.id.transactionsList);
        final TransactionAdapter adapter = new TransactionAdapter(getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Get a new or existing ViewModel from the ViewModelProvider.
        mTransactionViewModel = ViewModelProviders.of(this).get(TransactionViewModel.class);

        mTransactionViewModel.getAllTransactions().observe(this, new Observer<List<Transaction>>() {
            @Override
            public void onChanged(@Nullable final List<Transaction> transactions) {
                // Update the cached copy of the words in the adapter.
                adapter.setTransactions(transactions);
            }
        });
    }

    @Override
    public void OpenAddNewDialog()
    {
        CreateTransactionDialog cdd = new CreateTransactionDialog(getActivity(), this);
        cdd.show();
    }

    @Override
    public void AddTransaction(Transaction transaction)
    {
        mTransactionViewModel.insert(transaction);
    }

    public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder> {

        class TransactionViewHolder extends RecyclerView.ViewHolder {
            protected TextView transactionNoteTextView;
            protected ImageView transactionIcon;
            protected ImageButton deleteButton;
            protected ImageButton editButton;


            private TransactionViewHolder(View itemView) {
                super(itemView);
                transactionIcon = itemView.findViewById(R.id.imgIcon);
                transactionNoteTextView = itemView.findViewById(R.id.txtTitle);
                deleteButton = itemView.findViewById(R.id.delete_button);
                editButton= itemView.findViewById(R.id.edit_button);
            }
        }

        private final LayoutInflater mInflater;
        private List<Transaction> mTransactions;

        TransactionAdapter(Context context) { mInflater = LayoutInflater.from(context); }

        @Override
        public TransactionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = mInflater.inflate(R.layout.transactions_row, parent, false);
            return new TransactionViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(TransactionViewHolder holder, int position) {
            if (mTransactions != null) {
                Transaction current = mTransactions.get(position);
                holder.transactionIcon.setImageResource(R.drawable.ic_attach_spending_black_24dp);
                holder.transactionNoteTextView.setText(current.getNote());

                holder.editButton.setOnClickListener((View v) -> {

                });

                holder.deleteButton.setOnClickListener((View v) -> {

                });
            } else {
                // Covers the case of data not being ready yet.
                holder.transactionNoteTextView.setText("No Word");
            }
        }

        void setTransactions(List<Transaction> transactions){
            mTransactions = transactions;
            notifyDataSetChanged();
        }

        // getItemCount() is called many times, and when it is first called,
        // mWords has not been updated (means initially, it's null, and we can't return null).
        @Override
        public int getItemCount() {
            if (mTransactions != null)
                return mTransactions.size();
            else return 0;
        }
    }
}
