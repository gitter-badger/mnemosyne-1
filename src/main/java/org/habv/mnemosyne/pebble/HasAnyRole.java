package org.habv.mnemosyne.pebble;

import com.mitchellbosecke.pebble.extension.Function;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Herman Barrantes
 * @since 0.1.0
 * @date 24/10/2016
 */
public class HasAnyRole extends AbstractSecurity implements Function {

    /**
     * Function name: <code>hasAnyRole()</code>.
     */
    public static final String FUNCTION_NAME = "hasAnyRole";

    @Override
    public Object execute(Map<String, Object> args) {
        Set<String> roles = args
                .entrySet()
                .stream()
                .filter(arg
                        -> !arg.getKey().equals("_context")
                        && !arg.getKey().equals("_self"))
                .map(arg -> arg.getValue().toString())
                .collect(Collectors.toSet());
        return hasAnyRole(roles);
    }

    @Override
    public List<String> getArgumentNames() {
        return null;
    }

}
