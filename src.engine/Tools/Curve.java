package Tools;

public interface Curve{
	
	public float calc(float x);
	
	default public float invert(float x) {
		return calc(x) * -1 + 1f;
	}
	
	
	//Curve Presets
	public static final Curve linear = (float x) -> {return x;};
	public static final Curve constant = x -> 1;
	public static final Curve exponetial = x -> x*x;
	public static final Curve cos = x -> (float)Math.cos((double)x  * Math.PI / 2);
	public static final Curve sin = x -> (float)Math.sin((double)x  * Math.PI / 2);
	
}
