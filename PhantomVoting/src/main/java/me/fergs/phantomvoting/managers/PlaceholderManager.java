package me.fergs.phantomvoting.managers;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.fergs.phantomvoting.database.VoteStorage;
import me.fergs.phantomvoting.enums.PlaceholderType;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PlaceholderManager extends PlaceholderExpansion {

    private final VoteStorage voteStorage;
    private final VotePartyManager votePartyManager;
    /**
     * Creates a new PlaceholderManager.
     *
     * @param voteStorage      The vote storage.
     * @param votePartyManager The vote party manager.
     */
    public PlaceholderManager(VoteStorage voteStorage, VotePartyManager votePartyManager) {
        this.voteStorage = voteStorage;
        this.votePartyManager = votePartyManager;
    }

    @Override
    public boolean register() {
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            return super.register();
        }
        return false;
    }

    @Override
    public @NotNull String getIdentifier() {
        return "phantomvoting";
    }

    @Override
    public @NotNull String getAuthor() {
        return "f.";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0";
    }

    // PlaceholderAPI will check if the placeholder is persistable.
    // Needed to avoid placeholder being lost on /papi reload.
    @Override
    public boolean persist() {
        return true;
    }

    /**
     * Gets the value of the placeholder.
     *
     * @param player     The player to get the placeholder value for.
     * @param identifier The placeholder identifier.
     * @return The value of the placeholder.
     */
    @Override
    public String onPlaceholderRequest(Player player, @NotNull String identifier) {
        PlaceholderType type = PlaceholderType.fromIdentifier(identifier);
        if (type == null) {
            return "0";
        }
        return type.getValue(voteStorage, votePartyManager, player);
    }
}
