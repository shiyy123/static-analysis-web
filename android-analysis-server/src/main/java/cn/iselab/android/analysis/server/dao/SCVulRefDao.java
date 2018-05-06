package cn.iselab.android.analysis.server.dao;

import cn.iselab.android.analysis.server.data.SCVulRef;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface SCVulRefDao extends JpaRepository<SCVulRef, Long>{
    public ArrayList<SCVulRef> findByVulId(Long vulId);
}
