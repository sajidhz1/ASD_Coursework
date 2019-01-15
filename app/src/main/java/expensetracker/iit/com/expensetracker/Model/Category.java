package expensetracker.iit.com.expensetracker.Model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import java.util.Date;

import expensetracker.iit.com.expensetracker.Common.DateConverter;

@Entity
public class Category
{
    @PrimaryKey(autoGenerate = true)
    public int cid;

    @ColumnInfo(name = "category_name")
    public String name;

    @ColumnInfo(name = "type")
    public int type;

    @ColumnInfo(name = "budget_id")
    public int budgetId;

    @ColumnInfo(name = "created_date")
    @TypeConverters(DateConverter.class)
    public Date createdDate;

    public Category() {
    }

    public Category(String name, int type, Date createdDate) {
        this.name = name;
        this.type = type;
    }

    public Category(String name, int type, int budgetId, Date createdDate) {
        this.name = name;
        this.type = type;
        this.budgetId = budgetId;
        this.createdDate = createdDate;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getBudgetId() {
        return budgetId;
    }

    public void setBudgetId(int budgetId) {
        this.budgetId = budgetId;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}
