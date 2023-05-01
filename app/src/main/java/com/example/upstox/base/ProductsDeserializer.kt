package com.example.upstox.base

import com.example.upstox.feature.data.model.ItemModel
import com.example.upstox.feature.data.model.ResponseModel
import com.google.gson.*
import com.google.gson.reflect.TypeToken
import org.json.JSONArray
import java.lang.reflect.Type


class ProductsDeserializer : JsonDeserializer<ResponseModel> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): ResponseModel {
        try {
            JsonParser().parse(json?.asJsonObject.toString())
        } catch (e: JsonSyntaxException) {
            return ResponseModel(status = "success", response = null)
        }

        val responseModel = Gson().fromJson(json, ResponseModel::class.java)

        val responseObject = json?.asJsonObject
        val responseModelObject = responseObject?.getAsJsonObject("response")
        val productObject: JsonArray? = responseModelObject?.getAsJsonArray("products")
        val products: JsonArray? = if (JSONArray(productObject.toString()).length() == 0)
            null
        else {
            productObject
        }

        val typeOfList = object : TypeToken<List<ItemModel>?>() {}.type
        val productJavaObject = Gson().fromJson<List<ItemModel>?>(products, typeOfList)
        responseModel.response?.productsList = productJavaObject

        return responseModel
    }
}