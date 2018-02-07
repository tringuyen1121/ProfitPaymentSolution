package com.tringuyen.profitpaymentsolution.util;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class BindingFragment<LB extends ViewDataBinding> extends Fragment {

    private LB layoutBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        layoutBinding = DataBindingUtil.bind(super.onCreateView(inflater, container, savedInstanceState));
        return layoutBinding.getRoot();
    }

    public LB getLayoutBinding() {
        return layoutBinding;
    }

}