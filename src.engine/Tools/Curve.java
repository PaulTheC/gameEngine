package Tools;

public interface Curve{
	public float calc(float x);
	default public float invert(float x) {
		return calc(x) * -1 + 0.5f;
	}
}
