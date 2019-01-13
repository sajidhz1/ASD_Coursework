package expensetracker.iit.com.expensetracker.Fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import expensetracker.iit.com.expensetracker.Model.Transaction;
import expensetracker.iit.com.expensetracker.R;
import expensetracker.iit.com.expensetracker.ViewModel.TransactionViewModel;

public class SpendingFragment extends BaseFragment {
    private TransactionViewModel mTransactionViewModel;
    private List spendingsIncomeList;
    private TransactionsFragment.TransactionAdapter adapter;
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

        // Get a new or existing ViewModel from the ViewModelProvider.
        mTransactionViewModel = ViewModelProviders.of(this).get(TransactionViewModel.class);


    }


    public class SpendingsAdapter extends BaseAdapter {

        private Context mContext;
        private String[]  Title;
        private int[] imge;

        public SpendingsAdapter(Context context, String[] text1, int[] imageIds) {
            mContext = context;
            Title = text1;
            imge = imageIds;

        }

        public int getCount() {
            // TODO Auto-generated method stub
            return Title.length;
        }

        public Object getItem(int arg0) {
            // TODO Auto-generated method stub
            return null;
        }

        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = getLayoutInflater();
            View row;
            row = inflater.inflate(R.layout.transactions_row, parent, false);
            TextView title;
            ImageView i1;
            i1 = (ImageView) row.findViewById(R.id.imgIcon);
            title = (TextView) row.findViewById(R.id.txtTitle);
            title.setText(Title[position]);
            i1.setImageResource(imge[position]);

            return (row);
        }
    }
    List<Transaction> Temp;
    @Override
    public void OnTitleClicked(View v)
    {

        PopupMenu popup = new PopupMenu(getContext(), v);
        popup.getMenuInflater().inflate(R.menu.months_menu, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.january:
                        //recyclerView.removeAllViews();
                        //adapter.setTransactions(getTransactionByMonth(1));
                        Temp = getTransactionByMonth(1);
                        Toast.makeText(getActivity(), "January", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.february:
                        //recyclerView.removeAllViews();
                        //adapter.setTransactions(getTransactionByMonth(2));
                        return true;
                    case R.id.march:
                        //recyclerView.removeAllViews();
                        //adapter.setTransactions(getTransactionByMonth(3));
                        return true;
                    case R.id.april:
                        //recyclerView.removeAllViews();
                        //adapter.setTransactions(getTransactionByMonth(4));
                        return true;
                    case R.id.may:
                        //recyclerView.removeAllViews();
                        //adapter.setTransactions(getTransactionByMonth(5));
                        return true;
                    case R.id.june:
                        //recyclerView.removeAllViews();
                        //adapter.setTransactions(getTransactionByMonth(6));
                        return true;
                    case R.id.july:
                        //recyclerView.removeAllViews();
                        //adapter.setTransactions(getTransactionByMonth(7));
                        return true;
                    case R.id.august:
                        //recyclerView.removeAllViews();
                        //adapter.setTransactions(getTransactionByMonth(8));
                        return true;
                    case R.id.september:
                        //recyclerView.removeAllViews();
                        //adapter.setTransactions(getTransactionByMonth(9));
                        return true;
                    case R.id.october:
                        //recyclerView.removeAllViews();
                        //adapter.setTransactions(getTransactionByMonth(10));
                    case R.id.november:
                        //recyclerView.removeAllViews();
                        //adapter.setTransactions(getTransactionByMonth(11));
                    case R.id.december:
                        //recyclerView.removeAllViews();
                        //adapter.setTransactions(getTransactionByMonth(12));
                    case R.id.all:
                        //recyclerView.removeAllViews();
                        //adapter.setTransactions(mTransactionViewModel.getAllTransactions().getValue());
                        return true;
                    default:
                        return false;
                }
            }
        });

        popup.show();
    }

    private List<Transaction> getTransactionByMonth(int i)
    {
        ArrayList<Transaction> transactions = new ArrayList<>();
        try {
            for (Transaction transaction : mTransactionViewModel.getAllTransactions().getValue()) {
                if (Integer.parseInt(DateFormat.format("MM", transaction.getAddedDate()).toString()) == i) {
                    transactions.add(transaction);
                }
            }
            return transactions;
        }
        catch (Exception e)
        {
            Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT).show();
        }
        finally {
            return transactions;
        }
    }


}
