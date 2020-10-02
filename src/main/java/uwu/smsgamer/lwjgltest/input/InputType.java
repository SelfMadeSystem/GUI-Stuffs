package uwu.smsgamer.lwjgltest.input;

import lombok.Getter;
import uwu.smsgamer.lwjgltest.Main;

public class InputType {
    @Getter //isDown()
    private boolean down;
    private boolean released;
    @Getter
    private long pressTime, pressFrames;
    private KeyType[] values;

    public InputType() {
        this.values = new KeyType[0];
    }

    public InputType(KeyType... values) {
        this.values = values;
    }

    public void setKeys(KeyType... values) {
        this.values = values;
    }

    public boolean justPressed() {
        return down && pressFrames < 2;
    }

    public boolean justReleased() {
        return released;
    }

    public boolean getDownRaw() {
        for (KeyType k : values) {
            if (k.isDown()) return down = true;
        }
        return down = false;
    }

    public void update() {
        down = getDownRaw();
        released = !down && pressFrames > 0;
        pressFrames = down ? pressFrames + 1 : 0;
        pressTime = down ? pressTime + Main.deltaTime : 0;
    }
}
