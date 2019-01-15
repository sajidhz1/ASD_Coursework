package expensetracker.iit.com.expensetracker.Dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import expensetracker.iit.com.expensetracker.Model.Category;

@Dao
public interface CategoryDao {
    @Query("SELECT * FROM `category`")
    List<Category> getAll();

    @Query("SELECT * FROM `category` WHERE cid IN (:categoryIds)")
    List<Category> loadAllByIds(int[] categoryIds);

    @Insert
    void insertAll(Category... categories);

    @Delete
    void delete(Category category);

    @Update
    void update(Category category);

    @Insert
    void insert(Category category);

    @Query("SELECT * FROM `category`")
    LiveData<List<Category>> getAllLive();

    @Query("DELETE FROM `category`")
    void deleteAll();
}
