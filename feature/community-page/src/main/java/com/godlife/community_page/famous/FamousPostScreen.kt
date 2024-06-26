package com.godlife.community_page.famous

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.godlife.community_page.CommunityPageViewModel
import com.godlife.community_page.R
import com.godlife.community_page.navigation.PostDetailRoute
import com.godlife.designsystem.list.CommunityFamousPostListPreview
import com.godlife.designsystem.theme.GodLifeTheme
import com.godlife.designsystem.theme.GodLifeTypography
import com.godlife.designsystem.theme.GrayWhite
import com.godlife.designsystem.theme.GrayWhite3
import com.godlife.model.community.FamousPostItem
import com.godlife.model.community.TagItem
import com.godlife.network.model.PostDetailBody

@Composable
fun FamousPostScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: CommunityPageViewModel
){

    GodLifeTheme {
        Column(
            modifier
                .fillMaxSize()
                .background(GrayWhite3)
        ) {

            Box(
                modifier
                    .padding(start = 20.dp, end = 20.dp)
                    .height(25.dp)){


                Row(modifier.fillMaxWidth()){
                    Icon(painter = painterResource(R.drawable.star_icons8), contentDescription = "", tint = Color.Unspecified)
                    Spacer(modifier.size(5.dp))
                    Text(text = "일주일동안 갓생 인정을 많이 받은 게시물이에요", style = TextStyle(color = GrayWhite, fontSize = 18.sp), textAlign = TextAlign.Center)
                }


            }

            Spacer(modifier = modifier.size(20.dp))

            WeeklyFamousPostListView(viewModel = viewModel, navController = navController)

        }
    }
}

@Composable
fun WeeklyFamousPostListView(
    modifier: Modifier = Modifier,
    viewModel: CommunityPageViewModel,
    navController: NavController
){

    val weeklyFamousPost = viewModel.weeklyFamousPostList.collectAsState().value

    Box(
        modifier
            .background(Color.White)
            .fillMaxWidth()
            .padding(
                start = 10.dp,
                end = 10.dp,
                top = 10.dp,
                bottom = 10.dp
            ),
        contentAlignment = Alignment.CenterStart
    ){
        Column {


            Spacer(modifier.size(10.dp))

            LazyRow {
                itemsIndexed(weeklyFamousPost) { index, item ->

                    WeeklyFamousPostList(famousPostItem = item, navController = navController)
                }
            }

            Spacer(modifier.size(10.dp))

            Button(onClick = { /*TODO*/ },
                colors = ButtonDefaults.buttonColors(Color.White),
                modifier = modifier.size(30.dp)
            ) {
                Text(text = "> 더보기", style = GodLifeTypography.titleSmall)
            }


        }
    }
}

@Composable
fun WeeklyFamousPostList(modifier: Modifier = Modifier,
                         famousPostItem: PostDetailBody,
                         navController: NavController
){

    val postId = famousPostItem.board_id.toString()

    Box(modifier.padding(top = 5.dp, bottom = 5.dp, start = 10.dp, end = 10.dp)){
        Column(
            modifier
                .width(320.dp)
                .height(520.dp)
                .shadow(7.dp)
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(10.dp)
                )
                .clickable { navController.navigate("${PostDetailRoute.route}/$postId") }
        ){
            Box(
                modifier
                    .weight(0.4f)
                    .fillMaxWidth()
                    .background(
                        color = Color.Gray,
                        shape = RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp)
                    )
            ){
                Text(text = "IMAGE", modifier.align(Alignment.Center))
            }
            Column(
                modifier
                    .weight(0.6f)
                    .fillMaxWidth()
                    .padding(20.dp)) {

                Row(modifier.fillMaxWidth()){
                    Text(text = famousPostItem.nickname, style = TextStyle(color = Color.Black, fontSize = 20.sp, fontWeight = FontWeight.Bold))

                    Spacer(modifier.size(10.dp))

                    //티어 보여줄 부분
                    Text(text = famousPostItem.tier, style = TextStyle(color = Color.Magenta, fontWeight = FontWeight.Bold, fontSize = 15.sp))

                }

                Spacer(modifier.size(15.dp))

                Text(text = famousPostItem.title, style = GodLifeTypography.titleMedium)

                Spacer(modifier.size(20.dp))

                Text(text = famousPostItem.body, style = GodLifeTypography.bodyMedium)

                Spacer(modifier.size(20.dp))

                LazyVerticalGrid(
                    columns = GridCells.Fixed(4),
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    itemsIndexed(famousPostItem.tags) { index, item ->
                        TagItem(item)
                    }
                }


            }
        }
    }

}

@Preview
@Composable
fun WeeklyFamousPostListViewPreview(modifier: Modifier = Modifier){

    Box(
        modifier
            .background(Color.White)
            .fillMaxWidth()
            .padding(
                start = 10.dp,
                end = 10.dp,
                top = 10.dp,
                bottom = 10.dp
            ),
        contentAlignment = Alignment.CenterStart
    ){
        Column {


            Spacer(modifier.size(10.dp))

            LazyRow {
                items(5) { item ->
                    CommunityFamousPostListPreview()
                }
            }

            Spacer(modifier.size(10.dp))

            Button(onClick = { /*TODO*/ },
                colors = ButtonDefaults.buttonColors(Color.White),
                modifier = modifier.size(30.dp)
            ) {
                Text(text = "> 더보기", style = GodLifeTypography.titleSmall)
            }


        }
    }
}

