/*	
 * 	Class modificada consoante o guião prático da Semana 8
 */

/**
 * Represents color images.
 * Image data is represented as a matrix:
 * - the number of lines corresponds to the image height (data.length)
 * - the length of the lines corresponds to the image width (data[*].length)
 * - pixel color is encoded as integers (ARGB)
 */

class ColorImage {

	private int[][] data; // @colorimage


	// Construtors

	ColorImage(int width, int height) {
		data = new int[height][width];
	}

	ColorImage(String file) {
		this.data = ImageUtil.readColorImage(file);
	}

	ColorImage(int[][] data) {
		this.data = data;
	}

	ColorImage(int width, int height, Color color) {
		data = new int[height][width];

		for (int x = 0; x < this.getWidth(); x++) {
			for (int y = 0; y < this.getHeight(); y++) {
				this.setColor(x, y, color);
			}
		}
	}
	
//	alínea 1-a)	
//		Obter uma cópia da imagem.
	
	ColorImage(ColorImage copy)
	{
		//	dangerous possible null access at copy.data[0]
		data = new int[ copy.data.length ][ copy.data[0].length ];
		
		for(int i = 0; i < data.length; i++)
		{
			for(int j = 0; j < data[i].length; j++)
			{
				data[i][j] = copy.data[i][j];
			}
		}
	}
	
	// Methods

	int getWidth() {
		return data[0].length;
	}

	int getHeight() {
		return data.length;
	}

	void setColor(int x, int y, Color c) {
		data[y][x] = ImageUtil.encodeRgb(c.getR(), c.getG(), c.getB());
	}

	Color getColor(int x, int y) {
		int[] rgb = ImageUtil.decodeRgb(data[y][x]);
		return new Color(rgb[0], rgb[1], rgb[2]);
	}

	// Text functions

	void drawText(int textX, int textY, String text, int textSize, Color textColor) {
		drawText(textX, textY, text, textSize, textColor, false);
	}


	void drawCenteredText(int textX, int textY, String text, int textSize, Color textColor) {
		drawText(textX, textY, text, textSize, textColor, true);
	}
	

	private void drawText(int textX, int textY, String text, int textSize, Color textColor, boolean isCentered) {
		int r = 255 - textColor.getR();
		int g = 255 - textColor.getG();
		int b = 255 - textColor.getB();

		Color maskColor = new Color(r, g, b);

		int encodedMaskRGB = ImageUtil.encodeRgb(r, g, b);

		int[][] aux = ImageUtil.createColorImageWithText(getWidth(), getHeight(), maskColor, textX, textY, text, textSize, textColor, isCentered);

		for (int i = 0; i < aux.length; i++) {
			for (int j = 0; j < aux[i].length; j++) {
				int value = aux[i][j];
				if(value != encodedMaskRGB) {
					data[i][j] = aux[i][j];
				}
			}
		}
	}
	
//	alínea 1-b)
	
	void invertColors()
	{
		for(int x = 0; x < this.getWidth(); x++)
		{
			for(int y = 0; y < this.getHeight(); y++)
			{
				Color PixelColor = this.getColor(x,y);
				PixelColor.invert();
				this.setColor(x,y,PixelColor);
			}
		}
	}
	
//	alínea 1-c)
	
	void greyscale()
	{
		for(int x = 0; x < this.getWidth(); x++)
		{
			for(int y = 0; y < this.getHeight(); y++)
			{
				Color PixelColor = this.getColor(x,y);
				PixelColor.greyscale();
				this.setColor(x,y,PixelColor);
			}
		}
	}
	
//	alínea 1-d)
	
	void changeBrightness(int value)
	{
		for(int x = 0; x < this.getWidth(); x++)
		{
			for(int y = 0; y < this.getHeight(); y++)
			{
				Color PixelColor = this.getColor(x,y);
				PixelColor.changeBrightness(value);
				this.setColor(x,y,PixelColor);
			}
		}
	}
	
//	alinea 1-e)
	
	void invertHorizontal()
	{
		int Width = this.getWidth();
		
		for(int x = 0; x < Width/2; x++)
		{
			for(int y = 0; y < this.getHeight(); y++)
			{
				Color PixelOne = this.getColor(x, y);
				Color PixelTwo = this.getColor(Width - x - 1, y);

				this.setColor(x, y, PixelTwo);
				this.setColor(Width - x - 1, y, PixelOne);
			}
		}
	}
	
//	3)
	
	void paste(ColorImage img, int x, int y)
	{
		//	x and y is the most upper left pixel
		//	might make an option later to choose
		//	which pixel to anchor from (topleft, center, bottomright)
		
		int rightLimit = this.getWidth() - x;
		int bottomLimit = this.getHeight() - y;

		if(img.getWidth() < rightLimit)
			rightLimit = img.getWidth();

		if(img.getHeight() < bottomLimit)
			bottomLimit = img.getHeight();
		
		for(int copyX = 0; copyX < rightLimit; copyX++)
		{
			for(int copyY = 0; copyY < bottomLimit; copyY++)
			{
				Color Pixel = img.getColor(copyX,copyY);
				this.setColor(copyX + x, copyY + y, Pixel);
			}
		}
	}
	
	/*	
	 *		EXTRAS 
	 */

//	1)	
	
	ColorImage selection(int startx, int starty, int endx, int endy)
	{
		ColorImage selectionImage = new ColorImage(endx-startx, endy-starty);
		
		for(int i = 0; i < selectionImage.data.length; i++)
		{
			for(int j = 0; j < selectionImage.data[i].length; j++)
			{
				selectionImage.data[i][j] = this.data[starty + i][startx + j];
			}
		}
		
		return selectionImage;
	}


}

