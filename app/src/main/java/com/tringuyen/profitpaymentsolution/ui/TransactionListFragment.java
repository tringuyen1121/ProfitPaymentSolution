package com.tringuyen.profitpaymentsolution.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.tringuyen.profitpaymentsolution.R;
import com.tringuyen.profitpaymentsolution.model.Transaction;
import com.tringuyen.profitpaymentsolution.util.ApiUtils;
import com.tringuyen.profitpaymentsolution.util.ServerAPI;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class TransactionListFragment extends Fragment {

    private TransactionListAdapter transactionListAdapter;
    private CompositeDisposable disposable = new CompositeDisposable();
    private ServerAPI mAPI;
    RecyclerView transactionList;

    private ArrayList<TransactionViewModel> transactionViewModels = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_transaction_list, container, false);

        ((MainActivity) getActivity()).setActionBarTitle(getString(R.string.transactions_title));
        transactionListAdapter = new TransactionListAdapter(getContext(), transactionViewModels);
        mAPI = ApiUtils.getServerAPI();

        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        transactionList = view.findViewById(R.id.transaction_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        transactionList.setAdapter(transactionListAdapter);
        transactionList.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void onResume() {
        super.onResume();

        disposable.add(
                mAPI.getTransaction()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(this::populateTransactionListViewModels));
    }

    private void populateTransactionListViewModels(List<Transaction> transactions) {
        if (transactions == null) return;

        if (transactionViewModels == null) {
            transactionViewModels = new ArrayList<>();
        } else {
            transactionViewModels.clear();
        }

        for (Transaction transaction: transactions) {
            transactionViewModels.add(new TransactionViewModel(transaction));
        }

        if (transactionListAdapter != null) {
            transactionListAdapter.update(transactionViewModels);
        }
    }

}