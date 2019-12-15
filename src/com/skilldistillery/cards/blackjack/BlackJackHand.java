package com.skilldistillery.cards.blackjack;

import java.util.ArrayList;
import java.util.List;

import com.skilldistillery.cards.common.AbstractHand;
import com.skilldistillery.cards.common.Card;

public class BlackJackHand extends AbstractHand {

	// get size of hand, return integer value of each card from enum Rank.getValue
	@Override
	public int getHandValue() {

		int value = 0;
		for (int i = 0; i < super.cards.size(); i++) {
			value += super.cards.get(i).getValue();
		}
		return value;
	}

	public int getDealerValue() {
		return super.cards.get(0).getValue();
	}

	public List<Card> discard() {
		List<Card> toDiscard = new ArrayList<>();
		while(!cards.isEmpty()) {
			toDiscard.add(cards.remove(0));
		}
		return toDiscard;
	}

	public boolean isBlackJack() {
		if (getHandValue() == 21) {
			return true;
		} else {
			return false;
		}

	}

	public boolean isSoft() {
		return false;
		// TODO
	}

	public boolean isHard() {
		return false;
		// TODO
	}

	public boolean isBust() {
		if (getHandValue() > 21) {
			return true;
		}
		return false;
	}

	@Override
	public String toString() {
		String printCards = cards.toString() + " Value: " + getHandValue();
		return printCards;
	}
}
