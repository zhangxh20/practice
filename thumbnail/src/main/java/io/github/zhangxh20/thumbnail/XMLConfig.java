package io.github.zhangxh20.thumbnail;

import java.io.InputStream;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class XMLConfig {

    private String fileFrom;

    private String fileTo;

    private int width;

    private int height;

    private int column;

    private int gap;

    private float quality;

    public float getQuality() {
        return quality;
    }

    public void setQuality(float quality) {
        this.quality = quality;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public int getGap() {
        return gap;
    }

    public void setGap(int gap) {
        this.gap = gap;
    }

    public XMLConfig(String xmlFile) {
        load(xmlFile);
    }

    public String getFileFrom() {
        return fileFrom;
    }

    public void setFileFrom(String fileFrom) {
        this.fileFrom = fileFrom;
    }

    public String getFileTo() {
        return fileTo;
    }

    public void setFileTo(String fileTo) {
        this.fileTo = fileTo;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * 载入配置文件
     * @param xmlFile
     */
    private void load(String xmlFile) {
        InputStream in = this.getClass().getClassLoader().getResourceAsStream(xmlFile);
        try {
            Document doc = new SAXReader().read(in);
            Element root = doc.getRootElement();
            fileFrom = root.element("fileFrom").attributeValue("dir");
            fileTo = root.element("fileTo").attributeValue("file");
            Element image = root.element("image");
            width = Integer.parseInt(image.attributeValue("width"));
            height = Integer.parseInt(image.attributeValue("height"));
            gap = Integer.parseInt(image.attributeValue("gap"));
            column = Integer.parseInt(image.attributeValue("column"));
            quality = Float.parseFloat(root.element("compress").attributeValue("quality"));
        } catch (DocumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
