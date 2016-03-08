package com.jsrush.util;

import java.io.IOException;
import java.sql.Time;
import java.text.SimpleDateFormat;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * format Time data  HH:mm
 * @author sunburst
 *
 */
public class TimeJsonSerializer extends JsonSerializer<Time> {

	@Override
	public void serialize(Time value, JsonGenerator jgen,
			SerializerProvider provider) throws IOException {
		SimpleDateFormat formatter = new SimpleDateFormat(DateUtil.HOUR_FORMAT);
		String formattedDate = formatter.format(value);
		jgen.writeString(formattedDate);
	}


}
