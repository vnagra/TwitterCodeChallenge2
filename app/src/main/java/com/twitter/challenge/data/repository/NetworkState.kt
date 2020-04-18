package com.twitter.challenge.data.repository

class NetworkState {

    companion object {

        val LOADED: NetworkState = NetworkState()
        val LOADING: NetworkState = NetworkState()
        val ERROR: NetworkState = NetworkState()

    }
}