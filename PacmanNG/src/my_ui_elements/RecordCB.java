package my_ui_elements;

import base.Game;
import my_base.MyContent;
import ui_elements.GameCheckbox;

public class RecordCB extends GameCheckbox{
    private MyContent content;

    public RecordCB(String id, String name, int posX, int posY, int width, int height, boolean isSelected) {
        super(id, name, posX, posY, width, height, isSelected);
        this.content = (MyContent) Game.Content();
    }

    @Override
    public void action() {
        super.action();
        if (isSelected()) {
            content.historyRecorder().startRecording();
        } else {
            content.historyRecorder().stopRecording();
        }
    }
}
