package org.marvel.characters.marvelcharacters.controller;

import java.util.List;

import org.marvel.characters.marvelcharacters.model.CharacterDetails;
import org.marvel.characters.marvelcharacters.model.CharacterQuickData;
import org.marvel.characters.marvelcharacters.service.CharacterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CharactersApi {

	@Autowired
	private CharacterService characterService; 
	
	@RequestMapping(value = "/characters")
	public List<CharacterQuickData> getCharacterData() {
		return characterService.getCharacterData();
	}


	@RequestMapping(value = "/characters/{id}")
	public CharacterDetails getCharacterDetails(@PathVariable(value = "id") String id) {
		return this.characterService.getCharacterDetails(id);
	}

	@RequestMapping(value = "/")
	@ResponseBody()
	public String home() {
		return "characters api";
	}

}
