package snow.pierce.Util;

import java.awt.*;

public class Colour {

    private Color color;
    private float alpha;

    public Colour(Color color, float alpha) {
        this.color = color;
        this.alpha = alpha;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public float getAlpha() {
        return alpha;
    }

    public void setAlpha(float alpha) {
        this.alpha = alpha;
    }

    public int getRed() {
        return color.getRed();
    }

    public int getGreen() {
        return color.getGreen();
    }

    public int getBlue() {
        return color.getBlue();
    }
}
