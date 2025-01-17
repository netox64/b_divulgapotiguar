package com.oficinadobrito.api;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@ActiveProfiles("test")
@SpringBootTest
class DivulgapotiguarApplicationTests {

	@Disabled("this test disable for jump")
	@DisplayName("I tested that the application starts without any errors according to the log")
	@Test
	void TestInitAplicationSuccess(){
		// Given - Arrange
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		PrintStream originalOut = System.out;
		System.setOut(new PrintStream(outputStream));

		// when - Act
		DivulgapotiguarApplication.main(new String[0]);

		// Then - Assert
		String regex = ".*Tomcat started on port 8080.*|.*Started DivulgapotiguarApplication.*";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(outputStream.toString());

		Assertions.assertTrue(matcher.find(), "When started, the application should log messages indicating Tomcat is running on port 8080 and the application started successfully.");
		System.setOut(originalOut);
	}

}
