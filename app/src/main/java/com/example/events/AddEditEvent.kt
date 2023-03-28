package com.example.events

import androidx.compose.ui.focus.FocusState

sealed class AddEditEvent{
    data class EnterTitle(val value: String): AddEditEvent()
    data class ChangeTitleFocus(val focusState: FocusState): AddEditEvent()
    data class EnterTask(val value: String): AddEditEvent()
    data class ChangeTaskFocus(val focusState: FocusState): AddEditEvent()
    data class ChangeColor(val color: Int): AddEditEvent()
    object SaveTask: AddEditEvent()
}



