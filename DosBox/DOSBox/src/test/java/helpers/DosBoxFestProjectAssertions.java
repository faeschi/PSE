package helpers;

import interfaces.IOutputter;
import org.fest.assertions.api.Assertions;

public class DosBoxFestProjectAssertions extends Assertions { 
	public static OutputterAssert assertThat(IOutputter actual) {
		return new OutputterAssert(actual);
	}
}
