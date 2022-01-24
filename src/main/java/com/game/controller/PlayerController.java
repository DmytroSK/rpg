package com.game.controller;

import com.game.entity.Player;
import com.game.service.PlayerNotFoundException;
import com.game.service.PlayerNotValidException;
import com.game.service.PlayerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest")
public class PlayerController {

    private final PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping("/players")
    public ResponseEntity<List<Player>> getAllPlayers(PlayerFilter playerFilter) {
        List<Player> players = playerService.filterPlayers(playerFilter);
        return ResponseEntity.ok(players);
    }

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
    public ResponseEntity<Player> savePlayer(@RequestBody Player player) {
        try {
            return ResponseEntity.ok(playerService.savePlayer(player));
        } catch (PlayerNotValidException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/players/{id}")
    public ResponseEntity<Player> updatePlayer(@PathVariable Long id, @RequestBody UpdatePlayerRequest updatePlayerRequest) {
        if (id <= 0) {
            return ResponseEntity.badRequest().build();
        }
        try {
            return ResponseEntity.ok(playerService.updatePlayer(id, updatePlayerRequest));
        } catch (PlayerNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (PlayerNotValidException e) {
            return ResponseEntity.badRequest().build();
        }

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
    public ResponseEntity<Integer> count(PlayerFilter filter) {
        Integer count = (int) playerService.playersCount(filter);
        return new ResponseEntity<>(count, HttpStatus.OK);
    }
}
