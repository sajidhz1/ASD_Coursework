package expensetracker.iit.com.expensetracker.Fragments;

import android.annotation.SuppressLint;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.view.SupportMenuInflater;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuPopupHelper;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import expensetracker.iit.com.expensetracker.Dialogs.CreateTransactionDialog;
import expensetracker.iit.com.expensetracker.Model.Transaction;
import expensetracker.iit.com.expensetracker.R;
import expensetracker.iit.com.expensetracker.ViewModel.TransactionViewModel;

public class TransactionsFragment extends BaseFragment implements CreateTransactionDialog.OnTransactionAddListener {

    private RecyclerView recyclerView;
    private TextView sort;
    private TransactionViewModel mTransactionViewModel;
    private TransactionAdapter adapter;

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

        // Get a new or existing ViewModel from the ViewModelProvider.
        mTransactionViewModel = ViewModelProviders.of(this).get(TransactionViewModel.class);

        recyclerView = (RecyclerView) getView().findViewById(R.id.transactionsList);
        adapter = new TransactionAdapter(getContext(), mTransactionViewModel, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mTransactionViewModel.getAllTransactions().observe(this, new Observer<List<Transaction>>() {
            @Override
            public void onChanged(@Nullable final List<Transaction> transactions) {
                // Update the cached copy of the words in the adapter.
                adapter.setTransactions(transactions);
            }
        });

        sort =  getView().findViewById(R.id.sort);
        sort.setOnClickListener((View v) -> {

            @SuppressLint("RestrictedApi")
            MenuBuilder menuBuilder = new MenuBuilder(getContext());
            @SuppressLint("RestrictedApi")
            SupportMenuInflater inflater = new SupportMenuInflater(getContext());

            inflater.inflate(R.menu.menu_sort, menuBuilder);
            @SuppressLint("RestrictedApi")
            MenuPopupHelper optionsMenu = new MenuPopupHelper(getContext(), menuBuilder, v);
            optionsMenu.setForceShowIcon(true);
            menuBuilder.setCallback(new MenuBuilder.Callback() {
                @Override
                public boolean onMenuItemSelected(MenuBuilder menu, MenuItem item) {
                    List<Transaction> transactions = mTransactionViewModel.getAllTransactions().getValue();
                    switch (item.getItemId()) {
                        case R.id.amountHightoLow:
                            recyclerView.removeAllViews();
                            Collections.sort(transactions, (o1, o2) -> {
                                return Double.compare(o1.getAmount(), o2.getAmount());
                            });
                            adapter.setTransactions(transactions);
                            sort.setText(item.getTitle());
                            return true;
                        case R.id.amountLowtoHigh:
                            recyclerView.removeAllViews();
                            Collections.sort(transactions, (o1, o2) -> {
                                return Double.compare(o2.getAmount(), o1.getAmount());
                            });
                            adapter.setTransactions(transactions);
                            sort.setText(item.getTitle());
                            return true;
                        default:
                            return false;
                    }
                }

                @Override
                public void onMenuModeChange(MenuBuilder menu) {}
            });
            optionsMenu.show();
        });
    }

    @Override
    public void OpenAddNewDialog()
    {
        CreateTransactionDialog cdd = new CreateTransactionDialog(getActivity(), this, null);
        cdd.show();
    }

    @Override
    public void AddTransaction(Transaction transaction)
    {
        mTransactionViewModel.insert(transaction);
    }

    @Override
    public void UpdateTransaction(Transaction transaction)
    {
        mTransactionViewModel.update(transaction);
    }

    public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder> {

        private TransactionViewModel viewModel;

        class TransactionViewHolder extends RecyclerView.ViewHolder {
            protected TextView transactionNoteTextView, amountTextView;
            protected ImageView transactionIcon;
            protected ImageButton deleteButton;
            protected ImageButton editButton;


            private TransactionViewHolder(View itemView) {
                super(itemView);
                transactionIcon = itemView.findViewById(R.id.imgIcon);
                transactionNoteTextView = itemView.findViewById(R.id.txtTitle);
                amountTextView = itemView.findViewById(R.id.txtAmount);
                deleteButton = itemView.findViewById(R.id.delete_button);
                editButton= itemView.findViewById(R.id.edit_button);
            }
        }

        private final LayoutInflater mInflater;
        private Context mContext;
        private List<Transaction> mTransactions;
        private CreateTransactionDialog.OnTransactionAddListener onTransactionAddListener;

        TransactionAdapter(Context context, TransactionViewModel mTransactionViewModel, CreateTransactionDialog.OnTransactionAddListener listener)
        {
            this.mContext = context;
            mInflater = LayoutInflater.from(context);
            viewModel = mTransactionViewModel;
            onTransactionAddListener = listener;
        }

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
                holder.amountTextView.setText(current.getAmount() + " $");
                holder.editButton.setOnClickListener((View v) -> {
                    CreateTransactionDialog cdd = new CreateTransactionDialog(mContext, onTransactionAddListener, current);
                    cdd.show();
                });

                holder.deleteButton.setOnClickListener((View v) -> {
                    viewModel.delete(current);
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
