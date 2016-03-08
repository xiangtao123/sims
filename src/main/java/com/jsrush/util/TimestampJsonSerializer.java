package com.jsrush.util;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * format Timestamp data yyyy-MM-dd HH:mm:ss
 * @author sunburst
 *
 */
public class TimestampJsonSerializer extends JsonSerializer<Timestamp> {

	@Override
	public void serialize(Timestamp value, JsonGenerator jgen,
			SerializerProvider provider) throws IOException {
		SimpleDateFormat formatter = new SimpleDateFormat(DateUtil.NORMAL_SQL_DATE_FORMAT);
		String formattedDate = formatter.format(value);
		jgen.writeString(formattedDate);
	}


}
