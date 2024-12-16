package snow.pierce.Level;

import org.joml.Vector2f;
import snow.pierce.Components.GameObject;
import snow.pierce.Components.SpriteRenderer;
import snow.pierce.Components.Transform;
import snow.pierce.Renderer.SpriteSheet;
import snow.pierce.Renderer.Window;
import snow.pierce.Util.SpriteLayer;

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

                Vector2f startPosition = new Vector2f(chunk.getX(), -chunk.getY()); // negative Y for LWJGL shenanigans
                Vector2f chunkSize = new Vector2f(chunk.getWidth(), chunk.getHeight());
                Vector2f tileSize = new Vector2f(level.getTileWidth(), level.getTileHeight());

                System.out.println("loading chunk " + j + ", with starting position " +
                        startPosition.x + ", " + startPosition.y + ", chunk size: " + chunkSize.x + ", " + chunkSize.y);

                int currentRow = 0;
                int currentCol = 0;
                for (int k = 0; k < chunk.tileArray.length; k++) {

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
                            SpriteLayer.BACKGROUND_LAYER
                    );


                    int tileValue = chunk.tileArray[k];

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
