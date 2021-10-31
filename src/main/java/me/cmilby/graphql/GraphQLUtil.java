package me.cmilby.graphql;

import com.google.gson.Gson;
import graphql.schema.DataFetchingEnvironment;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import me.cmilby.graphql.data_fetchers.ChessDataFetcher;

public class GraphQLUtil {

    public static RuntimeWiring buildRuntimeWiring ( ) {
        return RuntimeWiring.newRuntimeWiring ( )
                .type ( "Query", builder -> builder
                        .dataFetcher ( "possibleMoves", ChessDataFetcher.getPossibleMoves )
                        .dataFetcher ( "position", ChessDataFetcher.getPosition )
                )
                .build ( );
    }

    public static GraphQLSchema buildSchema ( String sdl ) {
        TypeDefinitionRegistry typeDefinitionRegistry = new SchemaParser ( ).parse ( sdl );
        RuntimeWiring runtimeWiring = buildRuntimeWiring ( );
        SchemaGenerator schemaGenerator = new SchemaGenerator ( );
        return schemaGenerator.makeExecutableSchema ( typeDefinitionRegistry, runtimeWiring );
    }

    public static < T > T getArguments ( DataFetchingEnvironment env, Class < T > clazz ) {
        Gson gson = new Gson ( );
        return gson.fromJson ( gson.toJsonTree ( env.getArguments ( ) ), clazz );
    }
}
