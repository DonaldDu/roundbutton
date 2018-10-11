package ezy.ui.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import ezy.library.roundbutton.R;


public final class RoundButton extends android.support.v7.widget.AppCompatTextView {
    public RoundButton(Context context) {
        this(context, null);
    }

    public RoundButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RoundButton);

        float pressedRatio = a.getFloat(R.styleable.RoundButton_btnPressedRatio, 0.80f);
        int cornerRadius = a.getLayoutDimension(R.styleable.RoundButton_btnCornerRadius, 0);
        CornerRadius radiuses = new CornerRadius();
        radiuses.topLeft = a.getLayoutDimension(R.styleable.RoundButton_btnCornerRadiusTopLeft, -1);
        radiuses.topRight = a.getLayoutDimension(R.styleable.RoundButton_btnCornerRadiusTopRight, -1);
        radiuses.bottomLeft = a.getLayoutDimension(R.styleable.RoundButton_btnCornerRadiusBottomLeft, -1);
        radiuses.bottomRight = a.getLayoutDimension(R.styleable.RoundButton_btnCornerRadiusBottomRight, -1);

        ColorStateList solidColor = a.getColorStateList(R.styleable.RoundButton_btnSolidColor);
        ColorStateList strokeColor = a.getColorStateList(R.styleable.RoundButton_btnStrokeColor);
        int strokeWidth = a.getDimensionPixelSize(R.styleable.RoundButton_btnStrokeWidth, 0);
        int strokeDashWidth = a.getDimensionPixelSize(R.styleable.RoundButton_btnStrokeDashWidth, 0);
        int strokeDashGap = a.getDimensionPixelSize(R.styleable.RoundButton_btnStrokeDashGap, 0);
        a.recycle();

        RoundDrawable.Builder builder = new RoundDrawable.Builder();
        builder.pressedRatio = pressedRatio;
        builder.cornerRadius = cornerRadius;
        builder.radiuses = radiuses;
        builder.solidColor = solidColor;
        builder.strokeColor = strokeColor;
        builder.strokeWidth = strokeWidth;
        builder.strokeDashWidth = strokeDashWidth;
        builder.strokeDashGap = strokeDashGap;
        setBackground(builder.build());
    }
}