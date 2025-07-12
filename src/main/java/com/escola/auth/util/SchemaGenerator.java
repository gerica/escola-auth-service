package com.escola.auth.util;

import com.escola.auth.controller.AppConfigController;
import com.escola.auth.controller.AuthenticationController;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.SchemaPrinter;
import io.leangen.graphql.GraphQLSchemaGenerator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Esta classe NÃO é um bean do Spring.
 * É uma ferramenta para ser executada durante o build para gerar o arquivo schema.graphqls.
 */
public class SchemaGenerator {

    public static void main(String[] args) throws IOException {
        // 1. Crie uma instância do gerador de schema do SPQR.
        // A nova API é mais fluida e detecta automaticamente as anotações do Spring GraphQL
        // se a dependência estiver no classpath.
        GraphQLSchemaGenerator schemaGenerator = new GraphQLSchemaGenerator()
                // Adiciona os seus controllers (ou serviços) que contêm as operações GraphQL.
                // Você pode adicionar mais chamadas .withOperationsFromSingleton() para outros controllers.
                .withOperationsFromSingleton(new AuthenticationController(null))
                // <-- 2. Adicione o novo controller de configuração aqui
                .withOperationsFromSingleton(new AppConfigController(null, null, null));

        // 2. Gere o schema
        GraphQLSchema schema = schemaGenerator.generate();

        // 3. Imprima o schema em um formato legível (SDL - Schema Definition Language)
        // Usando a classe SchemaPrinter padrão do graphql-java
        String schemaSDL = new SchemaPrinter().print(schema);

        // 4. Salve o schema no arquivo que o Spring for GraphQL espera.
        // Melhoria: Usando um caminho relativo que funciona independentemente de onde o script é executado.
        Path outputPath = Paths.get("src/main/resources/graphql/schema.graphqls");

        // Melhoria: Garante que o diretório de destino exista antes de tentar escrever o arquivo.
        Files.createDirectories(outputPath.getParent());
        Files.write(outputPath, schemaSDL.getBytes());

        System.out.println("Schema gerado com sucesso em: " + outputPath.toAbsolutePath());
    }
}