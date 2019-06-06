package com.Backend.service;

import com.Backend.model.*;
import com.Backend.model.dto.CategoryDto;
import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.text.Normalizer;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class JobServiceImp implements JobService {

    private ModelMapper modelMapper;

    JobServiceImp(ModelMapper modelMapper){
        this.modelMapper = Objects.requireNonNull(modelMapper);
    }

    private List<City> initCities() {
        return List.of(
                new City("Warszawa", 1764615, 517.24, 9300),
                new City("Kraków", 767348, 326.85, 9200),
                new City("Łódź", 690422, 293.25, 5200),
                new City("Wrocław", 638586, 292.82, 8400),
                new City("Poznań", 538633, 261.91, 7600),
                new City("Gdańsk", 464254, 261.96, 8600),
                new City("Szczecin", 403883, 300.60, 5800),
                new City("Bydgoszcz", 352313, 175.98, 5400),
                new City("Lublin", 339850, 147.5, 6070),
                new City("Białystok", 297288, 102.12, 5860),
                new City("Katowice", 296262, 164.64, 5460),
                new City("Rzeszów", 193631, 126.57, 6100),
                new City("Kielce", 195774, 109.45, 5000),
                new City("Olsztyn", 173125, 88.33, 5350),
                new City("Zielona Góra", 140113, 278.32, 5000),
                new City("Opole", 128140, 148.99, 5340)
        );
    }

    private List<Technology> initTechnologies() {
        return List.of(
                new Technology("Java", "Language"),
                new Technology("Javascript", "Language"),
                new Technology("Typescript", "Language"),
                new Technology(".NET", "language"),
                new Technology("Python", "Language"),
                new Technology("PHP", "Language"),
                new Technology("C++", "Language"),
                new Technology("Ruby", "Language"),
                new Technology("Kotlin", "Language"),
                new Technology("Scala", "Language"),
                new Technology("Groovy", "Language"),
                new Technology("Swift", "Language"),
                new Technology("Objective-C", "Language"),
                new Technology("Visual Basic", "Language"),

                new Technology("Spring", "Framework"),
                new Technology("Java EE", "Framework"),
                new Technology("Android", "Framework"),
                new Technology("Angular", "Framework"),
                new Technology("React", "Framework"),
                new Technology("Vue", "Framework"),
                new Technology("Node", "Framework"),
                new Technology("JQuery", "Framework"),
                new Technology("Symfony", "Framework"),
                new Technology("Laravel", "Framework"),
                new Technology("iOS", "Framework"),
                new Technology("Asp.net", "Framework"),
                new Technology("Django", "Framework"),
                new Technology("Unity", "Framework"),

                new Technology("SQL", "DevOps"),
                new Technology("Linux", "DevOps"),
                new Technology("Git", "DevOps"),
                new Technology("Docker", "DevOps"),
                new Technology("Jenkins", "DevOps"),
                new Technology("Kubernetes", "DevOps"),
                new Technology("AWS", "DevOps"),
                new Technology("Azure", "DevOps"),
                new Technology("HTML", "DevOps"),
                new Technology("Maven", "DevOps"),
                new Technology("Gradle", "DevOps"),
                new Technology("JUnit", "DevOps"),
                new Technology("Jira", "DevOps"),
                new Technology("Scrum", "DevOps")
        );
    }

    private List<Category> initCategories(){
        return List.of(
                new Category(5001, "Administracja biurowa","Office administration"),
                new Category(5037, "Doradztwo / Konsulting","Consulting"),
                new Category(5002, "Badania i rozwój","Research and development"),
                new Category(5003, "Bankowość","Banking"),
                new Category(5004, "BHP / Ochrona środowiska","Health and Safety / Environmental protection"),
                new Category(5005, "Budownictwo","Architecture"),
                new Category(5006, "Call Center","Call Center"),
                new Category(5007, "Edukacja / Szkolenia","Education / Training"),
                new Category(5008, "Finanse / Ekonomia","Finance / Economy"),
                new Category(5009, "Franczyza / Własny biznes","Own business"),
                new Category(5010, "Hotelarstwo / Gastronomia / Turystyka","Hospitality / Gastronomy / Tourism"),
                new Category(5011, "Human Resources / Zasoby ludzkie","Human Resources"),
                new Category(5013, "Internet / e-Commerce / Nowe media","Internet / e-Commerce / New media"),
                new Category(5014, "Inżynieria","Engineering"),
                new Category(5015, "IT - Administracja","IT - Administration"),
                new Category(5016, "IT - Rozwój oprogramowania","IT - Software development"),
                new Category(5017, "Łańcuch dostaw","Supply chain"),
                new Category(5018, "Marketing","Marketing"),
                new Category(5019, "Media / Sztuka / Rozrywka","Media / Arts / Entertainment"),
                new Category(5020, "Nieruchomości","Real estate"),
                new Category(5021, "Obsługa klienta","Customer service"),
                new Category(5022, "Praca fizyczna","Physical work"),
                new Category(5023, "Prawo","Law"),
                new Category(5024, "Produkcja","Production"),
                new Category(5025, "Public Relations","Public Relations"),
                new Category(5026, "Reklama / Grafika / Kreacja / Fotografia","Advertisement / Graphic / Photography"),
                new Category(5027, "Sektor publiczny","Public sector"),
                new Category(5028, "Sprzedaż","Sale"),
                new Category(5031, "Transport / Spedycja","Transport / Shipping"),
                new Category(5032, "Ubezpieczenia","Insurance"),
                new Category(5033, "Zakupy","Shopping"),
                new Category(5034, "Kontrola jakości","Quality control"),
                new Category(5035, "Zdrowie / Uroda / Rekreacja","Health / Beauty / Recreation"),
                new Category(5036, "Energetyka","Energetics"),
                new Category(5012, "Inne","Other")
        );
    }

    private List<Country> initCountries(){
        return List.of(
                new Country("Macau", 667400, 32.9),
                new Country("Monaco", 38300, 2.02),
                new Country("Singapore", 5638700, 722.5),
                new Country("Hong Kong", 7482500, 1106),
                new Country("Gibraltar", 33140, 6.8),
                new Country("Vatican City", 1000, 0.44),
                new Country("Bahrain", 1566993, 778),
                new Country("Malta", 475701, 315),
                new Country("Maldives", 378114, 298),
                new Country("Bermuda", 63779, 52),
                new Country("Bangladesh", 166661616, 143998),
                new Country("Jersey", 104200, 116),
                new Country("Guernsey", 62723, 78),
                new Country("Barbados", 287010, 430),
                new Country("Taiwan", 23590744, 36197),
                new Country("San Marino", 33407, 61),
                new Country("South Korea", 51811167, 100210),
                new Country("Rwanda", 12374397, 26338),
                new Country("Comoros", 873724, 1861),
                new Country("Netherlands", 17322388, 41526),
                new Country("Haiti", 11242856, 27065),
                new Country("India", 1348005400, 3287240),
                new Country("Israel", 9037984, 22072),
                new Country("Burundi", 10953317, 27816),
                new Country("Belgium", 11467362, 30528),
                new Country("Philippines", 107730640, 300000),
                new Country("Puerto Rico", 3195153, 9104),
                new Country("Japan", 126220000, 377944),
                new Country("Sri Lanka", 21670000, 65610),
                new Country("Martinique", 371246, 1128),
                new Country("Guam", 172400, 541),
                new Country("El Salvador", 6704864, 21040),
                new Country("Grenada", 108825, 344),
                new Country("Vietnam", 94660000, 331212),
                new Country("United Kingdom", 66040229, 242910),
                new Country("Trinidad and Tobago", 1359193, 5155),
                new Country("Pakistan", 204875217, 803940),
                new Country("Jamaica", 2726667, 10991),
                new Country("Guadeloupe", 395725, 1628.4),
                new Country("Liechtenstein", 38380, 160),
                new Country("Kuwait", 4226920, 17818),
                new Country("Luxembourg", 613894, 2586),
                new Country("Antigua and Barbuda", 104084, 442),
                new Country("Qatar", 2700390, 11571),
                new Country("Germany", 82979100, 357168),
                new Country("Nigeria", 200962000, 923768),
                new Country("Dominican Republic", 10358320, 47875),
                new Country("Seychelles", 96762, 455),
                new Country("North Korea", 25450000, 120540),
                new Country("The Gambia", 2228075, 10690),
                new Country("Switzerland", 8542323, 41285),
                new Country("Nepal", 29609623, 147181),
                new Country("Italy", 60375749, 301308),
                new Country("Uganda", 40006700, 241551),
                new Country("Andorra", 76177, 464),
                new Country("Guatemala", 17679735, 108889),
                new Country("Malawi", 17563749, 118484),
                new Country("Kiribati", 120100, 811),
                new Country("Cyprus", 864200, 5896),
                new Country("China", 1397616080, 9640821),
                new Country("Indonesia", 268074600, 1904569),
                new Country("Tonga", 100651, 720),
                new Country("Czech Republic", 10649800, 78867),
                new Country("Thailand", 69183173, 513120),
                new Country("Denmark", 5806081, 43098),
                new Country("Togo", 7352000, 56600),
                new Country("Ghana", 30280811, 238533),
                new Country("France", 66998000, 543965),
                new Country("Poland", 38413000, 312685),
                new Country("Jordan", 10429300, 89342),
                new Country("Azerbaijan", 9981457, 86600),
                new Country("United Arab Emirates", 9541615, 83600),
                new Country("Portugal", 10291027, 92090),
                new Country("Slovakia", 5443120, 49036),
                new Country("Austria", 8869537, 83879),
                new Country("Turkey", 82003882, 783562),
                new Country("Hungary", 9764000, 93029),
                new Country("Moldova", 3547539, 33843),
                new Country("Cuba", 11221060, 109884),
                new Country("Slovenia", 2066880, 20273),
                new Country("Ethiopia", 107534882, 1063652),
                new Country("Benin", 11362269, 112622),
                new Country("Dominica", 74308, 739),
                new Country("Armenia", 2969200, 29743),
                new Country("Albania", 2870324, 28703),
                new Country("Syria", 18284407, 185180),
                new Country("Sierra Leone", 7075641, 71740),
                new Country("Malaysia", 32749204, 330803),
                new Country("Egypt", 98758126, 1002450),
                new Country("Costa Rica", 5003393, 51100),
                new Country("Iraq", 40877898, 437072),
                new Country("Spain", 46733038, 505990),
                new Country("Kenya", 52950879, 581834),
                new Country("Cambodia", 16561944, 181035),
                new Country("Serbia", 6901188, 77474),
                new Country("Romania", 19524000, 238391),
                new Country("Greece", 10741165, 131957),
                new Country("Senegal", 15726037, 196722),
                new Country("Honduras", 9012229, 112492),
                new Country("Ivory Coast", 25823071, 322921),
                new Country("Morocco", 35037811, 446550),
                new Country("Lesotho", 2263010, 30355),
                new Country("Burkina Faso", 20244080, 270764),
                new Country("Uzbekistan", 32653900, 447400),
                new Country("Brunei", 421300, 5765),
                new Country("Croatia", 4105493, 56542),
                new Country("Tunisia", 11551448, 163610),
                new Country("Ukraine", 42101650, 603000),
                new Country("Bosnia and Herzegovina", 3511372, 51209),
                new Country("Republic of Ireland", 4857000, 70273),
                new Country("Yemen", 28915284, 455000),
                new Country("Mexico", 125327797, 1967138),
                new Country("Bulgaria", 7000039, 111002),
                new Country("Tajikistan", 8931000, 143100),
                new Country("Ecuador", 17251220, 276841),
                new Country("Tanzania", 55890747, 945087),
                new Country("Panama", 4158783, 74177),
                new Country("Georgia (country)", 3729600, 69700),
                new Country("Nicaragua", 6393824, 121428),
                new Country("Cameroon", 24348251, 466050),
                new Country("Iran", 82501691, 1648195),
                new Country("Guinea", 12218357, 245857),
                new Country("Afghanistan", 31575018, 645807),
                new Country("Fiji", 884887, 18333),
                new Country("South Africa", 57725600, 1220813),
                new Country("Belarus", 9465300, 207600),
                new Country("Montenegro", 622359, 13812),
                new Country("Liberia", 4382387, 97036),
                new Country("Madagascar", 26262810, 587041),
                new Country("Eritrea", 5187948, 121100),
                new Country("Lithuania", 2793986, 65300),
                new Country("Colombia", 45798200, 1141748),
                new Country("Zimbabwe", 14848905, 390757),
                new Country("Democratic Republic of the Congo", 84004989, 2345095),
                new Country("Mozambique", 28861863, 799380),
                new Country("Venezuela", 31828110, 916445),
                new Country("United States", 329311603, 9833517),
                new Country("Kyrgyzstan", 6309300, 199945),
                new Country("Latvia", 1918500, 64562),
                new Country("Estonia", 1315635, 45339),
                new Country("The Bahamas", 386870, 13940),
                new Country("Laos", 6492400, 236800),
                new Country("Peru", 32162184, 1285216),
                new Country("Brazil", 209989323, 8515767),
                new Country("Somalia", 15181925, 637657),
                new Country("Angola", 29250009, 1246700),
                new Country("Chile", 17373831, 756096),
                new Country("Sweden", 10279005, 450295),
                new Country("Zambia", 16405229, 752612),
                new Country("Sudan", 40782742, 1839542),
                new Country("Bhutan", 813744, 38394),
                new Country("South Sudan", 12575714, 644329),
                new Country("Somaliland", 3508180, 176120),
                new Country("land Islands", 28502, 1552),
                new Country("Algeria", 42545964, 2381741),
                new Country("Niger", 21466863, 1186408),
                new Country("New Zealand", 4966421, 270467),
                new Country("Papua New Guinea", 8558800, 462840),
                new Country("Uruguay", 2990452, 176215),
                new Country("Paraguay", 7052983, 406752),
                new Country("Norway", 5328212, 323782),
                new Country("Finland", 5521773, 338424),
                new Country("Argentina", 44494502, 2780400),
                new Country("Saudi Arabia", 33413660, 2149690),
                new Country("Republic of the Congo", 5399895, 342000),
                new Country("Mali", 19107706, 1248574),
                new Country("New Caledonia", 258958, 18575),
                new Country("Oman", 4183841, 309500),
                new Country("Turkmenistan", 5851466, 491210),
                new Country("Chad", 15353184, 1284000),
                new Country("Bolivia", 11307314, 1098581),
                new Country("Russia", 146877088, 17125242),
                new Country("Gabon", 2067561, 267667),
                new Country("Central African Republic", 4737423, 622436),
                new Country("Kazakhstan", 18356900, 2724900),
                new Country("Botswana", 2302878, 581730),
                new Country("Mauritania", 3984233, 1030700),
                new Country("Canada", 37499341, 9984670),
                new Country("Libya", 6470956, 1770060),
                new Country("Guyana", 782225, 214999),
                new Country("Suriname", 568301, 163820),
                new Country("Iceland", 357050, 102775),
                new Country("Australia", 25382040, 7692024),
                new Country("Namibia", 2413643, 825118),
                new Country("French Guiana", 244118, 86504),
                new Country("Mongolia", 3000000, 1564100),
                new Country("Greenland", 55877, 2166000)
        );
        // Black list that broke linkedin search:
        // Sint Maarten, Saba, Curaao, Republic of Artsakh, eswatini, North Macedonia, North Cyprus, South Ossetia, Sao Tome and Principe
    }

    public List<City> getItJobOffersInPoland(ModelMap technology) {

        String selectedTechnology = technology.get("technology").toString().toLowerCase();
        List<City> cities = initCities();
        List<JustJoin> justJoinOffers = getJustJoin();

        cities.forEach(city -> {

                    String selectedCityUTF8 = city.getName().toLowerCase();
                    String selectedCityASCII = removePolishSigns(selectedCityUTF8);
                    WebClient linkedinURL = WebClient.create("https://www.linkedin.com/jobs/search?keywords=" + selectedTechnology + "&location=" + selectedCityASCII);
                    WebClient pracujURL = WebClient.create("https://www.pracuj.pl/praca/" + selectedTechnology + ";kw/" + selectedCityASCII + ";wp");
                    WebClient noFluffJobsURL = WebClient.create("https://nofluffjobs.com/api/search/posting?criteria=city=" + selectedCityASCII + "+" + selectedTechnology);

                    switch(selectedTechnology){
                        case "it category":
                            linkedinURL = WebClient.create("https://www.linkedin.com/jobs/search?location=" + selectedCityASCII + "&pageNum=0&position=1&f_TP=1%2C2%2C3%2C4&f_I=96");
                            pracujURL = WebClient.create("https://www.pracuj.pl/praca/" + selectedCityASCII + ";wp/it%20-%20rozw%c3%b3j%20oprogramowania;cc,5016");
                            noFluffJobsURL = WebClient.create("https://nofluffjobs.com/api/search/posting?criteria=city=" + selectedCityASCII);
                            break;
                        case "c++":
                            linkedinURL = WebClient.create("https://www.linkedin.com/jobs/c++-jobs-" + selectedCityASCII);
                            break;
                        case "c#":
                            linkedinURL = WebClient.create("https://www.linkedin.com/jobs/search?keywords=C%23&location=" + selectedCityASCII);
                            pracujURL = WebClient.create("https://www.pracuj.pl/praca/c%23;kw/" + selectedCityASCII + ";wp");
                            break;
                    }

                    city.setLinkedinOffers(getLinkedinOffers(linkedinURL));
                    city.setPracujOffers(getPracujOffers(pracujURL));
                    city.setNoFluffJobsOffers(getNoFluffJobsOffers(noFluffJobsURL));

                    if(selectedTechnology.equals("it category")){
                        city.setJustJoinOffers((int) justJoinOffers
                                .stream()
                                .filter(filterCity -> {
                                    if(selectedCityUTF8.equals("warszawa")){
                                            return (filterCity.getCity().toLowerCase().contains(selectedCityUTF8) || filterCity.getCity().toLowerCase().contains(selectedCityASCII) || filterCity.getCity().toLowerCase().contains("warsaw"));
                                        } else if (selectedCityUTF8.equals("kraków")){
                                            return (filterCity.getCity().toLowerCase().contains(selectedCityUTF8) || filterCity.getCity().toLowerCase().contains(selectedCityASCII) || filterCity.getCity().toLowerCase().contains("cracow"));
                                        } else {
                                            return (filterCity.getCity().toLowerCase().contains(selectedCityUTF8) || filterCity.getCity().toLowerCase().contains(selectedCityASCII));
                                        }
                                    })
                                .count());
                    } else {
                        city.setJustJoinOffers((int) justJoinOffers
                                .stream()
                                .filter(filterTechnology -> filterTechnology.getTitle().toLowerCase().contains(selectedTechnology)
                                    || filterTechnology.getSkills().get(0).get("name").toLowerCase().contains(selectedTechnology))
                                .filter(filterCity -> {
                                    if(selectedCityUTF8.equals("warszawa")){
                                        return (filterCity.getCity().toLowerCase().contains(selectedCityUTF8) || filterCity.getCity().toLowerCase().contains(selectedCityASCII) || filterCity.getCity().toLowerCase().contains("warsaw"));
                                    } else if (selectedCityUTF8.equals("kraków")){
                                        return (filterCity.getCity().toLowerCase().contains(selectedCityUTF8) || filterCity.getCity().toLowerCase().contains(selectedCityASCII) || filterCity.getCity().toLowerCase().contains("cracow"));
                                    } else {
                                        return (filterCity.getCity().toLowerCase().contains(selectedCityUTF8) || filterCity.getCity().toLowerCase().contains(selectedCityASCII));
                                    }
                                })
                                .count());
                    }

                    city.setTotalJobOffers(city.getLinkedinOffers() + city.getPracujOffers() + city.getNoFluffJobsOffers() + city.getJustJoinOffers());
                    city.setJobOfferPer100kCitizens((double) Math.round(((city.getPracujOffers() + city.getLinkedinOffers() + city.getJustJoinOffers() + city.getNoFluffJobsOffers()) / 4.0 * 1.0 / (city.getPopulation() * 1.0 / 100000)) * 100) / 100);
                    city.setDestinyOfPopulation((int) Math.round(city.getPopulation() / city.getAreaSquareKilometers()));
                }
        );

        return cities;
    }

    @Override
    public List<Country> getItJobOffersInWorld(ModelMap technology) {

        String selectedTechnology = technology.get("technology").toString().toLowerCase();
        List<Country> countries = initCountries();

        countries.forEach(country -> {

            String selectedCountry = country.getName().toLowerCase();

            WebClient linkedinURL = WebClient.create("https://www.linkedin.com/jobs/search?keywords=" + selectedTechnology + "&location=" + selectedCountry);

            switch(selectedTechnology){
                case "it category":
                    linkedinURL = WebClient.create("https://www.linkedin.com/jobs/search?location=" + selectedCountry + "&pageNum=0&position=1&f_TP=1%2C2%2C3%2C4&f_I=96");
                    break;
                case "c++":
                    linkedinURL = WebClient.create("https://www.linkedin.com/jobs/c++-jobs-" + selectedCountry);
                    break;
                case "c#":
                    linkedinURL = WebClient.create("https://www.linkedin.com/jobs/search?keywords=C%23&location=" + selectedCountry);
                    break;
            }

            country.setLinkedinOffers(getLinkedinOffers(linkedinURL));
            country.setJobOfferPer100kCitizens((double) Math.round(country.getLinkedinOffers() * 1.0 / (country.getPopulation() * 1.0 / 100000) * 100) / 100);
            country.setDestinyOfPopulation((double)Math.round(country.getPopulation() / country.getAreaSquareKilometers() * 100) / 100);
        });

        return countries;
    }

    @Override
    public List<Technology> getTechnologyStatistics(ModelMap city) {

        String selectedCityUTF8 = city.get("city").toString().toLowerCase();
        String selectedCityASCII = removePolishSigns(city.get("city").toString().toLowerCase());
        List<Technology> technologies = initTechnologies();
        List<JustJoin> justJoinOffers = getJustJoin();

        technologies.forEach(technology -> {

            String selectedTechnology = technology.getName().toLowerCase();
            WebClient linkedinURL = WebClient.create("https://www.linkedin.com/jobs/search?keywords=" + selectedTechnology + "&location=" + selectedCityASCII);
            WebClient pracujURL = WebClient.create("https://www.pracuj.pl/praca/" + selectedTechnology + ";kw/" + selectedCityASCII + ";wp");
            WebClient noFluffJobsURL = WebClient.create("https://nofluffjobs.com/api/search/posting?criteria=city=" + selectedCityASCII + "+" + selectedTechnology);

            if(selectedCityASCII.equals("poland")) {
                pracujURL = WebClient.create("https://www.pracuj.pl/praca/" + selectedTechnology + ";kw");
                noFluffJobsURL = WebClient.create("https://nofluffjobs.com/api/search/posting?criteria=" + selectedTechnology);
            }
            if(selectedTechnology.equals("c++")) {
                linkedinURL = WebClient.create("https://www.linkedin.com/jobs/c++-jobs-" + selectedCityASCII);
            }

            technology.setLinkedinOffers(getLinkedinOffers(linkedinURL));
            technology.setPracujOffers(getPracujOffers(pracujURL));
            technology.setNoFluffJobsOffers(getNoFluffJobsOffers(noFluffJobsURL));

            if(selectedCityASCII.equals("poland")){
                technology.setJustJoinOffers((int) justJoinOffers
                        .stream()
                        .filter(filterTechnology -> filterTechnology.getTitle().toLowerCase().contains(selectedTechnology)
                                || filterTechnology.getSkills().get(0).get("name").toLowerCase().contains(selectedTechnology))
                        .count());
            } else {
                technology.setJustJoinOffers((int) justJoinOffers
                        .stream()
                        .filter(filterTechnology -> filterTechnology.getTitle().toLowerCase().contains(selectedTechnology)
                                || filterTechnology.getSkills().get(0).get("name").toLowerCase().contains(selectedTechnology))
                        .filter(filterCity -> {
                            if(selectedCityASCII.equals("warszawa")){
                                return (filterCity.getCity().toLowerCase().contains(selectedCityUTF8) || filterCity.getCity().toLowerCase().contains(selectedCityASCII) || filterCity.getCity().toLowerCase().contains("warsaw"));
                            } else if (selectedCityASCII.equals("kraków")){
                                return (filterCity.getCity().toLowerCase().contains(selectedCityUTF8) || filterCity.getCity().toLowerCase().contains(selectedCityASCII) || filterCity.getCity().toLowerCase().contains("cracow"));
                            } else {
                                return (filterCity.getCity().toLowerCase().contains(selectedCityUTF8) || filterCity.getCity().toLowerCase().contains(selectedCityASCII));
                            }
                        })
                        .count());
            }
            technology.setTotalJobOffers(technology.getLinkedinOffers() + technology.getPracujOffers() + technology.getNoFluffJobsOffers() + technology.getJustJoinOffers());
        });

        return technologies;
    }

    public List<CategoryDto> getCategoryStatistics(ModelMap city){

        String selectedCityASCII = removePolishSigns(city.get("city").toString().toLowerCase());
        List<Category> categories = initCategories();

        categories.forEach(category -> {
            String categoryName = category.getPolishName().toLowerCase().replaceAll("/ ", "");
            WebClient pracujURL = WebClient.create("https://www.pracuj.pl/praca/" + selectedCityASCII + ";wp/" + categoryName + ";cc," + category.getPracujId());
            category.setPracujOffers(getPracujOffers(pracujURL));
        });

        return categories.stream().map(category -> modelMapper.map(category, CategoryDto.class)).collect(Collectors.toList());
    }

    private int getLinkedinOffers(WebClient url) {

        Mono<ClientResponse> result = url.get()
                .exchange();

        String resultString = result.flatMap(res -> res.bodyToMono(String.class)).block();

        // Can be changed in future by site owner
        String htmlFirstTag = "Past Month <span class=\"filter-list__label-count\">(";
        String htmlLastTag = ")</span></label></li><li class=\"filter-list__list-item filter-button-dropdown__list-item\"><input type=\"radio\" name=\"f_TP\" value=\"\" id=\"TIME_POSTED-3\" checked>";

        int jobAmount = 0;
        if (resultString != null && resultString.contains(htmlFirstTag) && resultString.contains(htmlLastTag)) {
            jobAmount = Integer.valueOf(resultString.substring(resultString.indexOf(htmlFirstTag) + htmlFirstTag.length(), resultString.indexOf(htmlLastTag)).replaceAll(",", ""));
        }

        return jobAmount;
    }

    private int getPracujOffers(WebClient url) {

        Mono<ClientResponse> result = url.get()
                .accept(MediaType.TEXT_PLAIN)
                .exchange();

        String resultString = result.flatMap(res -> res.bodyToMono(String.class)).block();

        // Can be changed in future by site owner
        String htmlFirstTag = "<span class=\"results-header__offer-count-text-number\">";
        String htmlLastTag = "</span> ofert";

        int jobAmount = 0;
        if (resultString != null && resultString.contains(htmlFirstTag) && resultString.contains(htmlLastTag)) {
            jobAmount = Integer.valueOf(resultString.substring(resultString.indexOf(htmlFirstTag) + htmlFirstTag.length(), resultString.indexOf(htmlLastTag)));
        }

        return jobAmount;
    }

    private int getNoFluffJobsOffers(WebClient url) {

        NoFluffJobsList postings =  url
                .get()
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .retrieve()
                .bodyToMono(NoFluffJobsList.class)
                .block();

        return Objects.requireNonNull(postings).getPostings().size();
    }

    private List<JustJoin> getJustJoin() {

        WebClient justJoinURL = WebClient.create("https://justjoin.it/api/offers");

        return justJoinURL.get()
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .retrieve()
                .bodyToFlux(JustJoin.class)
                .collectList()
                .block();
    }

    private String removePolishSigns(String city){
        return Normalizer.normalize(city, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "")
                .replaceAll("ł", "l");
    }
}
