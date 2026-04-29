package loan.system.com.Book.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class GenderSerializer extends JsonSerializer<String> {

    @Override
    public void serialize(String gender, JsonGenerator gen,
                          SerializerProvider serializerProvider) throws IOException {
        String formateGender;
        if("Male".equalsIgnoreCase(gender)){
            formateGender = "M";
        } else if ("Female".equalsIgnoreCase(gender)) {
            formateGender = "F";
        } else {
            formateGender = null;
        }
        gen.writeString(formateGender);
    }
}
