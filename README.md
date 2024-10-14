# Snap Store: Android E-commerce Application

## Overview

Snap  Store is a modern Android e-commerce application developed using Kotlin and following the MVVM (Model-View-ViewModel) architecture pattern.
The app provides a seamless shopping experience, allowing users to browse products, manage their cart, and place orders efficiently.

## Features

- **Product Catalog**: Browse a wide range of products with detailed information.
- **Shopping Cart**: Add products to cart and manage quantities.
- **Checkout Process**: Smooth and secure order placement.
- **User Authentication**: Sign up, log in, and guest mode access.
- **Product Search**: Easily find products with the search functionality.
- **Order History**: Track past orders and their status.
- **Favorites**: Save and manage favorite products.
- **User Profiles**: Manage personal information and preferences.
- **Email Notifications**: Receive order confirmations and invoices via email.
- **Customer Support**: In-app support and contact options.

## Technologies Used

- **Programming Language**: Kotlin
- **Architecture**: MVVM (Model-View-ViewModel)
- **Minimum SDK Version**: 24
- **Target SDK Version**: 34 (Android 14)
- **UI Components**: Jetpack Compose
- **Network Library**: Retrofit
- **E-commerce Backend**: Shopify API

## User Roles and Permissions

### Guest Users
- Can browse product catalog
- Can view brands and categories
- Can search for products
- Cannot access cart, favorites, profile, or settings
- Cannot place orders

### Registered Users
- Full access to all app features
- Can add items to favorites and cart
- Can place orders and select delivery address
- Can choose payment methods
- Receive order invoices via email
- Can access profile and settings
- Can contact support

## Setup and Installation

1. Clone the repository:
   ```
   git clone https://github.com/yourusername/snap-store.git
   ```
2. Open the project in Android Studio.
3. Sync the project with Gradle files.
4. Configure your Shopify API credentials in the appropriate configuration file.
5. Build and run the application on an emulator or physical device.

## Configuration

To connect the app with your Shopify store:

1. Create a `shopify.properties` file in the project root.
2. Add your Shopify API credentials:
   ```
   SHOPIFY_DOMAIN=your-store.myshopify.com
   SHOPIFY_API_KEY=your_api_key
   SHOPIFY_API_PASSWORD=your_api_password
   ```
## Contact

For support or queries, please contact us at ahmedhassan@fci.helwan.edu.eg
---
Made with ❤️ by [Snap Team] { Ahmed hassan , omar Gohary , mostafa Gamal }
