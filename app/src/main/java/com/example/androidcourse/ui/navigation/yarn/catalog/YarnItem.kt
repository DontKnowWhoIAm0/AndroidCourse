package com.example.androidcourse.ui.navigation.yarn.catalog

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.androidcourse.R
import com.example.androidcourse.model.Yarn

@Composable
fun YarnItem(
    yarn: Yarn,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth().clickable { onClick() }.padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = yarn.brand, style = MaterialTheme.typography.headlineSmall)

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column {
                    Text(stringResource(R.string.composition_label, yarn.composition))
                    Text(stringResource(R.string.skein_length_label, yarn.skeinLength))
                }
                Column(horizontalAlignment = Alignment.End) {
                    Text(stringResource(R.string.weight_label, yarn.weight))
                    Text(stringResource(R.string.hook_size_label, yarn.hookSize))
                    Text(stringResource(R.string.needle_size_label, yarn.needleSize))
                }
            }
        }
    }
}