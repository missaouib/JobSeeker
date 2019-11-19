#pip install requests
import requests
import datetime

#windows task scheduler working only when computer is on
# with open('output.txt', 'a') as file:
#     file.write('start ' + str(datetime.datetime.now()) + '\n')

#Requests
targetPage = 'http://localhost:8080'

cityList = ['All Cities', 'Warszawa', 'Kraków', 'Łódź', 'Wrocław', 'Poznań', 'Gdańsk', 'Szczecin', 'Bydgoszcz', 'Lublin', 'Białystok', 'Katowice', 'Rzeszów', 'Kielce', 'Toruń', 'Olsztyn', 'Gorzów Wielkopolski', 'Opole', 'Zielona Góra',];
technologyList = ['All Jobs', 'All IT Jobs', 'Java', 'Javascript', 'Typescript', '.NET', 'Python', 'PHP', 'C++', 'Ruby', 'Kotlin', 'Scala', 'Groovy', 'Swift', 'Objective-C', 'Visual Basic', 'Spring', 'Java EE', 'Android', 'Angular',
                  'React', 'Vue', 'Node', 'JQuery', 'Symfony', 'Laravel', 'iOS', 'Asp.net', 'Django', 'Unity', 'SQL', 'Linux', 'Git', 'Docker', 'Jenkins', 'Kubernetes', 'AWS', 'Azure', 'HTML', 'Maven', 'Gradle', 'Junit', 'Jira', 'Scrum'];

#build jar?

for city in cityList:
    requests.post(targetPage + '/technologyStatistics', json={'city': city})
    requests.post(targetPage + '/categoryStatistics', json={'city': city})

for technology in technologyList:
    requests.post(targetPage + '/itJobOffersInPoland', json={'technology': technology})
    requests.post(targetPage + '/itJobOffersInWorld', json={'technology': technology})

with open('output.txt', 'a') as file:
    file.write('end s' + str(datetime.datetime.now()) + '\n')
