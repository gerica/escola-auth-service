package com.escola.auth.config;

import graphql.scalars.ExtendedScalars;
import graphql.schema.GraphQLScalarType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;

@Configuration
public class GraphQLConfig {

    /**
     * Cria um bean que ensina ao Spring GraphQL como lidar com tipos escalares não padrão.
     * Cada chamada a .scalar() registra uma implementação para um tipo específico.
     *
     * @return um configurador que adiciona os scalars necessários.
     */
//    @Bean
//    public RuntimeWiringConfigurer runtimeWiringConfigurer() {
//        return wiringBuilder -> wiringBuilder
//                // Para o tipo java.time.Instant, o mais apropriado é o DateTime.
//                .scalar(ExtendedScalars.DateTime)
//                // Para o tipo java.time.LocalDate (se você usar em outro lugar).
//                .scalar(ExtendedScalars.Date)
//                // Para o tipo Long.
//                .scalar(ExtendedScalars.GraphQLLong);
//    }
    @Bean
    public RuntimeWiringConfigurer runtimeWiringConfigurer() {
        // Cria um novo scalar chamado "OffsetDateTime" que usa a lógica do "DateTime"
        GraphQLScalarType offsetDateTimeScalar = ExtendedScalars.newAliasedScalar("OffsetDateTime")
                .aliasedScalar(ExtendedScalars.DateTime)
                .build();

        return wiringBuilder -> wiringBuilder
                // Registra nosso novo scalar com o nome que o Spring espera
                .scalar(offsetDateTimeScalar)
                // Registra outros scalars que você possa precisar
                .scalar(ExtendedScalars.GraphQLLong);
    }
}