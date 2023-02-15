# l2s4-projet-2022

# Equipe

- Camille KASPRZAK
- Bryan DISTRIBUE
- Tom TAFFIN
- Samuel DUBUISSON

# Sujet

[Le sujet 2022](https://www.fil.univ-lille1.fr/portail/index.php?dipl=L&sem=S4&ue=Projet&label=Documents)

# Livrables

## Livrable 1

Le livrable 1 consiste en la génération d'un labyrinthe parfait; ce qui implique déjà de concevoir un squelette de projet contenant quelques classes atomiques telles que les classes des cellules, du labyrinthe, et diffèrents enum. Diffèrents algorithmes sont possibles pour cette génération.

### Atteinte des objectifs

Nous avons implémenté 3 algorithmes diffèrents pour générer le labyrinthe : deux algorithmes récursifs et un algorithme itératif. Pour choisir l'algorithme à utiliser, nous nous sommes basé sur la taille du labyrinthe que l'on cherche à générer. Si le labyrinthe est petit (moins de 50x50), un des algorithmes récursifs (le plus rapide) sera employé. Ce dernier s'essoufle pour un labyrinthe moyen (50x50 à 100x100), donc nous utilisons l'autre algorithme récursif (l'algorithme dit de la recursive division). Pour un labyrinthe grand (plus de 100x100) dont la génération demanderait trop de récursions, nous passons sur l'algorithme itératif.

La base dont nous partons pour ces algorithmes est un tableau à double dimension composés d'instances de cellules, qui elles même sont composées de murs. Les murs sont représentés par une table de hachage qui associe deux types énumérés : le type DIRECTION qui indique de quel mur il s'agit par rapport à la cellule (nord, sud, est, ouest), et du type WALL qui indique le statut du mur (présent, absent, bordure). Nous avons adoptés ces types enum pour simplifier l'écriture de certaines parties du code et nous laisser de la flexibilité pour d'éventuels ajouts futurs, par exemple le type "cassable" pour un mur que le joueur pourrait détruire. Ce type enum remplace un attribut booléen que nous avions choisi au départ pour représenter la présence ou l'absence des murs, mais qui s'est révélé trop restrictif et peu "futureproof".

Une fois cette base de labyrinthe construite, nous pouvons appliquer les divers algorithmes pour définir la bonne configuration de murs nécessaire à la formation d'un labyrinthe parfait. Enfin, une méthode toString (factorisée) permet d'afficher le labyrinthe dans la console en utilisant des caractères spéciaux. Les cases inexplorées par le joueur sont marquées de points d'interrogation. 


### Difficultés restant à résoudre

## Livrable 2

### Atteinte des objectifs

Ce livrable, nous nous sommes concentrés sur les objets utilisables ou équipables par le joueur, ainsi que quelques NPC de base.

Les items se décomposent en plusieurs types :

- Les items utilisables, qui permettent au joueur d'effectuer diverses actions, comme se téléporter, lire un indice, casser un mur, ou gagner la partie.
- Les items équipables, ou stuff, qui permettent au jour d'améliorer ses statistiques : les armes améliorent sa force, l'armure sa défense, et les boucliers sa capacité à esquiver.
- Les matériaux, qui permettent au joueur de crafter divers items (pas encore implémenté).
- Les golds, qui sont la monnaie du jeu.

Côté NPC, nous avons ajouté un chien qui peut être caressé, et un marchand qui peut échanger des items au joueur contre de l'argent

### Difficultés restant à résoudre

## Livrable 3

### Atteinte des objectifs

Ce livrable, nous nous sommes concentrés sur l'implémentation des NPC (personnages non joueur) et des interactions possibles avec eux.

Toutes les entités proposent des interactions diffèrentes avec le joueur : 
- le marchand permet au joueur de lui acheter des items
- le chien peut être caressé
- les villageois parlent et donnent des indications sur l'histoire du jeu et comment gagner
- le couturier est un marchand spécial qui vend un fil d'ariane permettant de gagner le jeu
- le sphinx adresse des énigmes (pas encore implémenté)
- le minotaure est le boss final, le tuer est l'objectif de la fin principale (pas encore implémenté)

Ces actions sont appelées par le biais d'une méthode d'interaction qui se trouve dans la super classe commune à tous ces NPC.
Ces interactions impliquent l'utilisation de scanner et l'input du joueur.
En plus de ces actions, le joueur peut se déplacer dans le labyrinthe, regarder dans son sac, crafter un item, ou encore afficher la map.


### Difficultés restant à résoudre

## Livrable 4

### Atteinte des objectifs

Nous nous sommes concentrés sur les derniers éléments à implémenter pour assurer la cohérence du jeu, et faire en sorte qu'il soit jouable
du début à la fin, ce qui implique d'intégrer des conditions de victoire et de défaite.

Nous avons ajouté un package data, dans lequel nous avons ajouté des enum qui listent l'intégralité des prix du marchand, des énigmes, des craft, et des phrases des villageois. Cette simulation de "base de donnée" permet d'accèder à toutes les données constantes du jeu.

Les combats ont été implémentés, le joueur peut engager un combat avec n'importe quel NPC qu'il rencontre. Ce sont des combats au tour par tour dans lesquels le joueur peut attaquer ou se soigner. Si les points de vie du joueur atteignent 0, il meurt, ce qui consistue la seule façon de perdre au jeu.

Diffèrentes conditions de victoire ont été ajoutées :

- la fin qui consiste à trouver le Minotaure et le tuer, pour récupérer son fil d'ariane qui permet de se téléporter en dehors du labyrinthe
- la fin qui consiste à trouver le Couturier et lui acheter son fil d'ariane
- la fin cachée qui consiste à trouver un des murs extérieurs qui peut être cassé avec la pioche

Les deux premières fins impliquent d'abord de trouver une clé pour ouvrir la trappe du minotaure ou du couturier.

Les indices trouvées dans les parchemins permettent d'orienter vers la clé, le minotaure ou le couturier.

Nous avons également améliorer le rendu du jeu dans le terminal en ajoutant des couleurs et des sauts de ligne, ainsi qu'une manière constante de se déplacer avec les mêmes touches, le tout rendant l'expérience plus fluide et agréable.

### Difficultés restant à résoudre

# Journal de bord

## Semaine 1

- Découverte du sujet.
- Recherche sur les algorithmes de labyrinthe et prise de décision sur les algorithmes que nous allons implémenter.
- Implémentation de ces algorithmes au brouillon, et premières constructions de labyrinthes.
- Élaboration d'une première version du diagramme UML, et création de l'arborescence du projet avec les divers fichiers de classe en suivant ce diagramme.

## Semaine 2

- Redéfinition de certaines propriétés atomiques du projet : 
- Les attributs booléens des cases pour définir les murs sont remplacés par un enum WALLS;
- Normalisation des noms de variables qui réferencent les coordonnées du labyrinthe : x et y sont remplacés par height et length pour éviter les confusions.


## Semaine 3

- Les attributs string définis pour les directions sont remplacés par un enum DIRECTIONS.
- Corrections et perfectionnements des algorithmes de génération de labyrinthes.

## Semaine 4

- Ajout de EDGE dans l'enum Wall pour définir les murs au bord du labyrinthe.
- Amélioration de l'affichage du labyrinthe (nouveaux murs, possibilité de personnaliser dans le futur).
- Poursuite des tests (Maze, Cell).
- Finition Javadoc (Maze, Cell).
- Commencement de la partie modélisation des personnages.

## Semaine 5

- Avancement modélisation des personnages
- Commencement modélisation des items
- Création de nouvelles classes pour chaque algortihme de création de labyrinthe
- Création d'un package et d'une classe util, où on implémente toutes les fonctions annexes aux créations de labytinthe
- Création du Makefile

## Semaine 6

- Réflexion sur les manières de modéliser les items
- Ajout d'un nombre d'utilisations pour les items
- Fix constructeur et tests de Maze (suite à la création de nouvelles classes pour chaque algorithme)
- Modifications méthodes dans Util
- Création des mouvements de fin de tour pour les NPC

## Semaine 7

- Modélisation personnages spécifiques
- Modélisation items spécifiques
- Ajout de nouveaux attributs pour plusieurs classes, afin de pouvoir remontrer dans l'arbre des classes plus facilement
- Refonte de la table de hashage qui concerne les items et leur quantité (remplacement de l'item par son nom)

## Semaine 8

- Etablissement de la quête principale : battre le minotaure qui se trouvera au centre du labyrinthe. Le joueur devra s'équiper et craft des objets pour l'emporter
- Ajout de nouveaux personnages (minotaure, villageois)
- Ajout de nouveaux items
- Avancement sur la table de craft
- Interactions entre personnages (buy/sell avec le trader par exemple)

## Semaine 9

- Création d'un premier gameMain pour avoir un aperçu d'un jeu simplifié
- Création nouveaux items d'équipement (armure, bouclier)
- Ajout de nouvelles manières d'obtenir des indices (parchemins, sphynx)
- Génération d'items au début de la partie
- Méthodes qui permettent l'intéraction entre les personnages

## Semaine 10

- Gestion des actions et de l'affichage
- Corrections de bugs
- Réalisation de la Javadoc
- Reprise des tests

## Semaine 11

- Poursuite des tests
- Ajouts de nouveaux objets
- Gestion des actions(backpack et items) et de l'affichage
- Ajout des conditions de victoires (trois fins)

## Semaine 12

- Poursuite des tests
- Ajout des couleurs, des déplacements ZQSD pour l'ergonomie
- Implémentation des indices et des énigmes du sphynx
- Equilibrage du jeu
- Génération des UML

## How:  

>Il faut exécuter les commandes suivantes à la racine du projet:

### Classes: 

Pour compiler les classes du projet, il faut entrer la commande:  
`make cls`  

### MazeMain:  

Pour exécuter le fichier MazeMain, il faut entrer le commande suivante, après avoir compilé les classes du projet:  
`make game <numéro d'algorithme de génération>` 
Nous disposons de 3 algorithmes de génération différents, le paramètre sera donc un entier compris entre 1 et 3.  

### Jar:

Pour créer le fichier jar excéutable, il faut entrer la commande suivante, après avoir compilé les classes du projet: 
`make jar`

Pour exécuter le jar, il faut entrer la commande suivante:
`java -jar game.jar <numéro d'algorithme de génération>`
Nous disposons de 3 algorithmes de génération différents, le paramètre sera donc un entier compris entre 1 et 3.  

### Tests:  

Pour compiler les tests, il faut entrer la commande :  
`make tests`  

Pour exécuter un test, il faut entrer la commande :  
`java -jar test4poo.jar <fichier de test>`  
Par exemple, pour exécuter les tests de la classe Maze:  
`java -jar test4poo.jar mazegame.MazeTest`  

### Documentation:

Pour générer la documentation, il faut entrer la commande:  
`make docs`

### Clean
Pour supprimer les dossiers et fichiers nouvellement créés, il faut entrer la commande:
`make clean`
