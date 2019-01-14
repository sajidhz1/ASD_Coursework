package expensetracker.iit.com.expensetracker.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

public class BaseFragment extends Fragment {

    public interface OnCreateListener
    {
        void SetAddButtonVisibility(boolean show);
        void SetEditButtonVisibility(boolean show);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getActivity() instanceof OnCreateListener)
        {
            ((OnCreateListener) getActivity()).SetAddButtonVisibility(false);
            ((OnCreateListener) getActivity()).SetEditButtonVisibility(false);
        }
    }

    public void OpenAddNewDialog()
    {

    }

    public void OnTitleClicked(View v)
    {

    }
}
