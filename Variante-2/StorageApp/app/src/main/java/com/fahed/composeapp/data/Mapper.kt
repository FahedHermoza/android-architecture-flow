package com.fahed.composeapp.data

import com.fahed.composeapp.data.storage.db.DBProduct
import com.fahed.composeapp.domain.model.Product

object Mapper {

    //TODO convertir entidad a DTO y DTO a entidad
    fun dbProductToProduct(dbProduct: DBProduct):Product = Product(dbProduct.id?:0,dbProduct.name?:"",
        dbProduct.cost?:0.0, dbProduct.description?: "", dbProduct.logo?:0
    )

    fun productToDbProduct(product: Product):DBProduct = DBProduct(product.id,product.name,
         product.description,product.cost, product.logo)

    fun mapDBProductListToProductList(dbProductList:List<DBProduct>):List<Product>{
        return  dbProductList.map {
            Product(it.id?:0,it.name?:"",it.cost?:0.0,
                it.description?:"", it.logo?:0)
        }
    }

}