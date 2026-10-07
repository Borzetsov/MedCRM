package by.tms.medcrm.converter;

import by.tms.medcrm.entity.GenderType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class GenderTypeConverter implements AttributeConverter<GenderType, String> {

    @Override
    public String convertToDatabaseColumn(GenderType attribute) {
        if (attribute == null) {
            return null;
        }
        if (attribute.name().equals("MALE")) return "M";
        else if (attribute.name().equals("FEMALE")) return "F";
        else return null;
    }

    @Override
    public GenderType convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        else if (dbData.equals("M")) return GenderType.MALE;
        else if (dbData.equals("F")) return GenderType.FEMALE;
        else return null;
    }
}
