package com.cdutcm.core.util;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * @author zhufj
 * 
 * 2017-05-22 ID转换器，将LONG转成String
 *
 */
public class JsonIDSerializer extends JsonSerializer<Long>{

	@Override
	public void serialize(Long id, JsonGenerator generator, SerializerProvider provider)
			throws IOException, JsonProcessingException {
		String formattedId = id.toString();
		generator.writeString(formattedId);
	}

}
