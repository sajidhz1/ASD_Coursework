package expensetracker.iit.com.expensetracker.Model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.sql.Timestamp;

@Entity
public class Transaction
{
    @PrimaryKey
    public int tid;

    @ColumnInfo(name = "amount")
    public double Amount;

    @ColumnInfo(name = "category")
    public Category category;

    @ColumnInfo(name = "note")
    public String note;

    @ColumnInfo(name = "recurring")
    public boolean recurring;

    @ColumnInfo(name = "recurring")
    public Timestamp addedDate;
}
