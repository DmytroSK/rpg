package com.game.service;

import com.game.controller.PlayerFilter;
import com.game.entity.Player;

import java.util.List;

public interface PlayerService {

    List<Player> getAllPlayers();

    void savePlayer(Player player);

    Player getPlayer(long id);

    void deletePlayer(long id);

//    void updatePlayer(long id);

    void validate(Player player);

    long playersCount();



//    List<Player> filterPlayers(PlayerFilter filter);


}

