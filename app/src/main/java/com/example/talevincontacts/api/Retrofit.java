package com.example.talevincontacts.api;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.converter.gson.GsonConverterFactory;

public class Retrofit {
    private retrofit2.Retrofit retrofit = new retrofit2.Retrofit.Builder()
            .baseUrl("https://api.genderize.io")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    private GetGenderApi api = retrofit.create(GetGenderApi.class);

    public void getGender(Callback<GenderInput> callback, String name) {
        Call<GenderInput> call = api.getGender(name);
        call.enqueue(callback);
    }

    public class GenderInput {
        private long count;
        private String name;
        private String gender;
        private float probability;

        public long getCount() {
            return count;
        }

        public void setCount(long count) {
            this.count = count;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public float getProbability() {
            return probability;
        }

        public void setProbability(float probability) {
            this.probability = probability;
        }
    }
}
