package com.epam.rd.jsp.currencies;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class Currencies {
    private Map<String, BigDecimal> curs = new TreeMap<>();


    public void addCurrency(String currency, BigDecimal weight) {
        curs.put(currency, weight);
    }

    public Collection<String> getCurrencies() {
        List<String> collect = curs.keySet().stream().sorted().collect(Collectors.toList());
        return collect;
    }

    public Map<String, BigDecimal> getExchangeRates(String referenceCurrency) {
        Map<String, BigDecimal> refCurs = new TreeMap<>();
        BigDecimal refCurrency = curs.get(referenceCurrency);
        curs.forEach((key, value) -> refCurs.put(key, refCurrency.divide(value,5,RoundingMode.HALF_UP)));
        return refCurs;
    }

    public BigDecimal convert(BigDecimal amount, String sourceCurrency, String targetCurrency) {
        Map<String, BigDecimal> exchangeRates = new TreeMap<>();
        BigDecimal refCurrency = curs.get(sourceCurrency);
        curs.forEach((key, value) -> exchangeRates.put(key, refCurrency.divide(value,16,RoundingMode.HALF_UP)));
        Map<String, BigDecimal> newMap = getExchangeRates(sourceCurrency);
        BigDecimal targetCurs2 = newMap.get(targetCurrency);
        BigDecimal targetCurs = exchangeRates.get(targetCurrency);

       return amount.multiply(targetCurs).setScale(5, RoundingMode.HALF_UP);

    }

}



