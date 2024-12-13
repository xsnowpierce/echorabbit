package snow.pierce.Level;

import org.joml.Vector2f;
import snow.pierce.Components.GameObject;
import snow.pierce.Components.SpriteRenderer;
import snow.pierce.Components.Transform;
import snow.pierce.Renderer.SpriteSheet;
import snow.pierce.Renderer.Window;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class LevelLoader {

    static String levelName;
    static boolean hasLevelLoaded;
    static List<GameObject> tiles = new ArrayList<>();

    public static void LoadLevel(String filePath, SpriteSheet spriteSheet) {

        if(hasLevelLoaded){
            UnloadLevel();
        }

        Level level = new Level(filePath);

        System.out.println("Level loaded. has " + level.getLayers().length + " layers");

        for (int i = 0; i < level.getLayers().length; i++) {
            Layer layer = level.getLayers()[i];
            System.out.println("loading layer " + i);
            for (int j = 0; j < layer.getChunks().length; j++) {

                Chunk chunk = layer.getChunks()[j];

                Vector2f startPosition = new Vector2f(chunk.getX(), chunk.getY());
                Vector2f tileSize = new Vector2f(level.getTilewidth(), level.getTileheight());

                System.out.println("loading chunk " + j + ", with starting position " + startPosition.x + ", " + startPosition.y);

                int currentRow = 0;
                int currentCol = 0;
                for (int k = 0; k < chunk.getData().size(); k++) {

                    int flippedRow = chunk.getHeight() - 1 - currentRow;

                    GameObject tile = new GameObject(
                            "tile",
                            new Transform(
                                    new Vector2f(
                                            currentCol * tileSize.x + (startPosition.x * tileSize.x),
                                            flippedRow * tileSize.y + (startPosition.y * tileSize.y)
                                    ),
                                    tileSize
                            ),
                            -1
                    );


                    int tileValue = ((BigDecimal) chunk.getData().get(k)).intValue();

                    tile.addComponent(new SpriteRenderer(spriteSheet.GetSprite(tileValue - 1)));
                    Window.getScene().addGameObjectToScene(tile);

                    currentCol ++;

                    if(currentCol > chunk.getWidth() - 1){
                        currentCol = 0;
                        currentRow ++;
                    }
                }
            }
        }

        hasLevelLoaded = true;
        levelName = level.getType();
    }

    public static void UnloadLevel(){

        for(GameObject object : tiles){
            object.removeComponent(SpriteRenderer.class);
            Window.getScene().removeGameObjectFromScene(object);
        }

        hasLevelLoaded = false;
        levelName = "";
    }
}
