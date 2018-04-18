package cn.iselab.android.analysis.server.data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * @author henrylee
 */

@Entity
@Table(name = "sample_data")
public class SampleData extends BaseData {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull
    private String name;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
