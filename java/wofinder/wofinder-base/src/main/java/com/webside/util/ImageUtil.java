/*
 *    Copyright 2012-2013 The Network Corporation
 */
package com.webside.util;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.itextpdf.text.pdf.PdfStructTreeController.returnType;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

/**
 * 图片压缩工具类 提供的方法中可以设定生成的 缩略图片的大小尺寸、压缩尺寸的比例、图片的质量等
 * 
 * <pre>
 * 	调用示例：
 * resiz(srcImg, tarDir + "car_1_maxLength_11-220px-hui.jpg", 220, 0.7F);
 * </pre>
 * 
 * @project haohui-b2b
 * @author sunhw
 * @create 2012-3-22 下午8:29:01
 */
@SuppressWarnings("restriction")
public class ImageUtil 
{

	/**
	 * * 图片文件读取
	 * 
	 * @param srcImgPath
	 * @return
	 */
	private static BufferedImage InputImage(String srcImgPath) throws RuntimeException 
	{

		BufferedImage srcImage = null;
		FileInputStream in = null;
		
		try 
		{
			// 构造BufferedImage对象
			File file = new File(srcImgPath);
			in = new FileInputStream(file);
			byte[] b = new byte[5];
			in.read(b);
			srcImage = javax.imageio.ImageIO.read(file);
		} 
		catch (IOException e) 
		{
			throw new RuntimeException("读取图片文件出错！", e);
		} 
		finally 
		{
			if (in != null) 
			{
				try {
					in.close();
				} catch (IOException e) {}
			}
		}
		return srcImage;
	}

	/**
	 * * 将图片按照指定的图片尺寸、源图片质量压缩(默认质量为1)
	 * 
	 * @param srcImgPath
	 *            :源图片路径
	 * @param outImgPath
	 *            :输出的压缩图片的路径
	 * @param new_w
	 *            :压缩后的图片宽
	 * @param new_h
	 *            :压缩后的图片高
	 */
	public static void resize(String srcImgPath, String outImgPath, int new_w, int new_h) 
	{
		resize(srcImgPath, outImgPath, new_w, new_h, 1F);
	}

	/**
	 * 将图片按照指定的尺寸比例、源图片质量压缩(默认质量为1)
	 * 
	 * @param srcImgPath
	 *            :源图片路径
	 * @param outImgPath
	 *            :输出的压缩图片的路径
	 * @param ratio
	 *            :压缩后的图片尺寸比例
	 * @param per
	 *            :百分比
	 */
	public static void resize(String srcImgPath, String outImgPath, float ratio) 
	{
		resize(srcImgPath, outImgPath, ratio, 1F);
	}

	/**
	 * 将图片按照指定长或者宽的最大值来压缩图片(默认质量为1)
	 * 
	 * @param srcImgPath
	 *            :源图片路径
	 * @param outImgPath
	 *            :输出的压缩图片的路径
	 * @param maxLength
	 *            :长或者宽的最大值
	 * @param per
	 *            :图片质量
	 */
	public static void resize(String srcImgPath, String outImgPath, int maxLength) 
	{
		resize(srcImgPath, outImgPath, maxLength, 1F);
	}

	/**
	 * * 将图片按照指定的图片尺寸、图片质量压缩
	 * 
	 * @param srcImgPath
	 *            :源图片路径
	 * @param outImgPath
	 *            :输出的压缩图片的路径
	 * @param new_w
	 *            :压缩后的图片宽
	 * @param new_h
	 *            :压缩后的图片高
	 * @param per
	 *            :百分比
	 * @author cevencheng
	 */
	public static void resize(String srcImgPath, String outImgPath, int new_w, int new_h, float per) 
	{
		// 得到图片
		BufferedImage src = InputImage(srcImgPath);
		int old_w = src.getWidth();
		// 得到源图宽
		int old_h = src.getHeight();
		// 得到源图长
		// 根据原图的大小生成空白画布
		BufferedImage tempImg = new BufferedImage(old_w, old_h, BufferedImage.TYPE_INT_RGB);
		// 在新的画布上生成原图的缩略图
		Graphics2D g = tempImg.createGraphics();
		g.setColor(Color.white);
		g.fillRect(0, 0, old_w, old_h);
		g.drawImage(src, 0, 0, old_w, old_h, Color.white, null);
		g.dispose();
		BufferedImage newImg = new BufferedImage(new_w, new_h, BufferedImage.TYPE_INT_RGB);
		newImg.getGraphics().drawImage(tempImg.getScaledInstance(new_w, new_h, Image.SCALE_SMOOTH), 0, 0, null);
		// 调用方法输出图片文件
		outImage(outImgPath, newImg, per);
	}

	/**
	 * * 将图片按照指定的尺寸比例、图片质量压缩
	 * 
	 * @param srcImgPath
	 *            :源图片路径
	 * @param outImgPath
	 *            :输出的压缩图片的路径
	 * @param ratio
	 *            :压缩后的图片尺寸比例
	 * @param per
	 *            :百分比
	 * @author cevencheng
	 */
	public static void resize(String srcImgPath, String outImgPath, float ratio, float per) 
	{
		// 得到图片
		BufferedImage src = InputImage(srcImgPath);
		int old_w = src.getWidth();
		// 得到源图宽
		int old_h = src.getHeight();
		// 得到源图长
		int new_w = 0;
		// 新图的宽
		int new_h = 0;
		// 新图的长
		BufferedImage tempImg = new BufferedImage(old_w, old_h, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = tempImg.createGraphics();
		g.setColor(Color.white);
		// 从原图上取颜色绘制新图g.fillRect(0, 0, old_w, old_h);
		g.drawImage(src, 0, 0, old_w, old_h, Color.white, null);
		g.dispose();
		// 根据图片尺寸压缩比得到新图的尺寸new_w = (int) Math.round(old_w * ratio);
		new_h = (int) Math.round(old_h * ratio);
		BufferedImage newImg = new BufferedImage(new_w, new_h, BufferedImage.TYPE_INT_RGB);
		newImg.getGraphics().drawImage(tempImg.getScaledInstance(new_w, new_h, Image.SCALE_SMOOTH), 0, 0, null);
		// 调用方法输出图片文件OutImage(outImgPath, newImg, per);
	}

	/**
	 * <b> 指定长或者宽的最大值来压缩图片 推荐使用此方法 </b>
	 * 
	 * @param srcImgPath
	 *            :源图片路径
	 * @param outImgPath
	 *            :输出的压缩图片的路径
	 * @param maxLength
	 *            :长或者宽的最大值
	 * @param per
	 *            :图片质量
	 * @author cevencheng
	 */
	public static void resize(String srcImgPath, String outImgPath, int maxLength, float per)
	{
		// 得到图片
		BufferedImage src = InputImage(srcImgPath);
		int old_w = src.getWidth();
		// 得到源图宽
		int old_h = src.getHeight();
		// 得到源图长
		int new_w = 0;
		// 新图的宽
		int new_h = 0;
		// 新图的长
		BufferedImage tempImg = new BufferedImage(old_w, old_h, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = tempImg.createGraphics();
		g.setColor(Color.white);
		// 从原图上取颜色绘制新图
		g.fillRect(0, 0, old_w, old_h);
		g.drawImage(src, 0, 0, old_w, old_h, Color.white, null);
		g.dispose();
		// 根据图片尺寸压缩比得到新图的尺寸
		if (old_w > old_h) {
			// 图片要缩放的比例
			new_w = maxLength;
			new_h = (int) Math.round(old_h * ((float) maxLength / old_w));
		} else {
			new_w = (int) Math.round(old_w * ((float) maxLength / old_h));
			new_h = maxLength;
		}
		BufferedImage newImg = new BufferedImage(new_w, new_h, BufferedImage.TYPE_INT_RGB);
		newImg.getGraphics().drawImage(tempImg.getScaledInstance(new_w, new_h, Image.SCALE_SMOOTH), 0, 0, null);
		// 调用方法输出图片文件
		outImage(outImgPath, newImg, per);
	}

	/**
	 * 将图片压缩成指定宽度， 高度等比例缩放
	 * 
	 * @param srcImgPath
	 * @param outImgPath
	 * @param width
	 * @param per
	 */
	public static void resizeFixedWidth(String srcImgPath, String outImgPath, int width, float per)
	{
		// 得到图片
		BufferedImage src = InputImage(srcImgPath);
		int old_w = src.getWidth();
		// 得到源图宽
		int old_h = src.getHeight();
		// 得到源图长
		int new_w = 0;
		// 新图的宽
		int new_h = 0;
		// 新图的长
		BufferedImage tempImg = new BufferedImage(old_w, old_h,
				BufferedImage.TYPE_INT_RGB);
		Graphics2D g = tempImg.createGraphics();
		g.setColor(Color.white);
		// 从原图上取颜色绘制新图
		g.fillRect(0, 0, old_w, old_h);
		g.drawImage(src, 0, 0, old_w, old_h, Color.white, null);
		g.dispose();
		
		// 根据图片尺寸压缩比得到新图的尺寸
		if (old_w > old_h) 
		{
			// 图片要缩放的比例
			new_w = width;
			new_h = (int) Math.round(old_h * ((float) width / old_w));
		}
		else 
		{
			new_w = (int) Math.round(old_w * ((float) width / old_h));
			new_h = width;
		}
		
		BufferedImage newImg = new BufferedImage(new_w, new_h, BufferedImage.TYPE_INT_RGB);
		newImg.getGraphics().drawImage(tempImg.getScaledInstance(new_w, new_h, Image.SCALE_SMOOTH), 0, 0, null);
		// 调用方法输出图片文件
		outImage(outImgPath, newImg, per);
	}

	/**
	 * * 将图片文件输出到指定的路径，并可设定压缩质量
	 * 
	 * @param outImgPath
	 * @param newImg
	 * @param per
	 * @author cevencheng
	 */
	private static void outImage(String outImgPath, BufferedImage newImg, float per)
	{
		// 判断输出的文件夹路径是否存在，不存在则创建
		File file = new File(outImgPath);
		if (!file.getParentFile().exists()) 
		{
			file.getParentFile().mkdirs();
		}
		// 输出到文件流
		FileOutputStream fos = null;
		try
		{
			fos = new FileOutputStream(outImgPath);
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(fos);
			JPEGEncodeParam jep = JPEGCodec.getDefaultJPEGEncodeParam(newImg);
			// 压缩质量
			jep.setQuality(per, true);
			encoder.encode(newImg, jep);
			fos.close();
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		} 
		finally 
		{
			if (fos != null) 
			{
				try {
					fos.close();
				}
				catch (IOException e) {}
			}
		}
	}

	/**
	 * 图片剪切工具方法
	 * 
	 * @param srcfile
	 *            源图片
	 * @param outfile
	 *            剪切之后的图片
	 * @param x
	 *            剪切顶点 X 坐标
	 * @param y
	 *            剪切顶点 Y 坐标
	 * @param width
	 *            剪切区域宽度
	 * @param height
	 *            剪切区域高度
	 * 
	 * @throws IOException
	 * @author cevencheng
	 */
	public static void cut(String srcfile, String outfile, int x, int y, int width, int height) throws IOException 
	{
		FileInputStream is = null;
		ImageInputStream iis = null;
		
		try 
		{
			// 判断输出的文件夹路径是否存在，不存在则创建
			File file = new File(srcfile);
			if (!file.exists()) 
			{
				return;
			}
			
			// 读取图片文件
			is = new FileInputStream(srcfile);

			/*
			 * 返回包含所有当前已注册 ImageReader 的 Iterator，这些 ImageReader 声称能够解码指定格式。
			 * 参数：formatName - 包含非正式格式名称 .（例如 "jpeg" 或 "tiff"）等 。
			 */
			
			// 获取图片流
			iis = ImageIO.createImageInputStream(is);
			Iterator<ImageReader> iter = ImageIO.getImageReaders(iis);
			if (!iter.hasNext()) {
	            return;
	        }
			
			ImageReader reader = iter.next();

			/*
			 * <p>iis:读取源.true:只向前搜索 </p>.将它标记为 ‘只向前搜索’。
			 * 此设置意味着包含在输入源中的图像将只按顺序读取，可能允许 reader 避免缓存包含与以前已经读取的图像关联的数据的那些输入部分。
			 */
			reader.setInput(iis, true);

			/*
			 * <p>描述如何对流进行解码的类<p>.用于指定如何在输入时从 Java Image I/O
			 * 框架的上下文中的流转换一幅图像或一组图像。用于特定图像格式的插件 将从其 ImageReader 实现的
			 * getDefaultReadParam 方法中返回 ImageReadParam 的实例。
			 */
			ImageReadParam param = reader.getDefaultReadParam();

			/*
			 * 图片裁剪区域。Rectangle 指定了坐标空间中的一个区域，通过 Rectangle 对象
			 * 的左上顶点的坐标（x，y）、宽度和高度可以定义这个区域。
			 */
			Rectangle rect = new Rectangle(x, y, width, height);

			// 提供一个 BufferedImage，将其用作解码像素数据的目标。
			param.setSourceRegion(rect);

			/*
			 * 使用所提供的 ImageReadParam 读取通过索引 imageIndex 指定的对象，并将 它作为一个完整的
			 * BufferedImage 返回。
			 */
			BufferedImage bi = reader.read(0, param);

			// 保存新图片
			ImageIO.write(bi, "jpg", new File(outfile));
		}
		finally 
		{
			if (is != null) {
				is.close();
			}
			if (iis != null) {
				iis.close();
			}
		}
	}
	
	/**
	 * 将头像按比例 剪切压缩
	 * 根据源文件路径 ，剪切完成 会在源文件目录生成 
	 * 后缀带 _133、_70、 _40、_24 的文件
	 * @param srcImgPath
	 * @throws IOException 
	 * @return 
	 * -1 源文件路径不存在
	 * 1   头像裁剪成功。
	 * 2  参数非法
	 */
	public static int resizeCut(String srcImgPath,Map<String, Object> map) throws IOException
	{
		JSONObject data = null;
		if(map!=null && !map.isEmpty() && map.containsKey("avatar_data")){
			Object object = map.get("avatar_data");
			if(object!=null){
				data = JSON.parseObject(object.toString());
			}
		}
		if(data==null || data.isEmpty()){
			return 2;
		}
		
		// 旋转度数
		int rotate = data.getIntValue("rotate");
				
		srcImgPath = srcImgPath.replace("\\", "/");
		
		if(srcImgPath.lastIndexOf("/") < 0) return -1;
		String fileDir  = srcImgPath.substring(0, srcImgPath.lastIndexOf("/"));
		String fileName = srcImgPath.substring(srcImgPath.lastIndexOf("/")+1, srcImgPath.length());
		
		if(!StringUtils.isNotEmpty(fileDir) || !StringUtils.isNotEmpty(fileName)) return -1;
		
		if(fileName.lastIndexOf(".") < 0) return -1;
		String name  = fileName.substring(0, fileName.lastIndexOf("."));
		String fix   = fileName.substring(fileName.lastIndexOf(".")+1, fileName.length());
		
		if(!StringUtils.isNotEmpty(name)) return -1;
		fix = StringUtils.isNotEmpty(fix) ? fix : "png";
		
		if(rotate!=0){
			rotateImage(srcImgPath,fileDir + "/" + name + "_rotate." + fix,rotate);
			srcImgPath = fileDir + "/" + name + "_rotate." + fix;
		}
		// 得到图片
		BufferedImage src = InputImage(srcImgPath);
		// 得到源图宽
		int old_w = src.getWidth();
		// 得到源图长
		int old_h = src.getHeight();
		
		// 新图的宽
		int new_w = StringUtils.toInteger(data.getDoubleValue("width"));
		// 新图的高
		int new_h = StringUtils.toInteger(data.getDoubleValue("height"));
		// 横坐标
		int x = StringUtils.toInteger(data.getDoubleValue("x"));
		// 纵坐标
		int y = StringUtils.toInteger(data.getDoubleValue("y"));
		
		if(new_w>old_w || new_h>old_h || x<0 || x>old_w || y<0 || y>old_h || Math.abs(rotate)>270 || Math.abs(rotate)%90!=0){
			return 2;
		}
		
		try 
		{
			cut(srcImgPath, fileDir + "/" + name + "_cut." + fix, x, y, new_w, new_h);
			srcImgPath = fileDir + "/" + name + "_cut." + fix;
			if(new_w < 133 || new_h < 133){
				resize(srcImgPath, fileDir + "/" + name + "_133." + fix, 133, 133);
			}
			resize(fileDir + "/" + name + "_133." + fix, fileDir + "/" + name + "_70." + fix, 70, 70);
			resize(fileDir + "/" + name + "_133." + fix, fileDir + "/" + name + "_40." + fix, 40, 40);
			resize(fileDir + "/" + name + "_133." + fix, fileDir + "/" + name + "_24." + fix, 24, 24);
		} 
		catch (Exception e) 
		{
			resize(srcImgPath, fileDir + "/" + name + "_133." + fix, 133, 133);
			resize(srcImgPath, fileDir + "/" + name + "_70."  + fix, 70, 70);
			resize(srcImgPath, fileDir + "/" + name + "_40."  + fix, 40, 40);
			resize(srcImgPath, fileDir + "/" + name + "_24."  + fix, 24, 24);
		}
		
		
		/*if(old_w > 300 || old_h > 300)
		{
			resize(srcImgPath, fileDir + "/" + name + "_cut." + fix, 300);
		}
		else
		{
			resize(srcImgPath, fileDir + "/" + name + "_cut." + fix, (old_w>old_h?old_w:old_h));
		}
		
		srcImgPath = fileDir + "/" + name + "_cut." + fix;
		src = InputImage(srcImgPath);
		old_w = src.getWidth();
		old_h = src.getHeight();
		
		new_w = old_w;
		if(old_w > 170)
		{
			new_w = 170;
			x = (old_w - 170) / 2;
		}
		
		new_h = old_h;
		if(old_h > 170)
		{
			new_h = 170;
			y = (old_h - 170) / 2;
		}
		
		if(old_w > 170 || old_h > 170)
		{
			try 
			{
				cut(srcImgPath, fileDir + "/" + name + "_170." + fix, x, y, new_w, new_h);
				resize(fileDir + "/" + name + "_170." + fix, fileDir + "/" + name + "_65." + fix, 65, 65);
				resize(fileDir + "/" + name + "_170." + fix, fileDir + "/" + name + "_35." + fix, 35, 35);
				resize(fileDir + "/" + name + "_170." + fix, fileDir + "/" + name + "_18." + fix, 18, 18);
			} 
			catch (Exception e) 
			{
				resize(srcImgPath, fileDir + "/" + name + "_170." + fix, 170, 170);
				resize(srcImgPath, fileDir + "/" + name + "_65."  + fix, 65, 65);
				resize(srcImgPath, fileDir + "/" + name + "_35."  + fix, 35, 35);
				resize(srcImgPath, fileDir + "/" + name + "_18."  + fix, 18, 18);
			}
		}
		else
		{
			resize(srcImgPath, fileDir + "/" + name + "_170." + fix, 170, 170);
			resize(srcImgPath, fileDir + "/" + name + "_65."  + fix, 65, 65);
			resize(srcImgPath, fileDir + "/" + name + "_35."  + fix, 35, 35);
			resize(srcImgPath, fileDir + "/" + name + "_18."  + fix, 18, 18);
		}*/
		
		return 1;
	}
	
	/**
     * 旋转图片为指定角度
     * 
     * @param bufferedimage
     *            目标图像
     * @param degree
     *            旋转角度
     * @return
     */
    public static void rotateImage(String srcImgPath,String outImgPath,int degree) {
    	BufferedImage oldImg = InputImage(srcImgPath);
    	int iw = oldImg.getWidth();
    	int ih = oldImg.getHeight();
    	int w = 0,h=0,x=0,y=0;
        if(Math.abs(degree)%180!=0){
        	w = ih;
        	h = iw;
        }else{
        	w = iw;
        	h = ih;
        }
        x = (w / 2) - (iw / 2);//确定原点坐标  
        y = (h / 2) - (ih / 2);
        int type = oldImg.getColorModel().getTransparency();
        BufferedImage newimg;
        Graphics2D graphics2d;
        (graphics2d = (newimg = new BufferedImage(w, h, type))
                .createGraphics()).setRenderingHint(
                RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphics2d.rotate(Math.toRadians(degree), w / 2, h / 2);
        graphics2d.drawImage(oldImg, x, y, null);
        graphics2d.dispose();
        outImage(outImgPath, newimg, 1F);
    }


	public static void main(String args[]) throws Exception 
	{
		/*String srcImg = "D:/videos/thumbs/u178.png";
		String tarDir = "D:/videos/thumbs/";
		System.out.println(ImageUtil.class.getResource("/"));
		URL url = ImageUtil.class.getResource("src-2012.jpg");
		File srcfile = new File(url.toURI());
		System.out.println(url);
		System.out.println(srcfile.exists() + ", dir=" + srcfile.getParent());
		tarDir = srcfile.getParent();
		srcImg = srcfile.getPath();
		System.out.println("srcImg=" + srcImg);
		long startTime = new Date().getTime();
		resize(srcImg, tarDir + "car_1_maxLength_1-200px.png", 200);
		// Tosmallerpic(srcImg, tarDir + "car_1_maxLength_2.jpg", 0.5F);
		resize(srcImg, tarDir + "car_1_maxLength_3.png", 400, 500);
		resize(srcImg, tarDir + "car_1_maxLength_4-400x400.png", 220, 220);
		resize(srcImg, tarDir + "car_1_maxLength_11-220px-yinhui.png", 220,
				0.7F);
		// Tosmallerpic(srcImg, tarDir + "car_1_maxLength_22.jpg", 0.5F, 0.8F);
		resize(srcImg, tarDir + "car_1_maxLength_33.jpg", 400, 500, 0.8F);
		System.out.println(new Date().getTime() - startTime);
		
		resizeCut("D:\\videos\\thumbs/100035.jpg",null);*/
		String srcImg = "G:/1.jpg";
		String outImg = "G:/2.jpg";
		//rotateImage(srcImg,outImg, 90);
		cut(srcImg, outImg, 1, 1, 60, 60);
	}
}
