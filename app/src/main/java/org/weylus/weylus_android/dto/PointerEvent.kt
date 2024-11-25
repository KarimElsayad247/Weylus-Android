package org.weylus.weylus_android.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PointerEvent(
    @SerialName("event_type")
    var eventType: String,

    @SerialName("pointer_id")
    var pointerId: Int,

    @SerialName("timestamp")
    var timestamp: Long,

    @SerialName("is_primary")
    var isPrimary: Boolean,

    @SerialName("pointer_type")
    var pointerType: String,

    @SerialName("button")
    var button: Byte,

    @SerialName("buttons")
    var buttons: Byte,

    @SerialName("x")
    var x: Float,

    @SerialName("y")
    var y: Float,

    @SerialName("movement_x")
    var movementX: Int,

    @SerialName("movement_y")
    var movementY: Int,

    @SerialName("pressure")
    var pressure: Float,

    @SerialName("tilt_x")
    var tiltX: Int,

    @SerialName("tilt_y")
    var tiltY: Int,

    @SerialName("twist")
    var twist: Int,

    @SerialName("width")
    var width: Float,

    @SerialName("height")
    var height: Float,
)
