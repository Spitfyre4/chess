package model;

import java.util.Collection;

public record GamesData(Collection<GameData> games) {
    public GameData getGame(int index) {
        if (index < 0 || index >= games.size()) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + games.size());
        }
        return games.stream().skip(index).findFirst().orElse(null);
    }
}
