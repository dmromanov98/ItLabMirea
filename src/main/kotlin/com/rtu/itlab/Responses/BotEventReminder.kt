package com.rtu.itlab.Responses

import com.google.gson.JsonObject
import com.rtu.itlab.Utils.getProp
import com.vk.api.sdk.client.VkApiClient
import com.vk.api.sdk.client.actors.GroupActor
import com.vk.api.sdk.httpclient.HttpTransportClient

class BotEventReminder (tmp: JsonObject?){
    private val transportClient = HttpTransportClient.getInstance()
    private val vk = VkApiClient(transportClient)
    private val properties = getProp()

    private val userId = tmp?.get("to")?.asInt
    private val eventTitle: String? = tmp?.get("eventTitle")?.asString
    private val address: String? = tmp?.get("address")?.asString
    private val actor = GroupActor(properties.getProperty("group.id").toInt(), properties.getProperty("group.accessToken"))

    fun send(){
        vk.messages()
                .send(actor)
                .userId(userId)
                .message("Напоминаем вам, что скоро произойдет событие, на которое вы подписаны: $eventTitle\nАдрес: $address")
                .execute()
    }
}