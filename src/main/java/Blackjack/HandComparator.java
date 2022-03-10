package Blackjack;

import java.util.Comparator;

public class HandComparator implements Comparator<PlayerInterface>{

	@Override
	public int compare(PlayerInterface player, PlayerInterface dealer) {
		
		if (player.getScore()>21)
			return 1;
		else if (dealer.getScore()>21)
			return -1;
		else
			return dealer.getScore() - player.getScore();
		
	}

    
}