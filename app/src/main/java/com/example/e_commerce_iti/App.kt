package com.example.e_commerce_iti

import android.app.Application
import android.content.Context
import android.util.Log
import com.example.e_commerce_iti.model.local.LocalDataSourceImp
import com.example.e_commerce_iti.model.remote.RemoteDataSourceImp
import com.example.e_commerce_iti.model.reposiatory.IReposiatory
import com.example.e_commerce_iti.model.reposiatory.ReposiatoryImpl
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch


class App : Application() {
    private lateinit var authStateListener: FirebaseAuth.AuthStateListener

    override fun onCreate() {
        super.onCreate()

        // Initialize Firebase Auth instance
        val auth = Firebase.auth
        val repository: IReposiatory = ReposiatoryImpl(
            RemoteDataSourceImp(),
            LocalDataSourceImp(this.getSharedPreferences(LocalDataSourceImp.EMAIL_CURRENCY_PREFIX, Context.MODE_PRIVATE))
        )

        // Define the auth state listener
        authStateListener = FirebaseAuth.AuthStateListener { firebaseAuth ->
            if (firebaseAuth.currentUser != null&&!firebaseAuth.currentUser!!.isAnonymous) {
                        CoroutineScope(Dispatchers.IO).launch{
                            try {
                                getCurrent(firebaseAuth.currentUser!!.email!!, repository)
                            }catch (e:Exception){
                                try {
                                    val d=  RemoteDataSourceImp().getCustomer(firebaseAuth.currentUser!!.email!!)
                                    val id=d.first().id
                                    RemoteDataSourceImp().createNewDraft(id!!)
                                }catch (ex:Exception){
                                }
                            }
                        }
                    }else{
                                                 currentUser.value=null
                    }
        }

        auth.addAuthStateListener(authStateListener)
    }

    override fun onTerminate() {
        super.onTerminate()
        // Remove the listener to prevent memory leaks when the app terminates
        Firebase.auth.removeAuthStateListener(authStateListener)
    }
}