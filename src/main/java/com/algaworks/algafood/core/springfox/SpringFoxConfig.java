package com.algaworks.algafood.core.springfox;

import com.algaworks.algafood.api.exceptionhandler.ApiProblem;
import com.algaworks.algafood.api.model.response.*;
import com.algaworks.algafood.api.openapi.model.*;
import com.fasterxml.classmate.TypeResolver;
import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Links;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.AlternateTypeRules;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.URLStreamHandler;
import java.util.Arrays;
import java.util.List;

@Configuration
@EnableSwagger2
@Import(BeanValidatorPluginsConfiguration.class)
public class SpringFoxConfig implements WebMvcConfigurer {

    TypeResolver typeResolver = new TypeResolver();

    @Bean
    public Docket apiDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                    .apis(RequestHandlerSelectors.basePackage("com.algaworks.algafood.api"))
                    .paths(Predicates.not(PathSelectors.ant("")))
                .build()
                .useDefaultResponseMessages(false)
                .globalResponseMessage(RequestMethod.GET, globalListGetResponseMessages())
                .globalResponseMessage(RequestMethod.POST, globalListPostPutResponseMessages())
                .globalResponseMessage(RequestMethod.PUT, globalListPostPutResponseMessages())
                .globalResponseMessage(RequestMethod.DELETE, globalListDeleteResponseMessages())
                .ignoredParameterTypes(ServletWebRequest.class, URL.class, URI.class,
                                       URLStreamHandler.class, Resource.class, File.class,
                                       InputStream.class)
                .additionalModels(typeResolver.resolve(ApiProblem.class))
                .directModelSubstitute(Pageable.class, PageableOpenApi.class)
                .directModelSubstitute(Links.class, LinksOpenApi.class)
                .alternateTypeRules(AlternateTypeRules.newRule(
                        typeResolver.resolve(PagedModel.class, CidadeResponse.class),
                        PageCidadeResponseOpenApi.class
                ))
                .alternateTypeRules(AlternateTypeRules.newRule(
                        typeResolver.resolve(PagedModel.class, CozinhaResponse.class),
                        PageCozinhaResponseOpenApi.class
                ))
                .alternateTypeRules(AlternateTypeRules.newRule(
                        typeResolver.resolve(PagedModel.class, PedidoResumidoResponse.class),
                        PagePedidoResumidoResponseOpenApi.class
                ))
                .alternateTypeRules(AlternateTypeRules.newRule(
                        typeResolver.resolve(CollectionModel.class, EstadoResponse.class),
                        EstadosOpenApi.class
                ))
                .alternateTypeRules(AlternateTypeRules.newRule(
                        typeResolver.resolve(CollectionModel.class, FormaPagamentoResponse.class),
                        FormasPagamentoOpenApi.class
                ))
                .alternateTypeRules(AlternateTypeRules.newRule(
                        typeResolver.resolve(CollectionModel.class, GrupoResponse.class),
                        GruposOpenApi.class
                ))
                .alternateTypeRules(AlternateTypeRules.newRule(
                        typeResolver.resolve(CollectionModel.class, PermissaoResponse.class),
                        PermissoesOpenApi.class
                ))
                .alternateTypeRules(AlternateTypeRules.newRule(
                        typeResolver.resolve(CollectionModel.class, ProdutoResponse.class),
                        ProdutosOpenApi.class
                ))
                .alternateTypeRules(AlternateTypeRules.newRule(
                        typeResolver.resolve(CollectionModel.class, RestauranteResumidoResponse.class),
                        RestaurantesOpenApi.class
                ))
                .alternateTypeRules(AlternateTypeRules.newRule(
                        typeResolver.resolve(CollectionModel.class, UsuarioResponse.class),
                        UsuariosOpenApi.class
                ))
                .tags(
                        new Tag("Cidades", "Gerencia as cidades"),
                        new Tag("Cozinhas", "Gerencia as cozinhas"),
                        new Tag("Estados", "Gerencia os estados"),
                        new Tag("Estatísticas", "Estatísticas da Algafood"),
                        new Tag("Formas de Pagamento", "Gerencia as formas de pagamento"),
                        new Tag("Grupos", "Gerencia os grupos de usuário"),
                        new Tag("Pedidos", "Gerencia os pedidos"),
                        new Tag("Permissões", "Gerencia as permissões de usuário"),
                        new Tag("Produtos", "Gerencia os produtos de restaurantes"),
                        new Tag("Restaurantes", "Gerencia os restaurantes"),
                        new Tag("Usuários", "Gerencia os usuários")
                )
                .apiInfo(apiInfo());
    }

    private List<ResponseMessage> globalListGetResponseMessages() {
        return Arrays.asList(
                new ResponseMessageBuilder()
                    .code(HttpStatus.NOT_ACCEPTABLE.value())
                    .message("Recurso não possui representação aceitável pelo consumidor")
                    .build(),
                new ResponseMessageBuilder()
                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .message("Erro interno do servidor")
                        .responseModel(new ModelRef("ApiProblem"))
                    .build()
        );
    }

    private List<ResponseMessage> globalListPostPutResponseMessages() {
        return Arrays.asList(
                new ResponseMessageBuilder()
                        .code(HttpStatus.BAD_REQUEST.value())
                        .message("Requisição inválida (erro do cliente)")
                        .responseModel(new ModelRef("ApiProblem"))
                        .build(),
                new ResponseMessageBuilder()
                        .code(HttpStatus.NOT_ACCEPTABLE.value())
                        .message("Recurso não possui representação aceitável pelo consumidor")
                        .build(),
                new ResponseMessageBuilder()
                        .code(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value())
                        .message("Requisição recusada devido envio de representação em formato não suportado")
                        .responseModel(new ModelRef("ApiProblem"))
                        .build(),
                new ResponseMessageBuilder()
                        .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .message("Erro interno do servidor")
                        .responseModel(new ModelRef("ApiProblem"))
                        .build()
        );
    }

    private List<ResponseMessage> globalListDeleteResponseMessages() {
        return Arrays.asList(
                new ResponseMessageBuilder()
                        .code(HttpStatus.BAD_REQUEST.value())
                        .message("Requisição inválida (erro do cliente)")
                        .responseModel(new ModelRef("ApiProblem"))
                        .build(),
                new ResponseMessageBuilder()
                        .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .message("Erro interno do servidor")
                        .responseModel(new ModelRef("ApiProblem"))
                        .build()
        );
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Algafood API")
                .description("API aberta para clientes e restaurantes do curso Especialista Spring REST.")
                .version("1.0.0")
                .contact(new Contact(
                        "Inalberth Pinheiro Santos",
                        "https://github.com/psinalberth",
                        null))
                .build();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
}