package com.example.strawberry_app.screens.devices

enum class DeviceTypes {
    PHONE,
    TABLET,
    FOLDABLE,
    FOLDABLE_CLOSED
}

enum class DeviceTypesBreakdown {
    SMALL_PHONE_PORTRAIT,
    SMALL_PHONE_LANDSCAPE,
    PHONE_PORTRAIT,
    PHONE_LANDSCAPE,
    TABLET_PORTRAIT,
    TABLET_LANDSCAPE,
    FOLDABLE_CLOSED,       // Acts like a phone
    FOLDABLE_HALF_OPEN,    // Tent/table top mode
    FOLDABLE_OPEN_PORTRAIT,  // Fully open, held portrait
    FOLDABLE_OPEN_LANDSCAPE  // Fully open, held landscape
}