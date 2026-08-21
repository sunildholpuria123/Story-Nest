package com.sd.storyteller.core.designsystem.component

/**
 * Created by SDHOLPURIA on 07-08-2026.
 */

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.sd.storyteller.core.constants.StoryCategories
import com.sd.storyteller.feature.create.model.StoryCategory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StoryCategoryDropdown(
    value: String,
    items: List<StoryCategory>,
    onSelected: (StoryCategory) -> Unit,
    modifier: Modifier = Modifier,
    label: String? = null
) {

    var expanded by remember {
        mutableStateOf(false)
    }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            expanded = !expanded
        }
    ) {

        OutlinedTextField(
            modifier = modifier
                .menuAnchor()
                .fillMaxWidth(),
            value = value,
            onValueChange = {},
            readOnly = true,
            singleLine = true,
            label = {
                if (label != null) {
                    Text(label)
                }
            },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = expanded
                )
            }
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
            }
        ) {

            items.forEach { item ->

                DropdownMenuItem(
                    text = {
                        Text(item.name)
                    },
                    onClick = {
                        expanded = false
                        onSelected(item)
                    }
                )
            }
        }
    }
}