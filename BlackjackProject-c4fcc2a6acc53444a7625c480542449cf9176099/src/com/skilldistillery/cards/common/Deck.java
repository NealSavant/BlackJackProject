package com.skilldistillery.cards.common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
	private List<Card> cards;
	private int maxDeckSize;

	public Deck(int numDecks) {
		cards = createDeck(numDecks);
		maxDeckSize = numDecks * 52;
	}

	// empty constructor for DiscardDeck which is not size dependent
	public Deck() {

	}

	public void reshuffleDiscardPile(List<Card> discardPile) {
		cards.addAll(discardPile);
		System.out.println("Adding discard pile to deck . . .");
		shuffle();
		System.out.println("Shuffling cards . . .");
	}

	private List<Card> createDeck(int numDecks) {
		List<Card> deck = new ArrayList<>();
		int deckCounter = 0;
		while (deckCounter < numDecks) {
			for (Suit s : Suit.values()) {
				for (Rank r : Rank.values()) {
					deck.add(new Card(r, s));
				}
			}
			deckCounter++;
		}
		return deck;
	}

	public int checkDeckSize() {
		return cards.size();
	}

	public int getMaxDeckSize() {
		return maxDeckSize;
	}

	public void shuffle() {
		Collections.shuffle(cards);
	}

	public Card dealCard() {
		return cards.remove(0);
	}

	public void dealCard(AbstractHand hand) {
		hand.addCard(dealCard()); // calls above method dealsCard
	}

}
