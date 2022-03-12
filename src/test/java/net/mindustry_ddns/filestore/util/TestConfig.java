package net.mindustry_ddns.filestore.util;

import org.aeonbits.owner.Accessible;

public interface TestConfig extends Accessible {

    @DefaultValue("7")
    @Key("test.lucky-number")
    int getLuckyNumber();
}
