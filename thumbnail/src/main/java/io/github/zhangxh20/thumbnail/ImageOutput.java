package io.github.zhangxh20.thumbnail;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.FileImageOutputStream;

public class ImageOutput {

    private XMLConfig config;
    
    public ImageOutput(XMLConfig config) {
        this.config = config;
    }
    
    /**
     * 输出图片文件
     * @param bufferedImage
     */
    public void writeImage(BufferedImage bufferedImage) {
        Iterator<ImageWriter> it = ImageIO.getImageWritersByFormatName("jpeg");
        while (it.hasNext()) {
            ImageWriter writer = it.next();
            ImageWriteParam param =  writer.getDefaultWriteParam();
            param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            param.setCompressionQuality(config.getQuality());
            try {
                writer.setOutput(new FileImageOutputStream(new File(config.getFileTo())));
                IIOImage image = new IIOImage(bufferedImage, null, null);
                writer.write(null, image, param);
                writer.dispose();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            
        }
    }
}
