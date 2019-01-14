package expensetracker.iit.com.expensetracker.Dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import expensetracker.iit.com.expensetracker.Model.User;

@Dao
public interface UserDao
{
    @Query("SELECT * FROM `user`")
    List<User> getAll();

    @Query("SELECT * FROM `user` WHERE pinNo IN (:pinNo)")
    List<User> getUsersByPin (int pinNo);

    @Delete
    void delete(User param);

    @Insert
    void insert(User param);

    @Query("SELECT * FROM `user`")
    LiveData<List<User>> getAllLive();

    @Query("DELETE FROM `user`")
    public void deleteAll();

    @Update
    void update(User user);

}
