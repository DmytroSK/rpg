package com.game.controller;

import com.game.entity.Player;
import com.game.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

@RestController
@RequestMapping("/rest")
public class PlayerController {


    private final PlayerService playerService;


    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping("/players")
    public ResponseEntity <List<Player>> getAllPlayers() {
        List<Player> list = playerService.getAllPlayers();
        return new ResponseEntity<>(list,HttpStatus.OK);
    }

//    @GetMapping("/players")
//    public ResponseEntity <List<Player>> getPlayers(PlayerFilter filter) {
//        List<Player> list = playerService.filterPlayers(filter);
//        return new ResponseEntity<>(list,HttpStatus.OK);
//    }

    @GetMapping("/players/{id}")
    public ResponseEntity<Player> getPlayer(@PathVariable long id) {
        if (id <= 0) {
            return ResponseEntity.badRequest().build();
        }
        Player player = playerService.getPlayer(id);
        if (player == null) {
          return ResponseEntity.notFound().build();
       }
        return new ResponseEntity<>(player, HttpStatus.OK);
    }

    @PostMapping("/players")
    public ResponseEntity<String> savePlayer(@RequestBody Player player) {
        try{
            playerService.validate(player);
        } catch (IllegalStateException e){
            return ResponseEntity.badRequest().build();
        }
        playerService.savePlayer(player);
        return new ResponseEntity<>("", HttpStatus.OK);
    }

    @PutMapping("/players/{id}")
    public ResponseEntity<String> updatePlayer(@PathVariable Long id) {
        if (id <= 0) {
            return ResponseEntity.badRequest().build();
        }
        Player player = playerService.getPlayer(id);
        if (player == null) {
            return ResponseEntity.notFound().build();
        }
        try{
            playerService.validate(player);
        } catch (IllegalStateException e){
            return ResponseEntity.badRequest().build();
        }
        playerService.savePlayer(player);
        return new ResponseEntity<>("", HttpStatus.OK);
    }

    @DeleteMapping("/players/{id}")
    public ResponseEntity<String> deletePlayer(@PathVariable Long id) {
        if (id <= 0) {
            return ResponseEntity.badRequest().build();
        }
        Player player = playerService.getPlayer(id);
        if (player == null) {
            return ResponseEntity.notFound().build();
        }
        playerService.deletePlayer(id);
        return new ResponseEntity<>("", HttpStatus.OK);
    }

    @GetMapping("/players/count")
    public ResponseEntity<Integer> count() {
        Integer count =  (int)playerService.playersCount();
        return new ResponseEntity<>(count,HttpStatus.OK);
    }
}
