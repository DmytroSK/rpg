package com.game.service;

import com.game.controller.PlayerFilter;
import com.game.controller.UpdatePlayerRequest;
import com.game.entity.Player;

import java.util.List;

public interface PlayerService {

    List<Player> getAllPlayers();

    Player savePlayer(Player player);

    Player getPlayer(long id);

    void deletePlayer(long id);

    Player updatePlayer(long id, UpdatePlayerRequest updatePlayerRequest);

    void validate(Player player);

    long playersCount(PlayerFilter filter);

    List<Player> filterPlayers(PlayerFilter filter);


//    List<Player> filterPlayers(PlayerFilter filter);


}

