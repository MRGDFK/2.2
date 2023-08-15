package com.example.taskzen.features;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.taskzen.Adapter.taskListAdapter;
import com.example.taskzen.Model.taskModel;
import com.example.taskzen.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;




public class HomeFragment extends Fragment {

    /*TextView userName;
    TextView userMail;
    ImageView userPhoto;*/
    RecyclerView taskRv;
    Activity activity = getActivity();
    FirebaseFirestore db;
    ArrayList<taskModel> dataList = new ArrayList<>();
    taskListAdapter taskListAdapter;
    String TAG = "Homepage";
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        db=FirebaseFirestore.getInstance();
        View rootView = inflater.inflate(R.layout.fragment_home,container,false);
        taskRv = rootView.findViewById(R.id.taskRecycler);




        /*userName = rootView.findViewById(R.id.userName_tv);
        userMail = rootView.findViewById(R.id.userMail_tv);
        userPhoto = rootView.findViewById(R.id.userPhoto_tv);*/
        //View separateLayout = inflater.inflate(R.layout.nav_header,container,false);

        /*userName = separateLayout.findViewById(R.id.userName_tv);
        userMail = separateLayout.findViewById(R.id.userMail_tv);
        userPhoto = separateLayout.findViewById(R.id.userPhoto_tv);
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if(currentUser!=null){
            userName.setText(currentUser.getDisplayName());
            userMail.setText(currentUser.getEmail());
            Picasso.get().load(currentUser.getPhotoUrl()).into(userPhoto);
        }*/
       /* userName.setText(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
        userMail.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());
        Picasso.get().load(FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl()).into(userPhoto);*/


        taskListAdapter = new taskListAdapter(dataList);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(requireContext(),RecyclerView.VERTICAL,false);
        taskRv.setLayoutManager(layoutManager);
        taskRv.setAdapter(taskListAdapter);

        db.collection("tasks")
                .whereEqualTo("userId", FirebaseAuth.getInstance().getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());


                                taskModel taskModel= document.toObject(taskModel.class);
                                taskModel.setTaskIdm(document.getId());

                                dataList.add(taskModel);
                                taskListAdapter.notifyDataSetChanged();


                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });


        return rootView;
    }

}