package com.example.e_commerce_iti

import com.example.e_commerce_iti.model.reposiatory.IReposiatory
import kotlinx.coroutines.flow.first

data class CurrentUser(val email:String,val fav:Long,val cart:Long)
 var currentUser : CurrentUser?=null

suspend fun  getCurrent(email: String,cartRepository:IReposiatory):CurrentUser?{
    if (currentUser==null){
        val user= cartRepository.getCustomer(email = email).first()
        val meta= cartRepository.getMetaFields(user.id!!).first()
        var cart:Long?=null
        var fav:Long?=null
        for (i in meta.metafields!!){
            if (i?.key=="cart_id"){
                 cart= cartRepository.getCart(i.value!!.toLong()).first().id
            }
            if (i?.key=="fav_id"){
                 fav= cartRepository.getCart(i.value!!.toLong()).first().id
            }
        }
        currentUser= CurrentUser(email,fav = fav!!, cart = cart!!)
    }
    return currentUser
}
fun deleteCurrentUser()=currentUser?.let { currentUser=null }