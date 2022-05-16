package br.com.vvaug.deliverycenter.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MapperUtils {

	private static final ObjectMapper objectMapper = new ObjectMapper();
	
	public static <T> T toObject(String json, Class<T> type) {
		try {
			return objectMapper.readValue(json, type);
		} catch (JsonMappingException e) {
			log.error("Error when serializing message: {}", json);
			throw new RuntimeException();
		} catch (JsonProcessingException e) {
			log.error("Error when serializing message: {}", json);
			throw new RuntimeException();
		}
	}
	
	public static <T> String toJson(T object) {
		try {
			return objectMapper.writeValueAsString(object);
		} catch (JsonMappingException e) {
			log.error("Error when deserializing message: {}", object);
			throw new RuntimeException();
		} catch (JsonProcessingException e) {
			log.error("Error when deserializing message: {}", object);
			throw new RuntimeException();
		}
	}
}
