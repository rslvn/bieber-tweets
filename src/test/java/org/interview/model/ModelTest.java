/**
 * 
 */
package org.interview.model;

import org.junit.Test;
import org.springframework.util.Assert;

import pl.pojo.tester.api.assertion.Assertions;
import pl.pojo.tester.api.assertion.Method;

/**
 * @author resulav
 *
 */
public class ModelTest {

	/**
	 * Message model tests
	 */
	@Test
	public void testMessage() {
		Assertions.assertPojoMethodsFor(Message.class).testing(Method.CONSTRUCTOR, Method.GETTER).areWellImplemented();
	}

	/**
	 * Author model tests
	 */
	@Test
	public void testAuthor() {
		Assertions.assertPojoMethodsFor(Author.class).testing(Method.CONSTRUCTOR, Method.GETTER).areWellImplemented();
	}

	/**
	 * Author model hash codes same tests
	 */
	@Test
	public void testAuthorHashSame() {
		Author author1 = new Author(1L, System.currentTimeMillis(), null, null);
		Author author2 = new Author(1L, System.currentTimeMillis(), null, null);
		Assert.state(author1.hashCode() == author2.hashCode(), "Hash codes not same");
	}

	/**
	 * Author model hash codes not same tests
	 */
	@Test
	public void testAuthorHashNotSame() {
		Author author1 = new Author(2L, System.currentTimeMillis(), null, null);
		Author author2 = new Author(3L, System.currentTimeMillis(), null, null);
		Assert.state(author1.hashCode() != author2.hashCode(), "Hash codes same");
	}
}
