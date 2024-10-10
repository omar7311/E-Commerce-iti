package com.example.e_commerce_iti.model.pojos.responsedorder

data class TotalPriceSet(
    var presentment_money: PresentmentMoney? = PresentmentMoney(),
    var shop_money: ShopMoney? = ShopMoney()
)