package expensetracker.iit.com.expensetracker.Repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

import expensetracker.iit.com.expensetracker.AppDatabase;
import expensetracker.iit.com.expensetracker.Dao.CategoryDao;
import expensetracker.iit.com.expensetracker.Dao.TransactionsDao;
import expensetracker.iit.com.expensetracker.Model.Category;
import expensetracker.iit.com.expensetracker.Model.Transaction;

public class CategoryRepository {

    private CategoryDao mCategoryDao;
    private LiveData<List<Category>> mAllCategories;

    public CategoryRepository(Application application){
        AppDatabase db = AppDatabase.getDatabase(application);
        mCategoryDao = db.categoryDao();
        mAllCategories = mCategoryDao.getAllLive();
    }

    public LiveData<List<Category>> getAllCategories(){
        return this.mAllCategories;
    }

    public void insert(Category category){
        new CategoryRepository.insertAsyncTask(mCategoryDao).equals(category);
    }

    public void delete(Category category) {
        new CategoryRepository.deleteTaskAsync(mCategoryDao).execute(category);
    }

    public void update(Category category) {
        new CategoryRepository.updateTaskAsync(mCategoryDao).execute(category);
    }

    private static class insertAsyncTask extends AsyncTask<Category, Void, Void> {

        private CategoryDao mAsyncTaskDao;

        insertAsyncTask(CategoryDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Category... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    private static class deleteTaskAsync extends AsyncTask<Category, Void, Void> {
        private CategoryDao mAsyncTaskDao;

        deleteTaskAsync(CategoryDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Category... transactions) {
            mAsyncTaskDao.delete(transactions[0]);
            return null;
        }
    }

    private static class updateTaskAsync extends AsyncTask<Category, Void, Void> {
        private CategoryDao mAsyncTaskDao;

        updateTaskAsync(CategoryDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Category... categories) {
            mAsyncTaskDao.update(categories[0]);
            return null;
        }
    }
}
