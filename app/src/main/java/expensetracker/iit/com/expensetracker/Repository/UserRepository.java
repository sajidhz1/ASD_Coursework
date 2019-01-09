package expensetracker.iit.com.expensetracker.Repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

import expensetracker.iit.com.expensetracker.AppDatabase;
import expensetracker.iit.com.expensetracker.Dao.UserDao;
import expensetracker.iit.com.expensetracker.Model.User;

public class UserRepository {
    private UserDao mUserDao;
    private LiveData<List<User>> mAllUsers;

    public UserRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        mUserDao= db.UserDao();
        mAllUsers = mUserDao.getAllLive();
    }

    public void insert (User obj) {
        new UserRepository.insertAsyncTask(mUserDao).execute(obj);
    }

    public void delete (User obj) {
        new UserRepository.deleteTaskAsync(mUserDao).execute(obj);
    }
    public LiveData<List<User>> getAllUsers() {
        return mUserDao.getAllLive();
    }

    private static class insertAsyncTask extends AsyncTask<User, Void, Void> {

        private UserDao mAsyncTaskDao;

        insertAsyncTask(UserDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(User... users) {
            mAsyncTaskDao.insert(users[0]);
            return null;
        }
    }
    private static class deleteTaskAsync extends AsyncTask<User, Void, Void> {
        private UserDao mAsyncTaskDao;

        deleteTaskAsync(UserDao dao) {
            mAsyncTaskDao = dao;
        }


        @Override
        protected Void doInBackground(User... users) {
            mAsyncTaskDao.insert(users[0]);
            return null;
        }
    }
}
