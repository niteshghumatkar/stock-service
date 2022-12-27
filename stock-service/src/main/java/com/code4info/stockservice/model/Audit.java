package com.code4info.stockservice.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class Audit {

    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="lastupdate")
    private Date lastUpdate;
}
