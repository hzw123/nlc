package com.nlc.nraas.tools;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
/**
 * 格式化用时
 * @author Dell
 *
 */
public class JsonLongTimeSerializer extends JsonSerializer<Long>{

	@Override
	public void serialize(Long time, JsonGenerator json, SerializerProvider sp)
			throws IOException, JsonProcessingException {
		json.writeString(formatting(time));
	}

	private String formatting(long time){
		StringBuffer sb=new StringBuffer();
		int n=(int) (time/(60*60));
		if(n>0){
			time=time-n*(60*60);
			sb.append(n);
			sb.append("h");
		}
		n=(int) (time/60);
		if(n>0){
			time=time-n*60;
			sb.append(n);
			sb.append("m");
		}
		if(time>0){
			sb.append(n);
			sb.append("s");
		}
		return sb.toString();
	}
}
