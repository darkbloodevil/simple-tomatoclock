package singleton;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;

public class Assets {
    private static Texture PLAYER_CHARACTER;

    private static Texture BACKGROUND;

    public static void load(){

    }
    public static Texture get_character() {
        if (PLAYER_CHARACTER == null) {
            Pixmap original_cell = new Pixmap(Gdx.files.internal("player_character.png"));
            Pixmap pixmap_cell = new Pixmap(50, 50,
                    original_cell.getFormat());
            pixmap_cell.drawPixmap(original_cell,
                    0, 0, original_cell.getWidth(), original_cell.getHeight(),
                    0, 0,
                    pixmap_cell.getWidth(), pixmap_cell.getHeight()
            );
            original_cell.dispose();
            PLAYER_CHARACTER = new Texture(pixmap_cell);
        }
        return PLAYER_CHARACTER;
    }
    public static Texture get_background() {
        if (BACKGROUND == null) {
            Pixmap original_background = new Pixmap(Gdx.files.internal("background.jpg"));
            Pixmap pixmap_background = new Pixmap(Settings.SCREEN_W, Settings.SCREEN_H,
                    original_background.getFormat());
            pixmap_background.drawPixmap(original_background,
                    0, 0, original_background.getWidth(), original_background.getHeight(),
                    0, 0,
                    pixmap_background.getWidth(), pixmap_background.getHeight()
            );
            original_background.dispose();
            BACKGROUND = new Texture(pixmap_background);
        }
        return BACKGROUND;
    }
}
