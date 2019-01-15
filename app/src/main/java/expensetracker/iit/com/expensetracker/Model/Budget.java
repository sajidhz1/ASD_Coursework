package expensetracker.iit.com.expensetracker.Model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Budget {

    @PrimaryKey(autoGenerate = true)
    public int bid;

    @ColumnInfo(name = "budget_amount")
    public double budget;

    public Budget() {
    }

    public Budget(double budget) {
        this.budget = budget;
    }

    public int getBid() {
        return bid;
    }

    public void setBid(int bid) {
        this.bid = bid;
    }

    public double getBudget() {
        return budget;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }

}
