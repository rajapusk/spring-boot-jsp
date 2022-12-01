package com.cardview.demo.model;

import javax.persistence.*;

@Entity
@Table(name="TBL_PF_NOMINEE")
public class PFNomineeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Long getid() {
        return this.id;
    }

    public void setid(Long value) {
        this.id = value;
    }

    @Column(name = "EMPCODE")
    private long _empcode;

    public long getempcode() {
        return this._empcode;
    }

    public void setempcode(long value) {
        this._empcode = value;
    }

    @Column(name="APPROVED")
    private byte approved;
    public byte getapproved()
    {
        return this.approved;
    }
    public void setapproved(byte value)
    {
        this.approved = value;
    }

    @Column(name="HRAPPROVED")
    private byte hrApproved;
    public byte getHRApproved()
    {
        return this.hrApproved;
    }
    public void setHRApproved(byte value)
    {
        this.hrApproved = value;
    }

    @Column(name="SUBMITTED")
    private byte submitted;
    public byte getsubmitted()
    {
        return this.submitted;
    }
    public void setsubmitted(byte value)
    {
        this.submitted = value;
    }

}
