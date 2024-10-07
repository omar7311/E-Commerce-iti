package com.example.e_commerce_iti

import com.example.e_commerce_iti.model.reposiatory.IReposiatory
import kotlinx.coroutines.flow.first

data class CurrentUser(var email:String="N/A",var id:Long,var fav:Long=-1,val cart:Long=-1,var name:String="N/A",var phone:String="N/A")
 var currentUser : CurrentUser?=null

suspend fun  getCurrent(email: String?,cartRepository:IReposiatory):CurrentUser?{
    if (currentUser==null&&email!=null){
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
        currentUser= CurrentUser(id=user.id!!,email=email,fav = fav!!, cart = cart!!, name = user.first_name?:"N/A", phone = user.phone?:"N/A")
    }
    return currentUser
}
fun deleteCurrentUser()=currentUser?.let { currentUser=null }