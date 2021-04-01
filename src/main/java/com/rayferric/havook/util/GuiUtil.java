package com.rayferric.havook.util;

import net.minecraft.client.gui.FontRenderer;

public class GuiUtil {
	public static void drawLongText(FontRenderer fontRenderer, String text, int x, int y, int maxWidth,
			int maxLineCount) {
		int lineIndex = 0;
		while (!text.isEmpty()) {
			String line = text;
			while (fontRenderer.getStringWidth(line) > maxWidth) {
				int index = line.lastIndexOf(" ");
				if (index < 0)
					line = "";
				else
					line = line.substring(0, index);
			}
			text = text.substring(text.indexOf(line) + line.length());
			line = "\2477" + line.trim();
			if (lineIndex >= maxLineCount - 1 && !text.isEmpty()) {
				fontRenderer.drawString(line + "...", x, y + (lineIndex * fontRenderer.FONT_HEIGHT + 4), 0xffffff);
				return;
			} else
				fontRenderer.drawString(line, x, y + (lineIndex * fontRenderer.FONT_HEIGHT + 4), 0xffffff);
			lineIndex++;
		}
	}

	public static void drawCenteredLongText(FontRenderer fontRenderer, String text, int x, int y, int maxWidth,
			int maxLineCount) {
		int lineIndex = 0;
		while (!text.isEmpty()) {
			String line = text;
			while (fontRenderer.getStringWidth(line) > maxWidth) {
				int index = line.lastIndexOf(" ");
				if (index < 0)
					line = "";
				else
					line = line.substring(0, index);
			}
			text = text.substring(text.indexOf(line) + line.length());
			line = "\2477" + line.trim();
			if (lineIndex >= maxLineCount - 1 && !text.isEmpty()) {
				fontRenderer.drawString(line + "...", x - fontRenderer.getStringWidth(line + "..."),
						y + (lineIndex * fontRenderer.FONT_HEIGHT + 4), 0xffffff);
				return;
			} else
				fontRenderer.drawString(line, x - fontRenderer.getStringWidth(line),
						y + (lineIndex * fontRenderer.FONT_HEIGHT + 4), 0xffffff);
			lineIndex++;
		}
	}
}
