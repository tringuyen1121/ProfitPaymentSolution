package com.tringuyen.profitpaymentsolution.util.databinding;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;

/**
 * @author A
 * @since 2/7/2018
 */

public class RecyclerViewBindingViewHolder<M> extends RecyclerView.ViewHolder {

    private ViewDataBinding layoutBinding;

    public RecyclerViewBindingViewHolder(ViewDataBinding layoutBinding) {
        super(layoutBinding.getRoot());
        this.layoutBinding = layoutBinding;
    }

    public void bind(int bindingId, M item) {
        layoutBinding.setVariable(bindingId, item);
        layoutBinding.executePendingBindings();
    }

    public void bindUIEvent() {}

    public void unbindUIEvent() {}

}
