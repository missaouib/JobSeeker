package com.Backend.domain;

import java.util.Map;

public class Indeed {

    Map<String, String> supportedCountries;

    public Indeed() {
        this.supportedCountries = Map.<String, String>ofEntries(
                Map.entry("Argentina", "ar"),
                Map.entry("Australia", "au"),
                Map.entry("Austria", "at"),
                Map.entry("Bahrain", "bh"),
                Map.entry("Belgium", "be"),
                Map.entry("Brazil", "br"),
                Map.entry("Canada", "ca"),
                Map.entry("Chile", "cl"),
                Map.entry("China", "cn"),
                Map.entry("Colombia", "co"),
                Map.entry("Czech Republic", "cz"),
                Map.entry("Denmark", "dk"),
                Map.entry("Finland", "fi"),
                Map.entry("France", "fr"),
                Map.entry("Germany", "de"),
                Map.entry("Greece", "gr"),
                Map.entry("Hong Kong", "hk"),
                Map.entry("Hungary", "hu"),
                Map.entry("India", "in"),
                Map.entry("Indonesia", "id"),
                Map.entry("Ireland", "ie"),
                Map.entry("Israel", "il"),
                Map.entry("Italy", "it"),
                Map.entry("Japan", "jp"),
                Map.entry("Korea", "kr"),
                Map.entry("Kuwait", "kw"),
                Map.entry("Luxembourg", "lu"),
                Map.entry("Malaysia", "my"),
                Map.entry("Mexico", "mx"),
                Map.entry("Netherlands", "nl"),
                Map.entry("New Zealand", "nz"),
                Map.entry("Norway", "no"),
                Map.entry("Oman", "om"),
                Map.entry("Pakistan", "pk"),
                Map.entry("Peru", "pe"),
                Map.entry("Philippines", "ph"),
                Map.entry("Poland", "pl"),
                Map.entry("Portugal", "pt"),
                Map.entry("Qatar", "qt"),
                Map.entry("Romania", "ro"),
                Map.entry("Russia", "ru"),
                Map.entry("Saudi Arabia", "sa"),
                Map.entry("Singapore", "sg"),
                Map.entry("South Africa", "za"),
                Map.entry("Spain", "es"),
                Map.entry("Sweden", "se"),
                Map.entry("Switzerland", "ch"),
                Map.entry("Taiwan", "tw"),
                Map.entry("Thailand", "th"),
                Map.entry("Turkey", "tr"),
                Map.entry("United Arab Emirates", "ae"),
                Map.entry("United Kingdom", "gb"),
                Map.entry("United States", "us"),
                Map.entry("Venezuela", "ve"),
                Map.entry("Vietnam", "vn")
        );
    }

    public Map<String, String> getSupportedCountries() {
        return supportedCountries;
    }

}
