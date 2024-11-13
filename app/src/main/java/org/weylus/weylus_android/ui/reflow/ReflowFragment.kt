package org.weylus.weylus_android.ui.reflow

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import org.weylus.weylus_android.databinding.FragmentReflowBinding
import org.weylus.weylus_android.websocket.ClientConfiguration
import org.weylus.weylus_android.websocket.MessageOutbound
import org.weylus.weylus_android.websocket.WeylusWebsocketListener

class ReflowFragment : Fragment() {

    private var _binding: FragmentReflowBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var webSocket: WebSocket
    private lateinit var client: OkHttpClient

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentReflowBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val ipField: EditText = binding.ipTextField
        ipField.setText("192.168.1.13")
        connectToWebsocket()

        val messageSpinner = binding.messageSpinner

        val adapter = ArrayAdapter<String>(
            requireContext(),
            android.R.layout.simple_spinner_item,
            arrayOf<String>("GetCapturableList", "PauseVideo", "ResumeVideo", "Config")
        )
        messageSpinner.setAdapter(adapter)

        val connectButton: Button = binding.connectButton
        connectButton.setOnClickListener { connectToWebsocket() }

        val sendButton: Button = binding.sendMessageButton
        sendButton.setOnClickListener { sendMessage() }

        return root
    }

    fun connectToWebsocket() {
        client = OkHttpClient()
        val url = "ws://" + binding.ipTextField.text + ":1701/ws"
        val request = Request.Builder().url(url).build()
        val listener: WebSocketListener = WeylusWebsocketListener()
        webSocket = client.newWebSocket(request, listener)
        client.dispatcher.executorService.shutdown()
    }

    fun sendMessage() {
        val message = binding.messageSpinner.selectedItem
        val clientConfig: ClientConfiguration = ClientConfiguration(
            false,
            1,
            false,
            1000,
            500,
            "android",
            60.0
        )
        when (message) {
            "GetCapturableList" -> webSocket.send(MessageOutbound.GetCapturableList().message())
            "PauseVideo" -> webSocket.send(MessageOutbound.PauseVideo().message())
            "ResumeVideo" -> webSocket.send(MessageOutbound.ResumeVideo().message())
            "Config" -> webSocket.send(MessageOutbound.Config(clientConfig).message())
        }
        Log.i("ReflowFragment: Sending message", message.toString())
        webSocket.send(message.toString())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}