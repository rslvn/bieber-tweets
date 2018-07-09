package org.interview;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by resulav on 09.07.2018.
 */
public class AppConstantTest {

    @Test
    public void testConstructor(){
        Assert.assertNotNull("null AppConstant",new AppConstant());
    }
}
