package vk.polis.espresso;

public class ListItem {
    private String itemText;
    private boolean isDone;

    public ListItem(String itemText) {
        this(itemText, false);
    }

    public ListItem(String itemText, boolean isDone) {
        this.itemText = itemText;
        this.isDone = isDone;
    }

    public String getItemText() {
        return itemText;
    }

    public void setItemText(String itemText) {
        this.itemText = itemText;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }
}
