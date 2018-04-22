package io.github.zhangxh20.thumbnail;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Arrays;
import java.util.List;

import javax.imageio.ImageIO;

public class ImageInput {

    private XMLConfig config;
    
    public ImageInput(XMLConfig config) {
        this.config = config;
    }
    
    /**
     * 由各张照片生成BufferedImage对象
     * @return
     * @throws Exception
     */
    public BufferedImage createImage() throws Exception {
        List<File> images = readFromDir(config.getFileFrom());
        // 合成图总的宽度
        int width = config.getWidth() * config.getColumn() + config.getGap() * (config.getColumn() - 1);
        // 总的行数
        int row = (int)Math.ceil(new Integer(images.size()).doubleValue() / config.getColumn());
        // 合成图的高度
        int height = config.getHeight() * row + config.getGap() * (row - 1);
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g = img.getGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);
        
        int i = 0;
        int j = 0;
        for (File file : images) {
            BufferedImage bufImg = ImageIO.read(file);
            // 把每一张图，生成缩略图，放在合成图上
            g.drawImage(bufImg, i * (config.getWidth() + config.getGap()), j * (config.getHeight() + config.getGap()),
                    config.getWidth(),config.getHeight(),null);
            i ++;
            if ( i == config.getColumn()) {
                j ++;
                i = 0;
            }
        }
        g.dispose();
        return img;
    }
    
    /**
     * 从目录读取文件
     * @param dir
     * @return
     */
    private List<File> readFromDir(String dir) {
        File file = new File(dir);
        if (file.isDirectory()) {
            return Arrays.asList(file.listFiles());
        }else {
            return null;
        }
    }
}
