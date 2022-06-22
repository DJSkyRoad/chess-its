package main.gui;

import java.awt.event.KeyEvent;

public class NumericTextField extends TextField {
    public NumericTextField(int content, int x, int y, int width, int height) {
        super(String.valueOf(content), x, y, width, height);
    }

    @Override
    public void onKeyPressed(KeyEvent event) {
        if (this.active) {
            if (Character.isDigit(event.getKeyChar())) this.content += event.getKeyChar();
            else if (event.getKeyChar() == KeyEvent.VK_BACK_SPACE && this.content.length() > 0) this.content = this.content.substring(0, this.content.length() - 1);
            else if (event.getKeyChar() == KeyEvent.VK_ENTER) this.active = false;
        }
    }
}
