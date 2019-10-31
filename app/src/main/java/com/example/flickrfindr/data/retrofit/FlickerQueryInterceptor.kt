package com.example.flickrfindr.data.retrofit

import com.example.flickrfindr.*
import okhttp3.Interceptor
import okhttp3.Response

class FlickerQueryInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()

        val url = request.url()
            .newBuilder()
            .addQueryParameter(FLICKR_FORMAT_QUERY_KEY, FLICKR_FORMAT_QUERY_VALUE)
            .addQueryParameter(FLICKR_NO_CALLBACK_QUERY_KEY, FLICKR_NO_CALLBACK_QUERY_VALUE)
            .addQueryParameter(FLICKR_API_KEY_QUERY_KEY, BuildConfig.API_KEY)
            .build()

        request = request.newBuilder()
            .url(url)
            .build()

        return chain.proceed(request)
    }
}