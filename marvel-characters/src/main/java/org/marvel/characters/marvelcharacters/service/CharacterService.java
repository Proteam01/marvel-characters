package org.marvel.characters.marvelcharacters.service;

import java.util.List;

import org.marvel.characters.marvelcharacters.model.CharacterDetails;
import org.marvel.characters.marvelcharacters.model.CharacterQuickData;

public interface CharacterService {
	
	public List<CharacterQuickData> getCharacterData();
	
	
	public CharacterDetails getCharacterDetails(String id);

}
