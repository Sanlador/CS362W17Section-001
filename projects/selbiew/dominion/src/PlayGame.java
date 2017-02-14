
public class PlayGame {

	public static void main(String[] args) {
		int seed = Integer.parseInt(args[0]);				//Player Count
		DomGame table = new DomGame(seed);
		table.play();

	}
}
