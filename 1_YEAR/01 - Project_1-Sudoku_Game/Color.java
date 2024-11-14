	
//Class modificada consoante o guião prático da Semana 8


/**
 * Represents RGB colors.
 * RGB values are stored in a 3-position array, with values in the interval [0, 255].
 * rgb[0] - Red
 * rgb[1] - Green
 * rgb[2] - Blue
 */
class Color {
	
	private final int[] rgb; // @color
	
	/**
	 * Creates an RGB color. Provided values have to 
	 * be in the interval [0, 255]
	 */
	Color(int r, int g, int b) {
		if(!valid(r) || !valid(g) || !valid(b))
			throw new IllegalArgumentException("invalid RGB values: " + r + ", " + g + ", " + b);
		
		this.rgb = new int[] {r, g, b};
	}

	/**
	 * Red value [0, 255]
	 */
	int getR() {
		return rgb[0];
	}

	/**
	 * Green value [0, 255]
	 */
	int getG() {
		return rgb[1];
	}

	/**
	 * Blue value [0, 255]
	 */
	int getB() {
		return rgb[2];
	}

	/**
	 * Obtains the luminance in the interval [0, 255].
	 */
	int getLuminance() {
		return (int) Math.round(rgb[0]*.21 + rgb[1]*.71 + rgb[2]*.08);
	}

	static boolean valid(int value) {
		return value >= 0 && value <= 255;
	}

	/*	Modificações na classe consoante o 
	 * 	Guião Pratico da Semana 8
	 */

	/*	Constant colors	*/
	
	static final Color BLACK	= new Color(0, 0, 0);
	static final Color WHITE	= new Color(255, 255, 255);
	
	static final Color RED 		= new Color(255, 0, 0);
	static final Color GREEN	= new Color(0, 255, 0);
	static final Color BLUE		= new Color(0, 0, 255);

	static final Color YELLOW	= new Color(255, 255, 0);
	static final Color LBLUE	= new Color(0, 255, 255);
	static final Color PURPLE	= new Color(255, 0, 255);
	static final Color MAGENTA	= PURPLE;	
	
	/*	New methods	*/
	
//	2)
	
	Color getInverted()
	{
		return new Color(255 - rgb[0], 255 - rgb[1], 255 - rgb[2]);
	}
	
	void invert()
	{	
		rgb[0] = (255 - rgb[0]);
		rgb[1] = (255 - rgb[1]);
		rgb[2] = (255 - rgb[2]);
	}
	
//	3)
	
	Color brighter(int value)
	{
		return new Color(	Math.min(255, Math.max(0, rgb[0] + value)),
							Math.min(255, Math.max(0, rgb[1] + value)),
							Math.min(255, Math.max(0, rgb[2] + value))
						);
	}
	
	void changeBrightness(int value)
	{
		rgb[0] = Math.min(255, Math.max(0, rgb[0] + value));
		rgb[1] = Math.min(255, Math.max(0, rgb[1] + value));
		rgb[2] = Math.min(255, Math.max(0, rgb[2] + value));
	}
	
//	4)

	Color getGreyscale()
	{
		int lum = this.getLuminance();
		return new Color(lum,lum,lum);
	}

	void greyscale()
	{
		int lum = this.getLuminance();
		rgb[0] = lum;
		rgb[1] = lum;
		rgb[2] = lum;
	}
	
//	5)
	
	boolean isEqualTo(Color c)
	{
		return rgb[0] == c.getR() && 
				rgb[1] == c.getG() && 
				rgb[2] == c.getB();
	}
	
//	6)
	
	boolean containedIn(Color vector[])
	{
		for(int i = 0; i < vector.length; i++)
		{
			if(this.isEqualTo(vector[i]))
				return true;
		}
		
		return false;
	}
}