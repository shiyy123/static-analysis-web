package cn.iselab.android.analysis.server.dao;

import cn.iselab.android.analysis.server.data.SC;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface SCDao extends JpaRepository<SC, Long>{
    public SC findByMd5(String md5);
    public List<SC> findAll();

    @Modifying
    @Query("update SC sc set sc.status=?1 where sc.md5=?2")
    public int update(String status, String id);
}
