package com.example.compose_mvvm_sample.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.example.compose_mvvm_sample.R
import com.example.compose_mvvm_sample.data.model.Repo
import com.example.compose_mvvm_sample.ui.theme.ComposeMVVMSampleTheme

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchScreen(
    uiState: ReposUiState,
    modifier: Modifier = Modifier,
    searchBtnOnClick: (String) -> Unit
) {
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
            when (uiState.state) {
                UiState.SUCCESS -> {
                    LazyColumn {
                        items(uiState.repos) {
                            RepoItem(repo = it, modifier = modifier)
                        }
                    }
                }

                UiState.LOADING -> {
                    Text(
                        text = "로딩 중...",
                        style = TextStyle(
                            fontSize = 24.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                }

                UiState.ERROR -> {
                    Text(
                        text = uiState.errorMessage,
                        style = TextStyle(
                            fontSize = 24.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                }

                UiState.NONE -> {
                    Text(
                        text = "상단 검색창을 이용해 검색하세요",
                        style = TextStyle(
                            fontSize = 24.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    )
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
        SearchScreen(uiState = ReposUiState(), searchBtnOnClick = { })
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