package com.naci.tutorial.dependencyinjectionudemyclass.networking

import com.naci.tutorial.dependencyinjectionudemyclass.Constants

class UrlProvider {

    fun getBaseUrl1(): String {
        return Constants.BASE_URL
    }

    fun getBaseUrl2(): String {
        return "another_base_url"
    }
}