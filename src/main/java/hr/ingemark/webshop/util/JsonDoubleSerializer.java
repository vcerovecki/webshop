package hr.ingemark.webshop.util;

import java.io.IOException;
import java.text.DecimalFormat;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class JsonDoubleSerializer extends JsonSerializer<Double> {

	@Override
	public void serialize(Double value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
		if (null == value) {
			gen.writeNull();
        } else {
            gen.writeNumber((int)(value * 100 + 0.5) /100.0);
        }
	}

}
