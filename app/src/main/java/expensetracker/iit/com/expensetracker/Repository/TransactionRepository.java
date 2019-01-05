package expensetracker.iit.com.expensetracker.Repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

import expensetracker.iit.com.expensetracker.AppDatabase;
import expensetracker.iit.com.expensetracker.Dao.TransactionsDao;
import expensetracker.iit.com.expensetracker.Model.Transaction;

public class TransactionRepository
{
    private TransactionsDao mTransactionDao;
    private LiveData<List<Transaction>> mAllBudgets;

    public TransactionRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        mTransactionDao = db.transactionsDao();
        mAllBudgets = mTransactionDao.getAllLive();
    }

    public LiveData<List<Transaction>> getAllBudgets() {
        return mAllBudgets;
    }


    public void insert (Transaction transaction) {
        new TransactionRepository.insertAsyncTask(mTransactionDao).execute(transaction);
    }

    private static class insertAsyncTask extends AsyncTask<Transaction, Void, Void> {

        private TransactionsDao mAsyncTaskDao;

        insertAsyncTask(TransactionsDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Transaction... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
}
