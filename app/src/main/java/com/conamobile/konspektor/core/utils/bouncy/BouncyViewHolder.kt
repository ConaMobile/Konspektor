package com.conamobile.konspektor.core.utils.bouncy

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BouncyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    abstract fun onPulled(delta: Float)
    abstract fun onRelease()
    abstract fun onAbsorb(velocity: Int)
}