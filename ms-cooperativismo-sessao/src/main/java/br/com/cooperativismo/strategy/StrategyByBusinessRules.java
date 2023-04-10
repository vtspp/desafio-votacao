package br.com.cooperativismo.strategy;

@FunctionalInterface
public interface StrategyByBusinessRules<T> {
    T applyBusinessRules(T concreteStrategy);
}