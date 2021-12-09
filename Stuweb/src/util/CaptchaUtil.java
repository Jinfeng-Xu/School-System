package util;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * CAPTCHA generation
 * 
 * @author Jinfeng.Xu
 */
public class CaptchaUtil {
	
	/**
	 * CAPTCHA source
	 */
	final private char[] code = {
		'2', '3', '4', '5', '6', '7', '8', '9',
		'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
		'k', 'm', 'n', 'p', 'q', 'r', 's', 't', 'u', 'v', 
		'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F',
		'G', 'H', 'J', 'K', 'L', 'M', 'N', 'P', 'Q', 'R',
		'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'
	};
	/**
	 * Font
	 */
	final private String[] fontNames = new String[]{"Courier", "Arial", "Verdana", "Times", "Tahoma", "Georgia"};
	/**
	 * Style
	 */
	final private int[] fontStyles = new int[]{
			Font.BOLD, Font.ITALIC|Font.BOLD
	};
	
	/**
	 * CAPTCHA Length
	 */
	private int vcodeLen = 4;
	/**
	 * CAPTCHA Font Size
	 */
	private int fontsize = 21;
	/**
	 * CAPTCHA Width
	 */
	private int width = (fontsize + 1) * vcodeLen + 10;
	/**
	 * CAPTCHA Height
	 */
	private int height = fontsize + 12;
	/**
	 * Number of interference lines 
	 */
	private int disturbline = 3;
	
	
	public CaptchaUtil(){}
	
	/**
	 * Decide CAPTCHA Length
	 * @param vcodeLen 
	 */
	public CaptchaUtil(int vcodeLen) {
		this.vcodeLen = vcodeLen;
		this.width = (fontsize + 1) * vcodeLen + 10;
	}
	
	/**
	 * Generate images
	 * @param vcode 
	 * @param drawline 
	 * @return
	 */
	public BufferedImage generatorVCodeImage(String vcode, boolean drawline){
		//Generate CAPTCHA images
		BufferedImage vcodeImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics g = vcodeImage.getGraphics();
		//Fill the background color
		g.setColor(new Color(246, 240, 250));
		g.fillRect(0, 0, width, height);
		if(drawline){
			drawDisturbLine(g);
		}
		//Random
		Random ran = new Random();
		//Draw CAPTCHA on picture
		for(int i = 0;i < vcode.length();i++){
			//SetFont
			g.setFont(new Font(fontNames[ran.nextInt(fontNames.length)], fontStyles[ran.nextInt(fontStyles.length)], fontsize));
			//Random color
			g.setColor(getRandomColor());
			//Draw CAPTCHA
			g.drawString(vcode.charAt(i)+"", i*fontsize+10, fontsize+5);
		}
		//GC
		g.dispose();
		return vcodeImage;
	}
	/**
	 * Gets the CAPTCHA image of the rotated font
	 * @param vcode
	 * @param drawline 
	 * @return
	 */
	public BufferedImage generatorRotateVCodeImage(String vcode, boolean drawline){
		//Generate CAPTCHA Image
		BufferedImage rotateVcodeImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2d = rotateVcodeImage.createGraphics();
		//Fill the background color
		g2d.setColor(new Color(246, 240, 250));
		g2d.fillRect(0, 0, width, height);
		if(drawline){
			drawDisturbLine(g2d);
		}
		//Draw CAPTCHA on picture
		for(int i = 0;i < vcode.length();i++){
			BufferedImage rotateImage = getRotateImage(vcode.charAt(i));
			g2d.drawImage(rotateImage, null, (int) (this.height * 0.7) * i, 0);
		}
		g2d.dispose();
		return rotateVcodeImage;
	}
	/**
	 * Generate CAPTCHA
	 * @return captcha
	 */
	public String generatorVCode(){
		int len = code.length;
		Random ran = new Random();
		StringBuffer sb = new StringBuffer();
		for(int i = 0;i < vcodeLen;i++){
			int index = ran.nextInt(len);
			sb.append(code[index]);
		}
		return sb.toString();
	}
	/**
	 * Draw some interference lines
	 * @param g 
	 */
	private void drawDisturbLine(Graphics g){
		Random ran = new Random();
		for(int i = 0;i < disturbline;i++){
			int x1 = ran.nextInt(width);
			int y1 = ran.nextInt(height);
			int x2 = ran.nextInt(width);
			int y2 = ran.nextInt(height);
			g.setColor(getRandomColor());
			//Draw interference lines
			g.drawLine(x1, y1, x2, y2);
		}
	}
	/**
	 * Get a rotated image
	 * @param c character
	 * @return
	 */
	private BufferedImage getRotateImage(char c){
		BufferedImage rotateImage = new BufferedImage(height, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = rotateImage.createGraphics();
		//Set opacity to 0
		g2d.setColor(new Color(255, 255, 255, 0));
		g2d.fillRect(0, 0, height, height);
		Random ran = new Random();
		g2d.setFont(new Font(fontNames[ran.nextInt(fontNames.length)], fontStyles[ran.nextInt(fontStyles.length)], fontsize));
		g2d.setColor(getRandomColor());
		double theta = getTheta();
		//rotate image
		g2d.rotate(theta, height/2, height/2);
		g2d.drawString(Character.toString(c), (height-fontsize)/2, fontsize+5);
		g2d.dispose();
		
		return rotateImage;
	}
	/**
	 * @return return a random color
	 */
	private Color getRandomColor(){
		Random ran = new Random();
		return new Color(ran.nextInt(220), ran.nextInt(220), ran.nextInt(220)); 
	}
	/**
	 * @return angle
	 */
	private double getTheta(){
		return ((int) (Math.random()*1000) % 2 == 0 ? -1 : 1)*Math.random();
	}

	/**
	 * @return number of char
	 */
	public int getVcodeLen() {
		return vcodeLen;
	}
	/**
	 * set the number of char
	 * @param vcodeLen
	 */
	public void setVcodeLen(int vcodeLen) {
		this.width = (fontsize+3)*vcodeLen+10;
		this.vcodeLen = vcodeLen;
	}
	/**
	 * @return FontSize
	 */
	public int getFontsize() {
		return fontsize;
	}
	/**
	 * set FontSize
	 * @param fontsize
	 */
	public void setFontsize(int fontsize) {
		this.width = (fontsize+3)*vcodeLen+10;
		this.height = fontsize+15;
		this.fontsize = fontsize;
	}
	/**
	 * @return Image Width
	 */
	public int getWidth() {
		return width;
	}
	/**
	 * Set picture width
	 * @param width
	 */
	public void setWidth(int width) {
		this.width = width;
	}
	/**
	 * @return Image Height
	 */
	public int getHeight() {
		return height;
	}
	/**
	 * set Image Height
	 * @param height 
	 */
	public void setHeight(int height) {
		this.height = height;
	}
	/**
	 * @return Interference lines
	 */
	public int getDisturbline() {
		return disturbline;
	}
	/**
	 * set the number of Interference lines
	 * @param disturbline
	 */
	public void setDisturbline(int disturbline) {
		this.disturbline = disturbline;
	}
	
}
