package ezy.ui.view;

public class CornerRadius {
    public float topLeft, topRight, bottomLeft, bottomRight;

    float[] toRadii(float defaultRadius) {
        float[] radiuses = new float[]{
                topLeft, topLeft,
                topRight, topRight,
                bottomRight, bottomRight,
                bottomLeft, bottomLeft
        };
        for (int i = 0; i < radiuses.length; i++) {
            if (radiuses[i] == -1) radiuses[i] = defaultRadius;
        }
        return radiuses;
    }

    boolean isEmpty() {
        return topLeft == -1 && topRight == -1 && bottomLeft == -1 && bottomRight == -1;
    }
}
