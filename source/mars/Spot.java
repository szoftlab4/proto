package mars;

/**
 * A foltok közös interfésze.
 */
public interface Spot {

	public void handlePlayer(Player player);

	public boolean isDeletable();
	public ImageType getType();
}