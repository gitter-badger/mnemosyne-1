package org.habv.mnemosyne.util;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * @author Herman Barrantes
 * @since 0.1.0
 * @date 25/10/2016
 */
public class StringUtilTest {

    /**
     * Test of toURL method, of class StringUtil.
     */
    @Test
    public void testToURL() {
        System.out.println("StringUtil.toURL");
        String text = "Lorem ipsum? 41 dolor sit amet.";
        String expResult = "lorem-ipsum-41-dolor-sit-amet";
        String result = StringUtil.toURL(text);
        assertThat(result, is(equalTo(expResult)));
    }

    /**
     * Test of normalize method, of class StringUtil.
     */
    @Test
    public void testNormalize() {
        System.out.println("StringUtil.normalize");
        String text = "Tĥïŝ ĩš â fůňķŷ Šťŕĭńġ";
        String expResult = "This is a funky String";
        String result = StringUtil.normalize(text);
        assertThat(result, is(equalTo(expResult)));
    }

}
