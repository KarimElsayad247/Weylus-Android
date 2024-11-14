package org.weylus.weylus_android.websocket

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import okhttp3.WebSocketListener

class WeylusWebsocketClient {
    private val DEFAULT_PORT = 1701

    private var webSocket: WebSocket? = null

    fun connect(ip: String, port: Int = DEFAULT_PORT) {
        val client = OkHttpClient()
        val url = "ws://${ip}:${port}/ws"
        val request = Request.Builder().url(url).build()
        val listener: WebSocketListener = WeylusWebsocketListener()
        webSocket = client.newWebSocket(request, listener)
        client.dispatcher.executorService.shutdown()
    }

    fun getCapturableList() {
        webSocket?.send(MessageOutbound.GetCapturableList().message())
    }

    fun pauseVideo() {
        webSocket?.send(MessageOutbound.PauseVideo().message())
    }

    fun resumeVideo() {
        webSocket?.send(MessageOutbound.ResumeVideo().message())
    }

    fun sendConfig(clientConfig: ClientConfiguration) {
        webSocket?.send(MessageOutbound.Config(clientConfig).message())
    }

    fun sendPointerEvent(pointerEvent: PointerEvent) {
        webSocket?.send(MessageOutbound.PointerOutboundEvent(pointerEvent).message())
    }
}