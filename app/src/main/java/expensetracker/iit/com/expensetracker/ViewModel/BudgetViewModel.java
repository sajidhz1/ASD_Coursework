package expensetracker.iit.com.expensetracker.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

import expensetracker.iit.com.expensetracker.Model.Budget;
import expensetracker.iit.com.expensetracker.Repository.BudgetRepository;

public class BudgetViewModel extends AndroidViewModel
{
    private BudgetRepository mRepository;

    private LiveData<List<Budget>> mAllBudgets;

    public BudgetViewModel(@NonNull Application application)
    {
        super(application);
        mRepository = new BudgetRepository(application);
        mAllBudgets = mRepository.getAllBudgets();
    }

    LiveData<List<Budget>> getAllBudgets() { return mAllBudgets; }

    public void insert(Budget budget) { mRepository.insert(budget); }


}
