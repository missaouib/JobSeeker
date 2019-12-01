# install requests, beautifulsoup4

from bs4 import BeautifulSoup
import requests
import re

website_url = requests.get(
    'https://en.wikipedia.org/wiki/List_of_countries_and_dependencies_by_area,_population_and_population_density').text
soup = BeautifulSoup(website_url, 'html.parser')

countryTable = soup.find('table', {'class': 'sortable wikitable'})

countryNames = []
countryPopulation = []
countryArea = []
countryDensity = []

rows = countryTable.findAll('tr')
rows.pop()

for x in range(0, 4):
    rows.pop(0)

for row in rows:
    a = row.findAll('a')
    countryNames.append(a[0].get('title'))

    populationColumn = row.findAll('td')[4]
    countryPopulation.append(re.search('>(.*)\n', str(populationColumn)).group(1))

    areaColumn = row.findAll('td')[2]
    countryArea.append(re.search('>(.*)\n', str(areaColumn)).group(1))

    densityColumn = row.findAll('td')[5]
    countryDensity.append(re.search('>(.*)\n', str(densityColumn)).group(1))

for x in range(0, len(countryNames)):
    countryNames[x] = countryNames[x].encode('ascii', 'ignore').decode()
    countryPopulation[x] = countryPopulation[x].replace(',', '')
    countryArea[x] = countryArea[x].replace(',', '')
    countryDensity[x] = countryDensity[x].replace(',', '')
    if countryNames[x] == 'South Korea':
        countryNames[x] = 'Korea'
    if countryNames[x] == 'Republic of Ireland':
        countryNames[x] = 'Ireland'

blackListCountries = [
    'Sint Maarten', 'Saba', 'Curaao', 'Republic of Artsakh', 'Eswatini', 'North Macedonia',
    'Sahrawi Arab Democratic Republic', 'Saint Helena, Ascension and Tristan da Cunha',
    'Saint Pierre and Miquelon', 'Cocos (Keeling) Islands', 'Federated States of Micronesia',
    'Sao Tome and Principe', 'So Tom and Prncipe', 'Northern Cyprus', 'South Ossetia',
    'State of Palestine', 'Runion', 'Faroe Islands'
]

countryCodes = {
    'Argentina': 'ar', 'Australia': 'au', 'Austria': 'at', 'Bahrain': 'bh', 'Belgium': 'be',
    'Brazil': 'br', 'Canada': 'ca', 'Chile': 'cl', 'China': 'cn', 'Colombia': 'co',
    'Czech Republic': 'cz', 'Denmark': 'dk', 'Finland': 'fi', 'France': 'fr', 'Germany': 'de',
    'Greece': 'gr', 'Hong Kong': 'hk', 'Hungary': 'hu', 'India': 'in', 'Indonesia': 'id',
    'Ireland': 'ie', 'Israel': 'il', 'Italy': 'it', 'Japan': 'jp', 'Korea': 'kr',
    'Kuwait': 'kw', 'Luxembourg': 'lu', 'Malaysia': 'my', 'Mexico': 'mx', 'Netherlands': 'nl',
    'New Zealand': 'nz', 'Norway': 'no', 'Oman': 'om', 'Pakistan': 'pk', 'Peru': 'pe', 'Philippines': 'ph',
    'Poland': 'pl', 'Portugal': 'pt', 'Qatar': 'qa', 'Romania': 'ro', 'Russia': 'ru',
    'Saudi Arabia': 'sa', 'Singapore': 'sg', 'South Africa': 'za', 'Spain': 'es', 'Sweden': 'se',
    'Switzerland': 'ch', 'Taiwan': 'tw', 'Thailand': 'th', 'Turkey': 'tr', 'United Arab Emirates': 'ae',
    'United Kingdom': 'gb', 'United States': 'us', 'Venezuela': 've', 'Vietnam': 'vn'
}

with open('data.txt', 'w') as file:
    for x in range(0, len(countryNames)):
        if countryNames[x] not in blackListCountries:
            if countryNames[x] in countryCodes:
                file.write('INSERT INTO country(id, name, code, population, area, density) VALUES (\'' + str(
                    str(x + 1) + '\', \'' + countryNames[x]) + '\', \'' + countryCodes[countryNames[x]] + '\', ' + countryPopulation[x] + ', ' +
                           countryArea[x] + ', ' + countryDensity[x] + ') ON CONFLICT DO NOTHING;\n')
            else:
                file.write('INSERT INTO country(id, name, code, population, area, density) VALUES (\'' + str(
                    str(x + 1) + '\', \'' + countryNames[x]) + '\', ' + 'null, ' + countryPopulation[x] + ', ' + countryArea[x] + ', ' +
                           countryDensity[x] + ') ON CONFLICT DO NOTHING;\n')
