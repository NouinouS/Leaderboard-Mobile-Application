package com.snouinou.gadsleaderboard.api

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import retrofit2.http.Url

interface FormSubmitApiService {
    @POST
    @FormUrlEncoded
    fun submitForm(
        @Url url: String?,
        @Field("entry.1877115667") firstName: String?,
        @Field("entry.2006916086") lastName: String?,
        @Field("entry.1824927963") emailAddress: String?,
        @Field("entry.284483984") githubLink: String?
    ): Call<Void?>?
}