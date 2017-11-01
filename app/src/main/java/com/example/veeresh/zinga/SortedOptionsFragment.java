package com.example.veeresh.zinga;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by veeresh on 10/31/17.
 */

public class SortedOptionsFragment extends Fragment {



    public static SortedOptionsFragment newInstance(Bundle bundle){
        Bundle incomingBundle = this.getArguments();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
         return inflater.inflate(R.layout.fragment_sorted_options, parent, false);
    }


}
