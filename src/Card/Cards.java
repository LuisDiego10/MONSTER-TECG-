package Card;

import com.fasterxml.jackson.annotation.JsonCreator;

public class Cards {
    public Minion[] minion;
    public Secret[] secrets;
    public Spell[] spell;
}
