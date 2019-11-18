# install requests
# install beautifulsoup4
# install lxml

from bs4 import BeautifulSoup
import requests
import re

website_url = requests.get('https://en.wikipedia.org/wiki/List_of_countries_and_dependencies_by_area,_population_and_population_density').text
soup = BeautifulSoup(website_url, l'lxml')

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
    countryNames[x] = countryNames[x].encode("ascii", "ignore").decode()
    countryPopulation[x] = countryPopulation[x].replace(',', '')
    countryArea[x] = countryArea[x].replace(',', '')
    countryDensity[x] = countryDensity[x].replace(',', '')

blackListCountries = ['Sint Maarten', 'Saba', 'Curaao', 'Republic of Artsakh', 'Eswatini', 'North Macedonia',
                      'Sahrawi Arab Democratic Republic', 'Saint Helena, Ascension and Tristan da Cunha', 'Saint Pierre and Miquelon',
                      'Cocos (Keeling) Islands', 'Federated States of Micronesia', 'Sao Tome and Principe',
                      'Northern Cyprus', 'South Ossetia', 'State of Palestine', 'Runion', 'Faroe Islands']

with open('data.txt', 'w') as file:
    for x in range(0, len(countryNames)):
        if countryNames[x] not in blackListCountries:
            file.write('INSERT INTO country(name, population, area, density) VALUES (\'' + str(countryNames[x]) + '\', ' + countryPopulation[x] + ', ' + countryArea[x] + ', ' + countryDensity[x] + ');\n')
