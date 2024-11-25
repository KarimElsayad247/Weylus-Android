package org.weylus.weylus_android.websocket

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import org.weylus.weylus_android.dto.ClientConfiguration
import org.weylus.weylus_android.dto.PointerEvent

object WeylusWebsocketClient {
    var port = "1701"
    var ip = ""

    private var webSocket: WebSocket? = null

    private val TAG = "WeylusWebsocketClient"

    // These messages never change, so avoid creating new objects to minimize garbage
    // collection overhead.
    private val getCapturableListMessage = MessageOutbound.GetCapturableList().message()
    private val resumeVideoMessage = MessageOutbound.ResumeVideo().message()
    private val pauseVideoMessage = MessageOutbound.PauseVideo().message()

    // Avoid creating a new object on every outbound message for these messages
    private val clientConfigMessageCreator = MessageOutbound.Config()
    private val pointerEventMessageCreator = MessageOutbound.PointerOutboundEvent()

    fun connect() {
        Log.i(TAG, "Connecting to $ip:$port")
        val client = OkHttpClient()
        val url = "ws://192.168.1.13:1701/ws"
        val request = Request.Builder().url(url).build()
        val listener: WebSocketListener = WeylusWebsocketListener()
        webSocket = client.newWebSocket(request, listener)
        client.dispatcher.executorService.shutdown()
    }

    fun disconnect() {
        Log.i(TAG, "Disconnecting from $ip:$port")
        webSocket?.close(1000, "Client Disconnected")
    }

    fun getCapturableList() {
        webSocket?.send(getCapturableListMessage)
    }

    fun pauseVideo() {
        webSocket?.send(pauseVideoMessage)
    }

    fun resumeVideo() {
        webSocket?.send(resumeVideoMessage)
    }

    fun sendConfig(clientConfig: ClientConfiguration) {
        clientConfigMessageCreator.clientConfiguration = clientConfig
        webSocket?.send(clientConfigMessageCreator.message())
    }

    fun sendPointerEvent(pointerEvent: PointerEvent) {
        pointerEventMessageCreator.pointerEvent = pointerEvent
        webSocket?.send(pointerEventMessageCreator.message())
    }
}