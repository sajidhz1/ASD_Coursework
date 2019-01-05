package expensetracker.iit.com.expensetracker.Dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

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

    @Insert
    void insertAll(Budget... budgets);

    @Delete
    void delete(Budget budget);
}
