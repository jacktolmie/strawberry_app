package com.example.strawberry_app.ui.theme.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

@Suppress("CheckReturnValue")
public val stack_off: ImageVector
  get() {
    if (_stack_off != null) {
      return _stack_off!!
    }
    _stack_off =
      ImageVector.Builder(
          name = "stack_off",
          defaultWidth = 24.dp,
          defaultHeight = 24.dp,
          viewportWidth = 24f,
          viewportHeight = 24f,
        )
        .apply {
          path(
            fill = SolidColor(Color.Black),
            fillAlpha = 1f,
            stroke = null,
            strokeAlpha = 1f,
            strokeLineWidth = 1f,
            strokeLineCap = StrokeCap.Butt,
            strokeLineJoin = StrokeJoin.Bevel,
            strokeLineMiter = 1f,
            pathFillType = PathFillType.Companion.NonZero,
          ) {
            moveTo(22f, 19.15f)
            lineToRelative(-2f, -2f)
            verticalLineTo(10f)
            horizontalLineTo(12.85f)
            lineToRelative(-2f, -2f)
            horizontalLineTo(20f)
            quadToRelative(0.83f, 0f, 1.41f, 0.59f)
            reflectiveQuadTo(22f, 10f)
            verticalLineToRelative(9.15f)
            close()
            moveTo(14f, 6f)
            verticalLineTo(4f)
            horizontalLineTo(6.85f)
            lineToRelative(-2f, -2f)
            horizontalLineTo(14f)
            quadToRelative(0.83f, 0f, 1.41f, 0.59f)
            reflectiveQuadTo(16f, 4f)
            verticalLineTo(6f)
            horizontalLineTo(14f)
            close()
            moveToRelative(2.43f, 7.57f)
            close()
            moveTo(10f, 20f)
            horizontalLineToRelative(7.15f)
            lineTo(10f, 12.85f)
            verticalLineTo(20f)
            close()
            moveToRelative(10.58f, 3.43f)
            lineTo(19.15f, 22f)
            horizontalLineTo(10f)
            quadTo(9.15f, 22f, 8.58f, 21.43f)
            reflectiveQuadTo(8f, 20f)
            verticalLineTo(10.85f)
            lineToRelative(-4f, -4f)
            verticalLineTo(14f)
            horizontalLineTo(6f)
            verticalLineToRelative(2f)
            horizontalLineTo(4f)
            quadTo(3.15f, 16f, 2.58f, 15.43f)
            reflectiveQuadTo(2f, 14f)
            verticalLineTo(4.85f)
            lineTo(0.58f, 3.42f)
            lineTo(2f, 2f)
            lineTo(22f, 22f)
            lineToRelative(-1.42f, 1.43f)
            close()
            moveToRelative(-7f, -7f)
            close()
          }
        }
        .build()
    return _stack_off!!
  }

private var _stack_off: ImageVector? = null
