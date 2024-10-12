package com.example.e_commerce_iti.ui.theme.settings

import BottomSheetContent
import CallableBottomSheet
import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.e_commerce_iti.NetworkErrorContent
import com.example.e_commerce_iti.currentUser
import com.example.e_commerce_iti.ingredientColor1
import com.example.e_commerce_iti.lavender
import com.example.e_commerce_iti.model.apistates.UiState
import com.example.e_commerce_iti.model.local.LocalDataSourceImp
import com.example.e_commerce_iti.model.pojos.customer.CustomerX
import com.example.e_commerce_iti.model.remote.RemoteDataSourceImp
import com.example.e_commerce_iti.model.reposiatory.ReposiatoryImpl
import com.example.e_commerce_iti.navyBlue
import com.example.e_commerce_iti.network.NetworkObserver
import com.example.e_commerce_iti.ui.theme.ECommerceITITheme
import com.example.e_commerce_iti.ui.theme._navigation.Screens
import com.example.e_commerce_iti.ui.theme.viewmodels.currencyviewmodel.CurrencyViewModel
import com.google.accompanist.flowlayout.FlowRow

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun SettingScreen(
    networkObserver: NetworkObserver,
    viewModel: CurrencyViewModel,
    navController: NavController? = null
) {

    val isConnected = networkObserver.isConnected.collectAsState()
    if (isConnected.value) {
        if (currentUser?.email != null) {
            viewModel.getCustomerData(currentUser!!.email)
            viewModel.getCurrency()
            val state = viewModel.userStateData.collectAsState()
            Column(Modifier.fillMaxWidth()) {
                Column(Modifier.padding(top = 15.dp)) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        verticalAlignment = Alignment.CenterVertically // Align items vertically to center
                    ) {
                        IconButton(onClick = { navController?.navigateUp() }) {
                            androidx.compose.material.Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Back"
                            )
                        }
                        Spacer(modifier = Modifier.width(8.dp)) // Add some spacing between the icon and the text
                        Text(
                            text = "Settings",
                            style = MaterialTheme.typography.headlineMedium,
                            color = ingredientColor1,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Spacer(modifier = Modifier.height(40.dp))

                    Box(modifier = Modifier.fillMaxSize()) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp)
                                .verticalScroll(rememberScrollState()),
                            verticalArrangement = Arrangement.spacedBy(40.dp) // Change here
                        ) {
                            when (state.value) {
                                is UiState.Success<CustomerX> -> {
                                    val user = (state.value as UiState.Success<CustomerX>).data
                                    /*   Box(
                                           modifier = Modifier
                                               .fillMaxWidth() // Fill the width of the screen
                                               .padding(top = 20.dp) // Optional: Add some vertical padding
                                       ) {
                                           Text(
                                               modifier = Modifier.align(Alignment.Center), // Center the text
                                               text = "Hello :  ${user.first_name}",
                                               style = MaterialTheme.typography.headlineSmall,
                                               color = navyBlue,
                                               fontWeight = FontWeight.Bold
                                           )
                                       }*/

                                    ItemsSettingScreen("Change User Data") {
                                        navController?.navigate(
                                            Screens.ChangeUserData.route
                                        )
                                    }
                                    /**
                                     *      for about and contact
                                     */
                                    var showBottomSheet by remember { mutableStateOf(false) }
                                    var currentSheetContent by remember {
                                        mutableStateOf(
                                            SheetContent.ABOUT
                                        )
                                    }  // for about and contact
                                    Currencies(viewModel)
                                    ItemsSettingScreen("Contact Us") {
                                        currentSheetContent = SheetContent.CONTACT
                                        showBottomSheet = true
                                    }
                                    ItemsSettingScreen("About Us") {
                                        currentSheetContent =
                                            SheetContent.ABOUT // or SheetContent.CONTACT
                                        showBottomSheet = true
                                    }

                                    // to show the sheets
                                    if (showBottomSheet) {
                                        CallableBottomSheet(
                                            onDismiss = { showBottomSheet = false }
                                        ) {
                                            BottomSheetContent(currentSheetContent)
                                        }
                                    }
                                    /**
                                     *      end
                                     */

                                }

                                else -> {
                                    Box(
                                        modifier = Modifier.fillMaxSize(),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        CircularProgressIndicator()
                                    }
                                }
                            }
                        }
                    }

                }
            }
        }
    } else {
        NetworkErrorContent()
    }


}

@Composable
fun ItemsSettingScreen(text: String, onClick: (() -> Unit)? = null) {
    Card(
        modifier = Modifier
            .padding(horizontal = 18.dp)
            .fillMaxWidth()
            .height(60.dp)
            .clickable { onClick?.invoke() },
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White // Set background color
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 30.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween // This keeps the text and icon on opposite sides
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.bodyLarge,
                color = ingredientColor1,
                fontWeight = FontWeight.Bold
            )
            Icon(
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}


@Composable
fun Currencies(viewModel: CurrencyViewModel) {
    val state = viewModel.currencyStateFlow.collectAsState()

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 18.dp)
            .wrapContentHeight(),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White // Set background color
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Select Currency",
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                color = ingredientColor1
            )

            Spacer(modifier = Modifier.height(8.dp))

            if (state.value is UiState.Success<Pair<String, Float>>) {
                val currency = (state.value as UiState.Success<Pair<String, Float>>).data
                var selectedCurrency by remember { mutableStateOf(currency.first) }

                // Use FlowRow to prevent overflow
                FlowRow(
                    modifier = Modifier
                        .fillMaxWidth(),
                    mainAxisSpacing = 8.dp,
                    crossAxisSpacing = 8.dp // Space between rows
                ) {
                    CurrencyChips("USD", selectedCurrency == "USD") {
                        selectedCurrency = "USD"
                        viewModel.changeCurrency("USD")
                    }
                    CurrencyChips("EUR", selectedCurrency == "EUR") {
                        selectedCurrency = "EUR"
                        viewModel.changeCurrency("EUR")
                    }
                    CurrencyChips("EGP", selectedCurrency == "EGP") {
                        selectedCurrency = "EGP"
                        viewModel.changeCurrency("EGP")
                    }
                    CurrencyChips("SAR", selectedCurrency == "SAR") {
                        selectedCurrency = "SAR"
                        viewModel.changeCurrency("SAR")
                    }
                }
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrencyChips(name: String, isSelected: Boolean, onSelect: () -> Unit) {
    FilterChip(
        selected = isSelected,
        onClick = onSelect,
        label = { Text(text = name, style = MaterialTheme.typography.bodyMedium) },
        modifier = Modifier.padding(4.dp),
        shape = RoundedCornerShape(16.dp),
        colors = FilterChipDefaults.filterChipColors(
            selectedContainerColor = ingredientColor1,
            selectedLabelColor = MaterialTheme.colorScheme.onPrimary,
            containerColor = Color.White,
            labelColor = MaterialTheme.colorScheme.onSurface
        ),
        border = BorderStroke(1.dp, ingredientColor1)
    )
}
