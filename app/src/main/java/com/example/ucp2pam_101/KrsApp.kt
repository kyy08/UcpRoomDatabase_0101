package com.example.ucp2pam_101

import android.app.Application
import com.example.ucp2pam_101.dependenciesinjection.ContainerApp

class KrsApp: Application() {
    lateinit var containerApp: ContainerApp

    override fun onCreate() {
        super.onCreate()

        containerApp = ContainerApp(this)
    }

}