package com.game.service;

import com.game.controller.PlayerFilter;
import com.game.controller.UpdatePlayerRequest;
import com.game.entity.Player;
import com.game.repository.PlayerRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PlayerServiceImpl implements PlayerService {

    private final PlayerRepository playerRepository;

    public PlayerServiceImpl(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Override
    public List<Player> getAllPlayers() {
        return playerRepository.findAll();
    }

    @Override
    public Player getPlayer(long id) {
        Player player = null;
        Optional<Player> optional = playerRepository.findById(id);
        if (optional.isPresent()) {
            player = optional.get();
        }
        return player;
    }

    @Override
    public Player savePlayer(Player player) {
        validate(player);
        player.setExperience(player.getExperience());
        return playerRepository.save(player);
    }

    @Override
    public void deletePlayer(long id) {
        playerRepository.deleteById(id);
    }

    @Override
    public Player updatePlayer(long id, UpdatePlayerRequest updatePlayerRequest) {
        Player player = getPlayer(id);
        if (player == null) {
            throw new PlayerNotFoundException();
        }
        if (updatePlayerRequest.getName() != null) {
            player.setName(updatePlayerRequest.getName());
        }
        if (updatePlayerRequest.getTitle() != null) {
            player.setTitle(updatePlayerRequest.getTitle());
        }
        if (updatePlayerRequest.getBirthday() != null) {
            player.setBirthday(new Date(updatePlayerRequest.getBirthday()));
        }
        if (updatePlayerRequest.getRace() != null) {
            player.setRace(updatePlayerRequest.getRace());
        }
        if (updatePlayerRequest.getBanned() != null) {
            player.setBanned(updatePlayerRequest.getBanned());
        }
        if (updatePlayerRequest.getExperience() != null) {
            player.setExperience(updatePlayerRequest.getExperience());
        }
        if (updatePlayerRequest.getProfession() != null) {
            player.setProfession(updatePlayerRequest.getProfession());
        }
        validate(player);
        return savePlayer(player);
    }


    @Override
    public void validate(Player player) {
        if (player.getName() == null
            || player.getTitle() == null
            || player.getRace() == null
            || player.getProfession() == null
            || player.getBirthday() == null
            || player.getExperience() < 0
            || player.getExperience() >= 10_000_000
            || player.getName().length() > 12
            || player.getTitle().length() > 30
            || player.getBirthday().getTime() < 0) {
            throw new PlayerNotValidException();
        }

    }

    @Override
    public long playersCount(PlayerFilter filter) {
        filter.setPageNumber(0);
        filter.setPageSize(Integer.MAX_VALUE);
        return filterPlayers(filter).size();
    }


    @Override
    public List<Player> filterPlayers(PlayerFilter filter) {
        return playerRepository.findAll()
                               .stream()
                               .filter(player -> filter.getName() == null || player.getName().contains(filter.getName()))
                               .filter(player -> filter.getTitle() == null || player.getTitle().contains(filter.getTitle()))
                               .filter(player -> filter.getRace() == null || player.getRace().equals(filter.getRace()))
                               .filter(player -> filter.getProfession() == null || player.getProfession().equals(filter.getProfession()))
                               .filter(player -> filter.getAfter() == null || player.getBirthday().getTime() > filter.getAfter())
                               .filter(player -> filter.getBefore() == null || player.getBirthday().getTime() < filter.getBefore())
                               .filter(player -> filter.getBanned() == null || player.getBanned().equals(filter.getBanned()))
                               .filter(player -> filter.getMinExperience() == null || player.getExperience() >= filter.getMinExperience())
                               .filter(player -> filter.getMaxExperience() == null || player.getExperience() <= filter.getMaxExperience())
                               .filter(player -> filter.getMinLevel() == null || player.getLevel() >= filter.getMinLevel())
                               .filter(player -> filter.getMaxLevel() == null || player.getLevel() <= filter.getMaxLevel())
                               .skip((long) filter.getPageSize() * filter.getPageNumber())
                               .limit(filter.getPageSize())
                               .collect(Collectors.toList());
    }
}

