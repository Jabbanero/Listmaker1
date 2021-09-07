package com.raywenderlich.listmaker1.models

import android.os.Parcel
import android.os.Parcelable

class TaskList(val name: String, val tasks: ArrayList<String> = ArrayList()) : Parcelable {

    //constructor taking a Parcel
    constructor(source: Parcel) : this(
        source.readString()!!,
        source.createStringArrayList()!!
    )

    override fun describeContents() = 0

    //create Parcel from TaskList
    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(name)
        dest.writeStringList(tasks)
    }

    //create TaskList from Parcel
    companion object CREATOR : Parcelable.Creator<TaskList> {
        override fun createFromParcel(source: Parcel): TaskList =
            TaskList(source)

        override fun newArray(size: Int): Array<TaskList?> =
            arrayOfNulls(size)
    }

}