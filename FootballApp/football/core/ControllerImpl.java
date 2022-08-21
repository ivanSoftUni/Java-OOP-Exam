package football.core;


import football.entities.field.ArtificialTurf;
import football.entities.field.Field;
import football.entities.field.NaturalGrass;
import football.entities.player.Men;
import football.entities.player.Player;
import football.entities.player.Women;
import football.entities.supplement.Liquid;
import football.entities.supplement.Powdered;
import football.entities.supplement.Supplement;
import football.repositories.SupplementRepository;
import football.repositories.SupplementRepositoryImpl;

import java.util.ArrayList;
import java.util.Collection;

import static football.common.ConstantMessages.*;
import static football.common.ExceptionMessages.*;

public class ControllerImpl implements Controller {

    private SupplementRepository supplement;
    private Collection<Field> fields;

    public ControllerImpl() {
        this.supplement = new SupplementRepositoryImpl();
        this.fields = new ArrayList<>();
    }

    @Override
    public String addField(String fieldType, String fieldName) {
        Field field = null;

        switch (fieldType) {
            case "ArtificialTurf":
                field = new ArtificialTurf(fieldName);
                break;
            case "NaturalGrass":
                field = new NaturalGrass(fieldName);
                break;
            default:
                throw new NullPointerException(INVALID_FIELD_TYPE);
        }
        fields.add(field);

        return String.format(SUCCESSFULLY_ADDED_FIELD_TYPE, fieldType);
    }

    @Override
    public String deliverySupplement(String type) {
        Supplement supplement1 = null;
        switch (type) {
            case "Liquid":
                supplement1 = new Liquid();
                break;
            case "Powdered":
                supplement1 = new Powdered();
                break;
            default:
                throw new IllegalArgumentException(INVALID_SUPPLEMENT_TYPE);
        }
        supplement.add(supplement1);

        return String.format(SUCCESSFULLY_ADDED_SUPPLEMENT_TYPE, type);
    }

    @Override
    public String supplementForField(String fieldName, String supplementType) {
        Supplement supplement1 = supplement.findByType(supplementType);
        Field field = fields.stream()
                .filter(f -> f.getName().equals(fieldName))
                .findFirst().orElse(null);
        if (supplement1 == null) {
            throw new IllegalArgumentException(String.format(NO_SUPPLEMENT_FOUND, supplementType));
        }
        field.getSupplement().add(supplement1);
        supplement.remove(supplement1);

        return String.format(SUCCESSFULLY_ADDED_SUPPLEMENT_IN_FIELD, supplementType, fieldName);
    }

    @Override
    public String addPlayer(String fieldName, String playerType, String playerName, String nationality, int strength) {
        Field field = fields.stream()
                .filter(f -> f.getName().equals(fieldName))
                .findFirst().orElse(null);
        Player player = null;
        if (playerType.equals("Women")) {
            if (field.getClass().getSimpleName().equals("ArtificialTurf")) {
                player = new Women(playerName, nationality, strength);
            } else {
                return String.format(FIELD_NOT_SUITABLE);
            }
        } else if (playerType.equals("Men")) {
            if (field.getClass().getSimpleName().equals("NaturalGrass")) {
                player = new Men(playerName, nationality, strength);
            } else {
                return String.format(FIELD_NOT_SUITABLE);
            }
        } else {
            throw new IllegalArgumentException(INVALID_PLAYER_TYPE);
        }
        field.getPlayers().add(player);

        return String.format(SUCCESSFULLY_ADDED_PLAYER_IN_FIELD, playerType, fieldName);
    }

    @Override
    public String dragPlayer(String fieldName) {
        Field field = fields.stream()
                .filter(f -> f.getName().equals(fieldName))
                .findFirst().orElse(null);

        field.drag();

        return String.format(PLAYER_DRAG, field.getPlayers().stream().count());
    }

    @Override
    public String calculateStrength(String fieldName) {
        Field field = fields.stream()
                .filter(f -> f.getName().equals(fieldName))
                .findFirst().orElse(null);

        int strenghtSum = field.getPlayers().stream()
                .mapToInt(Player::getStrength).sum();

        return String.format(STRENGTH_FIELD, fieldName, strenghtSum);
    }

    @Override
    public String getStatistics() {
        StringBuilder sb = new StringBuilder();
        for (Field field : fields) {
            sb.append(field.getInfo());
            sb.append(System.lineSeparator());
        }

        return sb.toString().trim();
    }
}
