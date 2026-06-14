package com.example.test

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

@Suppress("CheckReturnValue")
public val volume_down: ImageVector
  get() {
    if (_volume_down != null) {
      return _volume_down!!
    }
    _volume_down =
      ImageVector.Builder(
          name = "volume_down",
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
            moveTo(5f, 15f)
            verticalLineTo(9f)
            horizontalLineTo(9f)
            lineTo(14f, 4f)
            verticalLineTo(20f)
            lineTo(9f, 15f)
            horizontalLineTo(5f)
            close()
            moveToRelative(11f, 1f)
            verticalLineTo(7.95f)
            quadToRelative(1.13f, 0.53f, 1.81f, 1.63f)
            reflectiveQuadTo(18.5f, 12f)
            reflectiveQuadToRelative(-0.69f, 2.4f)
            quadTo(17.13f, 15.48f, 16f, 16f)
            close()
            moveTo(12f, 8.85f)
            lineTo(9.85f, 11f)
            horizontalLineTo(7f)
            verticalLineToRelative(2f)
            horizontalLineTo(9.85f)
            lineTo(12f, 15.15f)
            verticalLineTo(8.85f)
            close()
            moveTo(9.5f, 12f)
            close()
          }
        }
        .build()
    return _volume_down!!
  }

private var _volume_down: ImageVector? = null
