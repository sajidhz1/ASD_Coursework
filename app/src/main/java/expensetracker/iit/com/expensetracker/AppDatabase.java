package expensetracker.iit.com.expensetracker;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import expensetracker.iit.com.expensetracker.Dao.BudgetDao;
import expensetracker.iit.com.expensetracker.Dao.CategoryDao;
import expensetracker.iit.com.expensetracker.Dao.TransactionsDao;
import expensetracker.iit.com.expensetracker.Dao.UserDao;
import expensetracker.iit.com.expensetracker.Model.Budget;
import expensetracker.iit.com.expensetracker.Model.Category;
import expensetracker.iit.com.expensetracker.Model.Transaction;
import expensetracker.iit.com.expensetracker.Model.User;

@Database(entities = {Budget.class, Transaction.class, Category.class, User.class}, version = 3)
public abstract class AppDatabase extends RoomDatabase
{
    public abstract TransactionsDao transactionsDao();
    public abstract CategoryDao categoryDao();
    public abstract BudgetDao budgetDao();
    public abstract UserDao UserDao();

    private static volatile AppDatabase INSTANCE;

    public static AppDatabase getDatabase(final Context context)
    {
        if (INSTANCE == null)
        {
            synchronized (AppDatabase.class)
            {
                if (INSTANCE == null)
                {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "expensetracker_database_v6").build();
                }
            }
        }
        return INSTANCE;
    }
}
