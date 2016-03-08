package com.jsrush.util;

import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * format Timestamp data yyyy-MM-dd
 * @author sunburst
 *
 */
public class DateJsonSerializer extends JsonSerializer<Date> {

	@Override
	public void serialize(Date value, JsonGenerator jgen,
			SerializerProvider provider) throws IOException {
		SimpleDateFormat formatter = new SimpleDateFormat(DateUtil.NORMAL_DATE_FORMAT);
		String formattedDate = formatter.format(value);
		jgen.writeString(formattedDate);
	}


}
