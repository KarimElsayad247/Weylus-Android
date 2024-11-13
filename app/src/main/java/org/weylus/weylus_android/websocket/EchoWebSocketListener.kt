package org.weylus.weylus_android.websocket

import android.util.Log
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener

class EchoWebSocketListener: WebSocketListener() {
    override fun onMessage(webSocket: WebSocket, text: String) {
        Log.i("WEBSOCKET", "++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++")
        Log.i("WEBSOCKET", text )
        super.onMessage(webSocket, text)
    }

    override fun onOpen(webSocket: WebSocket, response: Response) {
        Log.i("WEBSOCKET", "++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++")
        Log.i("WEBSOCKET", "Socket open")
        super.onOpen(webSocket, response)
    }

    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
        Log.i("WEBSOCKET", "++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++")
        Log.i("WEBSOCKET", "Closing")
        super.onClosing(webSocket, code, reason)
    }


    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
        Log.i("WEBSOCKET", "++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++")
        Log.i("WEBSOCKET", "Error")
        super.onFailure(webSocket, t, response)
    }
}