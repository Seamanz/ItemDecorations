package com.github.zane;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.zane.itemdecorations.CommonItemDecoration;

import org.zane.commonutils.DimenUtils;

public class ListFragment extends Fragment {
    private boolean mIsVertical;
    private final int mGap = DimenUtils.dp2px(8);
    private final int mEdgeGap = DimenUtils.dp2px(12);

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mIsVertical = ListFragmentArgs.fromBundle(requireArguments()).getIsVertical();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView list = (RecyclerView) view.findViewById(R.id.list);
        list.setAdapter(new ListAdapter(mIsVertical));
        list.setLayoutManager(new LinearLayoutManager(requireActivity(), mIsVertical ?
                RecyclerView.VERTICAL : RecyclerView.HORIZONTAL, false));
        list.addItemDecoration(new CommonItemDecoration(this.mGap, this.mEdgeGap, Color.MAGENTA));
    }
}
