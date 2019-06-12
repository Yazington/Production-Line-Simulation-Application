package strategyPattern;

public class IntervalSell implements IVenteStrategy{

	private int currentAvionQTY;
	
	public IntervalSell() {}
	
	public boolean sell()
	{
		if(this.currentAvionQTY >= 3)
			return true;
		else
			return false;
	}

	@Override
	public void setInterval(int currentAvionQTY) {
		this.currentAvionQTY = currentAvionQTY;
	}
}
