package com.example.strawberry_app.screens.devices

fun isSmallDevice(deviceTypesBreakdown: DeviceTypesBreakdown) =
    deviceTypesBreakdown == DeviceTypesBreakdown.SMALL_PHONE_LANDSCAPE ||
    deviceTypesBreakdown == DeviceTypesBreakdown.SMALL_PHONE_PORTRAIT

fun isDeviceTablet(deviceTypesBreakdown: DeviceTypesBreakdown) =
    deviceTypesBreakdown == DeviceTypesBreakdown.TABLET_PORTRAIT ||
    deviceTypesBreakdown == DeviceTypesBreakdown.TABLET_LANDSCAPE

fun isDevicePhone(deviceTypesBreakdown: DeviceTypesBreakdown) =
    deviceTypesBreakdown == DeviceTypesBreakdown.PHONE_LANDSCAPE ||
    deviceTypesBreakdown == DeviceTypesBreakdown.PHONE_PORTRAIT ||
    deviceTypesBreakdown == DeviceTypesBreakdown.SMALL_PHONE_LANDSCAPE ||
    deviceTypesBreakdown == DeviceTypesBreakdown.SMALL_PHONE_PORTRAIT

fun isFoldableOpen(deviceTypesBreakdown: DeviceTypesBreakdown) =
    deviceTypesBreakdown == DeviceTypesBreakdown.FOLDABLE_HALF_OPEN ||
    deviceTypesBreakdown == DeviceTypesBreakdown.FOLDABLE_OPEN_LANDSCAPE ||
    deviceTypesBreakdown == DeviceTypesBreakdown.FOLDABLE_OPEN_PORTRAIT