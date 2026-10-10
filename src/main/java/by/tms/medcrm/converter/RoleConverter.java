package by.tms.medcrm.converter;

import by.tms.medcrm.entity.Role;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply=true)
public class RoleConverter implements AttributeConverter<Role,String> {

    @Override
    public String convertToDatabaseColumn(Role attribute) {
        if (attribute == null) {
            return null;
        }
        //Exclude "ROLE_"
        else return attribute.name().toLowerCase().substring(5);
    }

    @Override
    public Role convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        else return Role.valueOf("ROLE_" + dbData.toUpperCase());
    }
}
