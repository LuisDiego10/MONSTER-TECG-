package Card;

class Card {
    public String name;
    private int manaCost=300;

    public Card(){

    }
    public void Action(){

    }
}
class Minion extends Card{
    public int healt;
    int damage;
    public Minion(String minionName, int life, int minionDamage){
        name= minionName;
        healt= life;
        damage= minionDamage;
    }

    public int getHealt() {
        return healt;
    }

    public void setHealt(int healt) {
        this.healt = healt;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }
}
class Secret extends Card{
    String condition;
    String efect;
    public Secret(String secretName, String secretCondition, String secretEfect){
        name= secretName;
        condition= secretCondition;
        efect= secretEfect;
    }

    public String getCondition() {
        return condition;
    }

    public String getEfect() {
        return efect;
    }
}
class Spell extends Card{
    String efect;
    public Spell(String spellName, String spellEfect){
        name= spellName;
        efect= spellEfect;
    }

    public String getEfect() {
        return efect;
    }
}