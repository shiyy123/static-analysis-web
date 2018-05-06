package cn.iselab.android.analysis.server.dao;

import cn.iselab.android.analysis.server.data.SCVul;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SCVulDao extends JpaRepository<SCVul, Long>{
    public List<SCVul> findByScId(Long scId);
    public SCVul findByScIdAndName(Long scId, String name);
}
