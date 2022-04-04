package io.polyfrost.oneconfig.hud.interfaces;

import io.polyfrost.oneconfig.renderer.Renderer;

import java.awt.*;

public abstract class BasicHud {
    public double xUnscaled = 0;
    public double yUnscaled = 0;
    public float scale = 1;
    public int paddingX = 5;
    public int paddingY = 5;
    public boolean background = true;
    public boolean rounded = false;
    public BasicHud childLeft;
    public BasicHud childRight;
    public BasicHud childBottom;
    public BasicHud childTop;
    public BasicHud parent;

    public abstract int getWidth(float scale);

    public abstract int getHeight(float scale);

    public abstract void draw(int x, int y, float scale);

    public int getExampleWidth(float scale) {
        return getWidth(scale);
    }

    public int getExampleHeight(float scale) {
        return getHeight(scale);
    }

    public void drawAll(float x, float y, float scale) {
        if (parent == null) drawBackground(x, y, scale);
        draw((int) (x + paddingX * scale / 2f), (int) (y + paddingY * scale / 2f), scale);
        if (childLeft != null)
            childLeft.drawAll((int) (x + paddingX * scale / 2f)  - childRight.getWidth(scale), (int) (y + paddingY * scale / 2f), scale);
        if (childRight != null)
            childRight.drawAll((int) (x + paddingX * scale / 2f) + getWidth(scale), (int) (y + paddingY * scale / 2f), scale);
    }

    public void drawExampleAll(float x, float y, float scale) {
        if (parent == null) drawBackground(x, y, scale);
        drawExample((int) (x + paddingX * scale / 2f), (int) (y + paddingY * scale / 2f), scale);
        if (childLeft != null)
            childLeft.drawExampleAll((int) (x + paddingX * scale / 2f) - childRight.getWidth(scale), (int) (y + paddingY * scale / 2f), scale);
        if (childRight != null)
            childRight.drawExampleAll((int) (x + paddingX * scale / 2f) + getWidth(scale), (int) (y + paddingY * scale / 2f), scale);
    }

    public void drawExample(int x, int y, float scale) {
        draw(x, y, scale);
    }

    private void drawBackground(float x, float y, float scale) {
        Renderer.drawRoundRect((int) x, (int) y,
                (int) (getTotalWidth(scale) + paddingX * scale), (int) (getTotalHeight(scale) + paddingY * scale),
                (int) (2 * scale), new Color(0, 0, 0, 120).getRGB());
    }

    public float getXScaled(int screenWidth) {
        if (xUnscaled <= 0.5) {
            return (int) (screenWidth * xUnscaled);
        }
        return (float) (screenWidth - (1d - xUnscaled) * screenWidth - (getWidth(scale) + paddingX * scale));
    }

    public float getYScaled(int screenHeight) {
        if (yUnscaled <= 0.5) {
            return (int) (screenHeight * yUnscaled);
        }
        return (float) (screenHeight - (1d - yUnscaled) * screenHeight - (getHeight(scale) + paddingY * scale));
    }

    public int getTotalWidth(float scale) {
        int width = getWidth(scale);
        if (childLeft != null) width += childLeft.getWidth(scale);
        if (childRight != null) width += childRight.getWidth(scale);
        return width;
    }

    public int getTotalHeight(float scale) {
        int height = getHeight(scale);
        if (childBottom != null) height += childBottom.getHeight(scale);
        if (childTop != null) height += childTop.getHeight(scale);
        return height;
    }
}