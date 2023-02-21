# Blackjack
## Description and Setup
This project is a Java program that simulates a game of Blackjack between a single player and the House, or dealer.
The program may be compiled using the command "javac Blackjack.java" and may be run using the command "java Blackjack".
## Implementation
The program is composed of a library "lib" which acts as the package that is used in the "Blackjack.java" file.
The library has a "DeckOfCards" class and "Card" class.
The "Card" class contains rank and suit attributes that may be retrieved utilizing getter and setter methods.
All the different suits and ranks are defined in separate classes as enums.
The "DeckOfCards" class has a constructor that initializes the class as a list of cards.

The deck populates itself with cards of every suit and rank.
It contains a method to shuffle and deal itself.
Importing the "lib" package, the "Blackjack.java" file acts as the main program.
It contains methods to deal cards, convert the cards' attributes to strings with a Hash Map, and determine if bets are valid.
## How To Play
1. The player is first prompted to choose to either start or leave the game.
2. If the player chooses to leave, the program closes.
3. If the player chooses to start the game, the player and House are allotted a set amount of chips.
4. The player is then prompted to choose an initial bet of chips to wager.
5. After checking whether the bet is valid, the player and House are allotted two cards each.
6. The program then checks if the player and House have blackjack and will reward them accordingly.
7. If there is no blackjack, then the player is prompted to hit or stand.
8. The House then hits until it has a total hand value of 17 or more, or stands if it already has the required value.
9. The total hand values are compared to determine the winner and the winner receives the wager.
10. The game continues until either the player or House is out of chips.
