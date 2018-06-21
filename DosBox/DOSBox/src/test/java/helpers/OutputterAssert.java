package helpers;

import interfaces.IOutputter;

import org.fest.assertions.api.AbstractAssert;
import org.fest.assertions.api.Assertions;

public class OutputterAssert extends AbstractAssert<OutputterAssert, IOutputter> {

	  public OutputterAssert(IOutputter actual) {
		  super(actual, OutputterAssert.class);
	  }

	  public static OutputterAssert assertThat(IOutputter actual) {
		  return new OutputterAssert(actual);
	  }

	  public OutputterAssert contains(String containedText) {
		  String outputterText = actual.toString();
		  Assertions.assertThat(outputterText.contains(containedText)).isTrue();
		  return this;
	  }
	  
	  public OutputterAssert isEmpty() {
		  Assertions.assertThat(actual.toString().length()).isEqualTo(0);
		  return this; // return the current assertion for method chaining
	  }
	}
