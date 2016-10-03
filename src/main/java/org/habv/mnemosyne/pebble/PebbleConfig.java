package org.habv.mnemosyne.pebble;

import com.mitchellbosecke.pebble.extension.Extension;
import com.mitchellbosecke.pebble.spring4.extension.SpringExtension;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @author Herman Barrantes
 * @since 01/10/2016
 */
@Component
public class PebbleConfig {

    @Bean
    public Extension mnemosyneExtension() {
        return new MnemosyneExtension();
    }

    @Bean
    public Extension springExtension() {
        return new SpringExtension();
    }

}
