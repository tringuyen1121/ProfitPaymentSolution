package com.tringuyen.profitpaymentsolution.ui.payment;

import android.content.Context;

import com.tringuyen.profitpaymentsolution.BR;
import com.tringuyen.profitpaymentsolution.R;
import com.tringuyen.profitpaymentsolution.util.databinding.RecyclerViewBindingAdapter;

import java.util.ArrayList;
import java.util.List;

public class PersonAdapter extends RecyclerViewBindingAdapter<PersonViewModel> {

    private ArrayList<PersonViewModel> personViewModels = new ArrayList<>();

    PersonAdapter(Context context, List<PersonViewModel> vModels) {
        super(context);
        personViewModels = new ArrayList<>(vModels);
    }

    @Override
    public PersonViewModel getItem(int position) {
        return personViewModels.get(position);
    }

    @Override
    public int getLayoutForItem(int position) {
        return R.layout.person_item;
    }

    @Override
    public int getBindingForItem(int position) {
        return BR.person;
    }

    @Override
    public int getItemCount() {
        return personViewModels.size();
    }

    void update(List<PersonViewModel> persons) {
        this.personViewModels = new ArrayList<>(persons);
        notifyDataSetChanged();
    }
}
