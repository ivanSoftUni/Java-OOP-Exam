package football.entities.player;

public class Women extends BasePlayer {


    public Women(String name, String nationality, int strength) {
        super(name, nationality, strength);
        setKg(60);
    }

    @Override
    public void stimulation() {
        setStrength(getStrength() + 115);
    }
}
