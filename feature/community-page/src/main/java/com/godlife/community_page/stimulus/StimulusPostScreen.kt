package com.godlife.community_page.stimulus

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.godlife.community_page.BuildConfig
import com.godlife.community_page.R
import com.godlife.community_page.navigation.StimulusPostDetailRoute
import com.godlife.create_post.stimulus.CreateStimulusPostScreen
import com.godlife.designsystem.theme.GodLifeTheme
import com.godlife.designsystem.theme.GrayWhite
import com.godlife.designsystem.theme.OpaqueDark
import com.godlife.network.model.StimulusPost

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun StimulusPostScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    bottomBarVisibleState: MutableState<Boolean>,
    viewModel: StimulusPostViewModel = hiltViewModel()
){
    bottomBarVisibleState.value = true

    val navController2 = rememberNavController()

    val fabVisibleState = remember { mutableStateOf(true) }


    GodLifeTheme {
        Scaffold(
            floatingActionButton = {
                if(fabVisibleState.value){
                    FloatingActionButton(
                        onClick = { navController2.navigate("CreateStimulusPostScreen") }
                    ) {
                        Icon(imageVector = Icons.Default.Warning, contentDescription = "")
                    }
                }

            }
        ) {

            NavHost(navController = navController2, startDestination = "StimulusPostScreen"){

                composable("StimulusPostScreen"){
                    fabVisibleState.value = true
                    LazyColumn(
                        modifier
                            .fillMaxSize()
                            .background(Color.White)
                    ) {

                        item {
                            StimulusPostContent1Preview(navController = navController)
                        }


                        item {

                            Text(
                                modifier = modifier
                                    .padding(20.dp),
                                text = "닉네임 님, 인기 글을 읽어보세요.",
                                style = TextStyle(
                                    color = Color.Black,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            )

                        }

                        item {
                            StimulusPostFamousContentPreview()
                        }

                        item {

                            Text(
                                modifier = modifier
                                    .padding(20.dp),
                                text = "최신글",
                                style = TextStyle(
                                    color = Color.Black,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            )

                        }

                        item { LatestStimulusPostList(viewModel = viewModel, navController = navController) }

                        item { RecommendUserContentPreview() }

                        item {

                            Text(
                                modifier = modifier
                                    .padding(20.dp),
                                text = "추천 작가님의 게시물",
                                style = TextStyle(
                                    color = Color.Black,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            )

                        }

                        item { LatestStimulusPostListPreview() }


                    }

                }

                composable("CreateStimulusPostScreen"){
                    fabVisibleState.value = false
                    CreateStimulusPostScreen(bottomBarVisibleState = bottomBarVisibleState, fabVisibleState = fabVisibleState)

                }
            }


        }

    }

}

@Composable
fun LatestStimulusPostList(
    modifier: Modifier = Modifier,
    viewModel: StimulusPostViewModel,
    navController: NavController
){
    val postList = viewModel.latestPostList.collectAsLazyPagingItems()

    /*
    items(postList.itemCount){
        postList[it]?.let { it1 -> LatestPostListView(item = it1, navController = navController) }
    }
     */

    LazyHorizontalGrid(
        modifier = modifier
            .fillMaxWidth()
            .height(500.dp),
        rows = GridCells.Fixed(3)
    ) {

        items(postList.itemCount){
            postList[it]?.let { it1 -> LatestStimulusItem(item = it1, navController = navController)  }
        }
    }
}

@Composable
fun LatestStimulusItem(
    modifier: Modifier = Modifier,
    item: StimulusPost,
    navController: NavController
){
    val bitmap: MutableState<Bitmap?> = remember { mutableStateOf(null) }

    Row(
        modifier = modifier
            .height(150.dp)
            .fillMaxWidth()
            .background(Color.White)
            .padding(10.dp)
            .clickable { navController.navigate(StimulusPostDetailRoute.route) },
        verticalAlignment = Alignment.CenterVertically
    ){

        Glide.with(LocalContext.current)
            .asBitmap()
            .load(BuildConfig.SERVER_IMAGE_DOMAIN + item.thumbnailUrl)
            .error(R.drawable.category3)
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    bitmap.value = resource
                }

                override fun onLoadCleared(placeholder: Drawable?) {}
            })

        bitmap.value?.asImageBitmap()?.let { fetchedBitmap ->
            Image(
                bitmap = fetchedBitmap,
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
                modifier = modifier
                    .size(100.dp)
            )   //bitmap이 없다면
        } ?: Image(
            painter = painterResource(id = R.drawable.category3),
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
            modifier = modifier
                .size(100.dp)
        )

        Spacer(modifier.size(10.dp))

        Column(

        ) {
            Text(
                text = item.introduction,
                style = TextStyle(
                    color = GrayWhite,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal,
                )
            )

            Spacer(modifier.size(5.dp))

            HorizontalDivider()

            Spacer(modifier.size(5.dp))

            Text(
                modifier = modifier
                    .fillMaxWidth(),
                text = "by ${item.nickname}",
                style = TextStyle(
                    color = GrayWhite,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.End
                )
            )
        }
    }
}


@Preview
@Composable
fun StimulusPostContent1Preview(
    modifier: Modifier = Modifier,
    navController: NavController? = null
){

    var width by remember {
        mutableStateOf(0.dp)
    }

    val localDensity = LocalDensity.current

    val item = listOf(
        StimulusPostItem(
            title = "이것은 제목!",
            writer = "치킨 러버",
            coverImg = R.drawable.category3,
            introText = "대충 이러이러한 내용이라고 소개하는 내용"
        ),
        StimulusPostItem(
            title = "나도 제목!",
            writer = "초코 러버",
            coverImg = R.drawable.category4,
            introText = "대충 이러이러한 내용이라고 소개하는 내용"
        ),
        StimulusPostItem(
            title = "제목 등장",
            writer = "라면 좋아",
            coverImg = R.drawable.category3,
            introText = "대충 이러이러한 내용이라고 소개하는 내용"
        ),
        StimulusPostItem(
            title = "제모오오오옥",
            writer = "헬로우",
            coverImg = R.drawable.category4,
            introText = "대충 이러이러한 내용이라고 소개하는 내용"
        )
    )

    LazyRow(
        modifier = modifier
            .fillMaxWidth()
            .height(400.dp)
            .onGloballyPositioned {
                width = with(localDensity) {
                    it.size.width.toDp()
                }
            }
    ){

        itemsIndexed(item){index, it ->

            Box(
                modifier = modifier
                    .fillMaxHeight()
                    .width(width)
                    .background(Color.Black)
                    .clickable { navController?.navigate(StimulusPostDetailRoute.route) },
                contentAlignment = Alignment.Center
            ){


                Image(
                    modifier = modifier
                        .fillMaxSize()
                        .blur(
                            radiusX = 15.dp, radiusY = 15.dp
                        ),
                    painter = painterResource(id = it.coverImg),
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    alpha = 0.7f
                )

                Column(
                    modifier = modifier
                        .fillMaxHeight()
                        .width(220.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    StimulusCoverItemPreview(item = it)

                    Spacer(modifier.size(5.dp))

                    Text(
                        text = it.introText,
                        style = TextStyle(
                            color = Color.White,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Normal,
                            textAlign = TextAlign.Center
                        )
                    )

                    Spacer(modifier.size(5.dp))

                    HorizontalDivider()

                    Spacer(modifier.size(5.dp))

                    Text(
                        text = "by.${it.writer}",
                        style = TextStyle(
                            color = Color.White,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Normal,
                            textAlign = TextAlign.Center
                        )
                    )

                }

            }
        }

    }
}

@Preview
@Composable
fun StimulusCoverItemPreview(
    modifier: Modifier = Modifier,
    item: StimulusPostItem = StimulusPostItem(title = "이것이 제목이다", writer = "치킨 러버", coverImg = R.drawable.category3, introText = "")
){
    Box(
        modifier = modifier
            .padding(10.dp)
            .size(width = 200.dp, height = 250.dp)
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
                .padding(30.dp)
                .background(color = OpaqueDark)
                .padding(start = 20.dp, end = 20.dp, top = 10.dp, bottom = 10.dp),
            contentAlignment = Alignment.Center
        ){

            Text(text = item.title,
                style = TextStyle(color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            )

        }

    }
}

@Preview
@Composable
fun StimulusPostFamousContentPreview(
    modifier: Modifier = Modifier,
    navController: NavController? = null
){
    val item = listOf(
        StimulusPostItem(
            title = "이것은 제목!",
            writer = "치킨 러버",
            coverImg = R.drawable.category3,
            introText = "대충 이러이러한 내용이라고 소개하는 내용"
        ),
        StimulusPostItem(
            title = "나도 제목!",
            writer = "초코 러버",
            coverImg = R.drawable.category4,
            introText = "대충 이러이러한 내용이라고 소개하는 내용"
        ),
        StimulusPostItem(
            title = "제목 등장",
            writer = "라면 좋아",
            coverImg = R.drawable.category3,
            introText = "대충 이러이러한 내용이라고 소개하는 내용"
        ),
        StimulusPostItem(
            title = "제모오오오옥",
            writer = "헬로우",
            coverImg = R.drawable.category4,
            introText = "대충 이러이러한 내용이라고 소개하는 내용"
        )
    )

    LazyRow {
        itemsIndexed(item){index, it ->

            FamousStimulusItemPreview(item = it)

        }
    }
}

@Preview
@Composable
fun FamousStimulusItemPreview(
    modifier: Modifier = Modifier,
    item: StimulusPostItem = StimulusPostItem(title = "이것이 제목이다", writer = "치킨 러버", coverImg = R.drawable.category3, introText = "갓생을 살고 싶어하는 당신을 위해 작성한 글!")
){
    Column(
        modifier = modifier
            .size(width = 300.dp, height = 350.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Box(
            modifier = modifier
                .padding(10.dp)
                .size(width = 200.dp, height = 250.dp)
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
                    .padding(30.dp)
                    .background(color = OpaqueDark)
                    .padding(start = 20.dp, end = 20.dp, top = 10.dp, bottom = 10.dp),
                contentAlignment = Alignment.Center
            ){

                Text(text = item.title,
                    style = TextStyle(color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                )

            }

        }

        Spacer(modifier.size(5.dp))

        Text(
            text = item.introText,
            style = TextStyle(
                color = GrayWhite,
                fontSize = 12.sp,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Center
            )
        )

        Spacer(modifier.size(5.dp))

        HorizontalDivider(modifier.padding(start = 20.dp, end = 20.dp))

        Spacer(modifier.size(5.dp))

        Text(
            text = "by.${item.writer}",
            style = TextStyle(
                color = GrayWhite,
                fontSize = 12.sp,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Center
            )
        )

    }

}

@Preview
@Composable
fun LatestStimulusPostListPreview(
    modifier: Modifier = Modifier
){

    val item = listOf(
        StimulusPostItem(
            title = "이것은 제목!",
            writer = "치킨 러버",
            coverImg = R.drawable.category3,
            introText = "대충 이러이러한 내용이라고 소개하는 내용"
        ),
        StimulusPostItem(
            title = "나도 제목!",
            writer = "초코 러버",
            coverImg = R.drawable.category4,
            introText = "대충 이러이러한 내용이라고 소개하는 내용"
        ),
        StimulusPostItem(
            title = "제목 등장",
            writer = "라면 좋아",
            coverImg = R.drawable.category3,
            introText = "대충 이러이러한 내용이라고 소개하는 내용"
        ),
        StimulusPostItem(
            title = "제모오오오옥",
            writer = "헬로우",
            coverImg = R.drawable.category4,
            introText = "대충 이러이러한 내용이라고 소개하는 내용"
        ),
        StimulusPostItem(
            title = "제목 등장",
            writer = "라면 좋아",
            coverImg = R.drawable.category3,
            introText = "대충 이러이러한 내용이라고 소개하는 내용"
        ),
        StimulusPostItem(
            title = "제모오오오옥",
            writer = "헬로우",
            coverImg = R.drawable.category4,
            introText = "대충 이러이러한 내용이라고 소개하는 내용"
        )
    )

    LazyHorizontalGrid(
        modifier = modifier
            .fillMaxWidth()
            .height(500.dp),
        rows = GridCells.Fixed(3)
    ) {
        itemsIndexed(item){index, it ->
            LatestStimulusItemPreview(item = it)
        }
    }
}

@Preview
@Composable
fun LatestStimulusItemPreview(
    modifier: Modifier = Modifier,
    item: StimulusPostItem = StimulusPostItem(title = "이것이 제목이다", writer = "치킨 러버", coverImg = R.drawable.category3, introText = "갓생을 살고 싶어하는 당신을 위해 작성한 글!")
){
    Row(
        modifier = modifier
            .height(150.dp)
            .fillMaxWidth()
            .background(Color.White)
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        Image(
            modifier = modifier
                .size(100.dp),
            painter = painterResource(id = item.coverImg),
            contentDescription = "",
            contentScale = ContentScale.Crop
        )

        Spacer(modifier.size(10.dp))

        Column(

        ) {
            Text(
                text = item.introText,
                style = TextStyle(
                    color = GrayWhite,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal,
                )
            )

            Spacer(modifier.size(5.dp))

            HorizontalDivider()

            Spacer(modifier.size(5.dp))

            Text(
                modifier = modifier
                    .fillMaxWidth(),
                text = "by.${item.writer}",
                style = TextStyle(
                    color = GrayWhite,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.End
                )
            )
        }
    }
}

@Preview
@Composable
fun RecommendUserContentPreview(
    modifier: Modifier = Modifier
){
    var width by remember {
        mutableStateOf(0.dp)
    }

    val localDensity = LocalDensity.current

    val user = RecommendUserItem(
        nickname = "치킨 러버",
        introText = "치킨을 좋아하는 사람입니다.",
        profileImg = R.drawable.category3,
        backgroundImg = R.drawable.category4,
        postItem = listOf(
            StimulusPostItem(
                title = "이것은 제목!",
                writer = "치킨 러버",
                coverImg = R.drawable.category3,
                introText = "대충 이러이러한 내용이라고 소개하는 내용"
            ),
            StimulusPostItem(
                title = "나도 제목!",
                writer = "초코 러버",
                coverImg = R.drawable.category4,
                introText = "대충 이러이러한 내용이라고 소개하는 내용"
            ),
            StimulusPostItem(
                title = "제목 등장",
                writer = "라면 좋아",
                coverImg = R.drawable.category3,
                introText = "대충 이러이러한 내용이라고 소개하는 내용"
            ),
            StimulusPostItem(
                title = "제모오오오옥",
                writer = "헬로우",
                coverImg = R.drawable.category4,
                introText = "대충 이러이러한 내용이라고 소개하는 내용"
            )
        )
    )

    Box(
        modifier = modifier
            .height(400.dp)
            .fillMaxWidth()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ){


        Image(
            modifier = modifier
                .fillMaxSize()
                .blur(
                    radiusX = 15.dp, radiusY = 15.dp
                ),
            painter = painterResource(id = user.backgroundImg),
            contentDescription = "",
            contentScale = ContentScale.Crop,
            alpha = 0.6f
        )

        RecommendUserProfilePreview()

    }

}

@Preview
@Composable
fun RecommendUserProfilePreview(
    modifier: Modifier = Modifier,
    item: RecommendUserItem = RecommendUserItem(nickname = "치킨 러버", introText = "치킨을 좋아하는 사람입니다.", profileImg = R.drawable.category3, backgroundImg = R.drawable.category4, postItem = emptyList())
){
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            modifier = modifier
                .fillMaxWidth(),
            text = "추천 작가",
            style = TextStyle(
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            ),
            textAlign = TextAlign.Start
        )

        Spacer(modifier.size(20.dp))

        Row(
            modifier = modifier
                .fillMaxSize()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ){

            Column(
                modifier = modifier
                    .weight(0.5f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Image(
                    modifier = modifier
                        .size(150.dp)
                        .clip(shape = CircleShape),
                    painter = painterResource(id = item.profileImg),
                    contentDescription = "",
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier.size(20.dp))

                Text(
                    text = item.nickname,
                    style = TextStyle(
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    textAlign = TextAlign.Center
                )
            }

            Box(
                modifier = modifier
                    .weight(0.5f)
            ){

                Text(
                    modifier = modifier
                        .padding(start = 20.dp, end = 20.dp),
                    text = "\"${item.introText}\"",
                    style = TextStyle(
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Normal
                    ),
                    textAlign = TextAlign.Center
                )

            }



        }


    }

}

@Preview
@Composable
fun RecommendUserPostListPreview(
    modifier: Modifier = Modifier,
    item: StimulusPostItem = StimulusPostItem(title = "이것이 제목이다", writer = "치킨 러버", coverImg = R.drawable.category3, introText = "갓생을 살고 싶어하는 당신을 위해 작성한 글!")
){

}


data class StimulusPostItem(
    val title: String,
    val writer: String,
    val coverImg: Int,
    val introText: String
)

data class RecommendUserItem(
    val nickname: String,
    val introText: String,
    val profileImg: Int,
    val backgroundImg: Int,
    val postItem: List<StimulusPostItem>
)


