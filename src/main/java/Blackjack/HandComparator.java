package Blackjack;

import java.util.Comparator;

public class HandComparator implements Comparator<PlayerInterface>{

	@Override
	public int compare(PlayerInterface player, PlayerInterface dealer) {
		if (player.getScore(0) > 21)
			return 1;
		else if (dealer.getScore(0) > 21)
			return -1;
		else
			return dealer.getScore(0) - player.getScore(0);	
	}    
}