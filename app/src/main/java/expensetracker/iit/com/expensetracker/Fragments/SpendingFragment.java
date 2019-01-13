package expensetracker.iit.com.expensetracker.Fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import expensetracker.iit.com.expensetracker.Model.Transaction;
import expensetracker.iit.com.expensetracker.R;
import expensetracker.iit.com.expensetracker.ViewModel.TransactionViewModel;

public class SpendingFragment extends BaseFragment {
    private TransactionViewModel mTransactionViewModel;
    private ListView spendingsIncomeListVar;
    private ListView spendingsExpenseListVar;
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
        spendingsExpenseListVar  =  getView().findViewById(R.id.spendingsExpenseList);
        spendingsIncomeListVar  =  getView().findViewById(R.id.spendingsIncomeList);

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
                        SetListValues(getTransactionByMonth(1));
                        return true;
                    case R.id.february:
                        SetListValues(getTransactionByMonth(2));
                        return true;
                    case R.id.march:
                        SetListValues(getTransactionByMonth(3));
                        return true;
                    case R.id.april:
                        SetListValues(getTransactionByMonth(4));
                        return true;
                    case R.id.may:
                        SetListValues(getTransactionByMonth(5));
                        return true;
                    case R.id.june:
                        SetListValues(getTransactionByMonth(6));
                        return true;
                    case R.id.july:
                        SetListValues(getTransactionByMonth(7));
                        return true;
                    case R.id.august:
                        SetListValues(getTransactionByMonth(8));
                        return true;
                    case R.id.september:
                        SetListValues(getTransactionByMonth(9));
                        return true;
                    case R.id.october:
                        SetListValues(getTransactionByMonth(10));
                        return true;
                    case R.id.november:
                        SetListValues(getTransactionByMonth(11));
                        return true;
                    case R.id.december:
                        SetListValues(getTransactionByMonth(12));
                        return true;
                    case R.id.all:
                        SetListValues(getAllTransactions());
                        return true;
                    default:
                        return false;
                }
            }
        });

        popup.show();
    }

    private ArrayList<Transaction> getTransactionByMonth(int i)
    {
        ArrayList<Transaction> transactions = new ArrayList<>();
        for (Transaction transaction : mTransactionViewModel.getAllTransactions().getValue()) {
            if (Integer.parseInt(DateFormat.format("MM", transaction.getAddedDate()).toString()) == i) {
                transactions.add(transaction);
            }
        }
        return transactions;
    }

    private ArrayList<Transaction> getAllTransactions()
    {
        ArrayList<Transaction> transactions = new ArrayList<>();
        for (Transaction transaction : mTransactionViewModel.getAllTransactions().getValue()) {
            transactions.add(transaction);
        }
        return transactions;
    }

    private Boolean SetListValues(ArrayList<Transaction> transactions)
    {
        List<String> itemIncome = new ArrayList<String>();
        List<String> subItemIncome = new ArrayList<String>();
        List<String> itemExpenses = new ArrayList<String>();
        List<String> subItemExpenses = new ArrayList<String>();
        // Setting Income List
        for(int i=0; i < transactions.size(); i++) {
            if (transactions.get(i).getAmount() < 0) // Assumed getting all the expenses
            {
                itemIncome.add(Integer.toString(transactions.get(i).getCategoryID()));
                subItemIncome.add(Double.toString(transactions.get(i).getAmount()));
            }
            if (transactions.get(i).getAmount() >= 0) // Assumed getting all the expenses
            {
                itemExpenses.add(Integer.toString(transactions.get(i).getCategoryID()));
                subItemExpenses.add(Double.toString(transactions.get(i).getAmount()));
            }
        }

        CustomListViewAdapter incomeAdapter = new CustomListViewAdapter(itemIncome,subItemIncome);
        spendingsIncomeListVar.setAdapter(incomeAdapter);
        CustomListViewAdapter expenseAdapter = new CustomListViewAdapter(itemExpenses,subItemExpenses);
        spendingsExpenseListVar.setAdapter(expenseAdapter);
        return true;
    }

    public class CustomListViewAdapter extends BaseAdapter {
        List<String> Item;
        List<String> SubItem;

        public CustomListViewAdapter(List<String> Item, List<String> SubItem) {
            this.Item = Item;
            this.SubItem = SubItem;
        }

        @Override
        public int getCount() {
            return Item.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            TextView item = (TextView) view.findViewById(R.id.itemListView);
            TextView subitem = (TextView) view.findViewById(R.id.subItemListView);
            item.setText(Item.get(i));
            subitem.setText(SubItem.get(i));
            return view;
        }
    }
}
