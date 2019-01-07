package expensetracker.iit.com.expensetracker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class PinActivity extends Activity {

    public static PinActivity newInstance() {
        PinActivity fragment = new PinActivity();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pin_fragment);
    }

    public void pinSubmit(View v){
        Intent i = new Intent(PinActivity.this, MainActivity.class);
        startActivity(i);
    }
}
