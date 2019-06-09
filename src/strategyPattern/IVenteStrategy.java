package strategyPattern;

public interface IVenteStrategy {
	public boolean sell();
	public void setInterval(int currentAvionQTY);
}
