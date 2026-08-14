package com.example.strawberry_app.screens.devices

fun getDeviceType(deviceType: DeviceTypesBreakdown) = when (deviceType) {
        DeviceTypesBreakdown.PHONE_LANDSCAPE,
        DeviceTypesBreakdown.PHONE_PORTRAIT,
        DeviceTypesBreakdown.SMALL_PHONE_LANDSCAPE,
        DeviceTypesBreakdown.SMALL_PHONE_PORTRAIT -> DeviceTypes.PHONE

        DeviceTypesBreakdown.TABLET_LANDSCAPE,
        DeviceTypesBreakdown.TABLET_PORTRAIT -> DeviceTypes.TABLET

        DeviceTypesBreakdown.FOLDABLE_CLOSED,
        DeviceTypesBreakdown.FOLDABLE_HALF_OPEN,
        DeviceTypesBreakdown.FOLDABLE_OPEN_LANDSCAPE,
        DeviceTypesBreakdown.FOLDABLE_OPEN_PORTRAIT -> DeviceTypes.FOLDABLE
    }