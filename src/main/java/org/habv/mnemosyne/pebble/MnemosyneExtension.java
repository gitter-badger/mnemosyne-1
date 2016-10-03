package org.habv.mnemosyne.pebble;

import com.mitchellbosecke.pebble.extension.AbstractExtension;
import com.mitchellbosecke.pebble.extension.Function;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Herman Barrantes
 * @since 01/10/2016
 */
public class MnemosyneExtension extends AbstractExtension {

    @Override
    public Map<String, Function> getFunctions() {
        Map<String, Function> functions = new HashMap<String, Function>();
        functions.put(MD5Function.FUNCTION_NAME, new MD5Function());
        return functions;
    }

}
