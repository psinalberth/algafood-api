package com.algaworks.algafood.api.model.mapper;

import com.algaworks.algafood.api.controller.CozinhaController;
import com.algaworks.algafood.api.model.request.CozinhaIdRequest;
import com.algaworks.algafood.api.model.request.CozinhaRequest;
import com.algaworks.algafood.api.model.response.CozinhaResponse;
import com.algaworks.algafood.domain.model.Cozinha;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.hateoas.server.RepresentationModelAssembler;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Mapper
public interface CozinhaMapper extends RepresentationModelAssembler<Cozinha, CozinhaResponse> {

    Cozinha toDomain(CozinhaRequest request);

    Cozinha toDomain(CozinhaIdRequest request);

    Cozinha toDomainCopy(@MappingTarget Cozinha cozinha, CozinhaRequest request);

    List<CozinhaResponse> toCollectionModel(List<Cozinha> cozinhas);

    @AfterMapping
    default void addLinks(@MappingTarget CozinhaResponse cozinhaResponse) {
        cozinhaResponse.add(linkTo(methodOn(CozinhaController.class)
                .buscar(cozinhaResponse.getId())).withSelfRel());

        cozinhaResponse.add(linkTo(methodOn(CozinhaController.class)
                .listar(null)).withRel("cozinhas"));
    }
}