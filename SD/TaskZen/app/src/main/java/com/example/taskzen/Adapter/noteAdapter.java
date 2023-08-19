package com.example.taskzen.Adapter;

import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import io.grpc.okhttp.internal.Util;
import com.example.taskzen.notes.noteActivity;
import com.google.firebase.Timestamp;
import com.example.taskzen.Model.noteModel;
import com.example.taskzen.notes.utility;
import com.example.taskzen.R;
import com.example.taskzen.notes.utility;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class noteAdapter extends FirestoreRecyclerAdapter<noteModel,noteAdapter.NoteViewHolder> {
    Context context;

    public noteAdapter(@NonNull FirestoreRecyclerOptions<noteModel> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull NoteViewHolder holder, int position, @NonNull noteModel note) {
        holder.titleTextView.setText(note.getTitle());
        holder.contentTextView.setText(note.getContent());
        //holder.timestampTextView.setText(utility.timestampToString(note.getTimestamp()));


        holder.itemView.setOnClickListener((v)->{
            Intent intent = new Intent(context, noteActivity.class);
            intent.putExtra("title",note.getTitle());
            intent.putExtra("content",note.getContent());
            String docId = this.getSnapshots().getSnapshot(position).getId();
            intent.putExtra("docId",docId);
            context.startActivity(intent);
        });
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note,parent,false);
        return new NoteViewHolder(view);

    }

    class NoteViewHolder extends RecyclerView.ViewHolder{
        TextView titleTextView,contentTextView,timestampTextView;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.note_title_text_view);
            contentTextView = itemView.findViewById(R.id.note_content_text_view);
            timestampTextView = itemView.findViewById(R.id.note_timestamp_text_view);
        }
    }
}
