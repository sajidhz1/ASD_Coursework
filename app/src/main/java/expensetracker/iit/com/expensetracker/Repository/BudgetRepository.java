package expensetracker.iit.com.expensetracker.Repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

import expensetracker.iit.com.expensetracker.AppDatabase;
import expensetracker.iit.com.expensetracker.Dao.BudgetDao;
import expensetracker.iit.com.expensetracker.Model.Budget;

public class BudgetRepository
{
    private BudgetDao mbudgetDao;
    private LiveData<List<Budget>> mAllBudgets;

    public BudgetRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        mbudgetDao = db.budgetDao();
        mAllBudgets = mbudgetDao.getAllLive();
    }

    public LiveData<List<Budget>> getAllBudgets() {
        return mAllBudgets;
    }

    public void insert (Budget word) {
        new insertAsyncTask(mbudgetDao).execute(word);
    }

    public void deleteAll() {
        new BudgetRepository.deleteAllTaskAsync(mbudgetDao).execute();
    }

    private static class insertAsyncTask extends AsyncTask<Budget, Void, Void> {

        private BudgetDao mAsyncTaskDao;

        insertAsyncTask(BudgetDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Budget... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    private static class deleteAllTaskAsync extends AsyncTask<Void, Void, Void> {

        private BudgetDao mAsyncTaskDao;

        deleteAllTaskAsync(BudgetDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mAsyncTaskDao.deleteAll();
            return null;
        }
    }
}
