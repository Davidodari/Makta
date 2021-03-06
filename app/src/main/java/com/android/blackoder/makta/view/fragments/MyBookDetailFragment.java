package com.android.blackoder.makta.view.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.blackoder.makta.R;
import com.android.blackoder.makta.utils.AppUtils;

import org.parceler.Parcels;

import static com.android.blackoder.makta.utils.Constants.BOOK_PARCEL;

/**
 * Created By blackcoder
 * On 22/03/19
 **/
public final class MyBookDetailFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_detail_my_book, container, false);
        if (getArguments() != null)
            AppUtils.bindBookView(Parcels.unwrap(getArguments().getParcelable(BOOK_PARCEL)), v);
        return v;
    }

}
