package net.mindustry_ddns.store.util;

import org.aeonbits.owner.*;

public interface TestConfig extends Accessible {
    @DefaultValue("7")
    @Key("test.lucky-number")
    int getLuckyNumber();
}
