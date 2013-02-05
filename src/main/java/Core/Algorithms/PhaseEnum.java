package Core.Algorithms;

public enum PhaseEnum {
	// Gate instructions
	One,
	Pi,
	PiOverTwo,
	PiOverFour;
	private static final double	pi		= Math.PI;
	private static final double	pio2	= Math.PI / 2;
	private static final double	pio4	= Math.PI / 4;

	public static double getPhase(PhaseEnum i) {
		switch (i) {
			case One:
				return 1.0;
			case Pi:
				return pi;
			case PiOverTwo:
				return pio2;
			case PiOverFour:
				return pio4;
			default:
				return 0;
		}

	}

}