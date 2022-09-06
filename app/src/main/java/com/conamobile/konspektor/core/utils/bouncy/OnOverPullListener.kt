package com.conamobile.konspektor.core.utils.bouncy

interface OnOverPullListener
{
    fun onOverPulledTop(deltaDistance: Float)
    fun onOverPulledBottom(deltaDistance: Float)
    fun onRelease()
}