package com.example.lkh.printec;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class fragment_completed extends Fragment {

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
        adapter_1 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, android.R.id.text1, displayJobs_1);

        refresh_list();

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                save_job_info x = values_1.get(position);
                Intent intent = new Intent(getActivity(), Jobs.class);
                intent.putExtra("obj",values_1.get(position));
                startActivity(intent);
            }
        });

        return view;
    }

    public void refresh_list()
    {
        db_reference.child("jobs").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int i = 0;
                values_1.clear();
                displayJobs_1.clear();

                for (DataSnapshot single : dataSnapshot.getChildren()) {

                    save_job_info job = single.getValue(save_job_info.class);
                    if (!job.finished_on.equals("-99") && job.shop_id.equals(firebaseAuth.getCurrentUser().getUid()))
                    {
                        displayJobs_1.add(job.document_name);
                        values_1.add(job);
                    }

                    i++;
                }
                lv.setAdapter(adapter_1);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
