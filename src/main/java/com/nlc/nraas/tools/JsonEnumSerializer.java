package com.nlc.nraas.tools;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
/***
 * 序列化枚举
 * @author Dell
 *
 */
@SuppressWarnings("rawtypes")
public class JsonEnumSerializer extends JsonSerializer<Enum> {

	@Override
	public void serialize(Enum enum1, JsonGenerator json, SerializerProvider sp)
			throws IOException, JsonProcessingException {
		json.writeString(enum1.toString());
	}

}
