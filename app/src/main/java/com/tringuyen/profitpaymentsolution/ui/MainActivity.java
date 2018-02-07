package com.tringuyen.profitpaymentsolution.ui;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.tringuyen.profitpaymentsolution.R;
import com.tringuyen.profitpaymentsolution.model.Transaction;
import com.tringuyen.profitpaymentsolution.util.ApiUtils;
import com.tringuyen.profitpaymentsolution.util.ServerAPI;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private CompositeDisposable disposable;
    private TransactionListFragment transactionListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        transactionListFragment = new TransactionListFragment();

        Button viewBtn = findViewById(R.id.view_button);
        viewBtn.setOnClickListener( view -> showTransactionListFragment() );

    }

    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    private void showTransactionListFragment() {

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (transactionListFragment.isAdded()) {
            transaction.show(transactionListFragment);
        } else {
            transaction.add(R.id.fragment_container, transactionListFragment);
        }
        transaction.commit();
        Log.e("Main", "Fragment added");
    }
}
