package expensetracker.iit.com.expensetracker.Model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import java.util.Date;

import expensetracker.iit.com.expensetracker.Common.DateConverter;

@Entity
public class Transaction
{
    @PrimaryKey
    public int tid;

    @ColumnInfo(name = "amount")
    public double Amount;

    @ColumnInfo(name = "category_id")
    public int categoryID;

    @ColumnInfo(name = "note")
    public String note;

    @ColumnInfo(name = "recurring")
    public boolean recurring;

    @TypeConverters(DateConverter.class)
    public Date addedDate;
}
