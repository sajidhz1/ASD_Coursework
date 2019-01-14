package expensetracker.iit.com.expensetracker.Model;

public class UserRegisterModel {

    private static User currentUser;

    public static User getCurrentUser(){
        return currentUser;
    }

    public static void setCurrentUser(User user){
        currentUser = user;
    }
}
