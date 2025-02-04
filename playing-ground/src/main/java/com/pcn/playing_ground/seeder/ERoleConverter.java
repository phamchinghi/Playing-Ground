package com.pcn.playing_ground.seeder;

import com.pcn.playing_ground.entity.ERole;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * @pre Handle cases do not match upper/lowercase role from enum to database
 * */
@Converter(autoApply = true)
public class ERoleConverter implements AttributeConverter<ERole, String> {
    @Override
    public String convertToDatabaseColumn(ERole role) {
        return role.name();
    }

    @Override
    public ERole convertToEntityAttribute(String dbData) {
        return ERole.valueOf(dbData.toUpperCase()); // Convert to uppercase before matching enum
    }
}
