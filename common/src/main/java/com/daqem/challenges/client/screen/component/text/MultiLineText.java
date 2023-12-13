package com.daqem.challenges.client.screen.component.text;

import com.daqem.uilib.client.gui.text.AbstractText;
import net.minecraft.client.StringSplitter;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.locale.Language;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.Style;
import net.minecraft.util.FormattedCharSequence;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class MultiLineText extends AbstractText<MultiLineText> {

    private static final int[] TEST_SPLIT_OFFSETS = new int[]{0, 10, -10, 25, -25};

    public MultiLineText(Font font, Component text, int x, int y) {
        super(font, text, x, y);
    }

    public MultiLineText(Font font, Component text, int x, int y, int width, int height) {
        super(font, text, x, y, width, height);
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float delta) {
        List<FormattedCharSequence> descriptions = Language.getInstance().getVisualOrder(findOptimalLines(getText().copy(), getWidth()));

        for (int i1 = 0; i1 < descriptions.size(); i1++) {
            FormattedCharSequence line = descriptions.get(i1);
            graphics.drawString(getFont(),line, getX(), getY() + i1 * 9, 0xFFAAAAAA, false);
        }
    }

    private List<FormattedText> findOptimalLines(Component component, int i) {
        StringSplitter stringSplitter = getFont().getSplitter();
        List<FormattedText> list = null;
        float f = Float.MAX_VALUE;
        for (int j : TEST_SPLIT_OFFSETS) {
            List<FormattedText> list2 = stringSplitter.splitLines(component, i - j, Style.EMPTY);
            float g = Math.abs(getMaxWidth(stringSplitter, list2) - (float)i);
            if (g <= 10.0f) {
                return list2;
            }
            if (!(g < f)) continue;
            f = g;
            list = list2;
        }
        return list;
    }

    private static float getMaxWidth(StringSplitter stringSplitter, List<FormattedText> lines) {
        Stream<FormattedText> lineStream = lines.stream();
        Objects.requireNonNull(stringSplitter);
        return (float)lineStream.mapToDouble(stringSplitter::stringWidth).max().orElse(0.0);
    }
}
