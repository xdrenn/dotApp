package com.example.dot.utils

sealed class OrderType{
    object Ascending: OrderType()
    object Descending: OrderType()
}
