package expensetracker.iit.com.expensetracker.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

import expensetracker.iit.com.expensetracker.Model.Category;
import expensetracker.iit.com.expensetracker.Model.Transaction;
import expensetracker.iit.com.expensetracker.Repository.CategoryRepository;
import expensetracker.iit.com.expensetracker.Repository.TransactionRepository;

public class CategoryViewModel extends AndroidViewModel {

    private CategoryRepository mCategoryRepository;

    private LiveData<List<Category>> mAllCategories;

    public LiveData<List<Category>> getAllCategories() { return mAllCategories; }

    public void insert(Category category) { mCategoryRepository.insert(category); }

    public void delete(Category category) { mCategoryRepository.delete(category); }

    public CategoryViewModel(@NonNull Application application) {
        super(application);
        mCategoryRepository = new CategoryRepository(application);
        mAllCategories = mCategoryRepository.getAllCategories();
    }
}
