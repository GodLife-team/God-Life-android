package com.godlife.designsystem.component

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TimeInput
import androidx.compose.material3.TimePickerDefaults
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.godlife.designsystem.theme.GreyWhite
import com.godlife.designsystem.theme.PurpleMain

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GodLifeTimeInput(
    timePickerState: TimePickerState
){
    TimeInput(
        state = timePickerState,
        modifier = Modifier.padding(top = 10.dp),
        colors = TimePickerDefaults.colors(
            timeSelectorSelectedContainerColor = Color.White,
            timeSelectorSelectedContentColor = PurpleMain,
            timeSelectorUnselectedContainerColor = Color.White,
            timeSelectorUnselectedContentColor = GreyWhite,

            periodSelectorBorderColor = PurpleMain,
            periodSelectorSelectedContainerColor = PurpleMain,
            periodSelectorSelectedContentColor = Color.White,
            periodSelectorUnselectedContainerColor = Color.White,
            periodSelectorUnselectedContentColor = GreyWhite,

            containerColor = PurpleMain,
            selectorColor = PurpleMain,
            clockDialColor = PurpleMain,
            clockDialSelectedContentColor = PurpleMain,
            clockDialUnselectedContentColor = Color.White,

            )
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun CustomTimeInputPreview(){
    val timePickerState = rememberTimePickerState(
        initialHour = 17,
        initialMinute = 30,
        is24Hour = false
    )

    TimeInput(
        state = timePickerState,
        modifier = Modifier.padding(top = 10.dp),
        colors = TimePickerDefaults.colors(
            timeSelectorSelectedContainerColor = Color.White,
            timeSelectorSelectedContentColor = PurpleMain,
            timeSelectorUnselectedContainerColor = Color.White,
            timeSelectorUnselectedContentColor = GreyWhite,

            periodSelectorBorderColor = PurpleMain,
            periodSelectorSelectedContainerColor = PurpleMain,
            periodSelectorSelectedContentColor = Color.White,
            periodSelectorUnselectedContainerColor = Color.White,
            periodSelectorUnselectedContentColor = GreyWhite,

            containerColor = PurpleMain,
            selectorColor = PurpleMain,
            clockDialColor = PurpleMain,
            clockDialSelectedContentColor = PurpleMain,
            clockDialUnselectedContentColor = Color.White,


            )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun TimeInputPreview(){
    val timePickerState = rememberTimePickerState(
        initialHour = 17,
        initialMinute = 30,
        is24Hour = false
    )

    TimeInput(
        state = timePickerState,
        modifier = Modifier.padding(top = 10.dp),
        colors = TimePickerDefaults.colors(Color.Black)
    )
}