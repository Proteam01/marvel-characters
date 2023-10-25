package org.marvel.characters.marvelcharacters.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.marvel.characters.marvelcharacters.api.HttpRequestsClient;
import org.marvel.characters.marvelcharacters.model.CharacterDetails;
import org.marvel.characters.marvelcharacters.model.CharacterQuickData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CharacterServiceImpl implements CharacterService {
	
	@Autowired
	private HttpRequestsClient client;
	
	@SuppressWarnings("unchecked")
	public List<CharacterQuickData> getCharacterData() {
		List<CharacterQuickData> characterQuickData = new ArrayList<CharacterQuickData>();
		
		String charactersJSON = client.getCharacters();
		JSONParser parser = new JSONParser();
		try {
			JSONObject root = (JSONObject) parser.parse(charactersJSON);
			JSONObject data = (JSONObject) root.get("data");
			JSONArray results = (JSONArray) data.get("results");
			characterQuickData = (List<CharacterQuickData>) results.stream().map((result) -> {
				JSONObject characterDataRaw = (JSONObject) result;
				CharacterQuickData quickData = new CharacterQuickData();
				quickData.setId(characterDataRaw.get("id").toString());
				quickData.setName(characterDataRaw.get("name").toString());
				quickData.setDescription(characterDataRaw.get("description").toString());
				return quickData;
			}).collect(Collectors.toList());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return characterQuickData;
	}
	
	@SuppressWarnings("unchecked")
	public CharacterDetails getCharacterDetails(String id) {
		String jsonData = client.getCharacterDetails(id);
		List<CharacterDetails> characterQuickDataList = new ArrayList<CharacterDetails>();
		JSONParser parser = new JSONParser();
		CharacterDetails details = new CharacterDetails();
		try {
			JSONObject root = (JSONObject) parser.parse(jsonData);
			JSONObject data = (JSONObject) root.get("data");
			JSONArray results = (JSONArray) data.get("results");
			characterQuickDataList = (List<CharacterDetails>) results.stream().map((result) -> {
				JSONObject characterDataRaw = (JSONObject) result;
				CharacterDetails quickData = new CharacterDetails();
				quickData.setId(characterDataRaw.get("id").toString());
				quickData.setName(characterDataRaw.get("name").toString());
				quickData.setDescription(characterDataRaw.get("description").toString());
				JSONArray urlsJson = (JSONArray) characterDataRaw.get("urls");
				List<String> urls = (List<String>) urlsJson.stream().map(url -> {
					JSONObject urlObject = (JSONObject) url;
					return urlObject.get("url").toString();
				}).collect(Collectors.toList());
				quickData.setUrls(urls);
				return quickData;
			}).collect(Collectors.toList());
			if (characterQuickDataList.size()>0) {
				details = characterQuickDataList.get(0);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return details;
	}
	
	

}
