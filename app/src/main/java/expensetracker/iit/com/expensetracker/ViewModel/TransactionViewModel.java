package expensetracker.iit.com.expensetracker.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

import expensetracker.iit.com.expensetracker.Model.Transaction;
import expensetracker.iit.com.expensetracker.Repository.TransactionRepository;

public class TransactionViewModel extends AndroidViewModel
{
    private TransactionRepository mRepository;

    private LiveData<List<Transaction>> mAllTransactions;

    LiveData<List<Transaction>> getAllBudgets() { return mAllTransactions; }

    public void insert(Transaction transaction) { mRepository.insert(transaction); }

    public TransactionViewModel(@NonNull Application application) {
        super(application);
        mRepository = new TransactionRepository(application);
        mAllTransactions = mRepository.getAllBudgets();
    }
}
