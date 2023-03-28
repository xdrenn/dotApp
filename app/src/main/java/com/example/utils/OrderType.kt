package com.example.utils

sealed class OrderType{
    object Ascending: OrderType()
    object Descending: OrderType()
}
