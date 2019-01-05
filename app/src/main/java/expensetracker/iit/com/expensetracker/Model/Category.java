package expensetracker.iit.com.expensetracker.Model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Category
{
    @PrimaryKey
    public int cid;

    @ColumnInfo(name = "category_name")
    public String name;

    @ColumnInfo(name = "type")
    public String type;

    @ColumnInfo(name = "budget_id")
    public int budgetId;
}
