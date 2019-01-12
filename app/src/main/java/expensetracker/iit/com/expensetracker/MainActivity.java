package expensetracker.iit.com.expensetracker;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import expensetracker.iit.com.expensetracker.Fragments.BaseFragment;
import expensetracker.iit.com.expensetracker.Fragments.CategoriesFragment;
import expensetracker.iit.com.expensetracker.Fragments.SettingsFragment;
import expensetracker.iit.com.expensetracker.Fragments.SpendingFragment;
import expensetracker.iit.com.expensetracker.Fragments.TransactionsFragment;

public class MainActivity extends AppCompatActivity implements BaseFragment.OnCreateListener{

    private TextView toolBarTitle;
    private Button addButton, editButton;
    Fragment selectedFragment = null;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.navigation_spending:
                    toolBarTitle.setText(getResources().getString(R.string.title_spending));
                    toolBarTitle.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                    selectedFragment = SpendingFragment.newInstance();
                    break;
                case R.id.navigation_transactions:
                    toolBarTitle.setText(getResources().getString(R.string.title_transactions));
                    Drawable d  = getResources().getDrawable(R.drawable.ic_expand_more_black_24dp);
                    d.setTint(Color.WHITE);
                    toolBarTitle.setCompoundDrawablesWithIntrinsicBounds(null, null,d, null);
                    selectedFragment = TransactionsFragment.newInstance();
                    break;
                case R.id.navigation_categories:
                    toolBarTitle.setText(getResources().getString(R.string.title_categories));
                    toolBarTitle.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                    selectedFragment = CategoriesFragment.newInstance();
                    break;
                case R.id.navigation_settings:
                    toolBarTitle.setText(R.string.settings);
                    toolBarTitle.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                    selectedFragment = SettingsFragment.newInstance();
                    break;
            }

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frame_layout, selectedFragment);
            transaction.commit();
            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        selectedFragment = SpendingFragment.newInstance();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, selectedFragment);
        transaction.commit();

        addButton = (Button) findViewById(R.id.buttonAdd);
        addButton.setOnClickListener((View v) -> {
            if(selectedFragment instanceof BaseFragment)
            {
                ((BaseFragment) selectedFragment).OpenAddNewDialog();
            }
        });

        toolBarTitle = ((TextView) findViewById(R.id.toolbar_title));
        toolBarTitle.setOnClickListener((View v) -> {
            if(selectedFragment instanceof BaseFragment)
            {
                ((BaseFragment) selectedFragment).OnTitleClicked(v);
            }
        });


        editButton = (Button) findViewById(R.id.buttonEdit);
    }

    @Override
    public void SetAddButtonVisibility(boolean show) {
        if(show)
        {
            addButton.setVisibility(View.VISIBLE);
        }
        else
        {
            addButton.setVisibility(View.GONE);
        }
    }

    @Override
    public void SetEditButtonVisibility(boolean show) {
        if(show)
        {
            editButton.setVisibility(View.VISIBLE);
        }
        else
        {
            editButton.setVisibility(View.GONE);
        }
    }
}
