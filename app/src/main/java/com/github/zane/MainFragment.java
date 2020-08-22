package com.github.zane;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

public class MainFragment extends Fragment implements View.OnClickListener {


    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.btn_list_vertical).setOnClickListener(this);
        view.findViewById(R.id.btn_list_horizontal).setOnClickListener(this);
        view.findViewById(R.id.btn_grid_list).setOnClickListener(this);
        view.findViewById(R.id.btn_grid_list_horizontal).setOnClickListener(this);
        view.findViewById(R.id.btn_staggered_grid_list).setOnClickListener(this);
        view.findViewById(R.id.btn_staggered_grid_list2).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_list_vertical:
                NavHostFragment.findNavController(this)
                        .navigate(MainFragmentDirections
                                .actionMainPageToListPage().setIsVertical(true));
                break;

            case R.id.btn_list_horizontal:
                NavHostFragment.findNavController(this)
                        .navigate(MainFragmentDirections
                                .actionMainPageToListPage().setIsVertical(false));
                break;

            case R.id.btn_grid_list:
                NavHostFragment.findNavController(MainFragment.this)
                        .navigate(R.id.action_mainPage_to_gridListPage);
                break;

            case R.id.btn_grid_list_horizontal:
                break;

            case R.id.btn_staggered_grid_list:
                NavHostFragment.findNavController(MainFragment.this)
                        .navigate(R.id.action_mainPage_to_staggeredGridListPage);
                break;

            case R.id.btn_staggered_grid_list2:
                NavHostFragment.findNavController(MainFragment.this)
                        .navigate(MainFragmentDirections
                                .actionMainPageToStaggeredGridListPage()
                                .setIsHorizontal(true));
                break;
        }
    }
}