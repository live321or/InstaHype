package com.likesmm.instahype.welcome;

import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("response")
    private String Response;
    @SerializedName("balance")
    private String Balance;



    public String getResponse() {
        return Response;
    }

    public String getBalance() {
        return Balance;
    }
}
