package com.example.dot.utils

sealed class OrderTasks(val orderType: OrderType){
    class Title(orderTask: OrderType): OrderTasks(orderTask)
    class Date(orderTask: OrderType): OrderTasks(orderTask)

    fun copy(orderType: OrderType): OrderTasks {
        return when(this) {
            is Title -> Title(orderType)
            is Date -> Date(orderType)
        }
    }
}
