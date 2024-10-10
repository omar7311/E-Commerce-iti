package com.example.e_commerce_iti.model.pojos.responsedorder

data class TotalShippingPriceSet(
    var presentment_money: PresentmentMoney? = PresentmentMoney(),
    var shop_money: ShopMoney? = ShopMoney()
)