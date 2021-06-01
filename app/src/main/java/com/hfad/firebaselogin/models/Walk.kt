package com.hfad.firebaselogin.models

import java.time.LocalDateTime
import android.os.Parcel
import android.os.Parcelable

data class Walk(
        var WalkID:String= "",
        var WalkName:String? ="",
        var WalkDistance: String? ="",
        var WalkLocation:String? =""
): Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString()!!,
            parcel.readString()!!,
            parcel.readString()!!,
            parcel.readString()!!) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(WalkID)
        parcel.writeString(WalkName)
        parcel.writeString(WalkDistance)
        parcel.writeString(WalkLocation)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Walk> {
        override fun createFromParcel(parcel: Parcel): Walk {
            return Walk(parcel)
        }

        override fun newArray(size: Int): Array<Walk?> {
            return arrayOfNulls(size)
        }
    }
}

