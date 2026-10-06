package by.tms.medcrm.converter;

import by.tms.medcrm.entity.AccountType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply=true)
public class AccountTypeConverter implements AttributeConverter<AccountType,String> {

    @Override
    public String convertToDatabaseColumn(AccountType attribute) {
        if (attribute == null) {
            return null;
        }
        else return attribute.name().toLowerCase();
    }

    @Override
    public AccountType convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        else return AccountType.valueOf(dbData.toUpperCase());
    }
}
