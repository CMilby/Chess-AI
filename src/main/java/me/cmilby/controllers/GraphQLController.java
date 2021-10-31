package me.cmilby.controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import graphql.ExecutionInput;
import graphql.ExecutionResult;
import graphql.GraphQLError;
import lombok.extern.slf4j.Slf4j;
import me.cmilby.graphql.GraphQLProvider;
import me.cmilby.graphql.GraphQLRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.stream.Collectors;

@Slf4j
@RestController
public class GraphQLController {

    @Autowired
    GraphQLProvider graphQLService;

    @RequestMapping ( value = "/graphql" )
    public String get ( @RequestBody String query ) {
        GraphQLRequest request = new Gson ( ).fromJson ( query, GraphQLRequest.class );
        ExecutionInput executionInput = ExecutionInput
                .newExecutionInput ( request.getQuery ( ) )
                .variables ( request.getVariables ( ) == null ? new HashMap <> ( ) : request.getVariables ( ) )
                .operationName ( request.getOperationName ( ) )
                .build ( );
        ExecutionResult executionResult = graphQLService.getGraphQL ( ).execute ( executionInput );
        if ( !executionResult.getErrors ( ).isEmpty ( ) ) {
            log.error ( executionResult.getErrors ( ).stream ( ).map ( GraphQLError::getMessage )
                    .collect ( Collectors.joining ( ) ) );
        }

        return new GsonBuilder ( )
                .serializeNulls ( )
                .create ( )
                .toJson ( executionResult.toSpecification ( ) );
    }
}
