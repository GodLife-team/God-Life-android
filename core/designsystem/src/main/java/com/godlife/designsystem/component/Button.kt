package com.godlife.designsystem.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.godlife.designsystem.theme.GodLifeTheme
import com.godlife.designsystem.theme.OrangeLight
import com.godlife.designsystem.theme.OrangeMain


@Composable
fun GodLifeButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    content: @Composable RowScope.() -> Unit,
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = OrangeMain,
            contentColor = Color.White
        ),
        contentPadding = contentPadding,
        content = content,
        elevation = ButtonDefaults.buttonElevation(3.dp)
    )
}

@Composable
fun GodLifeButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    text: @Composable () -> Unit,
    leadingIcon: @Composable (() -> Unit)? = null,
) {
    GodLifeButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        contentPadding = if (leadingIcon != null) {
            ButtonDefaults.ButtonWithIconContentPadding
        } else {
            ButtonDefaults.ContentPadding
        },

    ) {
        GodLifeButtonContent(
            text = text,
            leadingIcon = leadingIcon,
        )
    }
}

@Composable
fun GodLifeButtonWhiteWrap(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    content: @Composable RowScope.() -> Unit,
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.White,
            contentColor = OrangeMain
        ),
        contentPadding = contentPadding,
        content = content,
        elevation = ButtonDefaults.buttonElevation(3.dp)
    )
}

@Composable
fun GodLifeButtonOrangeWrap(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    showElevation: Boolean = true,
    content: @Composable RowScope.() -> Unit,
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = OrangeLight,
            contentColor = OrangeMain
        ),
        contentPadding = contentPadding,
        content = content,
        elevation = if(showElevation) ButtonDefaults.buttonElevation(3.dp) else ButtonDefaults.buttonElevation(0.dp)
    )
}

@Composable
fun GodLifeButtonWhite(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    text: @Composable () -> Unit,
    leadingIcon: @Composable (() -> Unit)? = null,
) {
    GodLifeButtonWhiteWrap(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        contentPadding = if (leadingIcon != null) {
            ButtonDefaults.ButtonWithIconContentPadding
        } else {
            ButtonDefaults.ContentPadding
        },

        ) {
        GodLifeButtonContent(
            text = text,
            leadingIcon = leadingIcon,
        )
    }
}

@Composable
fun GodLifeButtonOrange(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    showElevation: Boolean = true,
    text: @Composable () -> Unit,
    leadingIcon: @Composable (() -> Unit)? = null,
) {
    GodLifeButtonOrangeWrap(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        showElevation = showElevation,
        contentPadding =
        if (leadingIcon != null) {
            ButtonDefaults.ButtonWithIconContentPadding
        } else {
            ButtonDefaults.ContentPadding
        }
    ) {
        GodLifeButtonContent(
            text = text,
            leadingIcon = leadingIcon,
        )
    }
}

@Composable
private fun GodLifeButtonContent(
    text: @Composable () -> Unit,
    leadingIcon: @Composable (() -> Unit)? = null,
) {
    if (leadingIcon != null) {
        Box(Modifier.sizeIn(maxHeight = ButtonDefaults.IconSize)) {
            leadingIcon()
        }
    }
    Box(
        Modifier
            .padding(
                start = if (leadingIcon != null) {
                    ButtonDefaults.IconSpacing
                } else {
                    0.dp
                },
            ),
    ) {
        text()
    }
}

@Preview(showBackground = true)
@Composable
fun GodLifeButtonPreview(){
    GodLifeTheme {
        Column {

            GodLifeButton(onClick = {},
                text = { Text("Test Button") }
            ) {

            }

            GodLifeButtonWhite(onClick = { /*TODO*/ },
                text = { Text(text = "Test Button")}) {

            }

        }

    }
}