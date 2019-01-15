package expensetracker.iit.com.expensetracker.Repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

import expensetracker.iit.com.expensetracker.AppDatabase;
import expensetracker.iit.com.expensetracker.Dao.BudgetDao;
import expensetracker.iit.com.expensetracker.Dao.CategoryDao;
import expensetracker.iit.com.expensetracker.Model.Budget;
import expensetracker.iit.com.expensetracker.Model.Category;

public class BudgetRepository {
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

    public int insert(Budget budget) {
        return (int) mbudgetDao.insert(budget);
    }

    public void update(Budget budget) {
        new BudgetRepository.updateTaskAsync(mbudgetDao).execute(budget);
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

    private static class updateTaskAsync extends AsyncTask<Budget, Void, Void> {
        private BudgetDao mAsyncTaskDao;

        updateTaskAsync(BudgetDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Budget... budgets) {
            mAsyncTaskDao.update(budgets[0]);
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