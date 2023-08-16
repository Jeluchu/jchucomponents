/*
 *
 *  Copyright 2018-2023 by Jeluchu <jelu@jeluchu.com>
 *
 */

package com.jeluchu.jchucomponents.ui.composables.progress

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.jeluchu.jchucomponents.ktx.colors.toColorFilter
import com.jeluchu.jchucomponents.ktx.compose.toImageVector
import com.jeluchu.jchucomponents.ktx.compose.toPainter
import com.jeluchu.jchucomponents.ktx.strings.empty
import com.jeluchu.jchucomponents.ui.R
import com.jeluchu.jchucomponents.ui.composables.column.ScrollableColumn
import com.jeluchu.jchucomponents.ui.extensions.modifier.cornerRadius
import com.jeluchu.jchucomponents.ui.runtime.remember.rememberMutableStateOf
import com.jeluchu.jchucomponents.ui.themes.artichoke
import com.jeluchu.jchucomponents.ui.themes.cosmicLatte

/**
 *
 * [LinearProgressbar]
 *
 * @param icon [ImageVector] the value we want in case the Bitmap is null,
 * by default it will be an empty one
 * @param enabled [Boolean] Enables or disables the content, showing a colour,
 * indicating that it is not active
 * @param number [Float] The current progress number
 * @param maxNumber [Float] The maximum amount of progress that can be achieved
 * @param indicatorHeight [Dp] Progress bar height
 * @param linearProgressCustom [LinearProgressCustom] Progress bar elements and icon
 * customisations
 * @param linearProgressCounter [LinearProgressCounter] Counter customisations
 * @param animationDuration [Int] Duration of the initial animation
 * @param animationDelay [Int] Time delay in the initial animation duration
 * @param style [TextStyle] Style to be displayed in the counter text
 *
 */
@Composable
fun LinearProgressbar(
    icon: ImageVector,
    enabled: Boolean = true,
    number: Float = 1000f,
    maxNumber: Float = 1000f,
    indicatorHeight: Dp = 15.dp,
    linearProgressCustom: LinearProgressCustom = LinearProgressCustom(),
    linearProgressCounter: LinearProgressCounter = LinearProgressCounter(),
    animationDuration: Int = 1000,
    animationDelay: Int = 0,
    style: TextStyle = LocalTextStyle.current
) {
    val checkMaxValue = if (number > maxNumber) maxNumber else number
    val numberTimes by rememberMutableStateOf(key1 = checkMaxValue, value = checkMaxValue)
    val animateNumber by animateFloatAsState(
        targetValue =  if (numberTimes > maxNumber) maxNumber else numberTimes,
        animationSpec = tween(
            durationMillis = animationDuration,
            delayMillis = animationDelay
        )
    )

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        Icon(
            modifier = Modifier
                .size(40.dp)
                .padding(start = 10.dp),
            imageVector = icon,
            tint = when {
                !enabled -> linearProgressCounter.disabledIndicator
                numberTimes != maxNumber -> linearProgressCustom.iconTint
                else -> linearProgressCustom.foregroundIndicatorComplete
            },
            contentDescription = String.empty()
        )

        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(indicatorHeight)
                .weight(6f)

        ) {
            drawLine(
                color = if (enabled) linearProgressCustom.backgroundIndicator
                else linearProgressCustom.disabledIndicator,
                cap = StrokeCap.Round,
                strokeWidth = size.height,
                start = Offset(x = 0f, y = center.y),
                end = Offset(x = size.width, y = center.y)
            )

            if (enabled) {
                val progress = (animateNumber / maxNumber) * size.width
                if (animateNumber != 0f)
                    drawLine(
                        color = if (numberTimes != maxNumber) linearProgressCustom.foregroundIndicator
                        else linearProgressCustom.foregroundIndicatorComplete,
                        cap = StrokeCap.Round,
                        strokeWidth = size.height,
                        start = Offset(x = 0f, y = center.y),
                        end = Offset(x = progress, y = center.y)
                    )
            }
        }

        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .weight(2.5f)
                .padding(end = 10.dp),
            shape = linearProgressCounter.shape.cornerRadius(),
            color = if (enabled) linearProgressCounter.background
            else linearProgressCounter.disabledIndicator,
            contentColor = linearProgressCounter.content
        ) {
            Text(
                modifier = Modifier.padding(2.dp),
                text = if (enabled) "${numberTimes.toInt()}/${maxNumber.toInt()}" else "- / -",
                style = style,
                textAlign = TextAlign.Center,
            )
        }
    }
}

/**
 *
 * [LinearProgressbar]
 *
 * @param icon [Painter] the value we want in case the Bitmap is null,
 * by default it will be an empty one
 * @param enabled [Boolean] Enables or disables the content, showing a colour,
 * indicating that it is not active
 * @param number [Float] The current progress number
 * @param maxNumber [Float] The maximum amount of progress that can be achieved
 * @param indicatorHeight [Dp] Progress bar height
 * @param linearProgressCustom [LinearProgressCustom] Progress bar elements and icon
 * customisations
 * @param linearProgressCounter [LinearProgressCounter] Counter customisations
 * @param animationDuration [Int] Duration of the initial animation
 * @param animationDelay [Int] Time delay in the initial animation duration
 * @param style [TextStyle] Style to be displayed in the counter text
 *
 */
@Composable
fun LinearProgressbar(
    icon: Painter,
    enabled: Boolean = true,
    number: Float = 1000f,
    maxNumber: Float = 1000f,
    indicatorHeight: Dp = 15.dp,
    linearProgressCustom: LinearProgressCustom = LinearProgressCustom(),
    linearProgressCounter: LinearProgressCounter = LinearProgressCounter(),
    animationDuration: Int = 1000,
    animationDelay: Int = 0,
    style: TextStyle = LocalTextStyle.current
) {
    val checkMaxValue = if (number > maxNumber) maxNumber else number
    val numberTimes by rememberMutableStateOf(key1 = checkMaxValue, value = checkMaxValue)
    val animateNumber by animateFloatAsState(
        targetValue =  if (numberTimes > maxNumber) maxNumber else numberTimes,
        animationSpec = tween(
            durationMillis = animationDuration,
            delayMillis = animationDelay
        )
    )

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        Image(
            modifier = Modifier
                .size(40.dp)
                .padding(start = 10.dp),
            painter = icon,
            colorFilter = when {
                !enabled -> linearProgressCounter.disabledIndicator
                numberTimes != maxNumber -> linearProgressCustom.iconTint
                else -> linearProgressCustom.foregroundIndicatorComplete
            }.toColorFilter(),
            contentDescription = null
        )

        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(indicatorHeight)
                .weight(6f)

        ) {
            drawLine(
                color = if (enabled) linearProgressCustom.backgroundIndicator
                else linearProgressCustom.disabledIndicator,
                cap = StrokeCap.Round,
                strokeWidth = size.height,
                start = Offset(x = 0f, y = center.y),
                end = Offset(x = size.width, y = center.y)
            )

            if (enabled) {
                val progress = (animateNumber / maxNumber) * size.width
                if (animateNumber != 0f)
                    drawLine(
                        color = if (numberTimes != maxNumber) linearProgressCustom.foregroundIndicator
                        else linearProgressCustom.foregroundIndicatorComplete,
                        cap = StrokeCap.Round,
                        strokeWidth = size.height,
                        start = Offset(x = 0f, y = center.y),
                        end = Offset(x = progress, y = center.y)
                    )
            }
        }

        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .weight(2.5f)
                .padding(end = 10.dp),
            shape = linearProgressCounter.shape.cornerRadius(),
            color = if (enabled) linearProgressCounter.background
            else linearProgressCounter.disabledIndicator,
            contentColor = linearProgressCounter.content
        ) {
            Text(
                modifier = Modifier.padding(2.dp),
                text = if (enabled) "${numberTimes.toInt()}/${maxNumber.toInt()}" else "- / -",
                style = style,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Immutable
class LinearProgressCustom constructor(
    val disabledIndicator: Color = Color.Gray,
    val backgroundIndicator: Color = Color.LightGray.copy(alpha = 0.3f),
    val foregroundIndicator: Color = Color(0xFF35898f),
    val foregroundIndicatorComplete: Color = Color(0xFF7DA88C),
    val iconTint: Color = artichoke
)

@Immutable
class LinearProgressCounter constructor(
    val shape: Int = 10,
    val disabledIndicator: Color = Color.Gray,
    val background: Color = Color(0xFF35898f),
    val content: Color = cosmicLatte
)

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun LinearProgressbarPreview(
    primary: Color = Color(0xFFA9D2B5),
    secondary: Color = Color(0xFF79BA98),
    milky: Color = Color(0xFFF9F8DD)
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "LinearProgressbar",
                        color = milky,
                        fontWeight = FontWeight.ExtraBold
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = secondary
                )
            )
        },
        containerColor = secondary
    ) { contentPadding ->
        ScrollableColumn(
            modifier = Modifier
                .padding(contentPadding)
                .padding(horizontal = 15.dp)
        ) {
            Text(text = "LinearProgressbar with ImageVector")
            Text(text = "Disable")
            LinearProgressbar(
                icon = R.drawable.ic_btn_share.toImageVector(),
                enabled = false,
                number = 0f,
                maxNumber = 1000f
            )

            Text(text = "Enabled")
            LinearProgressbar(
                icon = R.drawable.ic_btn_share.toImageVector(),
                number = 0f,
                maxNumber = 1000f
            )

            Text(text = "When the number is less than the maximum")
            LinearProgressbar(
                icon = R.drawable.ic_btn_share.toImageVector(),
                number = 400f,
                maxNumber = 1000f
            )

            Text(text = "When the number is equal to the maximum")
            LinearProgressbar(
                icon = R.drawable.ic_btn_share.toImageVector(),
                number = 1000f,
                maxNumber = 1000f
            )

            Text(text = "When the number is greater than the maximum")
            LinearProgressbar(
                icon = R.drawable.ic_btn_share.toImageVector(),
                number = 2000f,
                maxNumber = 1000f
            )

            Divider()

            Text(text = "LinearProgressbar with Painter")

            Text(text = "Disable")
            LinearProgressbar(
                icon = R.drawable.ic_deco_jeluchu.toPainter(),
                enabled = false,
                number = 0f,
                maxNumber = 1000f
            )

            Text(text = "Enable")
            LinearProgressbar(
                icon = R.drawable.ic_deco_jeluchu.toPainter(),
                number = 0f,
                maxNumber = 1000f
            )

            Text(text = "When the number is less than the maximum")
            LinearProgressbar(
                icon = R.drawable.ic_deco_jeluchu.toPainter(),
                number = 400f,
                maxNumber = 1000f
            )

            Text(text = "When the number is equal to the maximum")
            LinearProgressbar(
                icon = R.drawable.ic_deco_jeluchu.toPainter(),
                number = 1000f,
                maxNumber = 1000f
            )

            Text(text = "When the number is greater than the maximum")
            LinearProgressbar(
                icon = R.drawable.ic_deco_jeluchu.toPainter(),
                number = 2000f,
                maxNumber = 1000f
            )
        }
    }
}