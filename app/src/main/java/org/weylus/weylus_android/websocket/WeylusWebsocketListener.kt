package org.weylus.weylus_android.websocket

import android.util.Log
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okio.ByteString

class WeylusWebsocketListener: WebSocketListener() {

    val TAG = "WeylusWebsocketListener"

    override fun onOpen(webSocket: WebSocket, response: Response) {
        Log.i(TAG, "Socket open")
        webSocket.send(MessageOutbound.GetCapturableList().message())
    }

    override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
        Log.i(TAG, "Closed: $code, $reason")
    }

    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
        Log.i(TAG, "Closing: $code, $reason")
    }

    override fun onFailure(
        webSocket: WebSocket,
        t: Throwable,
        response: Response?
    ) {
        Log.i(TAG, "Failure: ${t.message}")
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        MessageHandler().handle(text)
    }

    override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
        MessageHandler().handle(bytes)
    }
}