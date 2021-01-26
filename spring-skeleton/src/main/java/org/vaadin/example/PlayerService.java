package org.vaadin.example;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class PlayerService {
	private PlayerRepository playerRepository;
	
	public PlayerService(PlayerRepository playerRepository) {
		this.playerRepository = playerRepository;
	}
	
	public List<Player> findAll(){
		return playerRepository.findAll();
	}
	
	public Optional<Player> findById(Long id) {
		return playerRepository.findById(id);
	}
	
	public Player save(Player player) {
		return playerRepository.save(player);
	}
	
	public boolean delete(Long id) {
		try {
			playerRepository.deleteById(id);
			return true;
		}catch (Exception e) {
			return false;
		}
	}
	
	public Player update(Player player, Long id) {
		player.setId(id);
		return save(player);
	}

}
