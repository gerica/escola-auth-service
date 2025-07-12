package com.escola.auth.controller;


import com.escola.auth.model.model.mapper.AppConfigMapper;
import com.escola.auth.model.model.response.AppConfigResponse;
import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.info.BuildProperties;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class AppConfigController {

    BuildProperties buildProperties;
    AppConfigMapper appConfigMapper;
    String appDescription;


    public AppConfigController(
            BuildProperties buildProperties,
            AppConfigMapper appConfigMapper,
            @Value("${app.description}") String appDescription
    ) {
        this.buildProperties = buildProperties;
        this.appConfigMapper = appConfigMapper;
        this.appDescription = appDescription;
    }

    @GraphQLQuery(name = "getAppConfigAuth") // Para o SchemaGenerator (SPQR)
    @QueryMapping(name = "getAppConfigAuth")
    public AppConfigResponse getAppConfigAuth() {
        log.info("Executando getAppConfigAuth..."); // Adicione um log para confirmar a execução
        return appConfigMapper.toOutput(buildProperties, appDescription);
    }
}
