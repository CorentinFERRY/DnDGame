# 🎮 DnD Game

Un jeu de rôle Donjon & Dragon en Java où vous dirigez un personnage dans des aventures épiques dans votre invit de commande.

---

## 🚀 Lancement du jeu

### Prérequis

- Java 11 ou supérieur
- Un IDE Java (IntelliJ IDEA, Eclipse, etc.) ou compilateur Java

### Instructions

1. **Cloner ou ouvrir le projet**

   ```bash
   cd DnDGame
   ```

2. **Compiler le projet**

   ```bash
   javac -d out src/fr/campus/dndgame/**/*.java
   ```

3. **Lancer le jeu**
   ```bash
   java -cp out fr.campus.dndgame.Main
   ```

Ou directement depuis votre IDE, lancez la classe `Main.java`.

---

## ✨ Fonctionnalités

### 🎭 Créations de personnages

- **Warrior** : Combattant robuste avec endurance élevée
  - Santé : 10
  - Attaque : 5
  - Possibilité d'équiper une arme
- **Wizard** : Magicien puissant basé sur la magie
  - Santé : 6
  - Attaque : 8
  - Possibilité d'équiper un sort

### ⚔️ Système de combat

- Affrontements contre différents ennemis :
  - Gobelin
  - Dragon
  - Sorcier

### 🎒 Équipements

- **Armes** : Augmentent les dégâts réservées au Warrior
- **Sorts** : Capacités spéciales pour les wizards
- **Potions** : Récupération de santé

### 🗺️ Plateau de jeu

- Déplacement sur un plateau
- Exploration de cellules
- Rencontres aléatoires

### 🎲 Mécanique de hasard

- Système de dés pour les probabilités
- Boîte surprise avec récompenses aléatoires

---

## 📋 Structure du projet

```
src/fr/campus/dndgame/
├── Main.java                 # Point d'entrée du jeu
├── characters/               # Classes de personnages
├── enemies/                  # Classes d'ennemis
├── equipments/               # Système d'équipements
├── board/                    # Plateau de jeu
├── game/                     # Logique principale du jeu
└── utils/                    # Utilitaires (menu, dés, etc.)
```

---

```mermaid
classDiagram
direction TB
    class Menu {
	    +getStringInput(message)
	    +getIntInput(message,min,max))
	    +showMessage(message)
	    +displayMenu(title, options[])
    }

    class Game {
	    +Menu menu
	    +Board board
	    +Dice dice
	    +Character player
	    +boolean gameFinished
	    +start()
	    +startGame()
	    +restartGame()
	    +playTurn()
	    +createCharacter()
	    +getCharacter()
	    +isGameFinished()
    }

    class Board {
	    +int size
	    Cell[] cells
	    +isLastCell()
    }

    class Cell {
	    +Enemy enemy
	    +SurpriseBox box
	    +isEmpty()
	    +removeEnemy()
	    +removeBox()
    }

    class Dice {
	    +Random random
	    +int nbrFaces
	    +roll()
    }

    class Character {
	    +String type
	    +int health
	    +int attack
	    +String name
	    +int position
	    +DefensiveEquipment defensiveEquipment
	    +move()
	    +useDefensiveEquip(defensiveEquipment)
    }

    class Warrior {
	    +Weapon weapon
	    +equip(Weapon)
    }

    class Wizard {
	    +Spell spell
	    +equip(Spell)
    }

    class Equipment {
	    +String type
	    +String name
    }

    class OffensiveEQuipment {
	    +int attackBonus
    }

    class DefensiveEquipment {
	    +int effect
    }

    class Weapon {
    }

    class Spell {
    }

    class Potion {
	    +int lifeBonus
    }

    class Enemy {
	    +String name
	    +int attack
	    +int health
	    +attack(Character)
	    +takeDamage(int)
	    +isAlive()
    }

    class Sorcerer {
    }

    class Goblin {
    }

    class Dragon {
    }

    class SurpriseBox {
	    +Equipment equipment
	    +open()
    }

    Game -- Menu
    Game -- Board
    Board -- Cell
    Game -- Dice
    Game -- Character
    Character -- DefensiveEquipment
    Warrior --|> Character
    Wizard --|> Character
    OffensiveEQuipment --|> Equipment
    DefensiveEquipment --|> Equipment
    Weapon --|> OffensiveEQuipment
    Spell --|> OffensiveEQuipment
    Potion --|> DefensiveEquipment
    Warrior -- Weapon
    Wizard -- Spell
    Cell -- Enemy
    Sorcerer --|> Enemy
    Goblin --|> Enemy
    Dragon --|> Enemy
    Cell -- SurpriseBox
```

---

**Amusez-vous bien dans votre aventure ! 🗡️✨**
