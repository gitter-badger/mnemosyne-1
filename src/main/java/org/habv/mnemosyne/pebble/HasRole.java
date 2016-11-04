package org.habv.mnemosyne.pebble;

import com.mitchellbosecke.pebble.extension.Function;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author Herman Barrantes
 * @since 0.1.0
 * @date 24/10/2016
 */
public class HasRole extends AbstractSecurity implements Function {

    /**
     * Function name: <code>hasRole()</code>.
     */
    public static final String FUNCTION_NAME = "hasRole";
    private static final String ROLE = "role";
    private final List<String> argumentNames;

    public HasRole() {
        this.argumentNames = Collections.singletonList(ROLE);
    }

    @Override
    public Object execute(Map<String, Object> args) {
        String role = (String) args.get(ROLE);
        return hasRole(role);
    }

    @Override
    public List<String> getArgumentNames() {
        return argumentNames;
    }

}
