package Blackjack;

import java.util.Comparator;

public class HandComparator implements Comparator<PlayerInterface>{

	@Override
	public int compare(PlayerInterface player, PlayerInterface dealer) {
		if (player.getScore(player.getCurrentHandIndex()) > 21)
			return 1;
		else if (dealer.getScore(dealer.getCurrentHandIndex()) > 21)
			return -1;
		else
			return dealer.getScore(dealer.getCurrentHandIndex()) - player.getScore(dealer.getCurrentHandIndex());	
	}    
}