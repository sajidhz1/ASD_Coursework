package expensetracker.iit.com.expensetracker.Dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import expensetracker.iit.com.expensetracker.Model.Budget;
import expensetracker.iit.com.expensetracker.Model.Transaction;

@Dao
public interface TransactionsDao
{
    @Query("SELECT * FROM `transaction`")
    List<Transaction> getAll();

    @Query("SELECT * FROM `transaction` WHERE tid IN (:transactionIds)")
    List<Transaction> loadAllByIds(int[] transactionIds);

    @Insert
    void insertAll(Transaction... transactions);

    @Delete
    void delete(Transaction transaction);

    @Insert
    void insert(Transaction param);

    @Query("SELECT * FROM `transaction`")
    LiveData<List<Transaction>> getAllLive();
}
