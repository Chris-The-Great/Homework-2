package com.example.homework2_2.database

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName =  "User_table")
data class User (
    @PrimaryKey(autoGenerate = true)
    var key : Int,
    val name: String,
    val catergory : String,
    val description : String,
    val date : String,
    val year : Int,
    val month : Int,
    val dayofmonth : Int
): Parcelable