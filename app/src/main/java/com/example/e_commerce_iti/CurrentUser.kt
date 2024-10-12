package com.example.e_commerce_iti

import android.util.Log
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.SaverScope
import androidx.lifecycle.MutableLiveData
import com.example.e_commerce_iti.model.pojos.metadata.Metafield
import com.example.e_commerce_iti.model.pojos.repsonemetadata.FullMeatDataResponse
import com.example.e_commerce_iti.model.pojos.repsonemetadata.ResponseMetaData
import com.example.e_commerce_iti.model.reposiatory.IReposiatory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext

data class CurrentUser(var address:String="N/A", var email:String="N/A", var id:Long, var fav:Long=-1,
                       var cart:Long=-1, var name:String="N/A", var lname:String="N/A", var phone:String="N/A")
 var currentUser = MutableLiveData<CurrentUser?>(null)
 var metadata: ResponseMetaData?=null
suspend fun  getCurrent(email: String?,cartRepository:IReposiatory){
    if (currentUser.value==null&&!email.isNullOrBlank()){
        val user= cartRepository.getCustomer(email = email).first()
        val meta= cartRepository.getMetaFields(user.id!!).first()
        var cart:Long?=null
        var fav:Long?=null
        for (i in meta.metafields){
            if (i.key=="cart_id"){
                metadata=i
                cart= cartRepository.getCart(i.value!!.toLong()).first().id
            }
            if (i.key=="fav_id"){
                 fav= cartRepository.getCart(i.value!!.toLong()).first().id
            }
        }
        Log.i("metdddddddddddddddddddddddddadata", currentUser.value.toString())
        withContext(Dispatchers.Main) {
            currentUser.value = CurrentUser(
                name = user.first_name!!,
                lname = user.last_name!!,
                id = user.id!!,
                email = email,
                fav = fav!!,
                cart = cart!!,
                address = "medaerde"
            )
        }
            Log.i("metdddddddddddddddddddddddddadata", currentUser.value.toString())

    }
}



val CurrentUserSaver: Saver<CurrentUser, *> = object : Saver<CurrentUser, Map<String, Any?>> {
    override fun restore(value: Map<String, Any?>): CurrentUser {
        return CurrentUser(
            address = value["address"] as? String ?: "N/A",
            email = value["email"] as? String ?: "N/A",
            id = value["id"] as? Long ?: -1L,
            fav = value["fav"] as? Long ?: -1L,
            cart = value["cart"] as? Long ?: -1L,
            name = value["name"] as? String ?: "N/A",
            lname = value["lname"] as? String ?: "N/A",
            phone = value["phone"] as? String ?: "N/A"
        )
    }

    override fun SaverScope.save(value: CurrentUser): Map<String, Any?> {
        return mapOf(
            "address" to value.address,
            "email" to value.email,
            "id" to value.id,
            "fav" to value.fav,
            "cart" to value.cart,
            "name" to value.name,
            "lname" to value.lname,
            "phone" to value.phone
        )
    }
}