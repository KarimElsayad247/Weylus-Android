package org.weylus.weylus_android.ui.transform

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.weylus.weylus_android.databinding.FragmentTransformBinding
import org.weylus.weylus_android.websocket.ButtonType
import org.weylus.weylus_android.dto.ClientConfiguration
import org.weylus.weylus_android.dto.PointerEvent
import org.weylus.weylus_android.websocket.WeylusWebsocketClient
import java.time.Instant


class TransformFragment : Fragment() {

    private var _binding: FragmentTransformBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val client = WeylusWebsocketClient

    private val motionEventTypesMapping: Map<Int, String> = mapOf(
        MotionEvent.ACTION_DOWN to "pointerdown",
        MotionEvent.ACTION_UP to "pointerup",
        MotionEvent.ACTION_CANCEL to "pointercancel",
        MotionEvent.ACTION_MOVE to "pointermove",
        MotionEvent.ACTION_HOVER_MOVE to "pointerover",
        MotionEvent.ACTION_HOVER_ENTER to "pointerenter",
        MotionEvent.ACTION_POINTER_UP to "pointerleave",
        MotionEvent.ACTION_HOVER_EXIT to "pointerout",
    )


    private val clientConfiguration = ClientConfiguration(
        uinputSupport = true,
        capturableId = 1,
        captureCursor = false,
        maxWidth = 2000,
        maxHeight = 2000,
        clientName = "android",
        frameRate = 30.0
    )
    
    private val pointerEvent: PointerEvent = PointerEvent(
        eventType = "pointerup",
        pointerId = 0,
        timestamp = Instant.now().toEpochMilli(),
        isPrimary = true,
        pointerType = "pen",
        button = ButtonType.PRIMARY.value,
        buttons = ButtonType.NONE.value,
        x = 0F,
        y = 0F,
        movementX = 0,
        movementY = 0,
        pressure = 0F,
        tiltX = 0,
        tiltY = 0,
        twist = 0,
        width = 0F,
        height = 0F
    )
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        client.ip = "1701"
        client.port = "192.168.1.13"
        client.connect()
        client.getCapturableList()
        client.sendConfig(clientConfiguration)
        client.pauseVideo()

        _binding = FragmentTransformBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val inputArea = binding.inputArea
        inputArea?.setOnTouchListener { v, event ->
            touchHandler(v, event)
        }

        inputArea?.setOnHoverListener { v, motionEvent ->
            hoverHandler(v, motionEvent)
        }

        return root
    }

    private fun touchHandler(view: View, motionEvent: MotionEvent): Boolean {
        pointerEvent.pointerType = "pen"
        processHistoricalEvents(view, motionEvent);
        setPointerEventAttrs(view, motionEvent)
        client.sendPointerEvent(pointerEvent)

        return true
    }

    private fun hoverHandler(view: View, motionEvent: MotionEvent): Boolean {
        pointerEvent.pointerType = "pen"
        processHistoricalEvents(view, motionEvent);
        setPointerEventAttrs(view, motionEvent)
        client.sendPointerEvent(pointerEvent)

        return true
    }

    private fun processHistoricalEvents(view: View, motionEvent: MotionEvent) {
        val eventType = motionEventTypesMapping[motionEvent.action] ?: unhandledAction(motionEvent)
        for (i in 0 until motionEvent.historySize) {
            pointerEvent.x = motionEvent.getHistoricalX(i) / view.width
            pointerEvent.y = motionEvent.getHistoricalY(i) / view.height
            pointerEvent.movementX = motionEvent.getHistoricalX(i).toInt()
            pointerEvent.movementY = motionEvent.getHistoricalY(i).toInt()
            pointerEvent.pressure = motionEvent.getHistoricalPressure(i)
            pointerEvent.tiltX = motionEvent.getHistoricalAxisValue(MotionEvent.AXIS_TILT, i).toInt()
            pointerEvent.tiltY = motionEvent.getHistoricalAxisValue(MotionEvent.AXIS_TILT, i).toInt()
            pointerEvent.eventType = eventType
            client.sendPointerEvent(pointerEvent)
        }
    }

    private fun setPointerEventAttrs(view : View, motionEvent: MotionEvent) {
        pointerEvent.x = motionEvent.x / view.width
        pointerEvent.y = motionEvent.y / view.height
        pointerEvent.movementX = motionEvent.x.toInt()
        pointerEvent.movementY = motionEvent.y.toInt()
        pointerEvent.pressure = motionEvent.pressure
        pointerEvent.tiltX = motionEvent.getAxisValue(MotionEvent.AXIS_TILT).toInt()
        pointerEvent.tiltY = motionEvent.getAxisValue(MotionEvent.AXIS_TILT).toInt()
        pointerEvent.eventType = motionEventTypesMapping[motionEvent.action] ?: unhandledAction(motionEvent)
    }

    fun unhandledAction(motionEvent: MotionEvent): String {
        Log.e("WEYLUS", "unhandled action")
        Log.e("WEYLUS", motionEvent.toString())
        return "pointerup"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}