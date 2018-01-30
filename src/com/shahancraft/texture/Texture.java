package com.shahancraft.texture;

import com.shahancraft.world.block.BlockType;
import de.matthiasmann.twl.utils.PNGDecoder;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.*;

public class Texture{
    int textureHandle;
    public static final float GRASS = 0.0f;
    public static final float DIRT = 0.111f;
    public static final float Water = 0.222f;
    public static final float Sand = 0.333f;
    public static final float Stone = 0.444f;
    public Texture(String fileName) {
        try {
            File file = new File("C:\\Users\\Hassan\\Documents\\JShahanCraft\\res\\textures\\" + fileName);
            FileInputStream in = new FileInputStream(file);
            PNGDecoder decoder = new PNGDecoder(in);
            // assuming RGB here but should allow for RGB and RGBA (changing wall.png to RGBA will crash this!)
            ByteBuffer buf = ByteBuffer.allocateDirect(4 * decoder.getWidth() * decoder.getHeight());
            decoder.decode(buf, decoder.getWidth() * 4, PNGDecoder.Format.RGBA);
            buf.flip();

            if (buf == null) {
                System.out.print("Textrue loading failed ");
            }

            textureHandle = glGenTextures();
            glBindTexture(GL_TEXTURE_2D, textureHandle);
            glPixelStorei(GL_UNPACK_ALIGNMENT, 1);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);

            //TR
            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, decoder.getWidth(), decoder.getHeight(), 0,
                    GL_RGBA, GL_UNSIGNED_BYTE, buf);

            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
            in.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public void destroyTexture(){
        glDeleteTextures(textureHandle);
    }
    public void bind(int unit){
        glBindTexture(GL_TEXTURE_2D, textureHandle);
    }

    public static float GetTextureCoord(BlockType type){
        if (type == BlockType.DIRT ){
            return Texture.DIRT;
        }
        if (type == BlockType.GRASS){
            return Texture.GRASS;
        }
        if (type == BlockType.WATER){
            return Texture.Water;
        }
        if (type == BlockType.Sand){
            return Texture.Sand;
        }
        if (type == BlockType.Stone){
            return Texture.Stone;
        }
        return Texture.DIRT;
    }
}