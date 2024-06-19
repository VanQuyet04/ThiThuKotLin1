package com.example.thithukotlin1.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MayTinh(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    var ph35419_price: Float,
    var ph35419_name: String,
    var ph35419_description: String?,
    var ph35419_status: Boolean,
    var ph35419_image: String
)