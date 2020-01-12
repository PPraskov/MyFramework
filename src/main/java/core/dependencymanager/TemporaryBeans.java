package core.dependencymanager;

import core.execution.ExtendedHttpExchange;

final class TemporaryBeans {

    private final ExtendedHttpExchange exchange;

    TemporaryBeans(ExtendedHttpExchange exchange) {
        this.exchange = exchange;
    }


}
