package com.godlife.main_page

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.godlife.database.model.TodoEntity
import com.godlife.domain.LocalDatabaseUseCase
import com.godlife.model.todo.EndTimeData
import com.godlife.model.todo.NotificationTimeData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class MainPageViewModel @Inject constructor(
    private val localDatabaseUseCase: LocalDatabaseUseCase
): ViewModel(){

    private val _todoList = MutableStateFlow<TodoEntity>(TodoEntity(0, emptyList(), EndTimeData(0,0,0,0,0), NotificationTimeData(0,0,0,0,0)))
    val todoList: StateFlow<TodoEntity> = _todoList

    // 오늘 투두리스트 설정 유무
    private val _todayBoolean = MutableStateFlow<Boolean>(false)
    val todayBoolean: StateFlow<Boolean> = _todayBoolean

    init{
        viewModelScope.launch(Dispatchers.IO) {

            val allTodoList = localDatabaseUseCase.getAllTodoList()
            allTodoList.forEach {
                if(it.endTime.y == LocalDateTime.now().year
                    && it.endTime.m == LocalDateTime.now().monthValue
                    && it.endTime.d == LocalDateTime.now().dayOfMonth){
                    Log.e("allTodoList", "${it.endTime.y}, ${it.endTime.m}, ${it.endTime.d}," +
                            " ${LocalDateTime.now().year}, ${LocalDateTime.now().monthValue},${LocalDateTime.now().dayOfMonth}")
                    _todoList.value = it
                    _todayBoolean.value = true
                }

            }

        }
    }
}