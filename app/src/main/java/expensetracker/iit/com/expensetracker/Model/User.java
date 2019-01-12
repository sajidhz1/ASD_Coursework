package expensetracker.iit.com.expensetracker.Model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class User {

    @PrimaryKey(autoGenerate = true)
    public int pid;

    @ColumnInfo(name = "userName")
    private String userName;

    @ColumnInfo(name = "pinNo")
    private int pinNo;

    public User()
    {
    }

    public User(String userName, int pinNo)
    {
        this.setUserName(userName);
        this.setPinNo(pinNo);
    }

    public int getPid() {
        return pid;
    }


    public String getUserName() {
        return userName;
    }

    public int getPinNo() {
        return pinNo;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPinNo(int pinNo) {
        this.pinNo = pinNo;
    }
}
