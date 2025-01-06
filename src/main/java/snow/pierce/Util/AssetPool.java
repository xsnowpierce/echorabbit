package snow.pierce.Util;

import snow.pierce.Renderer.Shader;
import snow.pierce.Renderer.SpriteSheet;
import snow.pierce.Renderer.Texture;
import snow.pierce.Sound.Sound;

import java.io.File;
import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class AssetPool {

    private static final Map<String, Shader> shaders = new HashMap<>();
    private static final Map<String, Texture> textures = new HashMap<>();
    private static final Map<String, SpriteSheet> spriteSheetMap = new HashMap<>();
    private static final Map<String, Sound> sounds = new HashMap<>();

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
        assert AssetPool.spriteSheetMap.containsKey(file.getAbsolutePath()) : "Error: tried to access spritesheet '" + resourceName + "' and it has not been yet created.";
        return AssetPool.spriteSheetMap.getOrDefault(file.getAbsolutePath(), null);
    }

    public static Collection<Sound> getAllSounds() {
        return sounds.values();
    }

    public static Sound addSound(String audioFileName, boolean loop) {
        URL resource = AssetPool.class.getResource(getSoundPath() + audioFileName);

        if (resource == null) {
            assert false : "Audio file '" + audioFileName + "' not found.";
            return null;
        }

        File file = new File(resource.getFile());

        if (sounds.containsKey(file.getAbsolutePath())) {
            return sounds.get(file.getAbsolutePath());
        } else {
            Sound sound = new Sound(file.getAbsolutePath(), loop);
            sounds.put(file.getAbsolutePath(), sound);
            System.out.println("Added sound '" + file.getAbsolutePath() + "'.");
            return sound;
        }
    }

    public static Sound getSound(String audioFileName) {

        URL resource = AssetPool.class.getResource(getSoundPath() + audioFileName);

        if (resource == null) {
            assert false : "Audio file '" + audioFileName + "' not found.";
            return null;
        }

        File file = new File(resource.getFile());

        if (sounds.containsKey(file.getAbsolutePath())) {
            return sounds.get(file.getAbsolutePath());
        } else assert false : "Sound file not added '" + audioFileName + "'.";

        return null;
    }

    public static String getImagesPath() {
        return "/images/";
    }

    public static String getLevelPath() {
        return "/levels/";
    }

    public static String getShaderPath() {
        return "/shaders/";
    }

    public static String getSoundPath() {
        return "/sound/";
    }

}
