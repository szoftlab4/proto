package mars;

/**
 * A foltok k�z�s interf�sze.
 */
public interface Spot {

	public void handlePlayer(Player player);

	public boolean isDeletable();
	public ImageType getType();
}