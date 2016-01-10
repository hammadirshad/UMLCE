package com.mbe.umlce.validation;

import javax.validation.Payload;

public @interface ValidCollection {

	Class<?> elementType();

	/*
	 * Specify constraints when collection element type is NOT constrained
	 * validator.getConstraintsForClass(elementType).isBeanConstrained();
	 */
	Class<?>[] constraints() default {};

	boolean allViolationMessages() default true;

	String message() default "{constraints.collection}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}