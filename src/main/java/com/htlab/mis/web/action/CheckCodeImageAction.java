package com.htlab.mis.web.action;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.htlab.mis.common.Constants;
import com.htlab.mis.util.EditImage;


public class CheckCodeImageAction extends BaseAction {
	private static final long serialVersionUID = -1847697607986399665L;

	public String checkCode() throws Exception {

			HttpServletResponse response = ServletActionContext.getResponse();

			int width = 60, height = 28;
			BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

			Graphics g = image.getGraphics();

			Random random = new Random();

			g.setColor(EditImage.getRandColor(200, 250));
			g.fillRect(0, 0, width, height);

			g.setFont(new Font("Times New Roman", Font.PLAIN, 18));

			g.setColor(EditImage.getRandColor(160, 200));
			for (int i = 0; i < 155; i++) {
				int x = random.nextInt(width);
				int y = random.nextInt(height);
				int xl = random.nextInt(12);
				int yl = random.nextInt(12);
				g.drawLine(x, y, x + xl, y + yl);
			}

			String sRand = "";
			for (int i = 0; i < 4; i++) {
				String rand = String.valueOf(random.nextInt(10));
				sRand += rand;
				g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));
				g.drawString(rand, 13 * i + 6, 20);
			}
			this.getSession().put(Constants.CHECK_CODE, sRand);
			g.dispose();

			ImageIO.write(image, "JPEG", response.getOutputStream());
		return null;
	}

}
