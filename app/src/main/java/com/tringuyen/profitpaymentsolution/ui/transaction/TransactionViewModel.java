package com.tringuyen.profitpaymentsolution.ui.transaction;

import android.databinding.ObservableField;

import com.tringuyen.profitpaymentsolution.model.Person;
import com.tringuyen.profitpaymentsolution.model.Transaction;
import com.tringuyen.profitpaymentsolution.util.ApiUtils;
import com.tringuyen.profitpaymentsolution.util.ServerAPI;

import java.text.SimpleDateFormat;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class TransactionViewModel {

    private Transaction transaction;
    public final ObservableField<String> name = new ObservableField<>();

    private ServerAPI mAPI;

    public TransactionViewModel(Transaction transaction) {
        this.transaction = transaction;
        mAPI = ApiUtils.getServerAPI();
    }

    public ObservableField<String> getName() {
        String phone = "";
        switch (transaction.getTransactionType()) {
            case 0:
                phone = transaction.getReceiver();
                break;
            case 1:
                phone = transaction.getSender();
                break;
            case 2:
                break;
            case 3:
                break;
            default:
                break;
        }
        mAPI.getPersonByPhone(phone).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(person -> name.set(person.getName()));

        return name;
    }

    public int getType() {
        return transaction.getTransactionType();
    }

    public String getAmount() {
        String amount = "";
        switch (transaction.getTransactionType()) {
            case 0:
                amount = "-" + transaction.getAmount();
                break;
            case 1:
                amount = "+" + transaction.getAmount();
                break;
            case 2:
                break;
            case 3:
                break;
            default:
                break;
        }
        return amount;
    }

    public String getDate() {

        SimpleDateFormat formatter = new SimpleDateFormat("EEE, MMM d");

        return formatter.format(transaction.getTransactionDate());
    }
}
