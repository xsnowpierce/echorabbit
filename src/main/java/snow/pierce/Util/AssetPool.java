package snow.pierce.Util;

import snow.pierce.Renderer.Shader;
import snow.pierce.Renderer.SpriteSheet;
import snow.pierce.Renderer.Texture;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class AssetPool {

    private static Map<String, Shader> shaders = new HashMap<>();
    private static Map<String, Texture> textures = new HashMap<>();
    private static Map<String, SpriteSheet> spriteSheetMap = new HashMap<>();
    public static String shaderPath = "src/main/resources/shaders/";
    public static String imagesPath = "src/main/resources/images/";
    public static String levelPath = "src/main/resources/levels/";

    public static Shader getShader(String resourceName) {
        File file = new File(resourceName);
        if(shaders.containsKey(file.getAbsolutePath())) {
            return shaders.get(file.getAbsolutePath());
        }

        Shader shader = new Shader(resourceName);
        shader.compile();
        shaders.put(file.getAbsolutePath(), shader);
        return shader;
    }

    public static Texture getTexture(String resourceName) {
        File file = new File(resourceName);
        if(textures.containsKey(file.getAbsolutePath())) {
            return textures.get(file.getAbsolutePath());
        }

        Texture texture = new Texture(resourceName);
        textures.put(file.getAbsolutePath(), texture);
        return texture;
    }

    public static void addSpriteSheet(String resourceName, SpriteSheet spriteSheet){
        File file = new File(resourceName);
        if(!AssetPool.spriteSheetMap.containsKey(file.getAbsolutePath())){
            AssetPool.spriteSheetMap.put(file.getAbsolutePath(), spriteSheet);
        }
    }

    public static SpriteSheet getSpriteSheet(String resourceName){
        File file = new File(resourceName);
        if(!AssetPool.spriteSheetMap.containsKey(file.getAbsolutePath())){
            assert false : "Error: tried to access spritesheet '" + resourceName + "' and it has not been yet created.";
        }
        return AssetPool.spriteSheetMap.getOrDefault(file.getAbsolutePath(), null);
    }

}
