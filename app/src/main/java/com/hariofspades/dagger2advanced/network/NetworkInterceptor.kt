package com.hariofspades.dagger2advanced.network

import okhttp3.Interceptor
import okhttp3.Response

/**
 * Created by sandeepsingh on 6/18/18.
 */
class NetworkInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain?): Response? {
        val requestBuilder = chain?.request()?.newBuilder()?.method(chain.request().method(), chain.request().body())
        val basicAuth = "OAuth " + "oauth_signature_method=\"HMAC-SHA256\",oauth_timestamp=\"1528783414\",oauth_consumer_key=\"pjdrOnv4F06D3IPOmnP4AA\",device=\"TEST_DEVICE_ID\",data=\"kymaM4GCEzwZTW9bub1Hn93L9p0L4bytgr3GLnlYx6yNMiWn1etwf6pFSb8l0Dec2ZjibngETSQ/ip1g32gLF3dkYBMs6lMx9qGw2JKDJYlfxSs98/l0aBzg/haj/LlT/oFaDAakbxDZqT8D9aZaYQ==\",oauth_signature=\"OPAElpA+qrk7UWx5AFKSI2CTY40KZAgNnRWaAS/eLEc=\""
        requestBuilder?.header("Authorization", basicAuth)
        val request = requestBuilder?.build()

        return chain?.proceed(request)
    }

}