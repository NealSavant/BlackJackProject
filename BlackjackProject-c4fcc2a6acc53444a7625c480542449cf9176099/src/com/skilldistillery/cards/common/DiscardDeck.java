package com.skilldistillery.cards.common;

import java.util.ArrayList;
import java.util.List;

public class DiscardDeck extends Deck {
	private List<Card> discardDeck = new ArrayList<>();;

	// puts cards into discard pile
	public void addCards(List<Card> pile) {
		this.discardDeck.addAll(pile);
		System.out.println();
	}

	public int getPileSize() {
		return discardDeck.size();
	}

	public List<Card> emptyDiscardDeck() {
		List<Card> toAdd = new ArrayList<>();
		// put all cards into temporary deck
		// remove all cards from discard pile
		toAdd.removeAll(discardDeck);
		return toAdd;

	}
}
