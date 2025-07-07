package com.zachnr.bookplayfree.designsystem.checkbox

import androidx.compose.material3.CheckboxDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.zachnr.bookplayfree.designsystem.theme.CheckboxDisabledLightGray
import com.zachnr.bookplayfree.designsystem.theme.CheckboxDisabledUncheckedBorder
import com.zachnr.bookplayfree.designsystem.theme.CheckboxUncheckedGray
import com.zachnr.bookplayfree.designsystem.theme.GreenForest

@Composable
fun checkBoxGreenColors() = CheckboxDefaults.colors(
    checkedColor = GreenForest,
    uncheckedColor = CheckboxUncheckedGray,
    checkmarkColor = Color.White,
    disabledCheckedColor = CheckboxDisabledLightGray,
    disabledUncheckedColor = CheckboxDisabledUncheckedBorder,
    disabledIndeterminateColor = CheckboxDisabledLightGray
)
