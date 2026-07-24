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
public val drive_file_rename: ImageVector
  get() {
    if (_drive_file_rename != null) {
      return _drive_file_rename!!
    }
    _drive_file_rename =
      ImageVector.Builder(
          name = "drive_file_rename",
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
            moveTo(11.23f, 17f)
            horizontalLineTo(18f)
            verticalLineTo(15f)
            horizontalLineTo(13.23f)
            lineToRelative(-2f, 2f)
            close()
            moveTo(6f, 17f)
            horizontalLineTo(9.08f)
            lineToRelative(6.5f, -6.5f)
            quadTo(15.8f, 10.27f, 15.9f, 9.99f)
            reflectiveQuadTo(16f, 9.42f)
            reflectiveQuadTo(15.89f, 8.88f)
            reflectiveQuadTo(15.58f, 8.38f)
            lineTo(14.65f, 7.45f)
            quadTo(14.43f, 7.22f, 14.15f, 7.11f)
            reflectiveQuadTo(13.58f, 7f)
            quadTo(13.3f, 7f, 13.03f, 7.1f)
            reflectiveQuadToRelative(-0.5f, 0.33f)
            lineTo(6f, 13.93f)
            verticalLineTo(17f)
            close()
            moveTo(14.5f, 9.42f)
            lineTo(13.58f, 8.5f)
            lineTo(14.5f, 9.42f)
            close()
            moveToRelative(-7f, 6.08f)
            verticalLineTo(14.55f)
            lineToRelative(4.05f, -4.03f)
            lineToRelative(0.47f, 0.45f)
            lineToRelative(0.45f, 0.47f)
            lineTo(8.45f, 15.5f)
            horizontalLineTo(7.5f)
            close()
            moveToRelative(4.53f, -4.53f)
            lineToRelative(0.45f, 0.47f)
            lineTo(11.55f, 10.52f)
            lineToRelative(0.47f, 0.45f)
            close()
            moveTo(5f, 21f)
            quadTo(4.18f, 21f, 3.59f, 20.41f)
            reflectiveQuadTo(3f, 19f)
            verticalLineTo(5f)
            quadTo(3f, 4.17f, 3.59f, 3.59f)
            reflectiveQuadTo(5f, 3f)
            horizontalLineTo(19f)
            quadToRelative(0.83f, 0f, 1.41f, 0.59f)
            reflectiveQuadTo(21f, 5f)
            verticalLineTo(19f)
            quadToRelative(0f, 0.82f, -0.59f, 1.41f)
            reflectiveQuadTo(19f, 21f)
            horizontalLineTo(5f)
            close()
            moveTo(5f, 19f)
            horizontalLineTo(19f)
            verticalLineTo(5f)
            horizontalLineTo(5f)
            verticalLineTo(19f)
            close()
            moveTo(5f, 5f)
            verticalLineTo(19f)
            verticalLineTo(5f)
            close()
          }
        }
        .build()
    return _drive_file_rename!!
  }

private var _drive_file_rename: ImageVector? = null
