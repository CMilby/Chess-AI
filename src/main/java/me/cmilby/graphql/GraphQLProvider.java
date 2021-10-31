package me.cmilby.graphql;

import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Component
public class GraphQLProvider {

    private GraphQL graphQL;

    @Bean
    public GraphQL getGraphQL ( ) {
        return graphQL;
    }

    @PostConstruct
    public void init ( ) throws IOException {
        ClassLoader classLoader = getClass ( ).getClassLoader ( );
        File file = new File ( classLoader.getResource ( "schema.graphql" ).getFile ( ) );
        GraphQLSchema graphQLSchema = GraphQLUtil.buildSchema ( new String ( Files.readAllBytes ( file.toPath ( ) ) ) );
        this.graphQL = GraphQL.newGraphQL ( graphQLSchema ).build ( );
    }

    @Bean
    public WebMvcConfigurer corsConfigurer ( ) {
        return new WebMvcConfigurer ( ) {
            @Override
            public void addCorsMappings ( @NotNull CorsRegistry registry ) {
                registry.addMapping ( "/graphql" ).allowedOrigins ( "http://localhost:3000" );
            }
        };
    }
}
