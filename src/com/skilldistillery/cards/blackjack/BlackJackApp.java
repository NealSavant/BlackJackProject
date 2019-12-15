package com.skilldistillery.cards.blackjack;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.skilldistillery.cards.common.Card;
import com.skilldistillery.cards.common.Deck;
import com.skilldistillery.cards.common.DiscardDeck;

public class BlackJackApp {
	Scanner scan;
	boolean isRunning;
	boolean inGame;
	BlackJackHand dealer;
	BlackJackHand user;
	Deck deck; // user chooses initiation of 1 or 6 decks (checkDeckSize)
	DiscardDeck pile;

	{
		scan = new Scanner(System.in);
		isRunning = true;
		inGame = false;
		dealer = new BlackJackHand();
		user = new BlackJackHand();
		pile = new DiscardDeck();
	}

	public void run() {

		System.out.println("\t******************************");
		System.out.println("\t*** Welcome to Black Jack! ***");
		System.out.println("\t******************************");
		deck = new Deck(chooseDeckSize()); // create deck of user defined size
		deck.shuffle(); // shuffle deck
		int roundCounter = 1;

		while (isRunning) {

			// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
			// start game
			// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
			System.out.println("\n\tRound: " + roundCounter++);
			inGame = true;
			dealStarterCards();
			checkWinner(false);// early blackjack?

			// round
			while (inGame) {

				// user draw card menu
				boolean userHitting = true;
				while (userHitting) {
					userHitting = hitOrStand();
					System.out.println("\nYour Hand: " + user.toString());
					System.out.println("Dealer Hand: " + dealer.showOneCard() + " Value: " + dealer.getDealerValue());
					// check winner code
					checkWinner(false);
					if (inGame == false) {
						break; // end user hit loop
					}
				}
				
				if (inGame == false) { // double pasting this check in case user busts while hitting
					break; // end current game
				}

				System.out.println("\nDealer Hand: " + dealer.toString());
				dealerHitOrStand();
				System.out.println("\nYour Hand: " + user.toString());
				System.out.println("Dealer Hand: " + dealer.toString());

				// check winner code
				checkWinner(true);
				if (inGame == false) {
					break; // end current game
				}
			}
			// R O U N D F I N I S H E D
			playAgain();
			// if playing again, shuffle hands into discard pile and reshuffle into deck
			// program reshuffles after every round.
			discardPileLogic();

		}

	}

	public void discardPileLogic() {
		if (isRunning) {
			// add hands to discardDeck
			System.out.println("Discarding user's hand . . .");
			pile.addCards(user.discard());

			System.out.println("Discarding dealer's hand . . .");
			pile.addCards(dealer.discard());

			// reshuffle discard pile into deck
			deck.reshuffleDiscardPile(pile.emptyDiscardDeck());
		}
	}

	public void dealStarterCards() {
		user.addCard(deck.dealCard());
		dealer.addCard(deck.dealCard());

		user.addCard(deck.dealCard());
		dealer.addCard(deck.dealCard());
		System.out.println("Your Hand: " + user.toString());
		System.out.println("Dealer Hand: [" + dealer.showOneCard() + "] Value: " + dealer.getDealerValue());

	}

	public void checkWinner(boolean endOfMatch) {
		boolean userWin = false;
		boolean dealerWin = false;
		if (user.isBlackJack()) {
			System.out.println("\n\tYOU HIT BLACKJACK!");
			userWin = true;
			inGame = false;
		}
		if (dealer.isBlackJack()) {
			System.out.println("\n\tDEALER HIT BLACKJACK!");
			System.out.println("Dealer Hand: " + dealer.toString());
			dealerWin = true;
			inGame = false;
		}

		// check for busts
		if (user.isBust()) {
			System.out.println("You BUSTED");
			inGame = false; // ends current match
			dealerWin = true;// dealer wins
		}
		if (dealer.isBust()) {
			System.out.println("Dealer BUSTED");
			inGame = false;
			userWin = true;// user wins
		}

		// boolean parameter called when user is done hitting
		if (!inGame || endOfMatch) {
			inGame = false; // if end of match and game neither have busted.
			// check who has more points
			if (user.getHandValue() > dealer.getHandValue() && user.getHandValue() <= 21) {
				userWin = true;
			} else if (user.getHandValue() < dealer.getHandValue() && dealer.getHandValue() <= 21) {
				dealerWin = true;
			} else if (user.getHandValue() == dealer.getHandValue() && user.getHandValue() <= 21) {
				userWin = true;
				dealerWin = true; // triggers Tie Game.
			}
			if (userWin && dealerWin)
				System.out.println("\n\t~~~ Tie game. ~~~");
			else if (userWin) {
				System.out.println("\n\t~~~ Congrats you win! ~~~");
			} else if (dealerWin) {
				System.out.println("\n\t~~~ Dealer wins. ~~~");
			}
		}

	}

	public void dealerHitOrStand() {
		if(dealer.getHandValue() > 17) {
			System.out.println("Dealer decides not to hit.");
		} else if(dealer.getHandValue() > user.getHandValue()) {
			System.out.println("Dealer has more points and does not hit.");
		}
		// if less than 17 or less than user's hand
		while (dealer.getHandValue() < 17 && dealer.getHandValue() < user.getHandValue()) {
			System.out.println("\nDealer draws a card . . .");
			dealer.addCard(deck.dealCard());
			System.out.println("Dealer Hand: " + dealer.toString());
		}
		
	}

	public boolean hitOrStand() {
		System.out.println("\n\t1. Hit");
		System.out.println("\t2. Stand");
		System.out.println("\t3. Quit");
		int choice = 0;
		boolean uGonnaHit = false;
		while (true) {
			try {
				choice = scan.nextInt();
				if (choice == 1) {
					user.addCard(deck.dealCard());
					uGonnaHit = true;
					break;

				} else if (choice == 2) {
					uGonnaHit = false;
					break;

				} else if (choice == 3) {
					inGame = false;
					uGonnaHit = false;
					break;
				} else {
					throw new IllegalArgumentException();
				}

			} catch (IllegalArgumentException e) {
				System.err.println("Enter 1, 2, or 3.");
			}
		}
		return uGonnaHit;
	}

	// creates deck of size 1 or 6
	public int chooseDeckSize() {
		System.out.println("Will you play with '1' or '6' decks?");
		int choice = 0;
		while (true) {
			try {
				choice = scan.nextInt();
				if (choice == 1 || choice == 6) {
					break;
				} else {
					throw new IllegalArgumentException();
				}

			} catch (IllegalArgumentException e) {
				System.err.println("Enter 1 or 6.");
			}
		}
		return choice;

	}

	public void playAgain() {
		System.out.println("1. Play Again?\n2. Quit");
		int choice = 0;
		while (true) {
			try {
				choice = scan.nextInt();
				if (choice == 1) {
					break;
				} else if (choice == 2) {
					isRunning = false;
					break;
				} else {
					throw new IllegalArgumentException();
				}

			} catch (IllegalArgumentException e) {
				System.err.println("Enter 1 or 2.");
			}
		}

	}

	public static void main(String[] args) {
		BlackJackApp game = new BlackJackApp();
		game.run();
	}

}
