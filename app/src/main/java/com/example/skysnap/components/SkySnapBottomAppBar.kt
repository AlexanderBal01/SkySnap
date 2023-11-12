package com.example.skysnap.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.skysnap.R

@Composable
fun SkySnapBottomAppBar(
    goHome: () -> Unit,
    goToAppInfo: () -> Unit,
) {
    BottomAppBar(
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary,
        actions = {
            IconButton(onClick = goHome, Modifier.padding(horizontal = 75.dp)) {
                Icon(
                    imageVector = Icons.Filled.Home,
                    contentDescription = stringResource(id = R.string.go_home),
                    modifier = Modifier.fillMaxSize()
                )
            }

            IconButton(onClick = goToAppInfo, Modifier.padding(horizontal = 75.dp)) {
                Icon(
                    imageVector = Icons.Filled.Info,
                    contentDescription = stringResource(id = R.string.go_info),
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    )
}