package com.htlab.mis.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import net.sf.jxls.transformer.XLSTransformer;

import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.htlab.mis.entity.Irr;

public class JxlsUtil {

//	public static void export(String templateFile,Map beans,String dstXlsFile){
//		XLSTransformer transformer = new XLSTransformer();
//		try {
//			transformer.transformXLS(templateFile, beans, dstXlsFile);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
	
//	public static void export(String templateFile,Map beans,String dstXlsFile){
//		FileOutputStream fos=null;   
//		try{   
//			ByteArrayOutputStream out = new ByteArrayOutputStream();
//			InputStream template = JxlsUtil.class.getClassLoader().getResourceAsStream(templateFile);
//			XLSTransformer transformer = new XLSTransformer();
//			Workbook workbook;
//			workbook = transformer.transformXLS(template, beans);
//			workbook.write(out);
//			byte[] result = out.toByteArray();
//			System.out.println(result.length);
//			fos=new FileOutputStream(dstXlsFile,true);   
//			fos.write(result);   
//		}catch(Exception e){
//			e.printStackTrace();
//		}finally{   
//			try{   
//				fos.close();   
//			}   
//			catch(Exception iex){}   
//		}
//	}
	
	public static void export(String templateFile,Map beans,String dstXlsFile,String imgPath){
		FileOutputStream fos=null;   
		try{   
			ByteArrayOutputStream out = new ByteArrayOutputStream();
	        InputStream template = JxlsUtil.class.getClassLoader().getResourceAsStream(templateFile);
	        XLSTransformer transformer = new XLSTransformer();
	        HSSFWorkbook  workbook;
			workbook = (HSSFWorkbook )transformer.transformXLS(template, beans);
			
//			String imgPath = "d:/a.jpg";
			if(imgPath != null && imgPath.length() > 0){
				HSSFSheet sheet = workbook.getSheetAt(0);  
				HSSFPatriarch patriarch = sheet.createDrawingPatriarch();//创建绘图工具对象放循环外可正确显示  
	            //将图片以字节流的方式输入输出  
	            ByteArrayOutputStream bos = new ByteArrayOutputStream();  
	            BufferedImage BufferImg = ImageIO.read(new File(imgPath));
	            ImageIO.write(BufferImg, "JPEG", bos);  
				HSSFClientAnchor anchor = new HSSFClientAnchor(5,0,450,250,(short) 2, 7,(short)3,7+1); 
				patriarch.createPicture(anchor,workbook.addPicture(bos.toByteArray(),workbook.PICTURE_TYPE_JPEG)); 
			}
			workbook.write(out);
			byte[] result = out.toByteArray();
			System.out.println(result.length);
            fos=new FileOutputStream(dstXlsFile,true);
            fos.write(result); 
            
        }catch(Exception e){
        	e.printStackTrace();
        }finally{   
            try{   
                fos.close();   
            }   
            catch(Exception iex){}   
        }
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		Irr model = new Irr();
		model.setCode("fdfdfds");
		Map beans = new HashMap();
        beans.put("model", model);
       
//        String templateFile = "F:/Dropbox/mis/target/classes/templates/template.xls";
////        String systemPath = getRequest().getSession().getServletContext().getRealPath("/");
//        String excelPath = "d:/"  + System.currentTimeMillis() + ".xls";
//        try {
//        	export(templateFile, beans, excelPath);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        String templateFile = "F:/Dropbox/mis/src/main/java/templates/template.xls";
        System.out.println(templateFile);
        File f= new File(templateFile);
        System.out.println(f.exists());
//		JxlsUtil.export(templateFile, beans, "d:/1bc.xls");  
		
	}

}