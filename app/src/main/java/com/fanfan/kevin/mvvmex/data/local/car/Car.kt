package com.fanfan.kevin.mvvmex.data.local.car.car

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "cars")
data class Car(@PrimaryKey @ColumnInfo(name = "carid") val id:String = UUID.randomUUID().toString(),
               @ColumnInfo(name = "carname") val  carName:String,
               @ColumnInfo(name = "year") val  year:String )

class CarBuilder {
    private val id = UUID.randomUUID().toString()
    var carName: String = ""
    var year: String = ""
    fun build(): Car =
        Car(id, carName, year)
}

fun carDao(block: CarBuilder.() -> Unit): Car = CarBuilder().apply(block).build()
