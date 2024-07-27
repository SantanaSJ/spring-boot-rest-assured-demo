package com.example.sampleproject.validator;

import com.example.sampleproject.model.serviceModels.AlbumServiceModel;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;

public class AlbumValidator implements ConstraintValidator<ValidateAlbums, List<AlbumServiceModel>> {

    @Override
    public boolean isValid(List<AlbumServiceModel> albums, ConstraintValidatorContext context) {
        return !albums.isEmpty();
    }
}
