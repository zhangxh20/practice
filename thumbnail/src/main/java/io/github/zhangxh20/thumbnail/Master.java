package io.github.zhangxh20.thumbnail;

import java.awt.image.BufferedImage;

public class Master {

    private ImageInput input;

    private ImageOutput output;

    public static Master create(String xmlFile) {
        Master master = new Master();
        XMLConfig config = new XMLConfig(xmlFile);
        ImageInput input = new ImageInput(config);
        ImageOutput output = new ImageOutput(config);
        master.setInput(input);
        master.setOutput(output);
        return master;
    }

    public void generate() {
        try {
            // 创建图像
            BufferedImage image = input.createImage();
            // 输出图像文件
            output.writeImage(image);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setInput(ImageInput input) {
        this.input = input;
    }

    public void setOutput(ImageOutput output) {
        this.output = output;
    }

}
