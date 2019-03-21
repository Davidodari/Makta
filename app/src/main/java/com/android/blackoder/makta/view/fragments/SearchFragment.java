package com.android.blackoder.makta.view.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.blackoder.makta.R;
import com.android.blackoder.makta.model.FirestoreViewModel;
import com.android.blackoder.makta.model.entities.Book;
import com.android.blackoder.makta.utils.AppUtils;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestoreException;

/**
 * Created By blackcoder
 * On 30/01/19
 **/
public final class SearchFragment extends Fragment {

    private ImageButton mSearch;
    private EditText mSearchView;

    public SearchFragment() {
    }

    private FirestoreRecyclerAdapter adapter;
    private RecyclerView rvSearchResults;
    private ProgressBar lProgressBar;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_search, container, false);
        setupViews(rootView);
        LinearLayoutManager lLinearLayoutManager = new LinearLayoutManager(getActivity());
        rvSearchResults.setLayoutManager(lLinearLayoutManager);
        rvSearchResults.setHasFixedSize(true);
        FirestoreViewModel lFirestoreViewModel = ViewModelProviders.of(this).get(FirestoreViewModel.class);
        mSearch.setOnClickListener(v -> {
            lProgressBar.setVisibility(View.VISIBLE);
            String bookTitle = mSearchView.getText().toString();
            FirestoreRecyclerOptions firestoreRecyclerOptions = lFirestoreViewModel.searchBook(bookTitle.trim());
            setUpAdapter(firestoreRecyclerOptions);
        });

        AppUtils.recycelrViewDecoration(rvSearchResults, lLinearLayoutManager);
        return rootView;
    }

    private void setupViews(View rootView) {
        mSearchView = rootView.findViewById(R.id.search_books);
        mSearch = rootView.findViewById(R.id.btn_search);
        lProgressBar = rootView.findViewById(R.id.progress_bar_search);
        rvSearchResults = rootView.findViewById(R.id.recycler_view_search_results);
    }

    private void setUpAdapter(FirestoreRecyclerOptions firestoreRecyclerOptions) {
        adapter = new FirestoreRecyclerAdapter<Book, BooksViewHolder>(firestoreRecyclerOptions) {

            @NonNull
            @Override
            public BooksViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.list_item_my_books, viewGroup, false);
                return new BooksViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull BooksViewHolder booksViewHolder, int position, @NonNull Book model) {
                booksViewHolder.tvBookTitle.setText(model.getTitle());
                booksViewHolder.tvBookAuthor.setText(model.getAuthor());
            }

            @Override
            public void onError(@NonNull FirebaseFirestoreException e) {
                super.onError(e);
                Log.e("error", e.getMessage());
            }

            @Override
            public void onDataChanged() {
                super.onDataChanged();
                lProgressBar.setVisibility(View.GONE);
                if (!(super.getItemCount() > 0)) {
                    displayError();
                }
                rvSearchResults.setAdapter(this);
                notifyDataSetChanged();
            }
        };
        adapter.startListening();
    }


    public void displayError() {
        Toast.makeText(getActivity(), "No Book Found", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (adapter != null) {
            adapter.stopListening();
        }
    }

    class BooksViewHolder extends RecyclerView.ViewHolder {

        TextView tvBookTitle, tvBookAuthor;

        BooksViewHolder(@NonNull View itemView) {
            super(itemView);
            tvBookTitle = itemView.findViewById(R.id.text_view_book_title);
            tvBookAuthor = itemView.findViewById(R.id.text_view_book_author);
        }
    }
}
