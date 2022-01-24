package com.game.service;

import com.game.controller.PlayerFilter;
import com.game.controller.PlayerOrder;
import com.game.entity.Player;
import com.game.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
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
    public void savePlayer(Player player) {

        playerRepository.save(player);
    }

    @Override
    public void deletePlayer(long id) {

        playerRepository.deleteById(id);
    }


    @Override
    public void validate(Player player) {
        if(player.getName() == null || player.getTitle() == null || player.getRace() == null
                || player.getProfession() == null || player.getBirthday() == null ||
        !(player.getExperience() >=0 && player.getExperience() <=10_000_000) || player.getName().length() > 12 ||
                player.getTitle().length()>30 || player.getBirthday().getTime() <0) throw new IllegalStateException();

    }

    @Override
    public long playersCount() {
        return playerRepository.count();
    }




//    @Override
//    public List<Player> filterPlayers(PlayerFilter filter) {
//        return playerRepository.findAll(
//                PageRequest.of(
//                        filter.getPageNumber() != null? filter.getPageNumber(): 0,
//                        filter.getPageSize() != null? filter.getPageSize(): 10,
//                        filter.getOrder() != null? Sort.by(filter.getOrder().getFieldName()): Sort.by(PlayerOrder.ID.getFieldName())
//                )
//        )
//                  .filter(player -> filter.getName() == null || player.getName().contains(filter.getName()))
//                 .filter(player -> filter.getTitle() == null || player.getTitle().contains(filter.getTitle()))
//                 .filter(player -> filter.getRace() == null || player.getRace().equals(filter.getRace()))
//                 .filter(player -> filter.getProfession() == null || player.getProfession().equals(filter.getProfession()))
//                 .filter(player -> filter.getAfter() == null || player.getBirthday().getTime() > filter.getAfter())
//                 .filter(player -> filter.getBefore() == null || player.getBirthday().getTime() < filter.getBefore())
//                 .filter(player -> filter.getBanned() == null || player.getBanned().equals(filter.getBanned()))
//                 .filter(player -> filter.getMinExperience() == null || player.getExperience() >= filter.getMinExperience())
//                 .filter(player -> filter.getMaxExperience() == null || player.getExperience() <= filter.getMaxExperience())
//                 .filter(player -> filter.getMinLevel() == null || player.getLevel() >= filter.getMinLevel())
//                 .filter(player -> filter.getMaxLevel() == null || player.getLevel() <= filter.getMaxLevel())
//                 .stream().collect(Collectors.toList());
//    }
}

