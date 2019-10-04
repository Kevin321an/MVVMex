
package com.fanfan.kevin.mvvmex.UI.main

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.fanfan.kevin.mvvmex.R
import com.fanfan.kevin.mvvmex.data.local.car.car.Car
import kotlinx.android.synthetic.main.car_item.view.*

class CarViewHolder(parent :ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.car_item, parent, false)) {

    private val nameView = itemView.findViewById<TextView>(R.id.name)
    private val year_car_item = itemView.findViewById<TextView>(R.id.year_car_item)
    var car : Car? = null

    /**
     * Items might be null if they are not paged in yet. PagedListAdapter will re-bind the
     * ViewHolder when Item is loaded.
     */
    fun bindTo(car : Car?) {
        this.car = car
        nameView.text = car?.carName
        year_car_item.text = car?.year
    }
}