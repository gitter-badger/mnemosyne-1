package org.habv.mnemosyne.pebble;

import com.mitchellbosecke.pebble.extension.Function;
import java.util.List;
import java.util.Map;

/**
 * @author Herman Barrantes
 * @since 0.1.0
 * @date 21/10/2016
 */
public class Principal extends AbstractSecurity implements Function {

    /**
     * Function name: <code>principal()</code>.
     */
    public static final String FUNCTION_NAME = "principal";

    @Override
    public Object execute(Map<String, Object> args) {
        return getPrincipal();
    }

    @Override
    public List<String> getArgumentNames() {
        return null;
    }

}
