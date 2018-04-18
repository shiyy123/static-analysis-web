package cn.iselab.android.analysis.server.dao;

import cn.iselab.android.analysis.server.data.SampleData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

/**
 * @author henrylee
 */

@Transactional
public interface SampleDao extends JpaRepository<SampleData, Long> {

    public SampleData findByName(String name);

    @Modifying
    @Query("update SampleData sd set sd.name=?1 where sd.id=?2")
    public int update(String n,long id);
}
