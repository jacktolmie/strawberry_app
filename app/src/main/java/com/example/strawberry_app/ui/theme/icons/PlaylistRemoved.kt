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
public val playlist_remove: ImageVector
  get() {
    if (_playlist_remove != null) {
      return _playlist_remove!!
    }
    _playlist_remove =
      ImageVector.Builder(
          name = "playlist_remove",
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
            moveTo(14.4f, 22f)
            lineTo(13f, 20.6f)
            lineTo(15.6f, 18f)
            lineTo(13f, 15.4f)
            lineTo(14.4f, 14f)
            lineTo(17f, 16.6f)
            lineTo(19.6f, 14f)
            lineTo(21f, 15.4f)
            lineTo(18.4f, 18f)
            lineTo(21f, 20.6f)
            lineTo(19.6f, 22f)
            lineTo(17f, 19.4f)
            lineTo(14.4f, 22f)
            close()
            moveTo(3f, 16f)
            verticalLineTo(14f)
            horizontalLineToRelative(7f)
            verticalLineToRelative(2f)
            horizontalLineTo(3f)
            close()
            moveTo(3f, 12f)
            verticalLineTo(10f)
            horizontalLineTo(14f)
            verticalLineToRelative(2f)
            horizontalLineTo(3f)
            close()
            moveTo(3f, 8f)
            verticalLineTo(6f)
            horizontalLineTo(14f)
            verticalLineTo(8f)
            horizontalLineTo(3f)
            close()
          }
        }
        .build()
    return _playlist_remove!!
  }

private var _playlist_remove: ImageVector? = null
