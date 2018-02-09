package com.tringuyen.profitpaymentsolution.ui.payment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.tringuyen.profitpaymentsolution.R;
import com.tringuyen.profitpaymentsolution.model.Person;
import com.tringuyen.profitpaymentsolution.util.ApiUtils;
import com.tringuyen.profitpaymentsolution.util.ServerAPI;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class PaymentFragment extends Fragment {

    public static final String TAG = PaymentFragment.class.getSimpleName();

    private ServerAPI mAPI;
    private Button okButton;
    private EditText nameEditText;
    private EditText phoneEditText;
    private EditText amountEditText;

    private CompositeDisposable disposable = new CompositeDisposable();

    private ArrayList<PersonViewModel> personViewModels = new ArrayList<>();
    private PersonAdapter personAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_payment, container, false);

        nameEditText = v.findViewById(R.id.name_editText);
        phoneEditText = v.findViewById(R.id.phone_editText);
        amountEditText = v.findViewById(R.id.amount_editText);
        okButton = v.findViewById(R.id.ok_button);
        okButton.setOnClickListener(view -> {
            if (nameEditText.getText().toString().trim().equals("") ||
                   phoneEditText.getText().toString().trim().equals("") ||
                   amountEditText.getText().toString().trim().equals("")) {
                Toast.makeText(getContext(), "Please fill out all fields", Toast.LENGTH_LONG).show();
            } else confirmPayment();
        });

        mAPI = ApiUtils.getServerAPI();
        personAdapter = new PersonAdapter(getContext(), personViewModels);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView personListView = view.findViewById(R.id.person_list);
        personListView.setLayoutManager(new LinearLayoutManager(getContext()));
        personListView.setAdapter(personAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();

        disposable.add(
                mAPI.getPerson()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                persons -> updatePersonList(persons),
                                error -> {
                                    Log.e(TAG, error.getMessage());
                                    Toast.makeText(getContext(), "Connection error. Please try again later", Toast.LENGTH_LONG).show();
                                })
        );
        disposable.add(
                personAdapter.onItemClicked()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::fillEditText)
        );
    }

    @Override
    public void onPause() {
        super.onPause();
        disposable.clear();
    }

    private void confirmPayment() {
        new AlertDialog.Builder(getContext())
                .setTitle("Payment Confirm")
                .setMessage("Are you sure you want to make this payment?")
                .setPositiveButton("Yes", (dialog, which) -> doPost()
                ).setNegativeButton("No", null).show();
    }

    private void updatePersonList(List<Person> persons) {
        if (persons == null) return;

        if (personViewModels == null) {
            personViewModels = new ArrayList<>();
        } else {
            personViewModels.clear();
        }

        for (Person person: persons) {
            if (person.getName().equals("You")) continue;
            personViewModels.add(new PersonViewModel(person));
        }

        if (personAdapter != null) {
            personAdapter.update(personViewModels);
        }
    }

    private void fillEditText(PersonViewModel person) {
        nameEditText.setText(person.getName());
        phoneEditText.setText(person.getNumber());
    }

    private JsonObject postObject(double amount, String name, String phone) {
        JsonObject gsonObject = new JsonObject();

        try {
            JSONObject temp = new JSONObject();
            JSONObject person = new JSONObject();

            person.put("phone_number", phone).put("name", name);
            temp.put("amount", amount).put("receiver", person);

            JsonParser jsonParser = new JsonParser();
            gsonObject = (JsonObject) jsonParser.parse(temp.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return gsonObject;
    }

    private void doPost() {
        double amount = Double.parseDouble(amountEditText.getText().toString());
        String name = nameEditText.getText().toString();
        String phone = phoneEditText.getText().toString();

        mAPI.postTransaction(postObject(amount, name, phone))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(getContext(), "Error, Payment not executed", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onComplete() {}

                    @Override
                    public void onSubscribe(Disposable d) {}

                    @Override
                    public void onNext(ResponseBody startupResponseResponse) {
                        nameEditText.setText("");
                        phoneEditText.setText("");
                        amountEditText.setText("");

                        Toast.makeText(getContext(), "Payment successful", Toast.LENGTH_LONG).show();
                    }
                });
    }

}
