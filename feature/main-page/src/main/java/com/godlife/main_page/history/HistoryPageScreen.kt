package com.godlife.main_page.history

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.godlife.database.model.TodoEntity
import com.godlife.designsystem.theme.GrayWhite
import com.godlife.designsystem.theme.GrayWhite3
import com.godlife.designsystem.theme.PurpleMain

@Composable
fun HistoryPageScreen(
    modifier: Modifier = Modifier,
    viewModel: HistoryPageViewModel = hiltViewModel()
){
    val todoList = viewModel.todoList.collectAsState().value
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = GrayWhite3)
            .statusBarsPadding()
            .padding(10.dp)
    ){

        Text(
            text = "기록 저장소",
            style = TextStyle(
                color = Color.Black,
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp,
                lineHeight = 28.sp,
                letterSpacing = 0.sp
            )
        )

        Spacer(modifier = modifier.height(10.dp))

        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .background(color = GrayWhite3)
                .statusBarsPadding()
        ) {


            todoList?.size?.let { it ->
                items(it){
                    HistoryTodoItem(
                        todo = todoList[it]
                    )
                }
            }
        }

    }

}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun HistoryTodoItem(
    modifier: Modifier = Modifier,
    todo: TodoEntity
){
    Row(
        modifier = modifier
            .padding(vertical = 5.dp)
            .fillMaxWidth()
            .height(200.dp)
            .background(color = Color.White, shape = RoundedCornerShape(16.dp))
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = modifier
                .size(150.dp),
            contentAlignment = Alignment.Center
        ){
            Canvas(
                modifier = modifier
                    .fillMaxSize()
            ) {
                val size: Size = drawContext.size
                val sizeArc = size / 1.5F

                drawArc(
                    brush = Brush.linearGradient(
                        colors =
                        listOf(
                            Color(0xFFFF44A2),  // 밝은 핫핑크
                            Color(0xFFFF5890),  // 연한 핑크
                            Color(0xFFFA6B80),  // 연한 코럴 핑크
                            Color(0xFFFF7B75),  // 연한 살몬
                            Color(0xFFFF8161),  // 밝은 코럴
                            Color(0xFFFF884D),  // 연한 오렌지
                        ),
                        start = Offset.Zero,
                        end = Offset.Infinite,
                    ),
                    startAngle = 100f,
                    sweepAngle = 360f,
                    useCenter = false,
                    topLeft = Offset(
                        (size.width - sizeArc.width) / 2f,
                        (size.height - sizeArc.height) / 2f
                    ),
                    size = sizeArc,
                    style = Stroke(width = 15f, cap = StrokeCap.Round)
                )
            }

            Text(
                text = if(todo.isCompleted) "완료" else "미완료",
                style = TextStyle(
                    color = if(todo.isCompleted) Color(0xFFFA6B80) else GrayWhite,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            )

        }


        Column(
            modifier = modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "${todo.date.y}/${todo.date.m}/${todo.date.d}",
                style = TextStyle(
                    color = Color(0xFFFF8161),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            )

            Spacer(modifier = modifier.height(10.dp))

            FlowRow(
                modifier = modifier
                    .fillMaxSize(),
                horizontalArrangement = Arrangement.Center,
                verticalArrangement = Arrangement.Center
            ) {
                todo.todoList.forEach {
                    Box(
                        modifier = modifier
                            .padding(end = 5.dp, bottom = 5.dp)
                            .background(color = GrayWhite3, shape = RoundedCornerShape(16.dp))
                            .padding(5.dp),
                        contentAlignment = Alignment.Center
                    ){

                        Text(
                            text = it.name,
                            style = TextStyle(
                                color = Color.Black,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Normal
                            )
                        )
                    }

                }
            }
        }
    }
}

@Preview
@Composable
fun HistoryPageScreenPreview(
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = GrayWhite3)
            .statusBarsPadding()
            .padding(10.dp)
    ){

        Text(
            text = "기록 저장소",
            style = TextStyle(
                color = Color.Black,
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp,
                lineHeight = 28.sp,
                letterSpacing = 0.sp
            )
        )

        Spacer(modifier = modifier.height(10.dp))

        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .background(color = GrayWhite3)
                .statusBarsPadding()
        ) {


            items(10){
                HistoryTodoItemPreview()
            }
        }

    }

}

@OptIn(ExperimentalLayoutApi::class)
@Preview
@Composable
fun HistoryTodoItemPreview(
    modifier: Modifier = Modifier,
){
    val todoList: List<String> = listOf("todo1", "todo2", "todo3", "todo4")
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(200.dp)
            .background(color = Color.White, shape = RoundedCornerShape(16.dp))
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = modifier
                .size(150.dp),
            contentAlignment = Alignment.Center
        ){

            Canvas(
                modifier = modifier
                    .size(150.dp)
            ) {
                val size: Size = drawContext.size
                val sizeArc = size / 1.5F

                drawArc(
                    brush = Brush.linearGradient(
                        colors =
                        listOf(
                            Color(0xFFFF44A2),  // 밝은 핫핑크
                            Color(0xFFFF5890),  // 연한 핑크
                            Color(0xFFFA6B80),  // 연한 코럴 핑크
                            Color(0xFFFF7B75),  // 연한 살몬
                            Color(0xFFFF8161),  // 밝은 코럴
                            Color(0xFFFF884D),  // 연한 오렌지
                        ),
                        start = Offset.Zero,
                        end = Offset.Infinite,
                    ),
                    startAngle = 100f,
                    sweepAngle = 360f,
                    useCenter = false,
                    topLeft = Offset(
                        (size.width - sizeArc.width) / 2f,
                        (size.height - sizeArc.height) / 2f
                    ),
                    size = sizeArc,
                    style = Stroke(width = 15f, cap = StrokeCap.Round)
                )
            }

            Text(
                text = "완료",
                style = TextStyle(
                    color =  Color(0xFFFA6B80),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            )
            
        }

        VerticalDivider(
            modifier = modifier
                .height(150.dp)
        )


        Column(
            modifier = modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "2024/05/01",
                style = TextStyle(
                    color = Color(0xFFFF8161),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            )

            Spacer(modifier = modifier.height(10.dp))

            FlowRow(
                modifier = modifier
                    .fillMaxSize(),
                horizontalArrangement = Arrangement.Center,
                verticalArrangement = Arrangement.Center
            ) {
                todoList.forEach {
                    Box(
                        modifier = modifier
                            .padding(end = 5.dp, bottom = 5.dp)
                            .background(color = GrayWhite3, shape = RoundedCornerShape(16.dp))
                            .padding(5.dp),
                        contentAlignment = Alignment.Center
                    ){
                        Text(
                            text = it,
                            style = TextStyle(
                                color = Color.Black,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Normal
                            )
                        )
                    }

                }
            }
        }
    }
}