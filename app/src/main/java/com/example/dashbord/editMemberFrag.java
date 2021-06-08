package com.example.dashbord;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class editMemberFrag extends Fragment {


    private CardView Faculty,Prime,Core;


    public editMemberFrag() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_edit_student, container, false);

        Faculty=v.findViewById(R.id.faculty);
        Prime=v.findViewById(R.id.prime);
        Core=v.findViewById(R.id.core);

        Faculty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(getContext(),DeleteFaculty.class);
                startActivity(i);

            }
        });
        Prime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(getContext(),DeletePrime.class);
                startActivity(i);

            }
        });
        Core.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(getContext(),DeleteCore.class);
                startActivity(i);

            }
        });
        return v;
    }
}