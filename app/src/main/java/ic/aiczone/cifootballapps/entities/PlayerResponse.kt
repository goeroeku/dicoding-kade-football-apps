package ic.aiczone.cifootballapps.entities

import com.google.gson.annotations.SerializedName

data class PlayerResponse(
        @SerializedName("players") val player: List<Player>
)