package com.example.lkh.printec;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;


public class fragment_inprogress extends Fragment {

    private FirebaseAuth firebaseAuth;
    private DatabaseReference db_reference;
    private ArrayAdapter<String> adapter_1;
    private List<save_job_info> values_1;
    private List<String> displayJobs_1;

    private ListView lv;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab, container, false);

        firebaseAuth = FirebaseAuth.getInstance();
        db_reference = FirebaseDatabase.getInstance().getReference();

        lv = (ListView) view.findViewById(R.id.listv);

        values_1 = new ArrayList<save_job_info>();
        displayJobs_1 = new ArrayList<>();
        adapter_1 = new ArrayAdapter<String>(getActivity(), R.layout.job_list, R.id.tV, displayJobs_1);


        return view;
    }
}
