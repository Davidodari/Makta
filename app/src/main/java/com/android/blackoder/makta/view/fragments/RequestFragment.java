package com.android.blackoder.makta.view.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.blackoder.makta.R;
import com.android.blackoder.makta.model.books.FirestoreViewModel;
import com.android.blackoder.makta.model.entities.SharedBook;

import org.parceler.Parcels;

/**
 * Created By blackcoder
 * On 22/03/19
 **/

public class RequestFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_detail_request, container, false);
        Button btnRequest = v.findViewById(R.id.button_request_book);
        if (getArguments() != null) {
            SharedBook lSharedBook = Parcels.unwrap(getArguments().getParcelable("book"));
            setupBookView(lSharedBook, v);
            btnRequest.setOnClickListener(v1 -> {
                if (lSharedBook != null)
                    sendRequest(lSharedBook.getUser(), lSharedBook.getTitle());
            });
        }
        return v;
    }

    private void sendRequest(String user, String title) {
        FirestoreViewModel lFirestoreViewModel = ViewModelProviders.of(this).get(FirestoreViewModel.class);
        lFirestoreViewModel.borrowBook(user, title);
        Toast.makeText(getContext(), getString(R.string.request_sent), Toast.LENGTH_LONG).show();
    }

    private void setupBookView(SharedBook book, View view) {
        Log.d("Request Book", String.valueOf(book));
        if (book != null) {
            TextView lTextViewTitle = view.findViewById(R.id.text_view_detail_title);
            TextView lTextViewAuthor = view.findViewById(R.id.text_view_detail_author);
            TextView lTextViewDescription = view.findViewById(R.id.text_view_detail_description);
            TextView lTextViewEdition = view.findViewById(R.id.text_view_detail_edition);
            TextView lTextViewPublished = view.findViewById(R.id.text_view_detail_published);
            if (book.getTitle().isEmpty()) {
                lTextViewTitle.setText("-");
            } else {
                lTextViewTitle.setText(book.getTitle());
            }
            if (book.getAuthor().isEmpty()) {
                lTextViewAuthor.setText("-");
            } else {
                lTextViewAuthor.setText(book.getAuthor());
            }
            if (book.getDescription().isEmpty()) {
                lTextViewDescription.setText("-");
            } else {
                lTextViewDescription.setText(book.getDescription());
            }

            if (book.getEdition().isEmpty()) {
                lTextViewEdition.setText("-");
            } else {
                lTextViewEdition.setText(book.getEdition());
            }
            if (book.getPublished().isEmpty()) {
                lTextViewPublished.setText("-");
            } else {
                lTextViewPublished.setText(book.getPublished());
            }

        } else {
            Toast.makeText(getContext(), "No Book To Display", Toast.LENGTH_LONG).show();
        }
    }
}
