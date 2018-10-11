package ezy.ui.view;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.GradientDrawable;

public class RoundDrawable extends GradientDrawable {
    /**
     * 半圆角，stadium 表示半径为 min(height,width) / 2
     */
    private final boolean isStadium;

    private ColorStateList solidColor;
    private ColorStateList strokeColor;
    private int strokeWidth;
    private int strokeDashWidth;
    private int strokeDashGap;
    private final boolean isStateful;

    private RoundDrawable(Builder builder) {
        solidColor = builder.solidColor;

        strokeColor = builder.strokeColor;
        strokeWidth = builder.strokeWidth;
        strokeDashWidth = builder.strokeDashWidth;
        strokeDashGap = builder.strokeDashGap;
        int cornerRadius = builder.cornerRadius;
        float pressedRatio = builder.pressedRatio;

        CornerRadius radiuses = builder.radiuses;
        if (radiuses.isEmpty()) {
            isStadium = cornerRadius == -1;//default value
            setCornerRadius(cornerRadius == -1 ? 0 : cornerRadius);
        } else {
            if (cornerRadius == -1) cornerRadius = 0;
            setCornerRadii(cornerRadius, builder.radiuses);
            isStadium = false;
        }


        isStateful = (solidColor != null && solidColor.isStateful()) || (strokeColor != null && strokeColor.isStateful());

        if (strokeColor != null) {
            setStroke(strokeWidth, strokeColor.getDefaultColor(), strokeDashWidth, strokeDashGap);
        }
        if (solidColor == null) {
            solidColor = ColorStateList.valueOf(Color.TRANSPARENT);
        }
        if (solidColor.isStateful()) {
            setColor(solidColor.getDefaultColor());
        } else if (pressedRatio > 0.0001f) {
            setSolidColors(csl(solidColor.getDefaultColor(), pressedRatio));
        } else {
            setColor(solidColor.getDefaultColor());
        }
    }

    private void setCornerRadii(int cornerRadius, CornerRadius radius) {
        if (cornerRadius != -1) {
            setCornerRadii(radius.toRadii(cornerRadius));
        }
    }

    private ColorStateList csl(int normal, float ratio) {
        int pressed = darker(normal, ratio);
        int[][] states = new int[][]{{android.R.attr.state_pressed}, {}};
        int[] colors = new int[]{pressed, normal};
        return new ColorStateList(states, colors);
    }

    // 明度
    private int darker(int color, float ratio) {
        color = (color >> 24) == 0 ? 0x22808080 : color;
        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv);
        hsv[2] *= ratio;
        return Color.HSVToColor(color >> 24, hsv);
    }

    private void setSolidColors(ColorStateList colors) {
        solidColor = colors;
        setColor(colors.getDefaultColor());
    }

    @Override
    protected void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);
        if (isStadium) {
            RectF rect = new RectF(getBounds());
            setCornerRadius((rect.height() > rect.width() ? rect.width() : rect.height()) / 2);
        }
    }

    private int cSolid, cStroke;

    @Override
    public void setColor(int color) {
        cSolid = color;
        super.setColor(color);
    }

    @Override
    public void setStroke(int width, int color, float dashWidth, float dashGap) {
        super.setStroke(width, color, dashWidth, dashGap);
        cStroke = color;
    }

    @Override
    protected boolean onStateChange(int[] stateSet) {
        boolean update = false;
        if (isStateful) {
            if (solidColor != null) {
                final int newColor = solidColor.getColorForState(stateSet, 0);
                if (cSolid != newColor) {
                    setColor(newColor);
                    update = true;
                }
            }
            if (strokeColor != null) {
                final int newColor = strokeColor.getColorForState(stateSet, 0);
                if (cStroke != newColor) {
                    setStroke(strokeWidth, newColor, strokeDashWidth, strokeDashGap);
                    update = true;
                }
            }
        }
        return update;
    }

    @Override
    public boolean isStateful() {
        return super.isStateful() || isStateful;
    }

    public static class Builder {
        public ColorStateList solidColor;
        public ColorStateList strokeColor;
        public int strokeWidth;
        public int strokeDashWidth;
        public int strokeDashGap;
        public int cornerRadius;
        public float pressedRatio;
        public CornerRadius radiuses;

        public RoundDrawable build() {
            return new RoundDrawable(this);
        }
    }
}
