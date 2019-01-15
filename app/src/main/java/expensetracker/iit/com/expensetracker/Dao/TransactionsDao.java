package expensetracker.iit.com.expensetracker.Dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

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

    @Update
    void update(Transaction transaction);

    @Insert
    void insert(Transaction param);

    @Query("SELECT * FROM `transaction`")
    LiveData<List<Transaction>> getAllLive();

    @Query("DELETE FROM `transaction`")
    public void deleteAll();

    @Query("SELECT * FROM `Transaction` " +
            "INNER JOIN Category ON `transaction`.category_id = category.cid " +
            "WHERE Category.type LIKE :category "
    )
    public LiveData<List<Transaction>> getTransactionByCategoryType(int category);
}
