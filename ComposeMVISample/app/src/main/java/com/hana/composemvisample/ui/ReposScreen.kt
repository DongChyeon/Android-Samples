package com.hana.composemvisample.ui

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.example.compose_mvvm_sample.ui.theme.ComposeMVVMSampleTheme
import com.hana.composemvisample.R
import com.hana.composemvisample.data.model.Repo

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchScreen(
    state: MainState,
    modifier: Modifier = Modifier,
    searchBtnOnClick: (String) -> Unit
) {

    val pagingList = state.reposPaging.collectAsLazyPagingItems()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(all = 8.dp)
    ) {
        val keyboardController = LocalSoftwareKeyboardController.current
        var keyword by remember { mutableStateOf("") }

        OutlinedTextField(
            value = keyword,
            onValueChange = { keyword = it },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                unfocusedBorderColor = MaterialTheme.colors.primary.copy(alpha = ContentAlpha.high)
            ),
            label = { Text("검색창") },
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = {
                searchBtnOnClick(keyword)
                keyboardController?.hide()
                keyword = ""
            }),
            modifier = modifier.fillMaxWidth()
        )

        Spacer(
            modifier = modifier.height(8.dp)
        )

        Box(
            contentAlignment = Alignment.Center,
            modifier = modifier.fillMaxSize()
        ) {
            when {
                state.loading -> CircularProgressIndicator()
                state.error != null -> {
                    Text(
                        text = state.error,
                        style = TextStyle(
                            fontSize = 24.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                }
                pagingList.loadState.refresh is LoadState.Loading -> {
                    CircularProgressIndicator()
                }
            }

            LazyColumn {
                if (pagingList.loadState.refresh is LoadState.Loading) {
                    item {
                        CircularProgressIndicator()
                    }
                } else if (pagingList.itemCount == 0) {
                    item {
                        Text(
                            text = "검색 결과가 없습니다.",
                            style = TextStyle(
                                fontSize = 24.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        )
                    }
                } else {
                    items(
                        count = pagingList.itemCount,
                        key = pagingList.itemKey(),
                        contentType = pagingList.itemContentType()
                    ) { index ->
                        pagingList[index]?.let {
                            RepoItem(repo = it, modifier = modifier)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun RepoItem(
    repo: Repo,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(all = 8.dp)
    ) {
        Text(
            text = repo.fullName,
            style = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        )

        Text(
            text = repo.description ?: "",
            fontSize = 16.sp
        )

        Spacer(
            modifier = modifier.height(4.dp)
        )

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = modifier.fillMaxWidth()
        ) {
            Text(
                text = "language : ${repo.language}",
                fontSize = 16.sp
            )

            Row(
                modifier = modifier
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_star),
                    contentDescription = null
                )

                Text(
                    text = "${repo.stars}",
                    fontSize = 16.sp
                )

                Icon(
                    painter = painterResource(id = R.drawable.ic_git_branch),
                    contentDescription = null
                )

                Text(
                    text = "${repo.forks}",
                    fontSize = 16.sp
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewSearchScreen() {
    ComposeMVVMSampleTheme {
        SearchScreen(state = MainState(), searchBtnOnClick = { })
    }
}

@Preview
@Composable
fun PreviewRepoItem() {
    ComposeMVVMSampleTheme {
        RepoItem(
            repo = Repo(
                id = 1L,
                fullName = "저장소 이름",
                description = "저장소 설명",
                stars = 7,
                forks = 10,
                language = "kotlin"
            )
        )
    }
}