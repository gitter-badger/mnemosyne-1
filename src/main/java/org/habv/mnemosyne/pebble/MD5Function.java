package org.habv.mnemosyne.pebble;

import com.mitchellbosecke.pebble.extension.Function;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import static org.springframework.util.DigestUtils.md5DigestAsHex;

/**
 * @author Herman Barrantes
 * @since 0.1.0
 * @date 01/10/2016
 */
public class MD5Function implements Function {

    public static final String FUNCTION_NAME = "md5";
    private static final String EMAIL = "email";

    @Override
    public Object execute(Map<String, Object> args) {
        String email = (String) args.get(EMAIL);
        return md5DigestAsHex(email.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public List<String> getArgumentNames() {
        List<String> names = new ArrayList<>();
        names.add(EMAIL);
        return names;
    }

}
