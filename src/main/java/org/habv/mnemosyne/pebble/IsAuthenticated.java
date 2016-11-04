package org.habv.mnemosyne.pebble;

import com.mitchellbosecke.pebble.extension.Function;
import java.util.List;
import java.util.Map;

/**
 * @author Herman Barrantes
 * @since 0.1.0
 * @date 21/10/2016
 */
public class IsAuthenticated extends AbstractSecurity implements Function {

    /**
     * Function name: <code>isAuthenticated()</code>.
     */
    public static final String FUNCTION_NAME = "isAuthenticated";

    @Override
    public Object execute(Map<String, Object> args) {
        return isAuthenticated();
    }

    @Override
    public List<String> getArgumentNames() {
        return null;
    }

}
