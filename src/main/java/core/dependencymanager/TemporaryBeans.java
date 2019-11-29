package core.dependencymanager;

import core.execution.ExtendedHttpExchange;
import core.utill.FormParameters;
import core.utill.QueryParameters;

final class TemporaryBeans {

    private final ExtendedHttpExchange exchange;

    TemporaryBeans(ExtendedHttpExchange exchange) {
        this.exchange = exchange;
    }

    QueryParameters queryParams(){
        return new QueryParameters(this.exchange.getQueryParameters());
    }

    FormParameters inputParams(){
        return new FormParameters(this.exchange.getInputFormParameters());
    }

}
