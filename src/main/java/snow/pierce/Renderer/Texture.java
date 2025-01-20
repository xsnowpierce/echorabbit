package snow.pierce.Renderer;

import org.lwjgl.BufferUtils;
import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryUtil;
import snow.pierce.Util.AssetPool;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.stb.STBImage.stbi_image_free;
import static org.lwjgl.stb.STBImage.stbi_set_flip_vertically_on_load;

public class Texture {

    private final String filepath;
    private final transient int texID;
    private int width, height;

    public Texture() {
        texID = -1;
        width = -1;
        height = -1;
        filepath = "ERROR_TEXTURE";
    }

    public Texture(int width, int height) {
        this.filepath = "GENERATED_TEXTURE";

        // Generate texture on GPU
        texID = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, texID);

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, width, height,
                0, GL_RGB, GL_UNSIGNED_BYTE, 0);
    }

    public Texture(String filepath) {
        this.filepath = filepath;

        System.out.println("Loading texture: " + filepath);

        // Generate texture on GPU
        texID = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, texID);

        // Set texture parameters
        // Repeat image in both directions
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
        // When stretching the image, pixelate
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        // When shrinking an image, pixelate
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

        IntBuffer bufferWidth = BufferUtils.createIntBuffer(1);
        IntBuffer bufferHeight = BufferUtils.createIntBuffer(1);
        IntBuffer channels = BufferUtils.createIntBuffer(1);
        stbi_set_flip_vertically_on_load(true);
        ByteBuffer image = loadImage(filepath, bufferWidth, bufferHeight, channels);

        if (image != null) {
            this.width = bufferWidth.get(0);
            this.height = bufferHeight.get(0);
            if (channels.get(0) == 3) {
                glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, bufferWidth.get(0), bufferHeight.get(0),
                        0, GL_RGB, GL_UNSIGNED_BYTE, image);
            } else if (channels.get(0) == 4) {
                glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, bufferWidth.get(0), bufferHeight.get(0),
                        0, GL_RGBA, GL_UNSIGNED_BYTE, image);
            } else {
                assert false : "Error: (Texture) Unknown number of channels '" + channels.get(0) + "'";
            }
        } else {
            assert false : "Error: (Texture) Could not load image '" + filepath + "'";
        }

        stbi_image_free(image);
    }

    private static ByteBuffer loadImage(String filepath, IntBuffer width, IntBuffer height, IntBuffer channels) {
        try (InputStream is = AssetPool.class.getResourceAsStream(filepath)) {

            if (is == null) {
                throw new RuntimeException("Error: could not find resource '" + filepath + "'.");
            }

            byte[] buffer = is.readAllBytes();
            ByteBuffer byteBuffer = MemoryUtil.memAlloc(buffer.length);
            byteBuffer.put(buffer).flip();  // Ensure buffer is flipped after putting data

            // Optionally flip the image vertically (depending on the format)
            stbi_set_flip_vertically_on_load(true);

            return STBImage.stbi_load_from_memory(byteBuffer, width, height, channels, 0);

        } catch (IOException ioException) {
            throw new RuntimeException("Error loading resource: " + filepath, ioException);
        }
    }

    public void bind() {
        glBindTexture(GL_TEXTURE_2D, texID);
    }

    public void unbind() {
        glBindTexture(GL_TEXTURE_2D, 0);
    }

    public int getImageWidth() {
        return width;
    }

    public int getImageHeight() {
        return height;
    }

    public int getTextureID() {
        return texID;
    }

    public String getFilepath() {
        return filepath;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (!(o instanceof Texture other)) return false;
        return other.getImageHeight() == getImageHeight()
                && other.getImageWidth() == getImageWidth()
                && other.filepath.equals(filepath)
                && other.texID == texID;
    }

}
