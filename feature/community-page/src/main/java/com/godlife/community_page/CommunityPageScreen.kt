package com.godlife.community_page

import android.annotation.SuppressLint
import android.util.Log
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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.godlife.community_page.famous.FamousPostScreen
import com.godlife.community_page.latest.LatestPostListPreview
import com.godlife.community_page.latest.LatestPostScreen
import com.godlife.community_page.latest.LoadingLatestPostScreen
import com.godlife.community_page.navigation.FamousPostRoute
import com.godlife.community_page.navigation.LatestPostRoute
import com.godlife.community_page.navigation.RankingRoute
import com.godlife.community_page.navigation.SearchResultRoute
import com.godlife.community_page.navigation.StimulusPostRoute
import com.godlife.community_page.ranking.RankingScreen
import com.godlife.community_page.search.SearchResultScreen
import com.godlife.community_page.stimulus.StimulusPostScreen
import com.godlife.designsystem.component.GodLifeSearchBar
import com.godlife.designsystem.component.CommunitySearchBar
import com.godlife.designsystem.theme.GodLifeTheme
import com.godlife.designsystem.theme.GrayWhite
import com.godlife.designsystem.theme.GrayWhite2
import com.godlife.designsystem.theme.GrayWhite3
import com.godlife.designsystem.theme.OpaqueLight
import com.google.android.gms.ads.AdLoader
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommunityPageScreen(
    modifier: Modifier = Modifier,
    parentNavController: NavController,
    bottomBarVisibleState: MutableState<Boolean>,
    viewModel: CommunityPageViewModel = hiltViewModel()
){

    val snackBarHostState = remember { SnackbarHostState() }
    SnackbarHost(hostState = snackBarHostState)
    LaunchedEffect(key1 = true) {

    }

    val uiState by viewModel.uiState.collectAsState()

    Log.e("CommunityPageScreen", uiState.toString())

    val navController = rememberNavController()

    val navControllerBottomSheet = rememberNavController()

    val topTitle by remember { viewModel.topTitle }

    //val deviceHeight = LocalConfiguration.current.screenHeightDp.dp

    var height by remember {
        mutableStateOf(0.dp)
    }

    val localDensity = LocalDensity.current

    Log.e("height", height.toString())


    //BottomSheet가 접혀있을 때 높이
    val initBottomSheetHeight = height - 240.dp

    val searchText by viewModel.searchText.collectAsState()

    val searchCategory = viewModel.searchCategory.value

    NavHost(navController = navController, startDestination = "CommunityPageScreen"){
        composable("CommunityPageScreen"){
            bottomBarVisibleState.value = true
            GodLifeTheme(
                modifier
                    .fillMaxSize()
                    .onGloballyPositioned {
                        height = with(localDensity) {
                            it.size.height.toDp()
                        }
                    }
                    .background(
                        brush = Brush.linearGradient(
                            listOf(
                                Color(0xCC496B9F),
                                Color(0xCB494A9F),
                                Color(0xCC6A499F),
                                Color(0xCC6A499F),
                                Color(0xCC96499F),
                                Color(0xCCDB67AD),
                                Color(0xCCFF5E5E),
                            )
                        )
                    )
            ) {

                Column(
                    modifier = modifier
                        .fillMaxWidth()
                        .height(280.dp)
                        .background(
                            brush = Brush.linearGradient(
                                viewModel.setBackgroundColor()
                            )
                        )
                        .statusBarsPadding(),
                    verticalArrangement = Arrangement.Top
                ){

                    Box(
                        modifier
                            .padding(top = 20.dp, start = 10.dp, end = 10.dp)
                            .height(50.dp)
                            .fillMaxWidth()){

                        Text(
                            text = topTitle,
                            style = TextStyle(
                                color = Color.White,
                                fontSize = 25.sp,
                                fontWeight = FontWeight.Bold
                            )
                        )

                    }

                    Column(
                        modifier
                            .padding(start = 10.dp, end = 10.dp, bottom = 10.dp)
                            .height(80.dp)
                    ) {

                        Text(text = "다른 굿생러 분들은 어떻게 살고 있을까요?", style = TextStyle(color = Color.White, fontSize = 15.sp))

                        Spacer(modifier = Modifier.height(10.dp))

                        CommunitySearchBar(
                            modifier = modifier
                                .fillMaxWidth()
                                .align(Alignment.CenterHorizontally),
                            searchText = searchText,
                            containerColor = OpaqueLight,
                            onTextChanged = { viewModel.onSearchTextChange(it) },
                            onSearchClicked = {

                                when(searchCategory){
                                    "title" -> {
                                        viewModel.getSearchedPost(keyword = searchText)
                                    }
                                    "nickname" -> {
                                        viewModel.getSearchedPost(nickname = searchText)
                                    }
                                    "tag" -> {
                                        viewModel.getSearchedPost(tags = searchText)
                                    }
                                }

                                navControllerBottomSheet.navigate(SearchResultRoute.route)
                            },
                            optionBoolean = true,
                            optionMenu = {
                                Column {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically
                                    ){
                                        Checkbox(
                                            checked = searchCategory == "title",
                                            onCheckedChange = { viewModel.onSearchCategoryChange("title") }
                                        )
                                        Text(
                                            text = "제목으로 검색",
                                            style = TextStyle(
                                                color = Color.Black,
                                                fontSize = 14.sp
                                            )
                                        )
                                    }
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically
                                    ){
                                        Checkbox(
                                            checked = searchCategory == "nickname",
                                            onCheckedChange = { viewModel.onSearchCategoryChange("nickname") }
                                        )
                                        Text(
                                            text = "작성자명으로 검색",
                                            style = TextStyle(
                                                color = Color.Black,
                                                fontSize = 14.sp
                                            )
                                        )
                                    }
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically
                                    ){
                                        Checkbox(
                                            checked = searchCategory == "tag",
                                            onCheckedChange = { viewModel.onSearchCategoryChange("tag") }
                                        )
                                        Text(
                                            text = "태그로 검색",
                                            style = TextStyle(
                                                color = Color.Black,
                                                fontSize = 14.sp
                                            )
                                        )
                                    }
                                }
                            }
                        )

                    }

                    Row(modifier = modifier
                        .fillMaxWidth()
                        .height(50.dp),
                        verticalAlignment = Alignment.Top) {

                        Column(
                            modifier = modifier
                                .weight(0.25f)
                                .padding(bottom = 5.dp)
                                .clickable { navControllerBottomSheet.navigate(FamousPostRoute.route) },
                            verticalArrangement = Arrangement.Bottom,
                            horizontalAlignment = Alignment.CenterHorizontally
                        )  {

                            CategoryBox(route = FamousPostRoute.route, categoryName = "인기 게시물", viewModel = viewModel)

                        }

                        Column(
                            modifier = modifier
                                .weight(0.25f)
                                .padding(bottom = 5.dp)
                                .clickable { navControllerBottomSheet.navigate(LatestPostRoute.route) },
                            verticalArrangement = Arrangement.Bottom,
                            horizontalAlignment = Alignment.CenterHorizontally
                        )  {

                            CategoryBox(route = LatestPostRoute.route, categoryName = "최신 게시물", viewModel = viewModel)

                        }

                        Column(
                            modifier = modifier
                                .weight(0.25f)
                                .padding(bottom = 5.dp)
                                .clickable { navController.navigate(StimulusPostRoute.route) },
                            verticalArrangement = Arrangement.Bottom,
                            horizontalAlignment = Alignment.CenterHorizontally
                        )  {

                            CategoryBox(route = StimulusPostRoute.route, categoryName = "굿생 자극", viewModel = viewModel)

                        }

                        Column(
                            modifier = modifier
                                .weight(0.25f)
                                .padding(bottom = 5.dp)
                                .clickable { navControllerBottomSheet.navigate(RankingRoute.route) },
                            verticalArrangement = Arrangement.Bottom,
                            horizontalAlignment = Alignment.CenterHorizontally
                        )  {

                            CategoryBox(route = RankingRoute.route, categoryName = "명예의 전당", viewModel = viewModel)

                        }

                    }

                }


                //BottomSheetScaffold의 상태
                val scaffoldState = rememberBottomSheetScaffoldState()

                //BottomSheetScaffold의 상태에 따라 viewModel의 TopTitle을 변경
                viewModel.changeTopTitle(scaffoldState.bottomSheetState.currentValue.toString())

                //Log.e("bottomSheetState", scaffoldState.bottomSheetState.currentValue.toString())

                if(height!= 0.0.dp){
                    BottomSheetScaffold(
                        scaffoldState = scaffoldState,
                        sheetPeekHeight = initBottomSheetHeight,
                        sheetMaxWidth = LocalConfiguration.current.screenWidthDp.dp,
                        sheetContainerColor = GrayWhite3,
                        sheetContent = {
                            Box(
                                modifier = modifier
                                //.heightIn(max = expandedBottomSheetHeight)
                            ){
                                CommunityPageView(
                                    uiState = uiState,
                                    navController = navControllerBottomSheet,
                                    parentNavController = parentNavController,
                                    viewModel = viewModel
                                )
                            }

                        }
                    ) {

                    }
                }


            }

        }

        composable(StimulusPostRoute.route){
            bottomBarVisibleState.value = true
            viewModel.changeCurrentRoute(route = StimulusPostRoute.route)
            StimulusPostScreen(
                navController = parentNavController,
                bottomBarVisibleState = bottomBarVisibleState
            )

        }


    }


}

@Composable
fun CategoryBox(route: String, categoryName: String, modifier: Modifier = Modifier, viewModel: CommunityPageViewModel){


    if(viewModel.selectedRoute.value == route){

        Text(text = categoryName, style = TextStyle(color = Color.White, fontWeight = FontWeight.Bold, fontSize = 15.sp))

        HorizontalDivider(modifier = modifier
            .padding(10.dp)
            .background(Color.White))

    }
    else{
        Text(text = categoryName, style = TextStyle(color = GrayWhite2, fontWeight = FontWeight.Normal, fontSize = 12.sp))

        HorizontalDivider(modifier = modifier
            .padding(12.dp)
            .background(GrayWhite2))
    }



}

@Composable
fun CommunityPageView(
    modifier: Modifier = Modifier,
    uiState: CommunityPageUiState,
    navController: NavHostController,
    parentNavController: NavController,
    viewModel: CommunityPageViewModel
){

    NavHost(navController = navController, startDestination = FamousPostRoute.route) {

        composable(FamousPostRoute.route){
            viewModel.changeCurrentRoute(route = FamousPostRoute.route)
            viewModel.getWeeklyFamousPost()
            //viewModel.getAllFamousPost()

            when(uiState){

                is CommunityPageUiState.Loading -> {
                    LoadingFamousPostScreen()
                }

                is CommunityPageUiState.Success -> {
                    FamousPostScreen(
                        navController = parentNavController,
                        viewModel = viewModel
                    )
                }

                is CommunityPageUiState.Error -> {

                }
            }
        }

        composable(LatestPostRoute.route) {
            val context = LocalContext.current
            viewModel.changeCurrentRoute(route = LatestPostRoute.route)
            /*TODO 광고 테스트 UnitID 사용중*/
            viewModel.getLatestPost(AdLoader.Builder(context, "ca-app-pub-3940256099942544/2247696110"))

            when(uiState){

                is CommunityPageUiState.Loading -> {
                    LoadingLatestPostScreen()
                }

                is CommunityPageUiState.Success -> {
                    LatestPostScreen(
                        navController = parentNavController,
                        viewModel = viewModel
                    )
                }

                is CommunityPageUiState.Error -> {

                }
            }

        }

        /*
        composable(StimulusPostRoute.route) {
            viewModel.changeCurrentRoute(route = StimulusPostRoute.route)
            //StimulusPostScreen()
            StimulusPostScreenPreview(navController = navController)
        }

         */

        composable(RankingRoute.route) {
            viewModel.changeCurrentRoute(route = RankingRoute.route)
            viewModel.getWeeklyRanking()
            viewModel.getAllRanking()
            RankingScreen(
                parentNavController = parentNavController,
                viewModel = viewModel
            )

        }

        /*
        //PostDeatil Screen
        composable("${PostDetailRoute.route}/{postId}", arguments = listOf(navArgument("postId"){type = NavType.StringType})){
            val postId = it.arguments?.getString("postId")
            if (postId != null) {
                PostDetailScreen(
                    postId = postId,
                    parentNavController = parentNavController,
                    navController = navController
                )
            }
        }

         */
        /*
        //Stimulus Post Detail Screen
        composable(StimulusPostDetailRoute.route){

            StimulusDetailScreen()
        }

         */

        //검색 결과 뷰
        composable(SearchResultRoute.route){
            viewModel.changeCurrentRoute(route = SearchResultRoute.route)
            SearchResultScreen(viewModel = viewModel, navController = parentNavController)
        }


    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, showSystemUi = false)
@Composable
fun CommunityPageScreenPreview(modifier: Modifier = Modifier){

    val deviceHeight = LocalConfiguration.current.screenHeightDp

    val searchText by remember { mutableStateOf("") }

    var isRefreshing by remember { mutableStateOf(false) }

    val cScope = rememberCoroutineScope()

    val refreshState = rememberPullToRefreshState()

    var height by remember {
        mutableStateOf(0.dp)
    }

    val localDensity = LocalDensity.current

    GodLifeTheme(
        modifier
            .fillMaxSize()
            .onGloballyPositioned {
                height = with(localDensity) {
                    it.size.height.toDp()
                }
            }
            .background(
                brush = Brush.linearGradient(
                    listOf(
                        Color(0xCCFF5E5E),
                        Color(0xCCFF5E5E),
                        )
                )
            )
    ) {

        PullToRefreshBox(
            isRefreshing = isRefreshing,
            state = refreshState,
            onRefresh = {

                cScope.launch {
                    isRefreshing = !isRefreshing
                    delay(2000L)
                    isRefreshing = !isRefreshing
                }



            }
        ) {

            Column(
                modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .background(
                        brush = Brush.linearGradient(
                            listOf(
                                Color(0xFFFF44A2),  // 밝은 핫핑크
                                Color(0xFFFF5890),  // 연한 핑크
                                Color(0xFFFA6B80),  // 연한 코럴 핑크
                                Color(0xFFFF7B75),  // 연한 살몬
                                Color(0xFFFF8161),  // 밝은 코럴
                                Color(0xFFFF884D),  // 연한 오렌지
                            )
                        )
                    )
                , verticalArrangement = Arrangement.Top
            ){

                Box(
                    modifier
                        .padding(top = 20.dp, start = 20.dp, end = 20.dp)
                        .height(50.dp)
                        .fillMaxWidth()){

                    Text(text = "굿생 커뮤니티", style = TextStyle(color = Color.White, fontSize = 25.sp, fontWeight = FontWeight.Bold))

                }

                Column(
                    modifier
                        .padding(start = 20.dp, end = 20.dp, bottom = 20.dp)
                        .height(80.dp)) {

                    Text(text = "다른 굿생러 분들의 게시물을 확인하세요.", style = TextStyle(color = GrayWhite2, fontSize = 15.sp))

                    Spacer(modifier = Modifier.height(15.dp))

                    GodLifeSearchBar(
                        modifier = modifier
                            .fillMaxWidth()
                            .align(Alignment.CenterHorizontally),
                        searchText = searchText,
                        containerColor = OpaqueLight,
                        onTextChanged = { it -> searchText },
                        onSearchClicked = {  }
                    )

                    /*
                    SearchBar(
                        modifier = modifier.height(40.dp),
                        query = searchText,
                        onQueryChange = { it -> searchText },
                        onSearch = { it -> searchText },
                        active = false,
                        onActiveChange = {  },
                        placeholder = { Text(text = "검색어를 입력하세요.") },
                        leadingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = null, tint = Color.White) },
                        colors = SearchBarDefaults.colors(containerColor = OpaqueLight)
                    ) {

                    }

                     */

                }

                Row(modifier = modifier
                    .fillMaxWidth()
                    .height(50.dp),
                    verticalAlignment = Alignment.Top) {

                    Column(
                        modifier = modifier
                            .weight(0.25f)
                            .padding(bottom = 5.dp),
                        verticalArrangement = Arrangement.Bottom,
                        horizontalAlignment = Alignment.CenterHorizontally
                    )  {

                        CategoryBoxPreview(categoryName = "인기 게시물", isSelected = true)

                    }

                    Column(
                        modifier = modifier
                            .weight(0.25f)
                            .padding(bottom = 5.dp),
                        verticalArrangement = Arrangement.Bottom,
                        horizontalAlignment = Alignment.CenterHorizontally
                    )  {

                        CategoryBoxPreview(categoryName = "최신 게시물", isSelected = false)

                    }

                    Column(
                        modifier = modifier
                            .weight(0.25f)
                            .padding(bottom = 5.dp),
                        verticalArrangement = Arrangement.Bottom,
                        horizontalAlignment = Alignment.CenterHorizontally
                    )  {

                        CategoryBoxPreview(categoryName = "굿생 자극", isSelected = false)

                    }

                    Column(
                        modifier = modifier
                            .weight(0.25f)
                            .padding(bottom = 5.dp),
                        verticalArrangement = Arrangement.Bottom,
                        horizontalAlignment = Alignment.CenterHorizontally
                    )  {

                        CategoryBoxPreview(categoryName = "명예의 전당", isSelected = false)

                    }

                }

            }

            BottomSheetScaffold(
                modifier = modifier.fillMaxWidth(),
                sheetPeekHeight = deviceHeight.dp - 250.dp,
                sheetContainerColor = Color.White,
                sheetShape = RoundedCornerShape(
                    bottomStart = 0.dp,
                    bottomEnd = 0.dp,
                    topStart = 12.dp,
                    topEnd = 12.dp
                ),
                sheetContent = {
                    //LatestPostScreen2Preview(deviceHeight = deviceHeight.dp)
                }
            ) {

            }

        }


    }
}


@Preview(showBackground = true)
@Composable
fun LoadingFamousPostScreen(modifier: Modifier = Modifier){
    GodLifeTheme(modifier.background(Color.White)) {
        Box(modifier.fillMaxSize(), contentAlignment = Alignment.Center){
            CircularProgressIndicator()
        }
    }
}


@Composable
fun LatestPostScreen2Preview(modifier: Modifier = Modifier, deviceHeight: Dp){
    Column(
        modifier = modifier
            .fillMaxWidth()
            //.heightIn(min = deviceHeight - 250.dp, max = deviceHeight - 200.dp)
            .fillMaxHeight(deviceHeight.value - 70.dp.value)
    ) {

        Box(modifier.padding(start = 20.dp, end = 20.dp)){
            Text(text = deviceHeight.value.toString(), style = TextStyle(color = GrayWhite, fontSize = 18.sp), textAlign = TextAlign.Center)
        }

        LazyColumn {

            item { Spacer(modifier = modifier.size(20.dp)) }

            items(5) {
                LatestPostListPreview()
                Spacer(modifier.size(20.dp))
            }

        }

    }
}

@Preview
@Composable
fun CategoryBoxPreview(categoryName: String="인기 게시물", modifier: Modifier = Modifier, isSelected: Boolean = true){

    if(isSelected){

        Text(text = categoryName, style = TextStyle(color = Color.White, fontWeight = FontWeight.Bold, fontSize = 15.sp))

        HorizontalDivider(modifier = modifier
            .padding(10.dp)
            .background(Color.White))

    }
    else{
        Text(text = categoryName, style = TextStyle(color = GrayWhite2, fontWeight = FontWeight.Normal, fontSize = 12.sp))

        HorizontalDivider(modifier = modifier
            .padding(12.dp)
            .background(GrayWhite2))
    }

}