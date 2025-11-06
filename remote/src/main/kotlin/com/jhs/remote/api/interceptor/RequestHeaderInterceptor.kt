package com.jhs.remote.api.interceptor

import com.jhs.remote.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException


class RequestHeaderInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val newRequest = try {
            chain.request().newBuilder()
                .apply {
                    addHeader("Authorization", "KakaoAK ${BuildConfig.KAKAO_API_KEY}")
                }
                .build()
        } catch (exception: Exception) {
            chain.request()
        }

        return chain.proceed(newRequest)
    }

}
