package com.tringuyen.profitpaymentsolution.ui.payment;

import com.tringuyen.profitpaymentsolution.model.Person;

public class PersonViewModel {

    private Person person;

    public PersonViewModel(Person person) {
        this.person = person;
    }

    public String getName() {
        return person.getName();
    }

    public String getNumber() {
        return person.getPhoneNumber();
    }

}
