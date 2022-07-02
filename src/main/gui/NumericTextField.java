package main.gui;

public class NumericTextField extends TextField {
    public NumericTextField(int content, int x, int y, int width, int height) {
        super(String.valueOf(content), x, y, width, height);
        this.maxLength = 4;
    }

    @Override
    protected boolean isValidChar(char c) {
        return Character.isDigit(c);
    }
}
