package com.algaworks.algafood.api.model.mapper;

import com.algaworks.algafood.api.model.request.CidadeIdRequest;
import com.algaworks.algafood.api.model.request.CidadeRequest;
import com.algaworks.algafood.api.model.response.CidadeResponse;
import com.algaworks.algafood.core.security.AlgafoodSecurity;
import com.algaworks.algafood.domain.model.Cidade;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;

import java.util.List;

import static com.algaworks.algafood.api.AlgaLinks.linkToCidade;
import static com.algaworks.algafood.api.AlgaLinks.linkToCidades;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, uses = {
        EstadoMapper.class
})
public abstract class CidadeMapper implements RepresentationModelAssembler<Cidade, CidadeResponse> {

    @Autowired
    private AlgafoodSecurity algafoodSecurity;

    @Mapping(target = "estado", source = "estado")
    public abstract Cidade toDomain(CidadeRequest request);

    public abstract Cidade toDomain(CidadeIdRequest request);

    @InheritConfiguration(name = "toDomain")
    public abstract Cidade toDomainCopy(@MappingTarget Cidade cidade, CidadeRequest request);

    public abstract List<CidadeResponse> toCollectionModel(List<Cidade> cidades);

    @AfterMapping
    protected void addLinks(@MappingTarget CidadeResponse cidadeResponse) {
        cidadeResponse.add(linkToCidade(cidadeResponse.getId()));

        if (algafoodSecurity.podeConsultarCidades()) {
            cidadeResponse.add(linkToCidades("cidades"));
        }
    }

    @Override
    public CollectionModel<CidadeResponse> toCollectionModel(Iterable<? extends Cidade> entities) {
        CollectionModel<CidadeResponse> cidades = RepresentationModelAssembler.super.toCollectionModel(entities);

        if (algafoodSecurity.podeConsultarCidades()) {
            cidades.add(linkToCidades());
        }

        return cidades;
    }
}