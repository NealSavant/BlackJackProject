package com.skilldistillery.cards.common;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractHand {
	protected List<Card> cards;

	public AbstractHand() {
		cards = new ArrayList<>();
	}

	public void addCard(Card card) {
		cards.add(card);
	}

	public String showOneCard() {
		String hiddenCard = "HIDDEN CARD, " + cards.get(0).toString();
		return hiddenCard;
	}

	public abstract int getHandValue();

	@Override
	public String toString() {
		String printCards = "" + cards.toString();
		return printCards;
	}
}
