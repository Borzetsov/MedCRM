package by.tms.medcrm.converter;

import by.tms.medcrm.entity.AppointmentStatusType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class AppointmentStatusTypeConverter implements AttributeConverter<AppointmentStatusType, String> {

    @Override
    public String convertToDatabaseColumn(AppointmentStatusType attribute) {
        if (attribute == null) {
            return null;
        } else {
            return attribute.name().toLowerCase();
        }
    }

    @Override
    public AppointmentStatusType convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        } else {
            return AppointmentStatusType.valueOf(dbData.toUpperCase());
        }
    }
}
