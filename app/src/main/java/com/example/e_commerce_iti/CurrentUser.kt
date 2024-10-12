package com.example.e_commerce_iti

import android.util.Log
import com.example.e_commerce_iti.model.pojos.metadata.Metafield
import com.example.e_commerce_iti.model.pojos.repsonemetadata.FullMeatDataResponse
import com.example.e_commerce_iti.model.pojos.repsonemetadata.ResponseMetaData
import com.example.e_commerce_iti.model.reposiatory.IReposiatory
import kotlinx.coroutines.flow.first

data class CurrentUser(var address:String="N/A", var email:String="N/A", var id:Long, var fav:Long=-1,
                       var cart:Long=-1, var name:String="N/A", var lname:String="N/A", var phone:String="N/A")
 var currentUser : CurrentUser?=null
 var metadata: ResponseMetaData?=null
suspend fun  getCurrent(email: String?,cartRepository:IReposiatory):CurrentUser?{
    if (currentUser==null&&!email.isNullOrBlank()){
        val user= cartRepository.getCustomer(email = email).first()
        val meta= cartRepository.getMetaFields(user.id!!).first()
        var cart:Long?=null
        var fav:Long?=null
        for (i in meta.metafields){
            if (i.key=="cart_id"){
                metadata=i
                Log.i("emasdsadasdasdasd", metadata.toString())
                cart= cartRepository.getCart(i.value!!.toLong()).first().id
            }
            if (i.key=="fav_id"){
                 fav= cartRepository.getCart(i.value!!.toLong()).first().id
            }
        }
        Log.i("fffffffffffffffffffff", "$cart ${user.id}  $email  $fav")
        currentUser= CurrentUser(name = user.first_name!!, lname = user.last_name!!,id=user.id!!,email=email,fav = fav!!, cart = cart!!, address = "medaerde")
    }
    return currentUser
}
fun deleteCurrentUser()=currentUser?.let { currentUser=null }