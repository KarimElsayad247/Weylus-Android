package org.weylus.weylus_android.websocket

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.weylus.weylus_android.dto.ClientConfiguration
import org.weylus.weylus_android.dto.PointerEvent

/**
 * Messages going out from the app to the server
 */
interface MessageOutbound {

    fun message(): String

    class PointerOutboundEvent(): MessageOutbound {
        lateinit var pointerEvent: PointerEvent
        override fun message(): String {
            val pointerEventJson = Json.encodeToString(pointerEvent)
            return "{\"PointerEvent\": $pointerEventJson}"
        }
    }

    class WheelEvent: MessageOutbound {
        override fun message(): String {
            TODO("Not yet implemented")
        }

    }

    class KeyboardEvent: MessageOutbound {
        override fun message(): String {
            TODO("Not yet implemented")
        }

    }

    class GetCapturableList: MessageOutbound {
        override fun message(): String {
            return "\"GetCapturableList\""
        }

    }

    class Config: MessageOutbound {
        lateinit var clientConfiguration: ClientConfiguration

        override fun message(): String {
            val configJson = Json.encodeToString(this.clientConfiguration)
            return "{\"Config\": $configJson}"
        }

    }

    class PauseVideo: MessageOutbound {
        override fun message(): String {
            return "\"PauseVideo\""
        }

    }

    class ResumeVideo: MessageOutbound {
        override fun message(): String {
            return "\"ResumeVideo\""
        }

    }

    class ChooseCustomInputAreas: MessageOutbound {
        override fun message(): String {
            return "\"ChooseCustomInputAreas\""
        }
    }
}