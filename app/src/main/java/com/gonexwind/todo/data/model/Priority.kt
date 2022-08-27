package com.gonexwind.todo.data.model

import androidx.compose.ui.graphics.Color
import com.gonexwind.todo.ui.theme.HighPriorityColor
import com.gonexwind.todo.ui.theme.LowPriorityColor
import com.gonexwind.todo.ui.theme.MediumPriorityColor
import com.gonexwind.todo.ui.theme.NonePriorityColor

enum class Priority(val color: Color) {
    HIGH(HighPriorityColor),
    MEDIUM(MediumPriorityColor),
    LOW(LowPriorityColor),
    NONE(NonePriorityColor),
}