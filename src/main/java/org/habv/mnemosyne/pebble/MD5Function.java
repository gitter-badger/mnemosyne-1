package org.habv.mnemosyne.pebble;

import com.mitchellbosecke.pebble.extension.Function;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import static org.springframework.util.DigestUtils.md5DigestAsHex;

/**
 * @author Herman Barrantes
 * @since 0.1.0
 * @date 01/10/2016
 */
public class MD5Function implements Function {

    /**
     * Function name: <code>md5()</code>.
     */
    public static final String FUNCTION_NAME = "md5";
    private static final String EMAIL = "email";
    private final List<String> argumentNames;

    public MD5Function() {
        this.argumentNames = Collections.singletonList(EMAIL);
    }

    @Override
    public Object execute(Map<String, Object> args) {
        String email = (String) args.get(EMAIL);
        return md5DigestAsHex(email.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public List<String> getArgumentNames() {
        return argumentNames;
    }

}
