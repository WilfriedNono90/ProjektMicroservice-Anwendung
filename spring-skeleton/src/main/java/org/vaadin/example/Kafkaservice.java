package org.vaadin.example;

import java.util.Optional;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.json.JsonMapper;

@Service
public class Kafkaservice {
	
	@Autowired
	private PlayerService playerservice;

	
	//@KafkaListener(topics = "player", groupId = "playerDB_abgleichen")
	public void onDbAbgleichen(ConsumerRecord<String, String> cr) throws Exception {
		System.out.println("******************************");
		System.out.println(cr.key());
		Teilnehmer teilnehmer = convertJsonToPlayer(cr.value());
		System.out.println("wir haben den Player : "+teilnehmer.getId());
		//System.out.println("******* prozess start. Teilnehmer wird gespeichert wenn er unvorhanden ist");
		Optional<Player> player = playerservice.findById(teilnehmer.getId());
		if (player.isPresent()) {
			
		}else {
			System.out.println("Der ist neu und wird gespeichert");
			playerservice.save(new Player(teilnehmer.getId()));
		}
		
	}
	
	@KafkaListener(topics = "neuplayer", groupId = "neuplayer")
	public void onNeuPlayer(ConsumerRecord<String, String> cr) throws Exception {
		System.out.println("******************************");
		System.out.println(cr.key());
		Teilnehmer teilnehmer = convertJsonToPlayer(cr.value());
		System.out.println("wir haben den Player : "+teilnehmer.getId());
		//System.out.println("******* prozess start. Teilnehmer wird gespeichert wenn er unvorhanden ist");
		Optional<Player> player = playerservice.findById(teilnehmer.getId());
		if (player.isPresent()) {
			
		}else {
			System.out.println("Der ist neu und wird gespeichert");
			playerservice.save(new Player(teilnehmer.getId()));
		}
		
	}
	
	public Teilnehmer convertJsonToPlayer(String jsonString) throws Exception {
		JsonMapper jsonMapper = new JsonMapper();
		Teilnehmer player = jsonMapper.readValue(jsonString, Teilnehmer.class);
		return player;
	}

}
