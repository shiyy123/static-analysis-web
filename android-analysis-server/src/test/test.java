import cn.iselab.android.analysis.server.Application;
import cn.iselab.android.analysis.server.service.AaptService;
import cn.iselab.android.analysis.server.web.data.ApkForKivul;
import cn.iselab.android.analysis.server.web.logic.BaseInfoLogic;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class test {
    @Resource
    private BaseInfoLogic baseInfoLogic;
    @Test
    public void testGetName(){
        String url="http://kikbug-public.oss-cn-shanghai.aliyuncs.com/1493822543000_meiyitian.apk";
        String apkID="5";
        ApkForKivul apk=baseInfoLogic.analysis(url,apkID);
        Assert.assertEquals("5",apk.getApkID());
        Assert.assertEquals("good",apk.getCertStatus());
    }
}
