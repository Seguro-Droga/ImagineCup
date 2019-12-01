package com.hacks.imaginecup.utils;

import com.hacks.imaginecup.model.Medicine;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {
    @GET("/api/medicine")
    Call<List<Medicine>> getMedicineList();
}
