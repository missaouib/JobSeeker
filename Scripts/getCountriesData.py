# install requests
# install beautifulsoup4
# install lxml

from bs4 import BeautifulSoup
import requests
import re

website_url = requests.get('https://en.wikipedia.org/wiki/List_of_countries_and_dependencies_by_area,_population_and_population_density').text
soup = BeautifulSoup(website_url, 'lxml')

countryTable = soup.find('table', {'class': 'sortable wikitable'})

countryNames = []
countryArea = []
countryPopulation = []

rows = countryTable.findAll('tr')
rows.pop()

for x in range(0, 4):
    rows.pop(0)

for row in rows:
    a = row.findAll('a')
    countryNames.append(a[0].get('title'))

    areaColumn = row.findAll('td')[2]
    populationColumn = row.findAll('td')[4]

    countryArea.append(re.search('>(.*)\n', str(areaColumn)).group(1))
    countryPopulation.append(re.search('>(.*)\n', str(populationColumn)).group(1))

for x in range(0, len(countryNames)):
    countryArea[x] = countryArea[x].replace(',', '')
    countryPopulation[x] = countryPopulation[x].replace(',', '')
    countryNames[x] = countryNames[x].encode("ascii", "ignore").decode()

with open('data.txt', 'w') as file:
    for x in range(0, len(countryNames)):
        file.write('new Country("' + str(countryNames[x]) + '", ' + countryPopulation[x] + ', ' + countryArea[x] + '),\n')
