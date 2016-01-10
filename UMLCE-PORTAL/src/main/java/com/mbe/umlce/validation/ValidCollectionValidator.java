package com.mbe.umlce.validation;

import java.util.Collection;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidCollectionValidator implements
		ConstraintValidator<ValidCollection, Collection> {
	@Override
	public void initialize(ValidCollection constraintAnnotation) {
	}

	@Override
	public boolean isValid(Collection collection,
			ConstraintValidatorContext context) {
		boolean valid = true;

		if (collection == null) {
			return false;
		}
		if (collection.size() <= 0) {
			return false;
		}

		return valid;
	}

}