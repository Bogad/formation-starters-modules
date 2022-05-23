FROM nginx:1.21.4

ENV NGINX_ENTRYPOINT_QUIET_LOGS=true

RUN chown -R 101:101 /var/cache/nginx \
&& chmod -R g+w /var/cache/nginx \
&& touch /var/run/nginx.pid \
&& chown -R 101:101 /var/run/nginx.pid \
&& ln -sf /dev/stdout /var/log/nginx/access.log \
&& ln -sf /dev/stderr /var/log/nginx/error.log


COPY .docker/default.conf /etc/nginx/conf.d/default.conf
COPY .docker/nginx.conf /etc/nginx/nginx.conf
COPY dist /usr/share/nginx/html/

USER 101

EXPOSE 8080

