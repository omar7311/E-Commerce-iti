package com.example.e_commerce_iti.model.pojos.updatecustomer

data class UCustomer(
    var addresses: List<UAddresse?>?,
    var email: String?,
    var first_name: String?,
    var last_name: String?,
    var phone: String?
)