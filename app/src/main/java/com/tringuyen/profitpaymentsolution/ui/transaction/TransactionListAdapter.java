package com.tringuyen.profitpaymentsolution.ui.transaction;

import android.content.Context;


import com.tringuyen.profitpaymentsolution.BR;
import com.tringuyen.profitpaymentsolution.R;
import com.tringuyen.profitpaymentsolution.util.databinding.RecyclerViewBindingAdapter;

import java.util.ArrayList;
import java.util.List;

public class TransactionListAdapter extends RecyclerViewBindingAdapter<TransactionViewModel> {

    private ArrayList<TransactionViewModel> transactionViewModels;

    public TransactionListAdapter(Context context, List<TransactionViewModel> viewModels) {
        super(context);
        transactionViewModels = new ArrayList<>(viewModels);
    }

    @Override
    public TransactionViewModel getItem(int position) {
        return transactionViewModels.get(position);
    }

    @Override
    public int getLayoutForItem(int position) {
        return R.layout.transaction_item;
    }

    @Override
    public int getBindingForItem(int position) {
        return BR.viewModel;
    }

    @Override
    public int getItemCount() {
        return transactionViewModels.size();
    }

    public void update(List<TransactionViewModel> transactionViewModels) {
        this.transactionViewModels = new ArrayList<>(transactionViewModels);
        notifyDataSetChanged();
    }

}
