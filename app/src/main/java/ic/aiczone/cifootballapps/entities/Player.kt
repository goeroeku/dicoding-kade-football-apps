package ic.aiczone.cifootballapps.entities

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Player(

        @SerializedName("idPlayer")
        var id: String? = null,

        @SerializedName("strPlayer")
        var name: String? = null,

        @SerializedName("strDescriptionEN")
        var description: String? = null,

        @SerializedName("strHeight")
        var height: String? = null,

        @SerializedName("strWeight")
        var weight: String? = null,

        @SerializedName("strCutout")
        var badge: String? = null,

        @SerializedName("strFanart1")
        var image: String? = null,

        @SerializedName("strPosition")
        var position: String? = null

): Parcelable