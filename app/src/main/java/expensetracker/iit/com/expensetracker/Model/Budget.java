package expensetracker.iit.com.expensetracker.Model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Budget {
    @PrimaryKey
    public int bid;

    @ColumnInfo(name = "budget_amount")
    public double budget;
}
