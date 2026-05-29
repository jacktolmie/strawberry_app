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
public val fast_rewind: ImageVector
  get() {
    if (_fast_rewind != null) {
      return _fast_rewind!!
    }
    _fast_rewind =
      ImageVector.Builder(
          name = "fast_rewind",
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
            moveTo(21.5f, 18f)
            lineToRelative(-9f, -6f)
            lineToRelative(9f, -6f)
            verticalLineTo(18f)
            close()
            moveToRelative(-10f, 0f)
            lineToRelative(-9f, -6f)
            lineToRelative(9f, -6f)
            verticalLineTo(18f)
            close()
            moveToRelative(-2f, -6f)
            close()
            moveToRelative(10f, 0f)
            close()
            moveToRelative(-10f, 2.25f)
            verticalLineTo(9.75f)
            lineTo(6.1f, 12f)
            lineToRelative(3.4f, 2.25f)
            close()
            moveToRelative(10f, 0f)
            verticalLineTo(9.75f)
            lineTo(16.1f, 12f)
            lineToRelative(3.4f, 2.25f)
            close()
          }
        }
        .build()
    return _fast_rewind!!
  }

private var _fast_rewind: ImageVector? = null
