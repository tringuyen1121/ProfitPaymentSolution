package com.tringuyen.profitpaymentsolution.ui;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.tringuyen.profitpaymentsolution.R;
import com.tringuyen.profitpaymentsolution.ui.payment.PaymentFragment;
import com.tringuyen.profitpaymentsolution.ui.transaction.TransactionListFragment;

public class MainActivity extends AppCompatActivity {

    private TransactionListFragment transactionListFragment;
    private PaymentFragment paymentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        transactionListFragment = new TransactionListFragment();
        paymentFragment = new PaymentFragment();

        Button viewBtn = findViewById(R.id.view_button);
        viewBtn.setOnClickListener(view -> {
            hidePaymentFragment();
            showTransactionListFragment();
        });

        Button payBtn = findViewById(R.id.pay_button);
        payBtn.setOnClickListener(view -> {
            hideTransactionListFragment();
            showPaymentFragment();
        });

    }


    private void showTransactionListFragment() {
        getSupportActionBar().setTitle(getResources().getString(R.string.transactions_title));

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (transactionListFragment.isAdded()) {
            transaction.show(transactionListFragment);
        } else {
            transaction.add(R.id.fragment_container, transactionListFragment);
        }
        transaction.commit();
    }

    private void hideTransactionListFragment() {
        getSupportFragmentManager().beginTransaction().hide(transactionListFragment).commit();
    }

    private void showPaymentFragment() {

        getSupportActionBar().setTitle(getResources().getString(R.string.payment_title));

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (paymentFragment.isAdded()) {
            transaction.show(paymentFragment);
        } else {
            transaction.add(R.id.fragment_container, paymentFragment);
        }
        transaction.commit();
    }

    private void hidePaymentFragment() {
        getSupportFragmentManager().beginTransaction().hide(paymentFragment).commit();
    }
}
