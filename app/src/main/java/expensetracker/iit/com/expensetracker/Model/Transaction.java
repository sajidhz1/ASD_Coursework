package expensetracker.iit.com.expensetracker.Model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import java.util.Date;

import expensetracker.iit.com.expensetracker.Common.DateConverter;

@Entity
public class Transaction
{
    @PrimaryKey(autoGenerate = true)
    private int tid;

    @ColumnInfo(name = "amount")
    private double Amount;

    @ColumnInfo(name = "category_id")
    private int categoryID;

    @ColumnInfo(name = "note")
    private String note;

    @ColumnInfo(name = "recurring")
    private boolean recurring;

    @TypeConverters(DateConverter.class)
    private Date addedDate;

    public Transaction()
    {
    }

    public Transaction(double amount, int categoryID, String note, boolean recurring, Date addedDate)
    {
        setAmount(amount);
        this.setCategoryID(categoryID);
        this.setNote(note);
        this.setRecurring(recurring);
        this.setAddedDate(addedDate);
    }

    public int getTid() {
        return tid;
    }

    public double getAmount() {
        return Amount;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public String getNote() {
        return note;
    }

    public boolean isRecurring() {
        return recurring;
    }

    public Date getAddedDate() {
        return addedDate;
    }

    public void setAmount(double amount) {
        Amount = amount;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setRecurring(boolean recurring) {
        this.recurring = recurring;
    }

    public void setAddedDate(Date addedDate) {
        this.addedDate = addedDate;
    }

    public void setTid(int tid) {
        this.tid = tid;
    }
}
