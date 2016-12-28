from threading import Thread
import random
import math
import time
import sys

class Game(Thread):
    def __init__(self, threadID, name, t):
        self.mainPot = 0
        self.sabaccPot = 0
        self.callingPhase = False
        self.shiftNum = 0
        self.shiftAmount = 0
        self.timeSeed = 0
        self.players = []
        self.cards = []
        self.threadID = threadID
        self.name = name
        self.t = t

    def start(self):
        print "Starting Game Thread"

        if self.t == null:
            self.t = Game(self, (threadID + 1), "Game Thread", t)
            self.t.start(self)

    def run(self):
        self.game()

    def game(self):
        print "Please input a number to use as the seed, or type 0 and press enter for a random seed."
        self.timeSeed = input("Seed: ")

        if self.timeSeed == 0:
            self.timeSeed = int(round(time.time() * 1000))

        print "How many people are playing?"
        ans = input("# of Players: ")

        if ans < 2:
            print "You must play with 2 - 8 players."
            sys.exit(0)
        elif (ans >= 2) and (ans <= 8):
            for i in range(0, ans):
                p = Player()
                self.players.append(p)
                self.players[i].money = 100

            print "There are " + ans + " players."
        else:
            print "You must play with 2 - 8 players."
            sys.exit(0)

        for i in range(0, len(self.players)):
            print "Please input the name for Player " + (i + 1)
            self.players[i].name = input("Player " + (i + 1) + ": ")

        self.callingPhase = False
        self.bettingRound(self)
        self.cardSetup(self)
        self.dealTwoCardsToEach(self)
        self.hitOrStand(self)
        self.bettingRound(self)
        self.hitOrStand(self)
        self.decideCalling(self)
        self.endRound(self)

    def bettingRound(self):
        bets = int[len(self.players)]

        for i in range(0, len(bets)):
            print "Player " + (i + 1) + ", you have " + self.players[i].money + " credits."

            if i == 0:
                if (not self.players[i].bombedout) and (not self.players[i].fold):
                    while True:
                        print "How much would you like to bet?"
                        ans = input("# of Credits: ")

                        if ans > self.players[i].money:
                            print "You do not have enough money."
                        else:
                            bets[i] = ans
                            self.players[i] -= ans
                            break
                elif self.players[i].bombedout:
                    print "You have bombed out. You may not bet."
                elif self.players[i].fold:
                    print "You have folded. You may not bet."
            else:
                if (not self.players[i].bombedout) and (not self.players[i].fold):
                    while True:
                        print "Player " + i + " has bet " + bets[i - 1] + " credits."
                        print "Will you match or raise?"
                        ans = input("match / raise: ")

                        if ans == "match":
                            if bets[i - 1] > self.players[i].money:
                                print "You do not have enough money. You must fold."
                                self.players[i].fold = True
                                break;
                            else:
                                print "You matched."
                                bets[i] = bets[i - 1]
                                self.players[i].money -= bets[i]
                                break;
                        elif ans == "raise":
                            print "By how much?"
                            ans1 = input("Credits: ")

                            if (bets[i - 1] + ans1) > self.players[i].money:
                                print "You do not have enough money."
                            else:
                                bets[i] = bets[i - 1] + ans1
                                self.players[i].money -= bets[i]
                                bets[i - 1] = bets[i]

                                for j in range(0, i):
                                    self.players[j].money -= ans1

                                break
                        else:
                            print "That is not an acceptable choice."
                elif self.players[i].bombedout:
                    print "You have bombed out. You may not bet."
                elif self.players[i].fold:
                    print "You have folded. You may not bet."

    def cardSetup(self):
        # Suits
        # 0 - Saber
        # 1 - Flask
        # 2 - Coin
        # 3 - Staff

        # Parameters:
        # String name, null for non-special cards
        # int value
        # int suit, -1 for special cards
        # int copy, -1 for non-special, non-face cards (0 or 1)
        # boolean face

        self.cards.append(Card(null, 1, 0, -1, False)) # 1 of Sabers
        self.cards.append(Card(null, 2, 0, -1, False)) # 2 of Sabers
        self.cards.append(Card(null, 3, 0, -1, False)) # 3 of Sabers
        self.cards.append(Card(null, 4, 0, -1, False)) # 4 of Sabers
        self.cards.append(Card(null, 5, 0, -1, False)) # 5 of Sabers
        self.cards.append(Card(null, 6, 0, -1, False)) # 6 of Sabers
        self.cards.append(Card(null, 7, 0, -1, False)) # 7 of Sabers
        self.cards.append(Card(null, 8, 0, -1, False)) # 8 of Sabers
        self.cards.append(Card(null, 9, 0, -1, False)) # 9 of Sabers
        self.cards.append(Card(null, 10, 0, -1, False)) # 10 of Sabers
        self.cards.append(Card(null, 11, 0, -1, False)) # 11 of Sabers
        self.cards.append(Card("Commander", 12, 0, True)) # Commander of Sabers
        self.cards.append(Card("Mistress", 13, 0, -1, True)) # Mistress of Sabers
        self.cards.append(Card("Master", 14, 0, -1, True)) # Master of Sabers
        self.cards.append(Card("Ace", 15, 0, -1, True)) # Ace of Sabers

        self.cards.append(Card(null, 1, 1, -1, False)) # 1 of Flasks
        self.cards.append(Card(null, 2, 1, -1, False)) # 2 of Flasks
        self.cards.append(Card(null, 3, 1, -1, False)) # 3 of Flasks
        self.cards.append(Card(null, 4, 1, -1, False)) # 4 of Flasks
        self.cards.append(Card(null, 5, 1, -1, False)) # 5 of Flasks
        self.cards.append(Card(null, 6, 1, -1, False)) # 6 of Flasks
        self.cards.append(Card(null, 7, 1, -1, False)) # 7 of Flasks
        self.cards.append(Card(null, 8, 1, -1, False)) # 8 of Flasks
        self.cards.append(Card(null, 9, 1, -1, False)) # 9 of Flasks
        self.cards.append(Card(null, 10, 1, -1, False)) # 10 of Flasks
        self.cards.append(Card(null, 11, 1, -1, False)) # 11 of Flasks
        self.cards.append(Card("Commander", 12, 1, -1, True)) # Commander of Flasks
        self.cards.append(Card("Mistress", 13, 1, -1, True)) # Mistress of Flasks
        self.cards.append(Card("Master", 14, 1, -1, True)) # Master of Flasks
        self.cards.append(Card("Ace", 15, 1, -1, True)) # Ace of Flasks

        self.cards.append(Card(null, 1, 2, -1, False)) # 1 of Coins
        self.cards.append(Card(null, 2, 2, -1, False)) # 2 of Coins
        self.cards.append(Card(null, 3, 2, -1, False)) # 3 of Coins
        self.cards.append(Card(null, 4, 2, -1, False)) # 4 of Coins
        self.cards.append(Card(null, 5, 2, -1, False)) # 5 of Coins
        self.cards.append(Card(null, 6, 2, -1, False)) # 6 of Coins
        self.cards.append(Card(null, 7, 2, -1, False)) # 7 of Coins
        self.cards.append(Card(null, 8, 2, -1, False)) # 8 of Coins
        self.cards.append(Card(null, 9, 2, -1, False)) # 9 of Coins
        self.cards.append(Card(null, 10, 2, -1, False)) # 10 of Coins
        self.cards.append(Card(null, 11, 2, -1, False)) # 11 of Coins
        self.cards.append(Card("Commander", 12, 2, -1, True)) # Commander of Coins
        self.cards.append(Card("Mistress", 13, 2, -1, True)) # Mistress of Coins
        self.cards.append(Card("Master", 14, 2, -1, True)) # Master of Coins
        self.cards.append(Card("Ace", 15, 2, -1, True)) # Ace of Coins

        self.cards.append(Card(null, 1, 3, -1, False)) # 1 of Staffs
        self.cards.append(Card(null, 2, 3, -1, False)) # 2 of Staffs
        self.cards.append(Card(null, 3, 3, -1, False)) # 3 of Staffs
        self.cards.append(Card(null, 4, 3, -1, False)) # 4 of Staffs
        self.cards.append(Card(null, 5, 3, -1, False)) # 5 of Staffs
        self.cards.append(Card(null, 6, 3, -1, False)) # 6 of Staffs
        self.cards.append(Card(null, 7, 3, -1, False)) # 7 of Staffs
        self.cards.append(Card(null, 8, 3, -1, False)) # 8 of Staffs
        self.cards.append(Card(null, 9, 3, -1, False)) # 9 of Staffs
        self.cards.append(Card(null, 10, 3, -1, False)) # 10 of Staffs
        self.cards.append(Card(null, 11, 3, -1, False)) # 11 of Staffs
        self.cards.append(Card("Commander", 12, 3, -1, False)) # Commander of Staffs
        self.cards.append(Card("Mistress", 13, 3, -1, False)) # Mistress of Staffs
        self.cards.append(Card("Master", 14, 3, -1, False)) # Master of Staffs
        self.cards.append(Card("Ace", 15, 3, -1, False)) # Ace of Staffs

        self.cards.append(Card("The Star", -17, -1, 0, False)) # The Star 1
        self.cards.append(Card("The Star", -17, -1, 1, False)) # The Star 2
        self.cards.append(Card("The Evil One", -15, -1, 0, False)) # The Evil One 1
        self.cards.append(Card("The Evil One", -15, -1, 1, False)) # The Evil One 2
        self.cards.append(Card("Moderation", -14, -1, 0, False)) # Moderation 1
        self.cards.append(Card("Moderation", -14, -1, 1, False)) # Moderation 2
        self.cards.append(Card("Demise", -13, -1, 0, False)) # Demise 1
        self.cards.append(Card("Demise", -13, -1, 1, False)) # Demise 2
        self.cards.append(Card("Balance", -11, -1, 0, False)) # Balance 1
        self.cards.append(Card("Balance", -11, -1, 1, False)) # Balance 2
        self.cards.append(Card("Endurance", -8, -1, 0, False)) # Endurance 1
        self.cards.append(Card("Endurance", -8, -1, 1, False)) # Endurance 2
        self.cards.append(Card("Queen of Air and Darkness", -2, -1, 0, False)) # Queen of Air and Darkness 1
        self.cards.append(Card("Queen of Air and Darkness", -2, -1, 1, False)) # Queen of Air and Darkness 2
        self.cards.append(Card("Idiot", 0, -1, 0, False)) # Idiot 1
        self.cards.append(Card("Idiot", 0, -1, 1, False)) # Idiot 2

    def dealTwoCardsToEach(self):
        for i in range(0, len(self.players)):
            num = random.randint(0, len(self.cards))
            self.players[i].hand.append(self.cards[num])
            self.cards.remove(self.cards[num])

    def hitOrStand(self):
        for i in range(0, len(self.players)):
            score = 0

            for j in range(0, len(self.players[i].hand)):
                score += self.players[i].hand[j].value

            print "Player " + (i + 1) + ", your cards have a score of " + score + "."
            print "Would you like to hit or stand?"

            while True:
                ans = input("hit / stand: ")

                if ans == "hit":
                    randNum = random.nextint(len(self.cards))
                    self.players[i].hand.append(self.cards[randNum])
                    self.cards.remove(self.cards[randNum])
                elif ans == "stand":
                    self.players[i].fold = True
                    break
                else:
                    print "That is not an acceptable answer."

    def decideCalling(self):
        for i in range(0, len(self.players)):
            print "Player " + (i + 1) + ", would you like to call?"
            while True:
                ans = input("y / n: ")

                if ans == "y":
                    print "The calling round has begun!"
                    self.callingRound(self, i)
                    break
                elif ans == "n":
                    print "Player " + (i + 1) + " has decided not to call."
                    print "Next player."
                    break
                else:
                    print "That is not an acceptable answer."

    def callingRound(self, beginningPlayer):
        self.callingPhase = True

        if beginningPlayer == len(self.players):
            self.callingPlayer(self, beginningPlayer)

            for i in range(0, (len(self.players) - 1)):
                self.callingPlayer(self, i)
        elif beginningPlayer == 0:
            for i in range(0, len(self.players)):
                self.callingPlayer(self, i)
        else:
            for i in range(beginnigPlayer, len(self.players)):
                self.callingPlayer(self, i)
            for i in range(0, beginningPlayer):
                self.callingPlayer(self, i)

    def callingPlayer(self, playerRef):
        print "Player "
        print playerRef + 1
        print " has "

        for i in range(0, len(self.players[playerRef].hand)):
            if self.players[playerRef].hand[i].name == null:
                # Not a special or face card
                print "The "
                print self.players[playerRef].hand[i].value
                print " of "

                if self.players[playerRef].hand[i].suit == 0:
                    print "Sabers, "
                elif self.players[playerRef].hand[i].suit == 1:
                    print "Flasks, "
                elif self.players[playerRef].hand[i].suit == 2:
                    print "Coins, "
                elif self.players[playerRef].hand[i].suit == 3:
                    print "Staffs, "
            else:
                if self.players[playerRef].hand[i].face:
                    # Face card
                    print "The "
                    print self.players[playerRef].hand[i].name
                    print " of "

                    if self.players[playerRef].hand[i].suit == 0:
                        print "Sabers, "
                    elif self.players[playerRef].hand[i].suit == 1:
                        print "Flasks, "
                    elif self.players[playerRef].hand[i].suit == 2:
                        print "Coins, "
                    elif self.players[playerRef].hand[i].suit == 3:
                        print "Staffs, "
                else:
                    # Special card
                    print "The "
                    print self.players[playerRef].hand[i].copy + 1

                    if self.players[playerRef].hand[i].copy == 0:
                        print "st"
                    elif self.players[playerRef].hand[i].copy == 1:
                        print "nd"

                    print " copy of "
                    print self.players[playerRef].hand[i].name
                    print ","


        score = 0

        for i in range(0, len(self.players[playerRef].hand)):
            score += self.players[playerRef].hand[i].value

        print " with a score of "
        print score
        print "."

        if (score > 23) or (score < -23):
            print "Player "
            print playerRef + 1
            print " has bombed out!"
            self.players[playerRef].bombedout = True
            self.players[playerRef].money -= self.mainPot
            self.sabaccPot += self.mainPot

    def endRound(self):
        for i in range(0, len(self.players)):
            if self.players[i].bombedout:
                print "Player "
                print i + 1
                print " has bombed out. They do not win."
            else:
                for j in range(0, len(self.players[i].hand)):
                    self.players[i].score += self.players[i].hand[j].value

        winningHands = []

        for i in range(0, len(self.players)):
            if self.plyaers[i].score == 23:
                winningHands[1] = self.players[i]
            elif self.players[i].score == -23:
                winningHands[2] = self.players[i]
            else:
               idiot = False
               two = False
               three = False
               sameSuit = False
               suit = -1

               for j in range(0, len(self.players[i].hand)):
                   if self.players[i].hand[j].name == "Idiot":
                       idiot = True
                    elif (self.players[i].hand[j].value == 2) and (self.players[i].hand[j].name == null):
                        two = True

                        if three:
                            if self.players[i].hand[j].suit == suit:
                                sameSuit = True
                            else:
                                sameSuit = False
                        else:
                            suit = self.players[i].hand[j].suit
                    elif (self.players[i].hand[j].value == 3) and (self.players[i].hand[j].name == null):
                        three = True

                        if two:
                            if self.players[i].hand[j].suit == suit:
                                sameSuit = True
                            else:
                                sameSuit = False
                        else:
                            suit = self.players[i].hand[j].suit

                if idiot and two and three and sameSuit:
                    winningHands[0] = self.players[i]

        noIdiotsArray = True
        noPositivePureSabacc = True
        noNegativePureSabacc = True

        if winningHands[0] != null:
            noIdiotsArray = False
        elif winningHands[1] != null:
            noPositivePureSabacc = False
        elif winningHands[2] != null:
            noNegativePureSabacc = False

        if not noIdiotsArray:
            num = 0

            for i in range(0, len(self.players)):
                if winningHands[0] == self.players[i]:
                    num = i

            print "Player "
            print num + 1
            print " has won with an Idiot's Array!"
            self.players[num].money += self.mainPot
            self.mainPot = 0
            self.players[num].money += self.sabaccPot
            self.sabaccPot = 0
            print "Player "
            print num + 1
            print " collects the Sabacc Pot and the Main Pot, bringing their wealth to "
            print self.players[num].money
            print " credits."
        else:
            if not noPositivePureSabacc:
                num = 0

                for i in range(0, len(self.players)):
                    if winningHands[1] == self.players[i]:
                        num = i

                print "Player "
                print num + 1
                print " has won with a Pure Sabacc (23)!"
                self.players[num].money += self.mainPot
                self.mainPot = 0
                self.players[num].money += self.sabaccPot
                self.sabaccPot = 0
                print "Player "
                print num + 1
                print " collects the Sabacc Pot and the Main Pot, bring their wealth to "
                print self.players[num].money
                print " credits."
            else:
                if not noNegativePureSabacc:
                    num = 0

                    for i in range(0, len(self.players)):
                        if winningHands[2] == self.players[i]:
                            num = i

                    print "Player "
                    print num + 1
                    print " has won with a Pure Sabacc (-23)!"
                    self.players[num].money += self.mainPot
                    self.mainPot = 0
                    self.players[num].money += self.sabaccPot
                    self.sabaccPot = 0
                    print "Player "
                    print num + 1
                    print " collects the Sabacc Pot and the Main Pot, bringing their wealth to"
                    print self.players[num].money
                    print " credits."
                else:
                    scores = []

                    for i in range(0, len(self.players)):
                        scores[i] = self.players[i].score

                    smallest = scores[0]
                    largest = scores[0]

                    for i in range(0, len(scores)):
                        if scores[i] > largest:
                            largest = scores[i]
                        elif scores[i] < smallest:
                            smallest = scores[i]

                    if largest == abs(smallest):
                        num = 0

                        for i in range(0, len(self.players)):
                            if largest == self.players[i].score:
                                num = i

                        print "Player "
                        print num + 1
                        print " won the game by having the largest hand with a score of "
                        print self.players[num].score
                        print ". They collect the Main Pot only."
                        self.players[num].money += self.mainPot
                        self.mainPot = 0
                    elif largest > abs(smallest):
                        num = 0

                        for i in range(0, len(self.players)):
                            if largest == self.players[i].score:
                                num = i

                        print "Player "
                        print num + 1
                        print " won the game by having the largest hand with a score of "
                        print self.players[num].score
                        print ". They collect the Main Pot only."
                        self.players[num].money += self.mainPot
                        self.mainPot = 0
                    elif larget < abs(smallest):
                        num = 0

                        for i in range(0, len(self.players)):
                            if smallest == self.players[i].score:
                                num = i

                        print "Player "
                        print num + 1
                        print " won the game by having the largest hand with a score of "
                        print self.players[num].score
                        print ". They collect the Main Pot only."
                        self.players[num].money += self.mainPot
                        self.mainPot = 0

class Player(object):
    def __init__(self):
        self.money = 0
        self.name = ""
        self.fold = False
        self.bombedout = False
        self.score = 0
        self.hand = []

class Card(object):
    def __init__(self, n, v, s, c, f):
        self.name = n
        self.value = v
        self.suit = s
        self.copy = c
        self.face = f
        self.interField = False

class Shift(Thread):
    def __init__(self, threadID, name, t):
        Thread.__init__(self)
        self.threadID = threadID
        self.name = name
        self.t = null
    
    def run(self, time, calling, cardsSize, face, copy, name, interField, cards, amount):
        time.sleep(long(time))
        if calling:
            for i in range(0, cardsSize):
                if (not face) and (copy == -1) and (name == null) and (not interField):
                    cards[i].value += amount
                elif (not face) and (copy == -1) and (name == null) and ((cards[i].value + amount) > 11):
                    if (cards[i].value + amount) == 12:
                        Card c = cards[i]
                        c.name = "Commander"
                        c.value = 12
                        c.face = True
                        cards[i] = c
                    elif (cards[i].value + amount) == 13:
                        Card c = cards[i]
                        c.name = "Mistress"
                        c.value = 13
                        c.face = True
                        cards[i] = c
                    elif (cards[i].value + amount) == 14:
                        Card c = cards[i]
                        c.name = "Master"
                        c.value = 14
                        c.face = True
                        cards[i] = c
                    elif (cards[i].value + amouont) == 15:
                        Card c = cards[i]
                        c.name = "Ace"
                        c.value = 15
                        c.face = True
                        cards[i] = c
                    elif (cards[i].value + amount) > 15:
                        Card c = cards[i]
                        c.name = null;
                        c.value = (c.value + amount) - 15
                        c.face = False
                        cards[i] = c
                elif face:
                    print "Face card"
                elif (not face) and (copy != -1):
                    print "Special card"
                elif interField:
                    print "Card is in Interferrence Field, cannot be shifted"

    def start(self):
        print "Starting Shift Timer Thread"
        self.setupShifts()

        if self.t == null:
            self.t = Shift(self, (self.threadID + 1), "Shift Timer", self.t)
            self.t.start()

    def coinFlip(self):
        num = random.randint(1, 100)

        if (num % 2) == 0:
            return True
        else:
            return False

    def setupShifts(self, shiftNum, shiftAmount):
        num = random.randint(1, 100)

        if num > 0:
            shiftNum += 1

        if (num % 2) == 0:
            shiftNum += 1

        if (num % 8) == 0:
            shiftNum += 1

        newNum = int(math.ceil(num * 0.1))

        if not self.coinFlip(self):
            newNum *= -1

        shiftAmount = newNum

if __name__ == '__main__':
    g = Game(1, "Game Thread", null)
    g.start()
