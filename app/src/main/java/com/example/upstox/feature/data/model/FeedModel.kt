package com.example.upstox.feature.data.model

import com.google.gson.annotations.SerializedName


data class ResponseModel(
    @SerializedName("status")
    val status: String? = "",
    @SerializedName("response")
    val response: ItemListModel? = null,
)

data class ItemListModel(
    @SerializedName("product_count")
    val count: Int? = 0,
    @SerializedName("total_found")
    val totalFound: Int? = 0,
    @SerializedName("products")
    var productsList: List<ItemModel>? = emptyList()
)

data class ItemModel(
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("rating")
    val rating: Double? = 0.0,
    @SerializedName("image_url")
    val imageUrl: String? = "",
    @SerializedName("name")
    val name: String? = ""
)

sealed class FeedItem(var itemId: Int) {
    data class FeedItemModel(val itemModel: ItemModel) : FeedItem(itemModel.id)
    data class NoMorePage(val text: String = "no more data", val id: Int = 0) : FeedItem(id)
}

