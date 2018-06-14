package vip.e7du.jfm.testing;

import org.springframework.stereotype.Component;

import vip.e7du.dc.api.dc.DC;

/**
 * @author Jobsz
 */
@Component
public class MyDC extends DC {

    public MyDC() {
        this.idc = new RemoteDCProxy();
    }

    @Override
    protected String subSysName() {
        return "Galaxy-Test-SubSystem😄";
    }

}
