package dominion;

public class Card {
	public int pointVictoire;
	public int prix;
	public int valeur;
	public String nom;
	public String type;
	public int id;

	/*
	 * adventurerD, ambassador, baron, council_room, cutpurse, embargo, feast,
	 * gardens, great_hall, mine
	 * 
	 * And then 3 from this list: minion, outpost, remodel, salvager, sea_hag,
	 * smithy, steward, treasure_map, tribute, village
	 */
	// adventurer
	public Card(int id) {
		this.id = id;
		switch (id) {
		case 1:
			pointVictoire = 0;
			prix = 0;
			valeur = 1;
			nom = "copper";
			type = "tresor";
			break;
		case 2:
			pointVictoire = 0;
			prix = 3;
			valeur = 2;
			nom = "silver";
			type = "tresor";
			break;
		case 3:
			pointVictoire = 0;
			prix = 4;
			valeur = 3;
			nom = "gold";
			type = "tresor";
			break;
		case 4:
			pointVictoire = 1;
			prix = 2;
			valeur = 0;
			nom = "estate";
			type = "victory";
			break;
		case 5:
			pointVictoire = 3;
			prix = 5;
			valeur = 0;
			nom = "duchy";
			type = "victory";
			break;
		case 6:
			pointVictoire = 3;
			prix = 5;
			valeur = 0;
			nom = "province";
			type = "victory";
			break;
		case 7:
			pointVictoire = -1;
			prix = 0;
			valeur = 0;
			nom = "curse";
			type = "cursee";
			break;
		case 8:
			pointVictoire = 1;
			prix = 6;
			valeur = 0;
			nom = "adventurer";
			type = "action";
			break;
		case 9:
			pointVictoire = 0;
			prix = 3;
			valeur = 0;
			nom = "ambassador";
			type = "action";
			break;
		// modify from here
		case 10:
			pointVictoire = 0;
			prix = 4;
			valeur = 0;
			nom = "baron";
			type = "action";
			break;
		case 11:
			pointVictoire = 0;
			prix = 5;
			valeur = 0;
			nom = "council_room";
			type = "action";
			break;
		case 12:
			pointVictoire = 0;
			prix = 4;
			valeur = 0;
			nom = "cutpurse";
			type = "action";
			break;
		case 13:
			pointVictoire = 0;
			prix = 2;
			valeur = 0;
			nom = "embargo";
			type = "action";
			break;
		case 14:
			pointVictoire = 0;
			prix = 4;
			valeur = 0;
			nom = "feast";
			type = "action";
			break;
		case 15:
			pointVictoire = 1;
			prix = 6;
			valeur = 0;
			nom = "garden";
			type = "victory";
			break;
		case 16:
			pointVictoire = 1;
			prix = 3;
			valeur = 0;
			nom = "great_hall";
			type = "action";
			break;
		case 17:
			pointVictoire = 0;
			prix = 5;
			valeur = 0;
			nom = "mine";
			type = "action";
			break;
		case 18:
			pointVictoire = 0;
			prix = 3;
			valeur = 0;
			nom = "village";
			type = "action";
			break;
		case 19:
			pointVictoire = 0;
			prix = 4;
			valeur = 0;
			nom = "smithy";
			type = "action";
			break;
		case 20:
			pointVictoire = 0;
			prix = 4;
			valeur = 0;
			nom = "salvager";
			type = "action";
			break;

		// etc etc etc
		}
	}

	public String getType() {
		return this.type;
	}

	public String getNom() {
		return this.nom;
	}

	public int getValeur() {
		return this.valeur;
	}

	public int getPrix() {
		return this.prix;
	}

	public int getPointV() {
		return this.pointVictoire;
	}

	public int getId() {
		return this.id;
	}

	public void print() {
		System.out.println(this.nom + "\n" + "id : " + this.id + "\n" + "price : " + this.prix + "\n");
	}
}
