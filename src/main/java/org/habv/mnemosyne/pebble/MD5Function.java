package org.habv.mnemosyne.pebble;

import com.mitchellbosecke.pebble.extension.Function;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.util.DigestUtils;

/**
 * @author Herman Barrantes
 * @since 01/10/2016
 */
public class MD5Function implements Function {
    
    public static final String FUNCTION_NAME = "md5";
    
    @Override
    public Object execute(Map<String, Object> args) {
        String email = (String) args.get("email");
        return DigestUtils.md5DigestAsHex(email.getBytes(StandardCharsets.UTF_8));
    }
    
    @Override
    public List<String> getArgumentNames() {
        List<String> names = new ArrayList<>();
        names.add("email");
        return names;
    }
    
}
