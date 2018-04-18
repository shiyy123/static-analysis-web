package cn.iselab.android.analysis.server.web.data.wrapper;

import cn.iselab.android.analysis.server.data.SampleData;
import cn.iselab.android.analysis.server.web.data.SampleDataVO;
import org.springframework.stereotype.Service;

@Service
public class SampleDataWrapper {

    public SampleDataVO wrap(SampleData sampleData) {
        SampleDataVO vo = new SampleDataVO();
        vo.setId(sampleData.getId());
        vo.setName(sampleData.getName());
        return vo;
    }

}
