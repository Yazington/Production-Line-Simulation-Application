package strategyPattern;

import java.util.Random;

public class RandomProbabilitySell implements IVenteStrategy {
	private static int SELLING_NUMBER = 4;
	
	public RandomProbabilitySell() {}
	
	public boolean sell()
	{
		int i = new Random().nextInt(200);
		if(SELLING_NUMBER == i)
			return true;
		else
			return false;
	}

	@Override
	public void setInterval(int avionQTY) {
		
	}
}
