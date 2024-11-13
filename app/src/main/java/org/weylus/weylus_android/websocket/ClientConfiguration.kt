package org.weylus.weylus_android.websocket

import kotlinx.serialization.*

@Serializable
data class ClientConfiguration(
    @SerialName("uinput_support")
    val uinputSupport: Boolean,

    @SerialName("capturable_id")
    val capturableId: Int,

    @SerialName("capture_cursor")
    val captureCursor: Boolean,

    @SerialName("max_width")
    val maxWidth: Int,

    @SerialName("max_height")
    val maxHeight: Int,

    @SerialName("client_name")
    val clientName: String?,

    @SerialName("frame_rate")
    val frameRate: Double,
)