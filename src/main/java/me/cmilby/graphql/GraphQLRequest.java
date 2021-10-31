package me.cmilby.graphql;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@Getter
@AllArgsConstructor
public class GraphQLRequest {
    String query;
    Map < String, Object > variables;
    String operationName;
}
