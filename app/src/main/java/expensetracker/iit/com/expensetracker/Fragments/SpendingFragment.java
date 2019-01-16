package expensetracker.iit.com.expensetracker.Fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import expensetracker.iit.com.expensetracker.Model.Category;
import expensetracker.iit.com.expensetracker.Model.Transaction;
import expensetracker.iit.com.expensetracker.R;
import expensetracker.iit.com.expensetracker.ViewModel.CategoryViewModel;
import expensetracker.iit.com.expensetracker.ViewModel.TransactionViewModel;

public class SpendingFragment extends BaseFragment {
    private TransactionViewModel mTransactionViewModel;
    private CategoryViewModel mCategoryViewModel;
    private ExpandableListAdapter spendingsAdapter;
    List<Category> categories;

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
        View  rootView = inflater.inflate(R.layout.spending_fragment, container, false);

        spendingsAdapter = new ExpandableListAdapter(getContext(), new ArrayList<>(), new HashMap<>());
        ((ExpandableListView) rootView.findViewById(R.id.spendingsIncomeList)).setAdapter(spendingsAdapter);

        mTransactionViewModel = ViewModelProviders.of(this).get(TransactionViewModel.class);
        mCategoryViewModel = ViewModelProviders.of(this).get(CategoryViewModel.class);

        mCategoryViewModel.getAllCategories().observe(this, new Observer<List<Category>>() {
            @Override
            public void onChanged(@Nullable final List<Category> items) {
                categories = items;
            }
        });

        mTransactionViewModel.getAllTransactions().observe(this, new Observer<List<Transaction>>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onChanged(@Nullable final List<Transaction> items)
            {
                HashMap<String, List<String>> adapterItems = new HashMap<>();
                List<String> headersList = new ArrayList<>();
                if(categories != null)
                {
                    for(Category c: categories)
                    {
                        headersList.add(c.name);
                        List<Transaction> trans =  items.stream().filter(transaction -> transaction.getCategoryID() == c.cid).collect(Collectors.toList());

                        List<String> valueList = new ArrayList<>();
                        for(Transaction t: trans)
                        {
                            valueList.add(t.getNote());
                        }

                        adapterItems.put(c.name, valueList);
                    }
                }

                spendingsAdapter.setItems(headersList , adapterItems);
            }
        });

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
    }


    public class ExpandableListAdapter extends BaseExpandableListAdapter {

        private Context _context;
        private List<String> _listDataHeader;
        private HashMap<String, List<String>> _listDataChild;

        public ExpandableListAdapter(Context context, List<String> listDataHeader, HashMap<String, List<String>> listChildData) {
            this._context = context;
            this._listDataHeader = listDataHeader;
            this._listDataChild = listChildData;
        }

        public void setItems(List<String> listDataHeader, HashMap<String, List<String>> listChildData)
        {
            this._listDataHeader = listDataHeader;
            this._listDataChild = listChildData;
            notifyDataSetChanged();
        }

        @Override
        public Object getChild(int groupPosition, int childPosititon) {
            return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                    .get(childPosititon);
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public View getChildView(int groupPosition, final int childPosition,
                                 boolean isLastChild, View convertView, ViewGroup parent) {

            final String childText = (String) getChild(groupPosition, childPosition);

            if (convertView == null) {
                LayoutInflater infalInflater = (LayoutInflater) this._context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater.inflate(R.layout.spendings_row, null);
            }

            TextView txtListChild = (TextView) convertView
                    .findViewById(R.id.txtTitle);

            txtListChild.setText(childText);
            return convertView;
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                    .size();
        }

        @Override
        public Object getGroup(int groupPosition) {
            return this._listDataHeader.get(groupPosition);
        }

        @Override
        public int getGroupCount() {
            return this._listDataHeader.size();
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded,
                                 View convertView, ViewGroup parent) {
            String headerTitle = (String) getGroup(groupPosition);
            if (convertView == null) {
                LayoutInflater infalInflater = (LayoutInflater) this._context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater.inflate(R.layout.list_group, null);
            }

            TextView lblListHeader = (TextView) convertView
                    .findViewById(R.id.lblListHeader);
            lblListHeader.setTypeface(null, Typeface.BOLD);
            lblListHeader.setText(headerTitle);

            return convertView;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }
    }
}
