/*
 * Copyright (c) 2009 Kathryn Huxtable and Kenneth Orr.
 *
 * This file is part of the Aqvavit Pluggable Look and Feel.
 *
 * Aqvavit is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of
 * the License, or (at your option) any later version.

 * Aqvavit is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with Aqvavit.  If not, see
 *     <http://www.gnu.org/licenses/>.
 * 
 * $Id: AqvavitLookAndFeel.java 80 2009-09-14 17:47:06Z kathryn@kathrynhuxtable.org $
 */
package com.seaglass.util;

import java.awt.Color;
import java.awt.Component;
import java.awt.GradientPaint;
import java.awt.Graphics2D;

import com.seaglass.painter.Painter;

public class MacPainterFactory {

    // iApp Header painters.
    // //////////////////////////////////////////////////////////////////////

    public static Painter<Component> createIAppUnpressedUnselectedHeaderPainter() {
        return new Painter<Component>() {

            private Color TOP_GRADIENT_COLOR    = new Color(0xdbdbdb);
            private Color BOTTOM_GRADIENT_COLOR = new Color(0xbbbbbb);

            public void paint(Graphics2D graphics2D, Component component, int width, int height) {
                paintLeopardGradientSelection(TOP_GRADIENT_COLOR, BOTTOM_GRADIENT_COLOR, TOP_GRADIENT_COLOR, BOTTOM_GRADIENT_COLOR,
                    graphics2D, width, height);
            }
        };
    }

    public static Painter<Component> createIAppPressedUnselectedHeaderPainter() {
        return new Painter<Component>() {

            private Color TOP_GRADIENT_COLOR    = new Color(0xc4c4c4);
            private Color BOTTOM_GRADIENT_COLOR = new Color(0x959595);

            public void paint(Graphics2D graphics2D, Component component, int width, int height) {
                paintLeopardGradientSelection(TOP_GRADIENT_COLOR, BOTTOM_GRADIENT_COLOR, TOP_GRADIENT_COLOR, BOTTOM_GRADIENT_COLOR,
                    graphics2D, width, height);
            }
        };
    }

    public static Painter<Component> createIAppUnpressedSelectedHeaderPainter() {
        return new Painter<Component>() {

            private Color TOP_GRADIENT_COLOR    = new Color(0xc2cfdd);
            private Color BOTTOM_GRADIENT_COLOR = new Color(0x7d93b2);

            public void paint(Graphics2D graphics2D, Component component, int width, int height) {
                paintLeopardGradientSelection(TOP_GRADIENT_COLOR, MacColorUtils.LEOPARD_BORDER_COLOR, TOP_GRADIENT_COLOR,
                    BOTTOM_GRADIENT_COLOR, graphics2D, width, height);
            }
        };
    }

    public static Painter<Component> createIAppPressedSelectedHeaderPainter() {
        return new Painter<Component>() {

            private Color TOP_GRADIENT_COLOR    = new Color(0xa6b7cb);
            private Color BOTTOM_GRADIENT_COLOR = new Color(0x536b90);

            public void paint(Graphics2D graphics2D, Component component, int width, int height) {
                paintLeopardGradientSelection(TOP_GRADIENT_COLOR, MacColorUtils.LEOPARD_BORDER_COLOR, TOP_GRADIENT_COLOR,
                    BOTTOM_GRADIENT_COLOR, graphics2D, width, height);
            }
        };
    }

    // Selection painters.
    // ////////////////////////////////////////////////////////////////////////

    /**
     * Paints a graident with a single pixel border at the top of the component
     * and a single pixel border at the bottom.
     * 
     * @param topLineColor
     *            the color of the top line border.
     * @param bottomLineColor
     *            the color of the bottom line border.
     * @param topGradientColor
     *            the top color to use in creating the gradient paint.
     * @param bottomGradientColor
     *            the bottom color to use in creating the gradient paint.
     * @param graphics2D
     *            the {@link Graphics2D} to render into. This graphics context
     *            need not be restore state upon completion.
     * @param width
     *            the width of the component to render into.
     * @param height
     *            the height of the component to render into.
     */
    private static void paintLeopardGradientSelection(Color topLineColor, Color bottomLineColor, Color topGradientColor,
        Color bottomGradientColor, Graphics2D graphics2D, int width, int height) {
        // create the paint - start the gradient one pixel from the top
        // of the component and finish the gradient one pixel from the
        // bottom.
        GradientPaint paint = new GradientPaint(0, 1, topGradientColor, 0, height - 2, bottomGradientColor);
        // install the paint and fill a rectangle with it.
        graphics2D.setPaint(paint);
        graphics2D.fillRect(0, 0, width, height);
        // set the graphics color and draw a line across the top of the
        // component.
        graphics2D.setColor(topLineColor);
        graphics2D.drawLine(0, 0, width, 0);
        // set the graphics color and draw a line across the bottom of the
        // component.
        graphics2D.setColor(bottomLineColor);
        graphics2D.drawLine(0, height - 1, width, height - 1);
    }

    public static Painter<Component> createTitleBarPainter() {
        return new Painter<Component>() {

            // Use Snow Leopard colors because it has the same Gamma correction
            // as non-Mac systems.
            private Color ACTIVE_TOP_GRADIENT_COLOR      = new Color(0xd0d0d0);
            private Color ACTIVE_BOTTOM_GRADIENT_COLOR   = new Color(0xc4c4c4);
            private Color INACTIVE_TOP_GRADIENT_COLOR    = new Color(0xececec);
            private Color INACTIVE_BOTTOM_GRADIENT_COLOR = new Color(0xe7e7e7);

            public void paint(Graphics2D graphics2D, Component component, int width, int height) {
                boolean containedInActiveWindow = WindowUtils.isParentWindowFocused(component);

                // We'll only paint this on a non-Mac.
                Color topColor = containedInActiveWindow ? ACTIVE_TOP_GRADIENT_COLOR : INACTIVE_TOP_GRADIENT_COLOR;
                Color bottomColor = containedInActiveWindow ? ACTIVE_BOTTOM_GRADIENT_COLOR : INACTIVE_BOTTOM_GRADIENT_COLOR;

                GradientPaint paint = new GradientPaint(0, 1, topColor, 0, height, bottomColor);
                graphics2D.setPaint(paint);
                graphics2D.fillRect(0, 0, width, height);
            }
        };
    }

    /**
     * Creates a {@link Painter} that paints a Mac unified tool bar gradient.
     * 
     * @return a {@code Painter} that paints a Mac unified tool bar gradient.
     */
    public static Painter<Component> createTexturedWindowWorkaroundPainter() {
        final boolean IS_MAC_OSX = PlatformUtils.isMac();

        return new Painter<Component>() {

            // For non-Mac use Snow Leopard colors because it has the same Gamma
            // correction.
            private Color ACTIVE_TOP_GRADIENT_COLOR      = IS_MAC_OSX ? new Color(0xbcbcbc) : new Color(0xc4c4c4);
            private Color ACTIVE_BOTTOM_GRADIENT_COLOR   = IS_MAC_OSX ? new Color(0x9a9a9a) : new Color(0xb2b2b2);
            private Color INACTIVE_TOP_GRADIENT_COLOR    = IS_MAC_OSX ? new Color(0xe4e4e4) : new Color(0xe7e7e7);
            private Color INACTIVE_BOTTOM_GRADIENT_COLOR = IS_MAC_OSX ? new Color(0xd1d1d1) : new Color(0xdfdfdf);

            public void paint(Graphics2D graphics2D, Component component, int width, int height) {
                boolean containedInActiveWindow = WindowUtils.isParentWindowFocused(component);

                Color topColor = containedInActiveWindow ? ACTIVE_TOP_GRADIENT_COLOR : INACTIVE_TOP_GRADIENT_COLOR;
                Color bottomColor = containedInActiveWindow ? ACTIVE_BOTTOM_GRADIENT_COLOR : INACTIVE_BOTTOM_GRADIENT_COLOR;

                GradientPaint paint = new GradientPaint(0, 1, topColor, 0, height, bottomColor);
                graphics2D.setPaint(paint);
                graphics2D.fillRect(0, 0, width, height);
            }
        };
    }
}
