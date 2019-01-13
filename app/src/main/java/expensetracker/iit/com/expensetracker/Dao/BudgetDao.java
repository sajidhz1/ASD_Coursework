package expensetracker.iit.com.expensetracker.Dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import expensetracker.iit.com.expensetracker.Model.Budget;
import expensetracker.iit.com.expensetracker.Model.Category;

@Dao
public interface BudgetDao
{
    @Query("SELECT * FROM `budget`")
    List<Budget> getAll();

    @Query("SELECT * FROM `budget` WHERE bid IN (:budgetIds)")
    List<Budget> loadAllByIds(int[] budgetIds);

    @Query("SELECT * FROM `budget` WHERE bid == (:budgetId)")
    Budget getBudgetById(int budgetId);

    @Insert
    void insertAll(Budget... budgets);

    @Insert
    long insert(Budget budget);

    @Update
    void update(Budget budget);

    @Delete
    void delete(Budget budget);

    @Query("SELECT * from budget")
    LiveData<List<Budget>> getAllLive(); 

    @Query("DELETE FROM `category`")
    public void deleteAll();
}
