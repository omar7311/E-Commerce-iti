package com.example.e_commerce_iti.model.pojos.metadata

data class Metafield(
    var key: String?,
    var namespace: String?,
    var value: String?,
    var type:String ="string",
    var value_type: String?
)