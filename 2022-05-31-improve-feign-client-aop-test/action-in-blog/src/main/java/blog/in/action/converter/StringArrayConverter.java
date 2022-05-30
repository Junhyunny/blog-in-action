package blog.in.action.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class StringArrayConverter implements AttributeConverter<String[], String> {

    private static final String SPLIT_CHAR = ";";

    @Override
    public String convertToDatabaseColumn(String[] attribute) {
        return attribute == null ? null : String.join(SPLIT_CHAR, attribute);
    }

    @Override
    public String[] convertToEntityAttribute(String dbData) {
        return dbData == null ? new String[]{} : dbData.split(SPLIT_CHAR);
    }
}
