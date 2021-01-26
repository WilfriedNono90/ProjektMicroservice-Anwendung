package org.vaadin.example;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

//spring instancie direct cet element , il peut avoir le 
//@postconstructor
//lement que son constructor prend en parametre sera automatiquement construit par Spring
@Service
public class ResultService {
	
	
	private PlayerRepository playerService;
	
	@Autowired
	private KafkaTemplate< String, String> kafkatemplate;
	
	private ResultRepository resultRepository;
	
	public ResultService(ResultRepository resultRepository, PlayerRepository playerService) {
		this.resultRepository = resultRepository;
		this.playerService = playerService;
	}
	
	public List<Result> findAll(){
		return resultRepository.findAll();
	}
	
	public List<Result> findAll(String filter){
		if (filter == null || filter.isEmpty()) {
			return findAll();
		}else {
			return resultRepository.findByIdPlayer(Long.parseLong(filter));
			
		}
	}
	
	public Optional<Result> findById(Long id) {
		return resultRepository.findById(id);
	}
	
	public Result save(Result result) {
		if(playerService.findById( result.getIdplayer() ).isPresent() ) {
			return resultRepository.save(result);
		}
		Player player = new Player();
		player.setId(result.getIdplayer());
		playerService.save(player);
		Result result2 = resultRepository.save(result);
		kafkatemplate.send("neuplayerscore", player.getId().toString());
		return result2;
	}
	
	public boolean delete(Long id) {
		try {
			resultRepository.deleteById(id);
			return true;
		}catch (Exception e) {
			return false;
		}
	}
	
	public Result update(Result result, Long idplayer) {
		result.setIdplayer(idplayer);
		return save(result);
	}
	
	@PostConstruct
	public void dbFüllen() {
		if(resultRepository.count() == 0) {
			resultRepository.save(new Result(null,1L,2L,2L,2L,new Date()));
			resultRepository.save(new Result(null,1L,1L,1L,2L,new Date()));
		}
	}

}
