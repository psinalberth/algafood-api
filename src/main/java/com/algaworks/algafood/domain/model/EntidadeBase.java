package com.algaworks.algafood.domain.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.time.OffsetDateTime;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class EntidadeBase implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column
    @CreationTimestamp
    private OffsetDateTime dataCriacao;

    @Column
    @UpdateTimestamp
    private OffsetDateTime dataAtualizacao;
}