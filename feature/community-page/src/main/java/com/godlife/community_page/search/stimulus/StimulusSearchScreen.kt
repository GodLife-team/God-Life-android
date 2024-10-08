package com.godlife.community_page.search.stimulus

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Create
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.godlife.community_page.BuildConfig
import com.godlife.community_page.R
import com.godlife.community_page.stimulus.StimulusPostItem
import com.godlife.designsystem.component.GodLifeSearchBar
import com.godlife.designsystem.theme.GrayWhite
import com.godlife.designsystem.theme.GrayWhite3
import com.godlife.designsystem.theme.OpaqueDark
import com.godlife.designsystem.theme.OrangeLight
import com.godlife.designsystem.theme.OrangeMain
import com.godlife.designsystem.view.GodLifeErrorScreen
import com.godlife.network.model.StimulusPostList
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage
import kotlinx.coroutines.launch

@Composable
fun StimulusSearchScreen(
    modifier: Modifier = Modifier,
    parentNavController: NavController,
    viewModel: StimulusSearchViewModel = hiltViewModel()
){
    val uiState by viewModel.uiState.collectAsState()

    val checkPostCategory by viewModel.checkPostCategory.collectAsState()
    val checkWriterCategory by viewModel.checkWriterCategory.collectAsState()

    val searchText = remember { mutableStateOf("") }

    val searchResult by viewModel.searchResult.collectAsState()

    val cScope = rememberCoroutineScope()

    if(uiState !is SearchStimulusUiState.Error){
        Column(
            modifier
                .fillMaxSize()
                .background(Color.White)
                .statusBarsPadding()
                .padding(16.dp)
        ) {

            Row(
                modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ){

                Checkbox(
                    checked = checkPostCategory,
                    onCheckedChange = {
                        viewModel.checkCategory("post")
                    },
                    colors = CheckboxDefaults.colors(
                        checkedColor = OrangeMain
                    )
                )
                Text(
                    text = "제목으로 검색",
                    style = TextStyle(
                        color = Color.Black,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal
                    )
                )

                Checkbox(
                    checked = checkWriterCategory,
                    onCheckedChange = {
                        viewModel.checkCategory("writer")
                    },
                    colors = CheckboxDefaults.colors(
                        checkedColor = OrangeMain
                    )
                )
                Text(
                    text = "작가명으로 검색",
                    style = TextStyle(
                        color = Color.Black,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal
                    )
                )

            }


            GodLifeSearchBar(
                searchText = searchText.value,
                hint = "검색어를 2자 이상 입력해주세요.",
                onTextChanged = { searchText.value = it },
                onSearchClicked = {
                    cScope.launch { viewModel.search(searchText.value) }
                },
                containerColor = OrangeLight,
                contentColor = GrayWhite
            )

            Spacer(modifier.height(16.dp))

            when(uiState){
                is SearchStimulusUiState.Initial -> {

                }
                is SearchStimulusUiState.Loading -> {
                    Box(
                        modifier = modifier
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ){
                        CircularProgressIndicator()
                    }

                }
                is SearchStimulusUiState.Success -> {

                    if(searchResult.isNotEmpty()){
                        Column() {

                            Text(
                                text = "\"${searchText.value}\"에 대해 ${searchResult.size}개의 게시물을 찾았어요.",
                                style = TextStyle(
                                    color = OrangeMain,
                                    fontSize = 14.sp
                                )
                            )

                            Spacer(modifier.height(10.dp))

                            LazyColumn {
                                itemsIndexed(searchResult){index, item ->
                                    SearchStimulusPostItem(
                                        parentNavController = parentNavController,
                                        item = item
                                    )
                                }
                            }

                        }

                    }

                    else{
                        Box(
                            modifier = modifier
                                .fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ){
                            Text(
                                text = "검색 결과가 없습니다.",
                                style = TextStyle(
                                    color = OrangeMain,
                                    fontSize = 16.sp
                                )
                            )
                        }
                    }



                }
                is SearchStimulusUiState.Error -> {

                }
            }


        }
    }



    if(uiState is SearchStimulusUiState.Error){
        GodLifeErrorScreen(
            errorMessage = (uiState as SearchStimulusUiState.Error).message,
            buttonEnabled = false
        )
    }
}

@Composable
fun SearchStimulusPostItem(
    modifier: Modifier = Modifier,
    parentNavController: NavController,
    item: StimulusPostList
){


    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.White)
            .clickable {
                parentNavController.navigate("StimulusDetailScreen/${item.boardId}") {
                    launchSingleTop = true
                }
            },
        verticalAlignment = Alignment.CenterVertically
    ){

        Box(
            modifier = modifier
                .padding(10.dp)
                .size(width = 120.dp, height = 150.dp)
                .shadow(10.dp),
            contentAlignment = Alignment.Center
        ){

            GlideImage(
                imageModel = { BuildConfig.SERVER_IMAGE_DOMAIN + item.thumbnailUrl },
                imageOptions = ImageOptions(
                    contentScale = ContentScale.Crop,
                    alignment = Alignment.Center
                ),
                modifier = modifier
                    .fillMaxSize(),
                loading = {
                    Box(
                        modifier = modifier
                            .background(GrayWhite3)
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ){

                        CircularProgressIndicator(
                            color = OrangeMain
                        )

                    }

                },
                failure = {
                    Image(
                        painter = painterResource(id = R.drawable.category3),
                        contentDescription = "",
                        contentScale = ContentScale.Crop
                    )
                }
            )

            Box(
                modifier = modifier
                    .fillMaxSize()
                    .padding(15.dp)
                    .background(color = OpaqueDark)
                    .padding(start = 10.dp, end = 10.dp, top = 10.dp, bottom = 10.dp),
                contentAlignment = Alignment.Center
            ){

                Text(text = item.title,
                    style = TextStyle(color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                )

            }

        }

        Spacer(modifier.width(16.dp))

        Column(
            modifier = modifier
                .padding(end = 16.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = item.introduction,
                style = TextStyle(
                    color = GrayWhite,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal
                )
            )

            Spacer(modifier.size(5.dp))

            HorizontalDivider()

            Spacer(modifier.size(5.dp))

            Row(
                modifier = modifier
                    .height(15.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Icon(
                    imageVector = Icons.Outlined.Create,
                    contentDescription = "",
                    tint = GrayWhite
                )

                Spacer(modifier.width(2.dp))

                Text(
                    text = item.nickname,
                    style = TextStyle(
                        color = GrayWhite,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                    )
                )
            }

        }

    }



}

@Preview
@Composable
fun StimulusSearchScreenPreview(
    modifier: Modifier = Modifier
){
    Column(
        modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
    ) {

        Row(
            modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ){

            Checkbox(checked = true, onCheckedChange = {})
            Text(
                text = "제목으로 검색",
                style = TextStyle(
                    color = Color.Black,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal
                )
            )

            Checkbox(checked = false, onCheckedChange = {})
            Text(
                text = "작가명으로 검색",
                style = TextStyle(
                    color = Color.Black,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal
                )
            )

        }


        GodLifeSearchBar(
            hint = "검색어를 2자 이상 입력해주세요.",
            onTextChanged = {  }
        )

        Spacer(modifier.height(16.dp))

        LazyColumn {

        }

    }
}

@Preview
@Composable
fun SearchStimulusPostItemPreview(
    modifier: Modifier = Modifier,
    item: StimulusPostItem = StimulusPostItem(title = "이것이 제목이다", writer = "치킨 러버", coverImg = R.drawable.category3, introText = "소개글임다")
){

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.White),
        verticalAlignment = Alignment.CenterVertically
    ){

        Box(
            modifier = modifier
                .padding(10.dp)
                .size(width = 100.dp, height = 150.dp)
                .shadow(10.dp),
            contentAlignment = Alignment.Center
        ){
            Image(
                modifier = modifier
                    .fillMaxWidth(),
                painter = painterResource(id = item.coverImg),
                contentDescription = "",
                contentScale = ContentScale.Crop
            )

            Box(
                modifier = modifier
                    .fillMaxSize()
                    .padding(15.dp)
                    .background(color = OpaqueDark)
                    .padding(start = 10.dp, end = 10.dp, top = 10.dp, bottom = 10.dp),
                contentAlignment = Alignment.Center
            ){

                Text(text = item.title,
                    style = TextStyle(color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                )

            }

        }

        Spacer(modifier.width(16.dp))

        Column(
            modifier = modifier
                .padding(end = 16.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = item.introText,
                style = TextStyle(
                    color = GrayWhite,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal
                )
            )

            Spacer(modifier.size(5.dp))

            HorizontalDivider()

            Spacer(modifier.size(5.dp))

            Text(
                text = "by.${item.writer}",
                style = TextStyle(
                    color = GrayWhite,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal
                )
            )
        }

    }



}