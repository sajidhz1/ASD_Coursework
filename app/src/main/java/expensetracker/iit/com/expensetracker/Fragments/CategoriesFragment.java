package expensetracker.iit.com.expensetracker.Fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.AsyncTask;
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

import expensetracker.iit.com.expensetracker.AppDatabase;
import expensetracker.iit.com.expensetracker.Dialogs.CreateCategoryDialog;
import expensetracker.iit.com.expensetracker.Model.Budget;
import expensetracker.iit.com.expensetracker.Model.Category;
import expensetracker.iit.com.expensetracker.Model.Transaction;
import expensetracker.iit.com.expensetracker.R;
import expensetracker.iit.com.expensetracker.ViewModel.BudgetViewModel;
import expensetracker.iit.com.expensetracker.ViewModel.CategoryViewModel;
import expensetracker.iit.com.expensetracker.ViewModel.TransactionViewModel;

public class CategoriesFragment extends BaseFragment implements CreateCategoryDialog.OnCategoryAddListener {

    private ListView categoriesListView;
    private CategoryViewModel mCategoryViewModel;
    private BudgetViewModel mbudgetViewModel;

    public static CategoriesFragment newInstance() {
        CategoriesFragment fragment = new CategoriesFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getActivity() instanceof OnCreateListener) {
            ((OnCreateListener) getActivity()).SetAddButtonVisibility(true);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.categories_fragment, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();

        // Get a new or existing ViewModel from the ViewModelProvider.
        mCategoryViewModel = ViewModelProviders.of(this).get(CategoryViewModel.class);
        mbudgetViewModel = ViewModelProviders.of(this).get(BudgetViewModel.class);

        RecyclerView recyclerView = (RecyclerView) getView().findViewById(R.id.categoriesList);
        final CategoryAdapter adapter = new CategoryAdapter(getContext(), mCategoryViewModel);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mCategoryViewModel.getAllCategories().observe(this, new Observer<List<Category>>() {
            @Override
            public void onChanged(@Nullable final List<Category> categories) {
                // Update the cached copy of the words in the adapter.
                adapter.setCategories(categories);
            }
        });
    }

    @Override
    public void OpenAddNewDialog() {
        CreateCategoryDialog ccd = new CreateCategoryDialog(getActivity(), this, null);

        ccd.show();
    }

    @Override
    public void AddCategory(Category category, double budgetAmount) {
        AppDatabase db = AppDatabase.getDatabase(getContext());
        Budget newBudget = new Budget();
        newBudget.setBudget(budgetAmount);
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                int budgetId = (int)db.budgetDao().insert(newBudget);
                category.setBudgetId(budgetId);
                mCategoryViewModel.insert(category);
            }
        });
    }

    @Override
    public void UpdateCategory(Category category) {
        mCategoryViewModel.update(category);
    }


    public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

        private CategoryViewModel viewModel;

        class CategoryViewHolder extends RecyclerView.ViewHolder {
            protected TextView categoryNameTextView;
            protected ImageView categoryIcon;
            protected ImageButton deleteButton;
            protected ImageButton editButton;


            private CategoryViewHolder(View itemView) {
                super(itemView);
                categoryIcon = itemView.findViewById(R.id.imgIcon);
                categoryNameTextView = itemView.findViewById(R.id.txtTitle);
                deleteButton = itemView.findViewById(R.id.delete_button);
                editButton = itemView.findViewById(R.id.edit_button);
            }
        }

        private final LayoutInflater mInflater;
        private List<Category> mCategories;

        CategoryAdapter(Context context, CategoryViewModel mCategoryViewModel) {
            mInflater = LayoutInflater.from(context);
            viewModel = mCategoryViewModel;
        }

        @Override
        public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = mInflater.inflate(R.layout.categories_row, parent, false);
            return new CategoryViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(CategoryViewHolder holder, int position) {
            if (mCategories != null) {
                Category current = mCategories.get(position);
                holder.categoryIcon.setImageResource(R.drawable.ic_attach_spending_black_24dp);
                holder.categoryNameTextView.setText(current.getName());

                holder.editButton.setOnClickListener((View v) -> {

                });

                holder.deleteButton.setOnClickListener((View v) -> {
                    viewModel.delete(current);
                });
            } else {
                // Covers the case of data not being ready yet.
                holder.categoryNameTextView.setText("No Word");
            }
        }

        void setCategories(List<Category> categories) {
            mCategories = categories;
            notifyDataSetChanged();
        }

        // getItemCount() is called many times, and when it is first called,
        // mWords has not been updated (means initially, it's null, and we can't return null).
        @Override
        public int getItemCount() {
            if (mCategories != null)
                return mCategories.size();
            else return 0;
        }
    }
}
