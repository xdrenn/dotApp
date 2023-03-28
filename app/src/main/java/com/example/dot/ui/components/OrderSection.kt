package com.example.dot.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.utils.OrderTasks
import com.example.utils.OrderType

@Composable
fun OrderSection(
    modifier: Modifier = Modifier,
    orderTasks: OrderTasks = OrderTasks.Date(OrderType.Descending),
    onOrderChange: (OrderTasks) -> Unit
){
  Column(
      modifier = modifier
  ){
      Row(
          modifier = Modifier.fillMaxWidth()
      ){
          DefaultRadioButton(
              text = "Title",
              selected = orderTasks is OrderTasks.Title ,
              onSelect = { onOrderChange(OrderTasks.Title(orderTasks.orderType))
              })

          Spacer(modifier = Modifier.width(8.dp))
          DefaultRadioButton(
              text = "Date",
              selected = orderTasks is OrderTasks.Date ,
              onSelect = { onOrderChange(OrderTasks.Date(orderTasks.orderType))
              })
      }
      Spacer(modifier = Modifier.height(16.dp))

      Row(modifier = Modifier.fillMaxWidth()){
              DefaultRadioButton(
                  text = "Ascending",
                  selected = orderTasks.orderType is OrderType.Ascending ,
                  onSelect = {
                      onOrderChange(orderTasks.copy(OrderType.Ascending))
                  })

              Spacer(modifier = Modifier.width(8.dp))
          DefaultRadioButton(
              text = "Descending",
              selected = orderTasks.orderType is OrderType.Descending ,
              onSelect = {
                  onOrderChange(orderTasks.copy(OrderType.Descending))
              })

      }
  }
}