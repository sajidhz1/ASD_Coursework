package expensetracker.iit.com.expensetracker.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

import expensetracker.iit.com.expensetracker.Model.User;
import expensetracker.iit.com.expensetracker.Repository.UserRepository;

public class UserViewModel extends AndroidViewModel {

    private UserRepository mRepository;

    public void insert(User user) { mRepository.insert(user); }

    public void delete(User user) { mRepository.delete(user); }

    public void deleteAll() { mRepository.deleteAll(); }

    public List<User> getUsersByPin(int pinNo){ return mRepository.getUsersByPin(pinNo); }

    public LiveData<List<User>> getAll() { return mRepository.getAllUsers(); }

    public UserViewModel(@NonNull Application application) {
        super(application);
        mRepository = new UserRepository(application);
    }
}
