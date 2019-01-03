package expensetracker.iit.com.expensetracker.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import expensetracker.iit.com.expensetracker.R;

public class CategoriesFragment extends BaseFragment {

    public static CategoriesFragment newInstance() {
        CategoriesFragment fragment = new CategoriesFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getActivity() instanceof OnCreateListener)
        {
            ((OnCreateListener) getActivity()).SetAddButtonVisibility(true);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.categories_fragment, container, false);
    }

    public class CategoriesAdapter extends BaseAdapter {

        private Context mContext;
        private String[]  Title;
        private int[] imge;

        public CategoriesAdapter(Context context, String[] text1, int[] imageIds) {
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
}
