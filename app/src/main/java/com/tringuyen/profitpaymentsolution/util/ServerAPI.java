package com.tringuyen.profitpaymentsolution.util;

import com.google.gson.JsonObject;
import com.tringuyen.profitpaymentsolution.model.Person;
import com.tringuyen.profitpaymentsolution.model.Transaction;
import com.tringuyen.profitpaymentsolution.model.TransactionType;


import java.util.List;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;


public interface ServerAPI {

    @GET("transactions")
    Observable<List<Transaction>> getTransaction();

    @GET("transactions/{id}")
    Observable<Transaction> getTransaction(@Path("id") int id);

    @POST("transactions")
    Observable<ResponseBody> postTransaction(@Body JsonObject object);

    @GET("transaction_types")
    Observable<List<TransactionType>> getTransactionTypes();

    @GET("persons")
    Observable<List<Person>> getPerson();

    @GET("persons/{phoneNumber}")
    Observable<Person> getPersonByPhone(@Path("phoneNumber") String phoneNumber);


}
