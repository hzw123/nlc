package com.nlc.nraas.tools;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
/***
 * 序列化时间格式
 * @author Dell
 *
 */
public class JsonDateSerializer extends JsonSerializer<Date> {

	private final SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	@Override
	public void serialize(Date date, JsonGenerator json, SerializerProvider sp)
			throws IOException, JsonProcessingException {
		json.writeString(sdf.format(date));
	}

}
