package com.algolia.search.inputs.query_rules;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class ConsequenceQuery implements Serializable {}
