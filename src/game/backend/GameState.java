package game.backend;

public abstract class GameState {
	
	private long score = 0;
	private int moves = 0;
	protected long maxMoves = 0;

	
	public void addScore(long value) {
		this.score = this.score + value;
	}
	
	public long getScore(){
		return score;
	}
	
	public void addMove() {
		moves++;
	}
	
	public int getMoves() {
		return moves;
	}

	public long getMovesLeft(){return maxMoves-moves;}

	public long getInfo(){return -1;};

	public abstract boolean gameOver();
	
	public abstract boolean playerWon();





}
