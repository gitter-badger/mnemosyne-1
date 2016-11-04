package org.habv.mnemosyne.pebble;

import com.mitchellbosecke.pebble.extension.AbstractExtension;
import com.mitchellbosecke.pebble.extension.Function;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Herman Barrantes
 * @since 0.1.0
 * @date 01/10/2016
 */
public class MnemosyneExtension extends AbstractExtension {

    @Override
    public Map<String, Function> getFunctions() {
        Map<String, Function> functions = new HashMap<>();
        functions.put(MD5Function.FUNCTION_NAME, new MD5Function());
        functions.put(IsAuthenticated.FUNCTION_NAME, new IsAuthenticated());
        functions.put(IsAnonymous.FUNCTION_NAME, new IsAnonymous());
        functions.put(HasRole.FUNCTION_NAME, new HasRole());
        functions.put(HasAnyRole.FUNCTION_NAME, new HasAnyRole());
        functions.put(Principal.FUNCTION_NAME, new Principal());
        return functions;
    }

}
