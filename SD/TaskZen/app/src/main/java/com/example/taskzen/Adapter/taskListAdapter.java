package com.example.taskzen.Adapter;


import android.annotation.SuppressLint;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskzen.Model.taskModel;
import com.example.taskzen.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class taskListAdapter extends RecyclerView.Adapter<taskListAdapter.ViewHolder> {

    private ArrayList<taskModel> taskDataset;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView taskTitle,taskStatus,dayInfo,dateInfo,monInfo;

        CardView container;
        CheckBox taskCheckbox;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            taskTitle = (TextView) view.findViewById(R.id.title);
            taskStatus = (TextView) view.findViewById(R.id.status);
            dayInfo = (TextView) view.findViewById(R.id.day);
            dateInfo = (TextView) view.findViewById(R.id.date);
            monInfo = (TextView) view.findViewById(R.id.month);
            container = (CardView) view.findViewById(R.id.task_container);
            taskCheckbox = (CheckBox) view.findViewById(R.id.task_checker);

        }

    }

    public taskListAdapter(ArrayList<taskModel> taskDataset) {

        this.taskDataset = taskDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_task, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, @SuppressLint("RecyclerView") final int position) {

            String date="",mon,yea,wee;
            viewHolder.taskTitle.setText(taskDataset.get(position).getTaskName());
            viewHolder.taskStatus.setText(taskDataset.get(position).getTaskStatus());
            String[] dateReVango = taskDataset.get(position).getTaskDate().split("-");
            if (dateReVango.length >= 4) { // Check if the array has enough elements
                  date = dateReVango[0];
                  mon = dateReVango[1];
                  yea = dateReVango[2];
                  wee = dateReVango[3];
                System.out.println(mon);
                System.out.println(wee);


                String month = "";
                String week = "";
                if(date==""){
                    date = "DUE";
                    viewHolder.dateInfo.setText(date);
                }
                else{
                    viewHolder.dateInfo.setText(String.valueOf(date));
                }


                switch (mon) {
                    case "":
                        month = "Date";
                        break;
                    case "1":
                        month = "Jan";
                        break;
                    case "2":
                        month = "Feb";
                        break;
                    case "3":
                        month = "Mar";
                        break;
                    case "4":
                        month = "Apr";
                        break;
                    case "5":
                        month = "May";
                        break;
                    case "6":
                        month = "Jun";
                        break;
                    case "7":
                        month = "Jul";
                        break;
                    case "8":
                        month = "Aug";
                        break;
                    case "9":
                        month = "Sep";
                        break;
                    case "10":
                        month = "Oct";
                        break;
                    case "11":
                        month = "Nov";
                        break;
                    case "12":
                        month = "Dev";
                        break;


                }

                viewHolder.monInfo.setText(month);
                viewHolder.dayInfo.setText(wee);


            }


            String status = taskDataset.get(position).getTaskStatus();
            if(status.toLowerCase().equals("pending")){
                viewHolder.taskStatus.setTextColor(Color.parseColor("#D31A38"));
                viewHolder.taskCheckbox.setChecked(false);
            } else if (status.toLowerCase().equals("completed")) {

                viewHolder.taskStatus.setTextColor(Color.parseColor("#9CFF2E"));
                viewHolder.taskCheckbox.setChecked(true);
            } else{
                viewHolder.taskStatus.setBackgroundColor(Color.parseColor("#FFFFFF"));
            }

            viewHolder.container.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    PopupMenu popupMenu = new PopupMenu(view.getContext(),viewHolder.container);
                    popupMenu.inflate(R.menu.taskmenu);
                    popupMenu.show();

                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem menuItem) {

                            if(menuItem.getItemId() == R.id.taskDeleteMenu){
                               FirebaseFirestore.getInstance().collection("tasks").document(taskDataset.get(position).getTaskIdm()).delete()
                                       .addOnSuccessListener(new OnSuccessListener<Void>() {
                                           @Override
                                           public void onSuccess(Void unused) {
                                               Toast.makeText(view.getContext(),"Task Deleted",Toast.LENGTH_SHORT).show();
                                               viewHolder.container.setVisibility(view.GONE);
                                           }
                                       });
                            }
                            return false;
                        }
                    });

                    return false;
                }
            });
            
            viewHolder.taskCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if(b){
                        taskModel completedTask = taskDataset.get(position);
                        completedTask.setTaskStatus("Completed");
                        FirebaseFirestore.getInstance().collection("tasks").document(taskDataset.get(position).getTaskIdm())
                                .set(completedTask).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        //viewHolder.taskCheckbox.setChecked(true);
                                        viewHolder.taskStatus.setTextColor(Color.parseColor("#9CFF2E"));
                                        viewHolder.taskStatus.setText("Completed");
                                    }
                                });
                    }
                    else{

                        taskModel completedTask = taskDataset.get(position);
                        completedTask.setTaskStatus("Pending");
                        FirebaseFirestore.getInstance().collection("tasks").document(taskDataset.get(position).getTaskIdm())
                                .set(completedTask).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        viewHolder.taskCheckbox.setChecked(false);
                                        viewHolder.taskStatus.setTextColor(Color.parseColor("#D31A38"));
                                        viewHolder.taskStatus.setText("Pending");
                                    }
                                });

                    }
                }
            });



    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {

        return taskDataset == null? 0 : taskDataset.size();
    }
}

