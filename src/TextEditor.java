class TextEditor {

    StringBuilder text;
    int cursor;

    public TextEditor() {
        this.text = new StringBuilder();
        this.cursor = 0;
    }

    public void addText(String t) {
        text.insert(cursor, t);
        cursor += t.length();
    }

    public int deleteText(int k) {
        int delCount = Math.min(k, cursor);
        text.delete(cursor - delCount, cursor);
        cursor -= delCount;
        return delCount;
    }

    public String cursorLeft(int k) {
        cursor = Math.max(0, cursor - k);
        return getCursorText();
    }

    public String cursorRight(int k) {
        cursor = Math.min(cursor + k, text.length());
        return getCursorText();
    }

    private String getCursorText() {
        int start = Math.max(0, cursor - 10);
        return text.substring(start, cursor);
    }
}
