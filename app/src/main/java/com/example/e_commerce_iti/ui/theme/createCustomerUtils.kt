package com.example.e_commerce_iti.ui.theme

import com.example.e_commerce_iti.model.pojos.customer.Customer
import com.example.e_commerce_iti.model.pojos.customer.CustomerX
import com.example.e_commerce_iti.model.pojos.metadata.MetaData
import com.example.e_commerce_iti.model.pojos.metadata.Metafield

fun createCustomer(email:String,firstName:String,lastName:String):Customer {
    val customerx = CustomerX()
    customerx.email = email
    customerx.first_name = firstName
    customerx.last_name = lastName
    val customer = Customer()
    customer.customer = customerx
    return customer
}
fun createMetaField(key:String,value:String)=Metafield(key,"N/A", value_type = "Long",value = value)
