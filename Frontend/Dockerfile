FROM node:alpine as node
WORKDIR /usr/src/app
COPY package*.json ./
RUN npm install
COPY . .
RUN npm run build

# Stage 2
FROM nginx:alpine
COPY --from=node /usr/src/app/dist/Frontend /usr/share/nginx/html
COPY ./nginx.conf /etc/nginx/conf.d/default.conf
CMD sed -i -e 's/80/'"80"'/g' /etc/nginx/conf.d/default.conf && nginx -g 'daemon off;'
