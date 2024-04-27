package com.example.a5_marcin_fedorowicz.Repo

import kotlin.random.Random

class DataItem {
    var Product : String = ""
    var Use : String = ""
    var Amount : Int = 0
    var Bought : Boolean = false
    var ProductType : Int = 0
    var Available : Boolean = false

    constructor()
    constructor(Product: String, Use: String, Amount: Int, ProductType: Int, Available: Boolean) {
        this.Product = Product
        this.Use = Use
        this.Amount = Amount
        this.ProductType = ProductType
        this.Available = Available
    }

}