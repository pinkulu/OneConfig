/*
 * This file is part of OneConfig.
 * OneConfig - Next Generation Config Library for Minecraft: Java Edition
 * Copyright (C) 2021, 2022 Polyfrost.
 *   <https://polyfrost.cc> <https://github.com/Polyfrost/>
 *
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 *   OneConfig is licensed under the terms of version 3 of the GNU Lesser
 * General Public License as published by the Free Software Foundation, AND
 * under the Additional Terms Applicable to OneConfig, as published by Polyfrost,
 * either version 1.0 of the Additional Terms, or (at your option) any later
 * version.
 *
 *   This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 *   You should have received a copy of the GNU Lesser General Public
 * License.  If not, see <https://www.gnu.org/licenses/>. You should
 * have also received a copy of the Additional Terms Applicable
 * to OneConfig, as published by Polyfrost. If not, see
 * <https://polyfrost.cc/legal/oneconfig/additional-terms>
 */

package cc.polyfrost.oneconfig.gui.elements.config;

import cc.polyfrost.oneconfig.config.annotations.DualOption;
import cc.polyfrost.oneconfig.gui.animations.*;
import cc.polyfrost.oneconfig.internal.assets.Colors;
import cc.polyfrost.oneconfig.config.elements.BasicOption;
import cc.polyfrost.oneconfig.renderer.RenderManager;
import cc.polyfrost.oneconfig.renderer.font.Fonts;
import cc.polyfrost.oneconfig.utils.InputHandler;

import java.lang.reflect.Field;

public class ConfigDualOption extends BasicOption {
    private final String left, right;
    private Animation posAnimation;

    public ConfigDualOption(Field field, Object parent, String name, String description, String category, String subcategory, int size, String left, String right) {
        super(field, parent, name, description, category, subcategory, size);
        this.left = left;
        this.right = right;
    }

    public static ConfigDualOption create(Field field, Object parent) {
        DualOption dualOption = field.getAnnotation(DualOption.class);
        return new ConfigDualOption(field, parent, dualOption.name(), dualOption.description(), dualOption.category(), dualOption.subcategory(), dualOption.size(), dualOption.left(), dualOption.right());
    }

    @Override
    public int getHeight() {
        return 32;
    }

    @Override
    public void draw(long vg, int x, int y, InputHandler inputHandler) {
        boolean toggled = false;
        try {
            toggled = (boolean) get();
            if (posAnimation == null) posAnimation = new DummyAnimation(toggled ? 356 : 228);
        } catch (IllegalAccessException ignored) {
        }
        if (!isEnabled()) RenderManager.setAlpha(vg, 0.5f);
        boolean hoveredLeft = inputHandler.isAreaHovered(x + 226, y, 128, 32) && isEnabled();
        boolean hoveredRight = inputHandler.isAreaHovered(x + 354, y, 128, 32) && isEnabled();
        RenderManager.drawText(vg, name, x, y + 16, nameColor, 14f, Fonts.MEDIUM);
        RenderManager.drawRoundedRect(vg, x + 226, y, 256, 32, Colors.GRAY_600, 12f);
        RenderManager.drawRoundedRect(vg, x + posAnimation.get(), y + 2, 124, 28, Colors.PRIMARY_600, 10f);
        if (!hoveredLeft && isEnabled()) RenderManager.setAlpha(vg, 0.8f);
        RenderManager.drawText(vg, left, x + 290 - RenderManager.getTextWidth(vg, left, 12f, Fonts.MEDIUM) / 2, y + 17, Colors.WHITE, 12f, Fonts.MEDIUM);
        if (isEnabled()) RenderManager.setAlpha(vg, 1f);
        if (!hoveredRight && isEnabled()) RenderManager.setAlpha(vg, 0.8f);
        RenderManager.drawText(vg, right, x + 418 - RenderManager.getTextWidth(vg, right, 12f, Fonts.MEDIUM) / 2, y + 17, Colors.WHITE, 12f, Fonts.MEDIUM);

        RenderManager.setAlpha(vg, 1);
        if ((hoveredLeft && toggled || hoveredRight && !toggled) && inputHandler.isClicked()) {
            toggled = !toggled;
            posAnimation = new EaseOutExpo(300, 228, 356, !toggled);
            try {
                set(toggled);
            } catch (IllegalAccessException e) {
                System.err.println("failed to write config value: class=" + this + " fieldWatching=" + field + " valueWrite=" + toggled);
                e.printStackTrace();
            }
        }
    }
}
