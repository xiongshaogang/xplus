package com.xplus.commons.pattern.interpreter.bridge;

/**
 * GIF格式图像：扩充抽象类
 * 
 * @author huzexiong
 */
public class GIFImage extends Image {

  @Override
  public void parseFile(String fileName) {
    // 模拟解析GIF文件并获得一个像素矩阵对象m;
    Matrix m = new Matrix();
    imp.doPaint(m);
    System.out.println(fileName + "，格式为GIF。");
  }

}
