package com.example.taskzen.features;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.taskzen.Adapter.noteAdapter;
import com.example.taskzen.Model.noteModel;
import com.example.taskzen.R;
import com.example.taskzen.notes.utility;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.Query;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Habits_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Habits_Fragment extends Fragment {

    RecyclerView notesRecyclerView;
    noteAdapter noteAdapter;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    public Habits_Fragment() {
        // Required empty public constructor
    }

    public static Habits_Fragment newInstance(String param1, String param2) {
        Habits_Fragment fragment = new Habits_Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_habits_,container,false);
        notesRecyclerView = rootView.findViewById(R.id.notesRecycler);



        setupRecyclerView();



        return rootView;
    }

    private void setupRecyclerView() {
        Query query  = utility.getCollectionReferenceForNotes().orderBy("timestamp", Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<noteModel> options = new FirestoreRecyclerOptions.Builder<noteModel>()
                .setQuery(query,noteModel.class).build();
        notesRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext(),RecyclerView.VERTICAL,false));
        noteAdapter = new noteAdapter(options,requireContext());
        notesRecyclerView.setAdapter(noteAdapter);

    }
    @Override
    public void onStart() {
        super.onStart();
        noteAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        noteAdapter.stopListening();
    }

    @Override
    public void onResume() {
        super.onResume();
        noteAdapter.notifyDataSetChanged();
    }
}