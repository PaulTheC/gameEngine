package Utilities;

public class Time {
	
	private static long lastTime;
	public static float deltaTime;
	public static int FPS;
	private static int FPSCount;
	private static float secondsTimer;
	private static int frameCount;
	private static final long timeCreated = System.currentTimeMillis();
	
	public static void init() {
		
	}
	
	
	//This must be called in the end of rendering
	public static void onUpdate() {
		deltaTime = (float) ((System.currentTimeMillis() - lastTime)*0.001);
		lastTime = System.currentTimeMillis();
		FPS = (int) (1/deltaTime);

		if(deltaTime < 0.001)
			deltaTime = 0.00001f;

		
		
		//count FPS
		secondsTimer += deltaTime;
		FPSCount++;
		if(secondsTimer > 1) {
			//System.out.println("FPS: "+FPSCount);
			
			if(FPSCount < 40) System.out.println("FPS warning: "+FPSCount);
			
			FPSCount = 0;
			secondsTimer = 0;
		}
		
		frameCount++;
	}
	
	public static void onDestroy() {
		System.out.println("Time Running: "+ getTimeAlive()+"       Average FPS:"+ (int)(frameCount / getTimeAlive()));
	}
	
	
	public static float getTimeAlive() {
		return (float)(System.currentTimeMillis() - timeCreated) / 1000f;
	}

}
